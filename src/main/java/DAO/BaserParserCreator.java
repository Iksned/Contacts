package DAO;

import DAO.impl.BaseContactParser;
import DAO.impl.BaseGroupParser;
import DAO.impl.BaseUserParser;

public class BaserParserCreator implements ParserCreator {
    @Override
    public CatalogDAO getParser(String parserName) {
        if (parserName.equals("User"))
            return BaseUserParser.getInstace();
        else if (parserName.equals("Contact"))
           return BaseContactParser.getInstace();
        else if (parserName.equals("Group"))
            return BaseGroupParser.getInstace();
        return null;
    }
}
