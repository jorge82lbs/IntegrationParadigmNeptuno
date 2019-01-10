package com.televisa.comer.integration.test;

import java.io.*;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.parsers.ParserConfigurationException;

import monfox.jdom.JDOMException;
import monfox.jdom.input.SAXBuilder;

import org.w3c.dom.Document;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class TestMethods {
    public TestMethods() {
        super();
    }

    public static void main(String[] args) {
        TestMethods testMethods = new TestMethods();
        testMethods.readXml();
    }
    
    public void readXml(){
        File inputFile = new File("C:\\Users\\JorgeOWM\\Desktop\\LeerXML.xml"); 
       
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("ItemCabecera");
            System.out.println("----------------------------");
             
            for (int temp = 0; temp < nList.getLength(); temp++) {
               Node nNode = nList.item(temp);
               System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                               Element eElement = (Element) nNode;
                    NodeList nProcessID =eElement.getElementsByTagName("ProcessID");
                    Node nodeProccessId = nProcessID.item(0);
                    System.out.println("\nSize nProccesID :" + nodeProccessId.getTextContent());
                }
            }
        } catch (ParserConfigurationException e) {
            System.out.println("ParserConfigurationException :" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException :" + e.getMessage());
        } catch (SAXException e) {
            System.out.println("SAXException :" + e.getMessage());
        }


    }
}
