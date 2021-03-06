//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2018.02.22 um 01:15:44 PM CET 
//


package net.opengis.citygml.transportation._2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * Type describing the class for railways. As subclass of _CityObject, a Railway inherits all attributes and
 * 				relations, in particular an id, names, external references, and generalization relations. 
 * 
 * <p>Java-Klasse für RailwayType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="RailwayType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/citygml/transportation/2.0}TransportationComplexType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/citygml/transportation/2.0}_GenericApplicationPropertyOfRailway" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RailwayType", propOrder = {
    "_GenericApplicationPropertyOfRailway"
})
public class RailwayType
    extends TransportationComplexType
{

    @XmlElementRef(name = "_GenericApplicationPropertyOfRailway", namespace = "http://www.opengis.net/citygml/transportation/2.0", type = JAXBElement.class, required = false)
    protected List<JAXBElement<Object>> _GenericApplicationPropertyOfRailway;

    /**
     * Gets the value of the genericApplicationPropertyOfRailway property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the genericApplicationPropertyOfRailway property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    get_GenericApplicationPropertyOfRailway().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link Object }{@code >}
     * {@link JAXBElement }{@code <}{@link Object }{@code >}
     * 
     * 
     */
    public List<JAXBElement<Object>> get_GenericApplicationPropertyOfRailway() {
        if (_GenericApplicationPropertyOfRailway == null) {
            _GenericApplicationPropertyOfRailway = new ArrayList<JAXBElement<Object>>();
        }
        return this._GenericApplicationPropertyOfRailway;
    }

    public boolean isSet_GenericApplicationPropertyOfRailway() {
        return ((this._GenericApplicationPropertyOfRailway!= null)&&(!this._GenericApplicationPropertyOfRailway.isEmpty()));
    }

    public void unset_GenericApplicationPropertyOfRailway() {
        this._GenericApplicationPropertyOfRailway = null;
    }

    public void set_GenericApplicationPropertyOfRailway(List<JAXBElement<Object>> value) {
        this._GenericApplicationPropertyOfRailway = value;
    }

}
