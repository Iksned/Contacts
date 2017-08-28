package DAO;

import ConModel.Contact;

import java.util.List;

public interface ContactDAO extends CatalogDAO<Contact> {
    @Override
    void create(Contact ob);

    @Override
    Contact read(String id);

    @Override
    List<Contact> readAll(String ob);

    @Override
    void update(Contact newOb);

    @Override
    void delete(Contact ob);
}
