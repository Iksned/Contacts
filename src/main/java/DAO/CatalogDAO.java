package DAO;

public interface CatalogDAO{
        public void create(Object ob);
        public Object read(Object ob);
        public void update(Object oldOb,Object newOb);
        public void delete(Object ob);


}
