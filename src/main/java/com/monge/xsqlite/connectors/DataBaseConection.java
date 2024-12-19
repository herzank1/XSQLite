/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monge.xsqlite.connectors;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.monge.xsqlite.utils.GenericDao;
import com.monge.xsqlite.utils.TableValidator;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DeliveryExpress
 */
public class DataBaseConection {

    private ConnectionSource connectionSource;
    private String url;

    private Map<Class<?>, GenericDao<?, String>> daos = new HashMap<>();

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public void setConnectionSource(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    /**
     * *
     *
     * @param fileName exmaple: database.sqlite
     */
    public DataBaseConection(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    public Map<Class<?>, GenericDao<?, String>> getDaos() {
        return this.daos;
    }

    public <T> void addDao(Class<T> clazz) {
        try {
            GenericDao<T, String> dao = new GenericDao<>(connectionSource, clazz);
            TableUtils.createTableIfNotExists(connectionSource, clazz);
            daos.put(clazz, dao); // Agregar el DAO al HashMap
            System.out.println("DAO registrado para la clase: " + clazz.getName()
                    + " en la conexion " + this.url);
        } catch (SQLException ex) {
            Logger.getLogger(SqliteConection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> GenericDao<T, String> getDao(Class<T> clazz) {
        GenericDao<T, String> dao = (GenericDao<T, String>) daos.get(clazz);
        if (dao == null) {
            throw new IllegalStateException("DAO no encontrado para la clase: " + clazz.getName());
        }
        return dao;
    }

    // Función para recorrer el HashMap y validar cada DAO
    public void validateClassChanges() {
        for (Map.Entry<Class<?>, GenericDao<?, String>> entry : daos.entrySet()) {
            Class<?> entityClass = entry.getKey();
            GenericDao<?, ?> dao = entry.getValue();
            TableValidator.verifyTable(this, dao, entityClass);
        }
    }

    public String getUrl() {
        return url;
    }

    /**
     * *
     * Clears all caches of this conection
     */
    public void clearCaches() {
        for (Map.Entry<Class<?>, GenericDao<?, String>> entry : daos.entrySet()) {

            GenericDao<?, ?> dao = entry.getValue();
            dao.clearCache();

        }
    }

}
