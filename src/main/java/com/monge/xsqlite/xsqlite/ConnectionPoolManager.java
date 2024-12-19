package com.monge.xsqlite.xsqlite;

import com.monge.xsqlite.connectors.DataBaseConection;
import com.monge.xsqlite.connectors.SqliteConection;
import com.monge.xsqlite.utils.GenericDao;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author DeliveryExpress a SQLITE balancer avoid frezzen
 */
public class ConnectionPoolManager {

    static final Map<Class, DataBaseConection> conections = new HashMap<>();

    /**
     * Agrega una nueva conexión a una base de datos SQLite con las clases
     * especificadas. Si la base de datos no existe, se creará automáticamente
     * con la extensión ".sqlite".
     *
     * @param databaseName Nombre de la base de datos (sin extensión). Si no
     * existe, se creará.
     * @param classes Clases que se asociarán con la conexión para realizar
     * operaciones CRUD. Cada clase representa una entidad gestionada por la
     * conexión.
     * @throws IllegalArgumentException Si el nombre de la base de datos o las
     * clases son nulos o vacíos.
     */
    public static void addConnection(DataBaseConection connection, Class<?>... classes) {
        // Validar el nombre de la base de datos
        if (connection == null) {
            throw new IllegalArgumentException("El nombre de la base de datos no puede ser nulo o vacío.");
        }

        // Validar las clases proporcionadas
        if (classes == null || classes.length == 0) {
            throw new IllegalArgumentException("Debe proporcionar al menos una clase para asociar con la conexión.");
        }

        // Crear una nueva conexión a la base de datos
        

        // Asociar las clases con la conexión y registrar la conexión en el mapa
        for (Class<?> clazz : classes) {
            if (clazz != null) {
                connection.addDao(clazz);
                conections.put(clazz, connection);
            } else {
                throw new IllegalArgumentException("Una de las clases proporcionadas es nula.");
            }
        }
    }

    /**
     * *
     *
     * @param <T>
     * @param clazz
     * @return la conexion relacionada a esa classe
     */
    public static <T> DataBaseConection getConection(Class<T> clazz) {

        DataBaseConection connection = conections.get(clazz);
        if (connection == null) {
            throw new IllegalStateException("No se pudo obtener una conexión para la clase: " + clazz.getName());
        }

        return conections.get(clazz);
    }

    public static GenericDao getDao(Class clazz) {
        return getConection(clazz).getDao(clazz);
    }

    public static <T> void clearCacheOf(Class<T> clazz) {
        conections.get(clazz).getDao(clazz).clearCache();
    }

}
