package DAO;

public interface DAOcreator {
    public CatalogDAO getDAO(String parserName);
}
