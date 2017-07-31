package DAO;

import DAO.impl.JacksonMapping;

public class JacksonCreator implements ParserCreator {
    @Override
    public CatalogDAO factory_method() {
        return new JacksonMapping();
    }
}
