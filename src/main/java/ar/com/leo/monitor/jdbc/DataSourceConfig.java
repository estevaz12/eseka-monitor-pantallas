package ar.com.leo.monitor.jdbc;

//import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Leo
 */
public class DataSourceConfig {

    public static MysqlDataSource dataSource;
//    public static SQLServerDataSource dataSource;

    static {
        try (InputStream in = DataSourceConfig.class.getResourceAsStream("/db.properties")) {
            Properties props = new Properties();
            props.load(in);
            dataSource = new MysqlDataSource();
            dataSource.setURL(props.getProperty("db.connection.url"));
            dataSource.setUser(props.getProperty("db.connection.username"));
            dataSource.setPassword(props.getProperty("db.connection.password"));
            dataSource.setDatabaseName(props.getProperty("db.connection.databaseName"));
//            dataSource.setEncrypt(false);
//            dataSource.setIntegratedSecurity(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
