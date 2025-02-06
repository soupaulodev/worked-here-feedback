package br.com.soupaulodev.springwithsoap.util;

import com.ctc.wstx.shaded.msv_core.datatype.xsd.DateType;
import com.ctc.wstx.shaded.msv_core.datatype.xsd.datetime.DateTimeFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;

public class DateTimeUtil {

    public static XMLGregorianCalendar toXMLGregorianCalendar(LocalDateTime localDateTime) {
        try {
            GregorianCalendar gregorianCalendar = GregorianCalendar.from(localDateTime.atZone(ZoneId.systemDefault()));
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException("Error while convert LocalDateTime to XMLGregorianCalendar", e);
        }
    }
}
