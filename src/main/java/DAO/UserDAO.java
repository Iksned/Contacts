package DAO;

import model.User;

import java.util.List;

public interface UserDAO extends CatalogDAO<User>{
    @Override
    void create(User ob);

    @Override
    User read(String id);

    @Override
    List<User> readAll(String ob);

    @Override
    void update(User newOb);

    @Override
    void delete(User ob);
}
