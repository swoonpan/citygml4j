//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2018.02.22 um 01:15:44 PM CET 
//


package net.opengis.citygml.building._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für GroundSurfaceType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="GroundSurfaceType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.opengis.net/citygml/building/1.0}AbstractBoundarySurfaceType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.opengis.net/citygml/building/1.0}_GenericApplicationPropertyOfGroundSurface" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroundSurfaceType", propOrder = {
    "_GenericApplicationPropertyOfGroundSurface"
})
public class GroundSurfaceType
    extends AbstractBoundarySurfaceType
{

    @XmlElementRef(name = "_GenericApplicationPropertyOfGroundSurface", namespace = "http://www.opengis.net/citygml/building/1.0", type = JAXBElement.class, required = false)
    protected List<JAXBElement<Object>> _GenericApplicationPropertyOfGroundSurface;

    /**
     * Gets the value of the genericApplicationPropertyOfGroundSurface property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the genericApplicationPropertyOfGroundSurface property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    get_GenericApplicationPropertyOfGroundSurface().add(newItem);
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
    public List<JAXBElement<Object>> get_GenericApplicationPropertyOfGroundSurface() {
        if (_GenericApplicationPropertyOfGroundSurface == null) {
            _GenericApplicationPropertyOfGroundSurface = new ArrayList<JAXBElement<Object>>();
        }
        return this._GenericApplicationPropertyOfGroundSurface;
    }

    public boolean isSet_GenericApplicationPropertyOfGroundSurface() {
        return ((this._GenericApplicationPropertyOfGroundSurface!= null)&&(!this._GenericApplicationPropertyOfGroundSurface.isEmpty()));
    }

    public void unset_GenericApplicationPropertyOfGroundSurface() {
        this._GenericApplicationPropertyOfGroundSurface = null;
    }

    public void set_GenericApplicationPropertyOfGroundSurface(List<JAXBElement<Object>> value) {
        this._GenericApplicationPropertyOfGroundSurface = value;
    }

}
