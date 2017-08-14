package DAO;

import DAO.impl.BaseContactParser;

public class contactPaserCreator implements ParserCreator {
    @Override
    public CatalogDAO factory_method() {
        return new BaseContactParser();
    }
}
