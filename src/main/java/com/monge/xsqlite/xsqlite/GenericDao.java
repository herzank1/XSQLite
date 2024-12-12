package com.monge.xsqlite.xsqlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP Classe generica para operaciones CRUD usando la liberia OMRlite
 * classes
 * @param <T>
 * @param <ID>
 */
public class GenericDao<T, ID> {

    private final Dao<T, ID> dao;
    private final Class<T> clazz;
    private final Map<ID, T> cache; // Caché para almacenar objetos
    private int cacheMaxSize = 50;

    public GenericDao(ConnectionSource connectionSource, Class<T> clazz) throws SQLException {
        this.clazz = clazz;
        dao = DaoManager.createDao(connectionSource, clazz);
        this.cache = new HashMap<>(); // Inicializamos el caché

    }

    /**
     * *
     * Inset entity to database
     *
     * @param entity
     */
    public void create(T entity) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        try {
            dao.create(entity);
            ID id = dao.extractId(entity);
            if (id != null) {
                ensureCacheLimit(); // Asegurar límite del caché
                cache.put(id, entity);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public T read(ID id) {
        // Busca en el caché primero
        if (cache.containsKey(id)) {
            return cache.get(id);
        }

        // Si no está en el caché, buscar en la base de datos
        try {
            T entity = dao.queryForId(id);
            if (entity != null) {
                ensureCacheLimit(); // Asegurar límite del caché
                cache.put(id, entity); // Guardar en el caché
            }
            return entity;
        } catch (SQLException ex) {
            Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<T> readAll() {
        try {
            return (ArrayList<T>) dao.queryForAll();
        } catch (SQLException ex) {
            Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*update statement*/
    public void update(T entity) {
        try {
            dao.update(entity);
            // Sincronizar el caché: obtener el ID del objeto actualizado
            ID id = dao.extractId(entity);
            if (id != null) {
                cache.put(id, entity); // Actualizar o agregar el objeto al caché
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void delete(T entity) {
        
        try {
            ID id = dao.extractId(entity);
            dao.deleteById(id);
            cache.remove(id); // Eliminar del caché también
        } catch (SQLException ex) {
            Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void clearCache() {
        this.cache.clear();
    }

    private void ensureCacheLimit() {
        if (cache.size() > cacheMaxSize) {
            ID firstKey = cache.keySet().iterator().next(); // Obtener el primer elemento
            cache.remove(firstKey); // Eliminar del caché
        }
    }

    // Método genérico para buscar por columna
    /**
     * *
     * select where
     *
     * @param columnName
     * @param value
     * @return
     */
    public T findByColumn(String columnName, Object value) {
        try {
            QueryBuilder<T, ID> queryBuilder = dao.queryBuilder();
            queryBuilder.where().eq(columnName, value);
            return queryBuilder.queryForFirst(); // Devuelve el primer resultado que coincide
        } catch (SQLException e) {
            return null;
        }

    }

    /**
     * Método genérico para buscar múltiples resultados por una columna
     *
     * @param columnName el nombre de la columna
     * @param value el valor a buscar
     * @return una lista de objetos que coinciden con la condición
     */
   public List<T> findByColumnList(String columnName, Object value) {
    try {
        QueryBuilder<T, ID> queryBuilder = dao.queryBuilder();
        queryBuilder.where().eq(columnName, value);
        List<T> results = queryBuilder.query();
        return results != null ? results : new ArrayList<>();
    } catch (SQLException e) {
        Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, "Error al buscar por columna: " + columnName, e);
        return new ArrayList<>(); // Retorna una lista vacía
    }
}

    public Dao<T, ID> getFinalDao() {
        return this.dao;
    }

    public String getTableName() {

        return generateTableNameFromClass(clazz);

    }

    /**
     * *
     *
     * @param clazz
     * @return examploe class User return class user as tableName
     */
    private static String generateTableNameFromClass(Class<?> clazz) {
        // Convertir el nombre de la clase a minúsculas
        String className = clazz.getSimpleName().toLowerCase();
        // Reemplazar caracteres especiales y espacios con guiones bajos
        String tableName = className.replaceAll("[^a-zA-Z0-9]", "_");
        return tableName;
    }

    public Map<ID, T> getCache() {
        return this.cache;
    }
}
