package DAO;

import java.util.List;

public interface CatalogDAO<T>{
        public void create(T ob);
        public T read(String id);
        public List<T> readAll(String ob);
        public void update(T newOb);
        public void delete(T ob);


}
