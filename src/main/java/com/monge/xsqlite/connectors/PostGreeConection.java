/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monge.xsqlite.connectors;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DeliveryExpress
 */
public class PostGreeConection extends DataBaseConection {

    private String driver = "jdbc:postgresql:";
    private String url; //"jdbc:postgresql://localhost:5432/mi_base_datos";
    private String username;
    private String password;

    /**
     * *
     *
     * @param url exm //localhost:5432/mi_base_datos
     * @param username
     * @param password
     * @param connectionSource
     */
    public PostGreeConection(String url, String dataBaseName, String username, String password) {
        super(null);
        this.url = url;
        this.username = username;
        this.password = password;
        setConnection(dataBaseName);
    }

    private void setConnection(String dataBaseName) {

        // Configurar la conexión
        ConnectionSource connectionSource;
        try {
            Class.forName("org.postgresql.Driver");

            // Verificar si la base de datos existe
            if (!databaseExists(dataBaseName)) {
                createDatabase(dataBaseName);
            }

            connectionSource = new JdbcConnectionSource(driver + url + dataBaseName, username, password);
            super.setConnectionSource(connectionSource);

            System.out.println("¡Conexión exitosa a PostgreSQL con ORMLite!");

        } catch (SQLException ex) {
            Logger.getLogger(PostGreeConection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PostGreeConection.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Cerrar la conexión
        // connectionSource.close();
    }

    private boolean databaseExists(String dbName) throws SQLException {
        String checkQuery = "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'";
        try (Connection connection = DriverManager.getConnection(driver + url + "postgres", username, password); Statement stmt = connection.createStatement()) {
            return stmt.executeQuery(checkQuery).next();
        }
    }

    private void createDatabase(String dbName) throws SQLException {
        String createQuery = "CREATE DATABASE " + dbName;
        try (Connection connection = DriverManager.getConnection(driver + url + "postgres", username, password); Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createQuery);
            System.out.println("Base de datos '" + dbName + "' creada exitosamente.");
        }
    }

}
