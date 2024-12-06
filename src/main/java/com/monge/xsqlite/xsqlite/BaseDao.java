package com.monge.xsqlite.xsqlite;

import java.util.ArrayList;

/**
 *
 * @author DeliveryExpress extiende un objeto de esta interfaz para acceder a
 * los metodos SQL CRUD del mismo
 */
public class BaseDao<T> {

    public T read(String id) {
        return (T) ConnectionPoolManager.getDao(this.getClass()).read(id);
    }

    public static <T> T read(Class clazz, String id) {
        return (T) ConnectionPoolManager.getDao(clazz).read(id);
    }

    public static <T> T findByColumn(Class clazz, String columnName, String value) {
        return (T) ConnectionPoolManager.getDao(clazz).findByColumn(columnName, value);
    }

    public static <T> ArrayList<T> readAll(Class<T> clazz) {
        // Supongamos que ConnectionPoolManager.getDato(clazz).readAll() devuelve una colección genérica
        return new ArrayList<>(ConnectionPoolManager.getDao(clazz).readAll());
    }

    public static ArrayList findByColumnList(Class clazz, String columnName, String value) {
        return (ArrayList) ConnectionPoolManager.getDao(clazz).findByColumnList(columnName, value);
    }

    public void clearCache() {
        ConnectionPoolManager.getDao(this.getClass()).clearCache();
    }

    /**
     * *
     * Registra/guarda/inserta el objeto en la base de datos
     */
    public void create() {
        ConnectionPoolManager.getDao(this.getClass()).create(this);
    }

    /*elimina el objeto de la base de datos*/
    public void delete() {
        ConnectionPoolManager.getDao(this.getClass()).delete(this);
    }

    /*actualiza el objeto en la base de datos*/
    public void update() {
        ConnectionPoolManager.getDao(this.getClass()).update(this);
    }

}
