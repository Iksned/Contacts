package DAO;

import model.Group;

import java.util.List;

public interface GroupDAO extends CatalogDAO<Group> {
    @Override
    void create(Group ob);

    @Override
    Group read(String id);

    @Override
    List<Group> readAll(String ob);

    @Override
    void update(Group newOb);

    @Override
    void delete(Group ob);
}
