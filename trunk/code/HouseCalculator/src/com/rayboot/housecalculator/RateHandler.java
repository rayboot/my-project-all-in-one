package com.rayboot.housecalculator;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RateHandler extends DefaultHandler {
	RateObj rateObj;
	List<RateObj> rateList;
	String tagName;

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.startDocument();
		rateList = new ArrayList<RateObj>();
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		tagName = localName;
		if (localName.equals("ratelist")) {
			rateObj = new RateObj();
		} else if (localName.equals("year1")) {
			rateObj.year1.name = attributes.getValue("name");
			rateObj.year1.gjj = attributes.getValue("gjj");
			rateObj.year1.sy = attributes.getValue("sy");
		} else if (localName.equals("year2")) {
			rateObj.year2.name = attributes.getValue("name");
			rateObj.year2.gjj = attributes.getValue("gjj");
			rateObj.year2.sy = attributes.getValue("sy");
		} else if (localName.equals("year4")) {
			rateObj.year4.name = attributes.getValue("name");
			rateObj.year4.gjj = attributes.getValue("gjj");
			rateObj.year4.sy = attributes.getValue("sy");
		} else if (localName.equals("year6")) {
			rateObj.year6.name = attributes.getValue("name");
			rateObj.year6.gjj = attributes.getValue("gjj");
			rateObj.year6.sy = attributes.getValue("sy");
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		if (localName.equals("ratelist")) {
			rateList.add(rateObj);
			return;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		String theString = String.valueOf(ch).trim();
		if (tagName.equals("ratename")) {
			rateObj.name = theString;
		}
	}

	public List<RateObj> getRates() {
		return rateList;
	}
}
