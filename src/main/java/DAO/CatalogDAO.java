package DAO;

public interface CatalogDAO<T>{
        public void create(String user, T ob);
        public Object read(String ob);
        public Object readAll(String ob);
        public void update(T oldOb,T newOb);
        public void delete(T ob);


}
