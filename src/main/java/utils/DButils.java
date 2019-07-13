package utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;

public class DButils {
    private static DataSource dataSource;

    public static DataSource getDataSource() {
        return dataSource;
    }
    static{
        dataSource=new ComboPooledDataSource();

    }
}
