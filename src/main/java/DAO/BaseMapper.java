package DAO;

import java.sql.ResultSet;

public interface BaseMapper<R> {
    public R map(ResultSet resultSet);
}
