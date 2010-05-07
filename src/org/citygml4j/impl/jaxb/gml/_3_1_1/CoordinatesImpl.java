package org.citygml4j.impl.jaxb.gml._3_1_1;

import java.util.ArrayList;
import java.util.List;

import org.citygml4j.jaxb.gml._3_1_1.CoordinatesType;
import org.citygml4j.model.gml.Coordinates;
import org.citygml4j.model.gml.GMLClass;

public class CoordinatesImpl extends GMLBaseImpl implements Coordinates {
	private CoordinatesType coordinatesType;

	public CoordinatesImpl() {
		coordinatesType = new CoordinatesType();
	}

	public CoordinatesImpl(CoordinatesType coordinatesType) {
		this.coordinatesType = coordinatesType;
	}

	@Override
	public CoordinatesType getJAXBObject() {
		return coordinatesType;
	}

	public String getCs() {
		return coordinatesType.getCs();
	}

	public String getDecimal() {
		return coordinatesType.getDecimal();
	}

	public String getTs() {
		return coordinatesType.getTs();
	}

	public String getValue() {
		return coordinatesType.getValue();
	}

	@Override
	public GMLClass getGMLClass() {
		return GMLClass.COORDINATES;
	}

	public boolean isSetCs() {
		return coordinatesType.isSetCs();
	}

	public boolean isSetDecimal() {
		return coordinatesType.isSetDecimal();
	}

	public boolean isSetTs() {
		return coordinatesType.isSetTs();
	}

	public boolean isSetValue() {
		return coordinatesType.isSetValue();
	}

	public void setCs(String cs) {
		coordinatesType.setCs(cs);
	}

	public void setDecimal(String decimal) {
		coordinatesType.setDecimal(decimal);
	}

	public void setTs(String ts) {
		coordinatesType.setTs(ts);
	}

	public void setValue(String value) {
		coordinatesType.setValue(value);
	}

	public void unsetCs() {
		coordinatesType.setCs(null);
	}

	public void unsetDecimal() {
		coordinatesType.setDecimal(null);
	}

	public void unsetTs() {
		coordinatesType.setTs(null);
	}

	public void unsetValue() {
		coordinatesType.setValue(null);
	}

	public List<Double> toList() {
		List<Double> tmp = new ArrayList<Double>();
		boolean isValid = false;

		if (isSetValue()) {
			String coordinates = getValue();
			String decimal = getDecimal();
			String cs = getCs();
			String ts = getTs();

			if (decimal == null || decimal.length() == 0)
				decimal = ".";

			if (cs == null || cs.length() == 0)
				cs = ",";

			if (ts == null || ts.length() == 0)
				ts = " ";

			isValid = true;
			decimal = prepareRegex(decimal);

			String[] tupels = coordinates.split(prepareRegex(ts));
			for (int i = 0; i < tupels.length && isValid ; ++i) {
				if (tupels[i] != null && tupels[i].trim().length() != 0) {				
					String[] coords = tupels[i].split(prepareRegex(cs));
					List<Double> point = new ArrayList<Double>();

					for (int j = 0; j < coords.length && isValid; ++j) {
						if (coords[j] != null && coords[j].trim().length() != 0) {
							coords[j] = coords[j].replaceAll(decimal, ".");

							try {
								double result = Double.parseDouble(coords[j]);
								point.add(result);
							} catch (NumberFormatException e) {
								isValid = false;
								break;
							}
						} else
							isValid = false;
					}

					if (isValid) {
						while (point.size() < 3)
							point.add(0.0);

						tmp.addAll(point.subList(0, 3));
					}
				}
			}
		}

		if (isValid && tmp.size() != 0)
			return tmp;

		return null;
	}

	public List<Double> toList(boolean reverseOrder) {
		List<Double> points = toList();

		if (reverseOrder && points != null) {
			List<Double> reversed = new ArrayList<Double>();

			for (int i = points.size() - 3; i >= 0; i -=3)
				reversed.addAll(points.subList(i, i + 3));

			points = reversed;
		}

		return points;
	}

	private String prepareRegex(String input) {
		StringBuilder buffer = new StringBuilder();

		for (char c : input.toCharArray()) {
			if ("+()^$.{}[]|\\".indexOf(c) != -1)
				buffer.append('\\').append(c);
			else
				buffer.append(c);
		}

		return buffer.toString();
	}
}
