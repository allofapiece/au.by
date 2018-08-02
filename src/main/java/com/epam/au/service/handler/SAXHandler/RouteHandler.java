package com.epam.au.service.handler.SAXHandler;

import com.epam.au.controller.Page;
import com.epam.au.controller.Router;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * SAX handler for reading book.xml file and getting information about
 * library books.
 *
 * @author Listratsenka Stanislau
 * @version 1.0
 */
public class RouteHandler extends DefaultHandler {
    private static final Logger LOG = Logger.getLogger(RouteHandler.class);
    private Router router;
    private Page page;
    private String route;
    private String element;

    /**
     * Default constructor
     */
    public RouteHandler() {
        router = Router.getInstance();
    }

    @Override
    public void startDocument(){
        LOG.debug("Started parsing of document - pages.xml");
    }

    @Override
    public void endDocument() throws SAXException {
        LOG.debug("Ended parsing of document - pages.xml");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        element = qName;

        switch (element) {
            case "page":
                page = new Page();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("page")) {
            router.setRoute(route, page);
        }
        element = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        switch (element) {
            case "route":
                route = new String(ch, start, length);
                break;

            case "title":
                page.setTitle(new String(ch, start, length));
                break;

            case "path":
                page.setPath(new String(ch, start, length));
                break;
        }
    }
}
