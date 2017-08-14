package DAO;

import DAO.impl.BaseUserParser;

public class UserParserCreator implements ParserCreator {
    @Override
    public CatalogDAO factory_method() {
        return new BaseUserParser();
    }
}
