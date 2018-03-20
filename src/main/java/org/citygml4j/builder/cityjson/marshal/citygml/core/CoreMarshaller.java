/*
 * citygml4j - The Open Source Java API for CityGML
 * https://github.com/citygml4j
 * 
 * Copyright 2013-2017 Claus Nagel <claus.nagel@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *     
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.citygml4j.builder.cityjson.marshal.citygml.core;

import org.citygml4j.binding.cityjson.feature.AbstractCityObjectType;
import org.citygml4j.binding.cityjson.feature.AddressType;
import org.citygml4j.binding.cityjson.feature.Attributes;
import org.citygml4j.binding.cityjson.feature.CityObjectGroupType;
import org.citygml4j.binding.cityjson.geometry.AbstractGeometryObjectType;
import org.citygml4j.binding.cityjson.geometry.GeometryTypeName;
import org.citygml4j.binding.cityjson.geometry.MultiPointType;
import org.citygml4j.builder.cityjson.marshal.CityJSONMarshaller;
import org.citygml4j.builder.cityjson.marshal.citygml.CityGMLMarshaller;
import org.citygml4j.builder.cityjson.marshal.util.AffineTransform;
import org.citygml4j.geometry.Matrix;
import org.citygml4j.model.citygml.core.AbstractCityObject;
import org.citygml4j.model.citygml.core.Address;
import org.citygml4j.model.citygml.core.CityModel;
import org.citygml4j.model.citygml.core.CityObjectMember;
import org.citygml4j.model.citygml.core.ImplicitGeometry;
import org.citygml4j.model.citygml.core.ImplicitRepresentationProperty;
import org.citygml4j.model.common.base.ModelObject;
import org.citygml4j.model.gml.feature.AbstractFeature;
import org.citygml4j.model.gml.feature.FeatureArrayProperty;
import org.citygml4j.model.gml.feature.FeatureProperty;
import org.citygml4j.model.gml.geometry.AbstractGeometry;
import org.citygml4j.model.xal.CountryName;
import org.citygml4j.model.xal.LocalityName;
import org.citygml4j.model.xal.PostalCodeNumber;
import org.citygml4j.model.xal.ThoroughfareName;
import org.citygml4j.model.xal.ThoroughfareNumber;
import org.citygml4j.util.walker.XALWalker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CoreMarshaller {
	private final CityJSONMarshaller json;
	private final CityGMLMarshaller citygml;

	public CoreMarshaller(CityGMLMarshaller citygml) {
		this.citygml = citygml;
		json = citygml.getCityJSONMarshaller();
	}

	public List<AbstractCityObjectType> marshal(ModelObject src) {
		if (src instanceof CityModel)
			return marshalCityModel((CityModel)src);
		
		return Collections.emptyList();	
	}

	public void marshalAbstractCityObject(AbstractCityObject src, AbstractCityObjectType dest, Attributes attributes) {
		if (src.isSetBoundedBy() && src.getBoundedBy().isSetEnvelope())
			dest.setBBox(src.getBoundedBy().getEnvelope().toBoundingBox().toList());

		if (src.isSetCreationDate())
			attributes.setCreationDate(src.getCreationDate().getTime());

		if (src.isSetTerminationDate())
			attributes.setTerminationDate(src.getTerminationDate().getTime());

		if (src.isSetGenericAttribute())
			citygml.getGenericsMarshaller().marshalGenericAttributes(src.getGenericAttribute(), attributes);
	}

	private void marshalCityModel(CityModel src, List<AbstractCityObjectType> dest) {
		if (src.isSetCityObjectMember()) {
			for (CityObjectMember property : src.getCityObjectMember()) {
				if (property.isSetCityObject())
					dest.addAll(citygml.marshal(property.getCityObject()));
			}
		}

		if (src.isSetFeatureMember()) {
			for (FeatureProperty<?> property : src.getFeatureMember()) {
				if (property.getFeature() instanceof CityModel)
					marshalCityModel((CityModel) property.getFeature(), dest);
				else if (property.isSetFeature())
					dest.addAll(citygml.marshal(property.getFeature()));
			}
		}

		if (src.isSetFeatureMembers()) {
			FeatureArrayProperty property = src.getFeatureMembers();
			for (AbstractFeature feature : property.getFeature()) {
				if (feature instanceof CityModel)
					marshalCityModel((CityModel) feature, dest);
				else
					dest.addAll(citygml.marshal(feature));
			}
		}
	}

	public List<AbstractCityObjectType> marshalCityModel(CityModel src) {
		List<AbstractCityObjectType> dest = new ArrayList<>();
		marshalCityModel(src, dest);

		// postprocess group members
		if (dest.stream().anyMatch(CityObjectGroupType.class::isInstance))
			citygml.getCityObjectGroupMarshaller().postprocessGroupMembers(src, dest);

		return dest;
	}

	public AbstractGeometryObjectType marshalImplicitGeometry(ImplicitGeometry src) {
		AffineTransform transformer = null;

		// get transformation matrix
		Matrix matrix = src.isSetTransformationMatrix() ? src.getTransformationMatrix().getMatrix().copy() : null;

		// add translation
		if (matrix != null && src.isSetReferencePoint() && src.getReferencePoint().isSetPoint()) {
			List<Double> point = src.getReferencePoint().getPoint().toList3d();
			matrix.set(0, 3, matrix.get(0, 3) + point.get(0));
			matrix.set(1, 3, matrix.get(1, 3) + point.get(1));
			matrix.set(2, 3, matrix.get(2, 3) + point.get(2));
		}

		// create affine transform if required
		if (matrix != null)
			transformer = new AffineTransform(matrix);

		return json.getGMLMarshaller().marshalGeometryProperty(src.getRelativeGMLGeometry(), transformer);
	}

	public AbstractGeometryObjectType marshalImplicitRepresentationProperty(ImplicitRepresentationProperty src) {
		Object dest = null;
		if (src.isSetImplicitGeometry())
			dest = marshalImplicitGeometry(src.getImplicitGeometry());
		else if (src.hasLocalProperty(CityJSONMarshaller.GEOMETRY_XLINK))
			dest = marshal((AbstractGeometry)src.getLocalProperty(CityJSONMarshaller.GEOMETRY_XLINK));

		return dest instanceof AbstractGeometryObjectType ? (AbstractGeometryObjectType)dest : null;
	}
	
	public void marshalAddress(Address src, AddressType dest) {
		if (src.isSetXalAddress() && src.getXalAddress().isSetAddressDetails()) {
			src.getXalAddress().getAddressDetails().accept(new XALWalker() {
				
				@Override
				public void visit(CountryName countryName) {
					if (!dest.isSetCountryName())
						dest.setCountryName(countryName.getContent());

					super.visit(countryName);
				}

				@Override
				public void visit(LocalityName localityName) {
					if (!dest.isSetLocalityName())
						dest.setLocalityName(localityName.getContent());

					super.visit(localityName);
				}

				@Override
				public void visit(PostalCodeNumber postalCodeNumber) {
					if (!dest.isSetPostalCode())
						dest.setPostalCode(postalCodeNumber.getContent());

					super.visit(postalCodeNumber);
				}

				@Override
				public void visit(ThoroughfareName thoroughfareName) {
					if (!dest.isSetThoroughfareName())
						dest.setThoroughfareName(thoroughfareName.getContent());

					super.visit(thoroughfareName);
				}

				@Override
				public void visit(ThoroughfareNumber thoroughfareNumber) {
					if (!dest.isSetThoroughfareNumber())
						dest.setThoroughfareNumber(thoroughfareNumber.getContent());

					super.visit(thoroughfareNumber);
				}
			});
		}
		
		if (src.isSetMultiPoint()) {
			AbstractGeometryObjectType geometry = json.getGMLMarshaller().marshalGeometryProperty(src.getMultiPoint());
			if (geometry != null && geometry.getType() == GeometryTypeName.MULTI_POINT)
				dest.setLocation((MultiPointType)geometry);
		}
	}
	
	public AddressType marshalAddress(Address src) {
		AddressType dest = new AddressType();
		marshalAddress(src, dest);
		
		return dest;
	}

}
