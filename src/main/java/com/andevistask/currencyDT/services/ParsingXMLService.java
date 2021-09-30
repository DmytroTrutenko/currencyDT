package com.andevistask.currencyDT.services;


import com.andevistask.currencyDT.models.Currency;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
public class ParsingXMLService {

    //getting logs, debug
    private static final Logger log = LoggerFactory.getLogger(ParsingXMLService.class);

    public static DataFromXML parsingXML() {
        try {
            String URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

            // Get a factory to get the document builder afterwards.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Received from the factory a builder that parses XML, creates a Document structure
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Parsed the XML by creating a Document structure.
            Document document = builder.parse(URL);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("Cube");
            LocalDate date = null;

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    if (elem.hasAttribute("time")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        date = LocalDate.parse((elem.getAttribute("time")), formatter);
                    }
                }
            }
            // Create an empty list of currencies
            List<Currency> currencies = new ArrayList<>();

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;

                    if (elem.hasAttribute("currency") && elem.hasAttribute("rate")) {
                        String c = elem.getAttribute("currency");
                        double r = Double.parseDouble(elem.getAttribute("rate"));
                        r = Math.round(r * 1000.0) / 1000.0;
                        Currency currency = new Currency(c, r, date);
                        currencies.add(currency);
                    }
                }
            }
            return new DataFromXML(currencies);

        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public static LocalDate getActualDate() {
        try {
            String URL = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";

            // Get a factory to get the document builder afterwards.
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Received from the factory a builder that parses XML, creates a Document structure
            DocumentBuilder builder = factory.newDocumentBuilder();
            // Parsed the XML by creating a Document structure.
            Document document = builder.parse(URL);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("Cube");

            LocalDate date = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;

                    if (elem.hasAttribute("time")) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        date = LocalDate.parse((elem.getAttribute("time")), formatter);
                        return date;
                    }
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return null;
    }
}
