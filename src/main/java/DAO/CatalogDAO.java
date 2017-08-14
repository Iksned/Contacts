package DAO;

public interface CatalogDAO{
        public void create(String user, Object ob);
        public Object read(Object ob);
        public void update(Object oldOb,Object newOb);
        public void delete(Object ob);


}
