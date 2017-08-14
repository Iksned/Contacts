package DAO;

import DAO.impl.BaseGroupParser;

public class groupParserCreator implements ParserCreator {
    @Override
    public CatalogDAO factory_method() {
        return new BaseGroupParser();
    }
}
