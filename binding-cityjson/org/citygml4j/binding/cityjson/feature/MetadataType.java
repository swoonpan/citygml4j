package org.citygml4j.binding.cityjson.feature;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MetadataType {
	private CRSType crs;
	private List<Double> bbox;
	private List<String> keywords;
	private String datasetTitle;
	private Date datasetReferenceDate;
	private String geographicLocation;
	private String datasetLanguage;
	private String datasetTopicCategory;
	private String datasetAbstract;
	private Date metadataDateStamp;
	private String pointOfContact;
	private String copyright;
	private List<Number> presentLoDs;
	
	public boolean isSetCRS() {
		return crs != null;
	}
	
	public CRSType getCRS() {
		return crs;
	}

	public void setCRS(CRSType crs) {
		this.crs = crs;
	}

	public boolean isSetBBox() {
		return bbox != null && bbox.size() >= 6;
	}
	
	public List<Double> getBBox() {
		return isSetBBox() ? bbox.subList(0, 6) : null;
	}

	public void setBBox(List<Double> bbox) {
		if (bbox == null)
			this.bbox = null;
		else if (bbox.size() >= 6)
			this.bbox = bbox.subList(0, 6);
	}
	
	public boolean isSetKeywords() {
		return keywords != null;
	}

	public List<String> getKeywords() {
		return keywords;
	}
	
	public void addKeyWord(String keyword) {
		if (keywords == null)
			keywords = new ArrayList<>();
		
		keywords.add(keyword);
	}

	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}

	public boolean isSetDatasetTitle() {
		return datasetTitle != null;
	}
	
	public String getDatasetTitle() {
		return datasetTitle;
	}

	public void setDatasetTitle(String datasetTitle) {
		this.datasetTitle = datasetTitle;
	}

	public boolean isSetDatasetReferenceDate() {
		return datasetReferenceDate != null;
	}
	
	public Date getDatasetReferenceDate() {
		return datasetReferenceDate;
	}

	public void setDatasetReferenceDate(Date datasetReferenceDate) {
		this.datasetReferenceDate = datasetReferenceDate;
	}

	public boolean isSetGeographicLocation() {
		return geographicLocation != null;
	}
	
	public String getGeographicLocation() {
		return geographicLocation;
	}

	public void setGeographicLocation(String geographicLocation) {
		this.geographicLocation = geographicLocation;
	}

	public boolean isSetDatasetLanguage() {
		return datasetLanguage != null;
	}
	
	public String getDatasetLanguage() {
		return datasetLanguage;
	}

	public void setDatasetLanguage(String datasetLanguage) {
		this.datasetLanguage = datasetLanguage;
	}

	public boolean isSetDatasetTopicCategory() {
		return datasetTopicCategory != null;
	}
	
	public String getDatasetTopicCategory() {
		return datasetTopicCategory;
	}

	public void setDatasetTopicCategory(String datasetTopicCategory) {
		this.datasetTopicCategory = datasetTopicCategory;
	}

	public boolean isSetDatasetAbstract() {
		return datasetAbstract != null;
	}
	
	public String getDatasetAbstract() {
		return datasetAbstract;
	}

	public void setDatasetAbstract(String datasetAbstract) {
		this.datasetAbstract = datasetAbstract;
	}

	public boolean isSetMetadataDateStamp() {
		return metadataDateStamp != null;
	}
	
	public Date getMetadataDateStamp() {
		return metadataDateStamp;
	}

	public void setMetadataDateStamp(Date metadataDateStamp) {
		this.metadataDateStamp = metadataDateStamp;
	}

	public boolean isSetPointOfContact() {
		return pointOfContact != null;
	}
	
	public String getPointOfContact() {
		return pointOfContact;
	}
	
	public void setPointOfContact(String pointOfContact) {
		this.pointOfContact = pointOfContact;
	}

	public boolean isSetCopyright() {
		return copyright != null;
	}
	
	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public boolean isSetPresentLoDs() {
		return presentLoDs != null;
	}
	
	public List<Number> getPresentLoDs() {
		return presentLoDs;
	}

	public void setPresentLoDs(List<Number> presentLoDs) {
		if (presentLoDs != null) {
			presentLoDs = new ArrayList<>(presentLoDs);
			presentLoDs.removeIf(lod -> lod.doubleValue() < 0.0 || lod.doubleValue() >= 4.0);
		}
		
		this.presentLoDs = presentLoDs;
	}
	
}