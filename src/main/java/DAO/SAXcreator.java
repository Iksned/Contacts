package DAO;

import DAO.impl.CatalogSAXparser;

public class SAXcreator implements ParserCreator {
    @Override
    public CatalogDAO factory_method() {
        return new CatalogSAXparser();
    }
}
