package DAO;

import DAO.impl.CatalogDOMparser;

public class DOMcreator implements ParserCreator {
    @Override
    public CatalogDAO factory_method() {
        return new CatalogDOMparser();
    }
}
