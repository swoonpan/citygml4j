package org.citygml4j.builder.json.objects;

import com.google.gson.annotations.JsonAdapter;

public class WaterBodyType extends AbstractCityObjectType {
	private final CityObjectTypeName type = CityObjectTypeName.WATER_BODY;
	@JsonAdapter(DefaultAttributesAdapter.class)
	private DefaultAttributes attributes;

	@Override
	public CityObjectTypeName getType() {
		return type;
	}

	@Override
	public boolean isSetAttributes() {
		return attributes != null;
	}

	@Override
	public DefaultAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(DefaultAttributes attributes) {
		this.attributes = attributes;
	}

	@Override
	public boolean isValidGeometryType(GeometryTypeName type) {
		return type == GeometryTypeName.MULTI_LINE_STRING
				|| type == GeometryTypeName.MULTI_SURFACE
				|| type == GeometryTypeName.COMPOSITE_SURFACE
				|| type == GeometryTypeName.SOLID;
	}

}
