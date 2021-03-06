//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.11.04 at 09:10:42 PM EDT 
//


package edu.stevens.cs548.clinic.service.web.rest.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import edu.stevens.cs548.clinic.service.web.rest.data.dap.LinkType;


/**
 * <p>Java class for TreatmentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TreatmentType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://cs548.stevens.edu/clinic/service/web/rest/data/dap}LinkType"/&gt;
 *         &lt;element name="patient" type="{http://cs548.stevens.edu/clinic/service/web/rest/data/dap}LinkType"/&gt;
 *         &lt;element name="provider" type="{http://cs548.stevens.edu/clinic/service/web/rest/data/dap}LinkType"/&gt;
 *         &lt;element name="diagnosis" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;choice&gt;
 *           &lt;element name="drugTreatment" type="{http://cs548.stevens.edu/clinic/service/web/rest/data}DrugTreatmentType"/&gt;
 *           &lt;element name="surgeryTreatment" type="{http://cs548.stevens.edu/clinic/service/web/rest/data}SurgeryTreatmentType"/&gt;
 *           &lt;element name="radiologyTreatment" type="{http://cs548.stevens.edu/clinic/service/web/rest/data}RadiologyTreatmentType"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TreatmentType", propOrder = {
    "id",
    "patient",
    "provider",
    "diagnosis",
    "drugTreatment",
    "surgeryTreatment",
    "radiologyTreatment"
})
public class TreatmentType {

    @XmlElement(required = true)
    protected LinkType id;
    @XmlElement(required = true)
    protected LinkType patient;
    @XmlElement(required = true)
    protected LinkType provider;
    @XmlElement(required = true)
    protected String diagnosis;
    protected DrugTreatmentType drugTreatment;
    protected SurgeryTreatmentType surgeryTreatment;
    protected RadiologyTreatmentType radiologyTreatment;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link LinkType }
     *     
     */
    public LinkType getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link LinkType }
     *     
     */
    public void setId(LinkType value) {
        this.id = value;
    }

    /**
     * Gets the value of the patient property.
     * 
     * @return
     *     possible object is
     *     {@link LinkType }
     *     
     */
    public LinkType getPatient() {
        return patient;
    }

    /**
     * Sets the value of the patient property.
     * 
     * @param value
     *     allowed object is
     *     {@link LinkType }
     *     
     */
    public void setPatient(LinkType value) {
        this.patient = value;
    }

    /**
     * Gets the value of the provider property.
     * 
     * @return
     *     possible object is
     *     {@link LinkType }
     *     
     */
    public LinkType getProvider() {
        return provider;
    }

    /**
     * Sets the value of the provider property.
     * 
     * @param value
     *     allowed object is
     *     {@link LinkType }
     *     
     */
    public void setProvider(LinkType value) {
        this.provider = value;
    }

    /**
     * Gets the value of the diagnosis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiagnosis() {
        return diagnosis;
    }

    /**
     * Sets the value of the diagnosis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiagnosis(String value) {
        this.diagnosis = value;
    }

    /**
     * Gets the value of the drugTreatment property.
     * 
     * @return
     *     possible object is
     *     {@link DrugTreatmentType }
     *     
     */
    public DrugTreatmentType getDrugTreatment() {
        return drugTreatment;
    }

    /**
     * Sets the value of the drugTreatment property.
     * 
     * @param value
     *     allowed object is
     *     {@link DrugTreatmentType }
     *     
     */
    public void setDrugTreatment(DrugTreatmentType value) {
        this.drugTreatment = value;
    }

    /**
     * Gets the value of the surgeryTreatment property.
     * 
     * @return
     *     possible object is
     *     {@link SurgeryTreatmentType }
     *     
     */
    public SurgeryTreatmentType getSurgeryTreatment() {
        return surgeryTreatment;
    }

    /**
     * Sets the value of the surgeryTreatment property.
     * 
     * @param value
     *     allowed object is
     *     {@link SurgeryTreatmentType }
     *     
     */
    public void setSurgeryTreatment(SurgeryTreatmentType value) {
        this.surgeryTreatment = value;
    }

    /**
     * Gets the value of the radiologyTreatment property.
     * 
     * @return
     *     possible object is
     *     {@link RadiologyTreatmentType }
     *     
     */
    public RadiologyTreatmentType getRadiologyTreatment() {
        return radiologyTreatment;
    }

    /**
     * Sets the value of the radiologyTreatment property.
     * 
     * @param value
     *     allowed object is
     *     {@link RadiologyTreatmentType }
     *     
     */
    public void setRadiologyTreatment(RadiologyTreatmentType value) {
        this.radiologyTreatment = value;
    }

}
