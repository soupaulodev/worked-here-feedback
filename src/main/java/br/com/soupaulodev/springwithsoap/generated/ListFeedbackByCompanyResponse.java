//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.2 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2025.02.10 at 10:07:46 PM BRT 
//


package br.com.soupaulodev.springwithsoap.generated;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="operationStatus" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="feedbacks" type="{https://soupaulodev.com.br/feedback}feedbackList" minOccurs="0"/&gt;
 *         &lt;element name="fault" type="{https://soupaulodev.com.br/fault}FaultType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "operationStatus",
    "feedbacks",
    "fault"
})
@XmlRootElement(name = "ListFeedbackByCompanyResponse")
public class ListFeedbackByCompanyResponse {

    @XmlElement(required = true)
    protected String operationStatus;
    protected FeedbackList feedbacks;
    protected FaultType fault;

    /**
     * Gets the value of the operationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationStatus() {
        return operationStatus;
    }

    /**
     * Sets the value of the operationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationStatus(String value) {
        this.operationStatus = value;
    }

    /**
     * Gets the value of the feedbacks property.
     * 
     * @return
     *     possible object is
     *     {@link FeedbackList }
     *     
     */
    public FeedbackList getFeedbacks() {
        return feedbacks;
    }

    /**
     * Sets the value of the feedbacks property.
     * 
     * @param value
     *     allowed object is
     *     {@link FeedbackList }
     *     
     */
    public void setFeedbacks(FeedbackList value) {
        this.feedbacks = value;
    }

    /**
     * Gets the value of the fault property.
     * 
     * @return
     *     possible object is
     *     {@link FaultType }
     *     
     */
    public FaultType getFault() {
        return fault;
    }

    /**
     * Sets the value of the fault property.
     * 
     * @param value
     *     allowed object is
     *     {@link FaultType }
     *     
     */
    public void setFault(FaultType value) {
        this.fault = value;
    }

}
