//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2018.02.22 um 01:15:44 PM CET 
//


package net.opengis.gml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * A coordinate reference system based on an ellipsoidal approximation of the geoid; this provides an accurate representation of the geometry of geographic features for a large portion of the earth's surface.
 * 
 * <p>Java-Klasse für GeographicCRSType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="GeographicCRSType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/gml}AbstractReferenceSystemType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/gml}usesEllipsoidalCS"/>
 *         &lt;element ref="{http://www.opengis.net/gml}usesGeodeticDatum"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeographicCRSType", propOrder = {
    "usesEllipsoidalCS",
    "usesGeodeticDatum"
})
public class GeographicCRSType
    extends AbstractReferenceSystemType
{

    @XmlElement(required = true)
    protected EllipsoidalCSRefType usesEllipsoidalCS;
    @XmlElement(required = true)
    protected GeodeticDatumRefType usesGeodeticDatum;

    /**
     * Ruft den Wert der usesEllipsoidalCS-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link EllipsoidalCSRefType }
     *     
     */
    public EllipsoidalCSRefType getUsesEllipsoidalCS() {
        return usesEllipsoidalCS;
    }

    /**
     * Legt den Wert der usesEllipsoidalCS-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link EllipsoidalCSRefType }
     *     
     */
    public void setUsesEllipsoidalCS(EllipsoidalCSRefType value) {
        this.usesEllipsoidalCS = value;
    }

    public boolean isSetUsesEllipsoidalCS() {
        return (this.usesEllipsoidalCS!= null);
    }

    /**
     * Ruft den Wert der usesGeodeticDatum-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link GeodeticDatumRefType }
     *     
     */
    public GeodeticDatumRefType getUsesGeodeticDatum() {
        return usesGeodeticDatum;
    }

    /**
     * Legt den Wert der usesGeodeticDatum-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link GeodeticDatumRefType }
     *     
     */
    public void setUsesGeodeticDatum(GeodeticDatumRefType value) {
        this.usesGeodeticDatum = value;
    }

    public boolean isSetUsesGeodeticDatum() {
        return (this.usesGeodeticDatum!= null);
    }

}
