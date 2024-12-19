package com.monge.xsqlite.connectors;

import com.monge.xsqlite.utils.GenericDao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Use this class to create more sql nodes for load distribution in sqlite data
 * base
 *
 * @author DeliveryExpress
 */
public class SqliteConection extends  DataBaseConection{

    private ConnectionSource connectionSource;
    private String url;

    private Map<Class<?>, GenericDao<?, String>> daos = new HashMap<>();

    /**
     * *
     *
     * @param fileName exmaple: database.sqlite
     */
    public SqliteConection(String fileName) {
        
        super(null);
        setConnection(fileName);

    
    }
    
    private void setConnection(String fileName){
        
            try {
            // Verificar si el archivo de la base de datos existe
            File dbFile = new File(fileName);
            if (!dbFile.exists()) {
                dbFile.createNewFile(); // Crear el archivo de base de datos si no existe
            }

            // Cargar el controlador JDBC para SQLite
            Class.forName("org.sqlite.JDBC");
            this.url = "jdbc:sqlite:" + fileName;
            connectionSource = new JdbcConnectionSource(this.url);
            super.setConnectionSource(connectionSource);

        } catch (SQLException | ClassNotFoundException | IOException ex) {
        }
    
    }

//    public Map<Class<?>, GenericDao<?, String>> getDaos() {
//        return this.daos;
//    }
//
//    public <T> void addDao(Class<T> clazz) {
//        try {
//            GenericDao<T, String> dao = new GenericDao<>(connectionSource, clazz);
//            TableUtils.createTableIfNotExists(connectionSource, clazz);
//            daos.put(clazz, dao); // Agregar el DAO al HashMap
//            System.out.println("DAO registrado para la clase: " + clazz.getName()
//                    + " en la conexion " + this.url);
//        } catch (SQLException ex) {
//            Logger.getLogger(SqliteConnection.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

//    @SuppressWarnings("unchecked")
//    public <T> GenericDao<T, String> getDao(Class<T> clazz) {
//        GenericDao<T, String> dao = (GenericDao<T, String>) daos.get(clazz);
//        if (dao == null) {
//            throw new IllegalStateException("DAO no encontrado para la clase: " + clazz.getName());
//        }
//        return dao;
//    }
//
//    // Función para recorrer el HashMap y validar cada DAO
//    public void validateClassChanges() {
//        for (Map.Entry<Class<?>, GenericDao<?, String>> entry : daos.entrySet()) {
//            Class<?> entityClass = entry.getKey();
//            GenericDao<?, ?> dao = entry.getValue();
//            TableValidator.verifyTable(this, dao, entityClass);
//        }
//    }

//    public String getUrl() {
//        return url;
//    }
//
//    /**
//     * *
//     * Clears all caches of this conection
//     */
//    public void clearCaches() {
//        for (Map.Entry<Class<?>, GenericDao<?, String>> entry : daos.entrySet()) {
//
//            GenericDao<?, ?> dao = entry.getValue();
//            dao.clearCache();
//
//        }
//    }

}
