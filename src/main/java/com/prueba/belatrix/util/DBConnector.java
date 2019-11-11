package com.prueba.belatrix.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @author German Liceta
 * Clase conexion a bd
 */
public class DBConnector {
    private static final String DB_CONFIG = "dbconfig.properties";
    private static DBConnector dbConnector;
    private Connection connection;

    /**
     * Inicializa la conexion de la base de datos en el constructor
     */
    private DBConnector() {
        Properties properties = this.getDBConfig();

        String url = properties.getProperty("jdbc.url");
        String driver = properties.getProperty("jdbc.driver");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");

        try {
            Class.forName(driver).newInstance();
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtencion de instancia
     * @return DBConnection dbConnection
     */
    public static synchronized DBConnector getInstance() {
        if (dbConnector == null) {
            dbConnector = new DBConnector();
        }

        return dbConnector;
    }

    /**
     * Obtencion de la conexion
     * @return Connection connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Obtencion de la configuracion de conexion
     * @return Properties properties
     */
    private Properties getDBConfig() {
        Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = DBConnector.class.getClassLoader().getResourceAsStream(DB_CONFIG);

            if (inputStream != null) {
                properties.load(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return properties;
    }
}
