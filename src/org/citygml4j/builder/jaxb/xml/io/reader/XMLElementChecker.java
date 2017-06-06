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
package org.citygml4j.builder.jaxb.xml.io.reader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.xml.namespace.QName;

import org.citygml4j.model.citygml.CityGML;
import org.citygml4j.model.module.Module;
import org.citygml4j.model.module.Modules;
import org.citygml4j.model.module.citygml.CityGMLModule;
import org.citygml4j.model.module.gml.GMLCoreModule;
import org.citygml4j.model.module.xal.XALCoreModule;
import org.citygml4j.xml.io.reader.FeatureReadMode;
import org.citygml4j.xml.io.reader.MissingADESchemaException;
import org.citygml4j.xml.schema.ElementDecl;
import org.citygml4j.xml.schema.Schema;
import org.citygml4j.xml.schema.SchemaHandler;
import org.xml.sax.SAXException;

public class XMLElementChecker {
	private final SchemaHandler schemaHandler;
	private final FeatureReadMode featureReadMode;
	private final boolean keepInlineAppearance;
	private final boolean parseSchema;
	private final boolean failOnMissingADESchema;

	private HashMap<String, List<String>> excludes;
	private HashSet<String> cityGMLFeatureProperties;
	private HashMap<String, HashSet<String>> adeFeatureProperties;

	XMLElementChecker(SchemaHandler schemaHandler, 
			FeatureReadMode featureReadMode, 
			boolean keepInlineAppearance,
			boolean parseSchema,
			boolean failOnMissingADESchema,
			List<QName> excludes,
			List<QName> featureProperties) {
		this.schemaHandler = schemaHandler;
		this.featureReadMode = featureReadMode;
		this.keepInlineAppearance = keepInlineAppearance;
		this.parseSchema = parseSchema;
		this.failOnMissingADESchema = failOnMissingADESchema;

		initExcludes(prepareNameList(excludes));		
		if (featureReadMode == FeatureReadMode.SPLIT_PER_COLLECTION_MEMBER)
			initCollectionSplitProperties(prepareNameList(featureProperties));
	}
	
	public boolean isCityGMLElement(String namespaceURI) {
		return namespaceURI.startsWith("http://www.opengis.net/citygml");
	}

	public boolean isCityGMLElement(QName name) {
		return isCityGMLElement(name.getNamespaceURI());
	}

	public boolean isGMLElement(String namespaceURI) {
		return GMLCoreModule.v3_1_1.getNamespaceURI().equals(namespaceURI);
	}

	public boolean isGMLElement(QName name) {
		return isGMLElement(name.getNamespaceURI());
	}
	
	public boolean isXALElement(String namespaceURI) {
		return XALCoreModule.v2_0.getNamespaceURI().equals(namespaceURI);
	}
	
	public boolean isXALElement(QName name) {
		return isXALElement(name.getNamespaceURI());
	}

	public boolean isParentInfoElement(String namespaceURI, String localPart) {
		if (isGMLElement(namespaceURI)) {
			if (localPart.equals("metaDataProperty") ||
					localPart.equals("description") ||
					localPart.equals("name") ||
					localPart.equals("boundedBy") ||
					localPart.equals("location"))
				return true;					
		} else if (isCityGMLElement(namespaceURI)) {
			if (localPart.equals("appearance") ||
					localPart.equals("appearanceMember"))
				return true;
		}

		return false;
	}

	public boolean isParentInfoElement(QName name) {
		return isParentInfoElement(name.getNamespaceURI(), name.getLocalPart());
	}

	public ElementInfo getGMLFeatureProperty(String localName) {
		ElementInfo elementInfo = null;

		if (localName.equals("featureMember")) {
			elementInfo = new ElementInfo();
			elementInfo.isFeatureProperty = true;
			elementInfo.hasXLink = true;

		} else if (localName.equals("featureMembers")) {
			elementInfo = new ElementInfo();
			elementInfo.isFeatureProperty = true;
		}

		return elementInfo;
	}

	public ElementInfo getCityGMLFeatureProperty(QName name) {
		ElementInfo elementInfo = null;
		String localName = name.getLocalPart();
		String namespaceURI = name.getNamespaceURI();

		boolean isFeatureProperty = false;
		boolean skipNestedElements = false;

		if (featureReadMode == FeatureReadMode.SPLIT_PER_COLLECTION_MEMBER) { 
			if (cityGMLFeatureProperties.contains(localName))
				isFeatureProperty = true;
		} else {
			Module module = Modules.getModule(namespaceURI);
			if (module instanceof CityGMLModule) {
				CityGMLModule cityGMLModule = (CityGMLModule)module;
				isFeatureProperty = cityGMLModule.hasFeaturePropertyElement(localName);					
				if (localName.equals("appearance"))
					skipNestedElements = keepInlineAppearance;
			}
		}

		if (isFeatureProperty) {
			elementInfo = new ElementInfo();
			elementInfo.isFeatureProperty = true;
			elementInfo.hasXLink = true;
			elementInfo.skipNestedElements = skipNestedElements;
		}

		return elementInfo;
	}

	public ElementInfo getElementInfo(QName name) throws MissingADESchemaException {
		ElementInfo elementInfo = null;
		
		// we do not support GML features
		if (isCityGMLElement(name))
			elementInfo = getCityGMLFeature(name);
		else
			elementInfo = getADEElementInfo(name, null, true);
		
		return elementInfo;
	}
	
	public ElementInfo getElementInfo(QName name, ElementInfo lastElementInfo) throws MissingADESchemaException {
		if (lastElementInfo != null && lastElementInfo.skipNestedElements)
			return lastElementInfo;

		ElementInfo elementInfo = null;

		if (lastElementInfo != null && lastElementInfo.isFeatureProperty) {
			// we do not support GML features
			if (isCityGMLElement(name))
				elementInfo = getCityGMLFeature(name);
			else
				elementInfo = getADEElementInfo(name, lastElementInfo, true);
		}

		else {
			if (isGMLElement(name))
				elementInfo = getGMLFeatureProperty(name.getLocalPart());
			else if (isCityGMLElement(name))
				elementInfo = getCityGMLFeatureProperty(name);
			else if (!isXALElement(name) && checkADEFeatureProperty(name))
				elementInfo = getADEElementInfo(name, lastElementInfo, false);
		}

		return elementInfo;
	}

	private ElementInfo getCityGMLFeature(QName name) {
		ElementInfo elementInfo = null;
		String localName = name.getLocalPart();
		String namespaceURI = name.getNamespaceURI();

		Module module = Modules.getModule(namespaceURI);
		if (module instanceof CityGMLModule) {
			CityGMLModule cityGMLModule = (CityGMLModule)module;
			Class<? extends CityGML> cityGMLClass = cityGMLModule.getFeatureElementClass(localName);

			if (cityGMLClass != null) {
				elementInfo = new ElementInfo();
				elementInfo.isFeature = true;
				elementInfo.cityGMLClass = cityGMLClass;
				
				if (excludes != null) {
					List<String> localNames = excludes.get(namespaceURI);
					if (localNames != null && localNames.contains(localName))
						elementInfo.isFeature = false;
				}				
			}

		}

		return elementInfo;
	}

	private ElementInfo getADEElementInfo(QName name, ElementInfo lastElementInfo, boolean checkForFeature) throws MissingADESchemaException {
		ElementInfo elementInfo = null;
		String localName = name.getLocalPart();
		String namespaceURI = name.getNamespaceURI();

		Schema schema = schemaHandler.getSchema(namespaceURI);

		// try and resolve unknown ADE schema
		if (schema == null && parseSchema) {
			try {
				schemaHandler.resolveAndParseSchema(namespaceURI);
				schema = schemaHandler.getSchema(namespaceURI);
			} catch (SAXException e) {
				//
			} catch (MissingADESchemaException e) {
				if (failOnMissingADESchema)
					throw e;
			}
		}

		if (schema != null) {
			ElementDecl parent = lastElementInfo != null ? lastElementInfo.elementDecl : null;
			ElementDecl elementDecl = schema.getElementDecl(localName, parent);

			if (elementDecl != null) {
				elementInfo = new ElementInfo(elementDecl);

				if (checkForFeature && elementDecl.isGlobal() && elementDecl.isFeature()) {
					elementInfo.isFeature = true;

					if (excludes != null) { 
						List<String> localNames = excludes.get(namespaceURI);
						if (localNames != null && localNames.contains(localName))
							elementInfo.isFeature = false;
					}

				} else if (elementDecl.isFeatureProperty()) {
					elementInfo.isFeatureProperty = true;	
					elementInfo.hasXLink = elementDecl.hasXLinkAttribute();
				}
			}
		}

		return elementInfo;
	}

	private void initExcludes(List<QName> excludes) {
		if (!excludes.isEmpty()) {
			this.excludes = new HashMap<>();

			for (QName exclude : excludes) {
				String localName = exclude.getLocalPart();
				String namespaceURI = exclude.getNamespaceURI();

				if (namespaceURI.length() == 0)
					continue;				

				List<String> localNames = this.excludes.get(namespaceURI);
				if (localNames == null) {
					localNames = new ArrayList<>();
					this.excludes.put(namespaceURI, localNames);
				}

				localNames.add(localName);
			}
		}
	}

	private void initCollectionSplitProperties(List<QName> featureProperties) {
		cityGMLFeatureProperties = new HashSet<String>();

		cityGMLFeatureProperties.add("cityObjectMember");
		cityGMLFeatureProperties.add("appearanceMember");
		cityGMLFeatureProperties.add("groupMember");
		cityGMLFeatureProperties.add("parent");

		for (QName featureProperty : featureProperties) {
			String localName = featureProperty.getLocalPart();
			String namespaceURI = featureProperty.getNamespaceURI();

			if (namespaceURI.length() == 0)
				continue;

			else if (isCityGMLElement(namespaceURI)) {
				Module module = Modules.getModule(namespaceURI);
				if (module instanceof CityGMLModule && ((CityGMLModule)module).hasFeaturePropertyElement(localName))
					cityGMLFeatureProperties.add(localName);
			}

			else {
				if (adeFeatureProperties == null)
					adeFeatureProperties = new HashMap<String, HashSet<String>>();

				HashSet<String> propertySet = adeFeatureProperties.get(namespaceURI);
				if (propertySet == null) {
					propertySet = new HashSet<String>();
					adeFeatureProperties.put(namespaceURI, propertySet);
				}

				propertySet.add(localName);
			}

		}
	}

	private boolean checkADEFeatureProperty(QName name) {
		if (featureReadMode == FeatureReadMode.SPLIT_PER_FEATURE)
			return true;

		if (adeFeatureProperties != null) {
			HashSet<String> properties = adeFeatureProperties.get(name.getNamespaceURI());
			if (properties != null)
				return properties.contains(name.getLocalPart());
		}

		return false;
	}
	
	private List<QName> prepareNameList(List<QName> nameList) {
		List<QName> result = new ArrayList<>();
		
		for (QName name : nameList) {
			if (name.getNamespaceURI().length() != 0)
				result.add(name);
			else {
				for (CityGMLModule module : Modules.getCityGMLModules()) {
					if (module.hasFeatureElement(name.getLocalPart()))
						result.add(new QName(module.getNamespaceURI(), name.getLocalPart()));
				}
			}
		}
		
		return result;
	}

	static class ElementInfo {
		private final ElementDecl elementDecl;

		private boolean isFeature = false;
		private boolean isFeatureProperty = false;
		private boolean hasXLink = false;
		private boolean skipNestedElements = false;
		private Class<? extends CityGML> cityGMLClass;

		ElementInfo() {
			elementDecl = null;
		}

		ElementInfo(ElementDecl decl) {
			this.elementDecl = decl;
		}

		boolean isFeature() {
			return isFeature;
		}

		boolean hasXLink() {
			return hasXLink;
		}
		
		Class<? extends CityGML> getCityGMLClass() {
			return cityGMLClass;
		}

	}

}
