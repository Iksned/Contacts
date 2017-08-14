package DAO;

import ConModel.User;

public interface BaseUserDAO extends CatalogDAO<User>{
    @Override
    void create(String user, User ob);

    @Override
    Object read(String ob);

    @Override
    Object readAll(String ob);

    @Override
    void update(User oldOb, User newOb);

    @Override
    void delete(User ob);
}
