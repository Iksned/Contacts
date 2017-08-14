package DAO;

import DAO.impl.BaseContactParser;
import DAO.impl.BaseGroupParser;
import DAO.impl.BaseUserParser;

public class BaserParserCreator implements ParserCreator {
    @Override
    public CatalogDAO getParser(String parserName) {
        if (parserName.equals("User"))
            return new BaseUserParser();
        else if (parserName.equals("Contact"))
           return new BaseContactParser();
        else if (parserName.equals("Group"))
            return new BaseGroupParser();
        return null;
    }
}
