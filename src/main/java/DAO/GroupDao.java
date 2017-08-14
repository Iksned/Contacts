package DAO;

import ConModel.Group;
import DAO.CatalogDAO;

public interface GroupDao extends CatalogDAO<Group>{
    @Override
    void create(String user, Group ob);

    @Override
    Object read(String ob);

    @Override
    Object readAll(String ob);

    @Override
    void update(Group oldOb, Group newOb);

    @Override
    void delete(Group ob);
}
