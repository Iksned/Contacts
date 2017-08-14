package DAO;

import ConModel.Contact;

public interface ContactDAO extends CatalogDAO<Contact> {
    @Override
    void create(String user, Contact ob);

    @Override
    Object read(String ob);

    @Override
    Object readAll(String ob);

    @Override
    void update(Contact oldOb, Contact newOb);

    @Override
    void delete(Contact ob);
}
