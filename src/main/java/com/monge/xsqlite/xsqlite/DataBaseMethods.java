/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.monge.xsqlite.xsqlite;

import java.util.ArrayList;

/***
 * Metodos estaticos para operaciones CRUD
 * @author DeliveryExpress
 */

public class DataBaseMethods {

    /**
     * Este método permite leer un objeto de la base de datos según su id.
     * @param clazz La clase del tipo de objeto que se quiere leer.
     * @param id El id del objeto que se quiere leer.
     * @param <T> El tipo genérico del objeto.
     * @return El objeto encontrado de tipo T.
     */
    public static <T> T read(Class clazz, String id) {
        // Obtiene el DAO correspondiente a la clase clazz y llama al método 'read' para obtener el objeto por su id.
        return (T) getDao(clazz).read(id);
    }

    /**
     * Este método busca un objeto en la base de datos basado en un valor de una columna específica.
     * @param clazz La clase del tipo de objeto que se quiere buscar.
     * @param columnName El nombre de la columna en la base de datos donde se realizará la búsqueda.
     * @param value El valor que se quiere buscar en la columna.
     * @param <T> El tipo genérico del objeto.
     * @return El objeto encontrado de tipo T.
     */
    public static <T> T findByColumn(Class clazz, String columnName, String value) {
        // Obtiene el DAO correspondiente a la clase clazz y llama al método 'findByColumn' para buscar por columna y valor.
        return (T) getDao(clazz).findByColumn(columnName, value);
    }

    /**
     * Este método lee todos los objetos de una clase en la base de datos.
     * @param clazz La clase del tipo de objeto que se quiere leer.
     * @param <T> El tipo genérico del objeto.
     * @return Una lista con todos los objetos encontrados de tipo T.
     */
    public static <T> ArrayList<T> readAll(Class<T> clazz) {
        // Obtiene el DAO correspondiente a la clase clazz y llama al método 'readAll' para leer todos los objetos de esa clase.
        ArrayList<T> readAll = (ArrayList<T>) getDao(clazz).readAll();
        return readAll;
    }

    /**
     * Este método busca una lista de objetos en la base de datos basándose en un valor de columna.
     * @param clazz La clase del tipo de objeto que se quiere buscar.
     * @param columnName El nombre de la columna en la base de datos donde se realizará la búsqueda.
     * @param value El valor que se quiere buscar en la columna.
     * @param <T> El tipo genérico del objeto.
     * @return Una lista con los objetos encontrados de tipo T.
     */
    public static <T> ArrayList<T> findByColumnList(Class clazz, String columnName, String value) {
        // Obtiene el DAO correspondiente a la clase clazz y llama al método 'findByColumnList' para buscar por columna y valor.
        ArrayList<T> findByColumnList = (ArrayList<T>) getDao(clazz).findByColumnList(columnName, value);
        return findByColumnList;
    }

    /**
     * Este método limpia la caché de un DAO asociado a una clase.
     * @param clazz La clase del tipo de objeto para el que se quiere limpiar la caché.
     */
    public static void clearCache(Class clazz) {
        // Obtiene el DAO correspondiente a la clase clazz y llama al método 'clearCache' para limpiar la caché.
       getDao(clazz).clearCache();
    }

    /**
     * Este método obtiene el DAO asociado a una clase.
     * @param clazz La clase del tipo de objeto para el que se quiere obtener el DAO.
     * @param <T> El tipo genérico del objeto.
     * @return El DAO correspondiente a la clase dada.
     */
    public static GenericDao  getDao(Class clazz) {
        // Devuelve el DAO asociado a la clase clazz, utilizado para operaciones CRUD.
        return  ConnectionPoolManager.getDao(clazz);
    }

}
