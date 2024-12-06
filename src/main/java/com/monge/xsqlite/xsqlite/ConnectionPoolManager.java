
package com.monge.xsqlite.xsqlite;



import com.monge.xsqlite.xsqlite.GenericDao;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author DeliveryExpress a SQLITE balancer avoid frezzen
 */
public  class ConnectionPoolManager {

    static final Map<Class,SqliteConnection> conections = new HashMap<>();
    
   /**
 * Agrega una nueva conexión a una base de datos SQLite con las clases especificadas.
 * Si la base de datos no existe, se creará automáticamente con la extensión ".sqlite".
 *
 * @param databaseName Nombre de la base de datos (sin extensión). Si no existe, se creará.
 * @param classes      Clases que se asociarán con la conexión para realizar operaciones CRUD.
 *                     Cada clase representa una entidad gestionada por la conexión.
 * @throws IllegalArgumentException Si el nombre de la base de datos o las clases son nulos o vacíos.
 */
public static void addConnection(String databaseName, Class<?>... classes) {
    // Validar el nombre de la base de datos
    if (databaseName == null || databaseName.trim().isEmpty()) {
        throw new IllegalArgumentException("El nombre de la base de datos no puede ser nulo o vacío.");
    }

    // Validar las clases proporcionadas
    if (classes == null || classes.length == 0) {
        throw new IllegalArgumentException("Debe proporcionar al menos una clase para asociar con la conexión.");
    }

    // Crear una nueva conexión a la base de datos
    SqliteConnection newConnection = new SqliteConnection(databaseName);

    // Asociar las clases con la conexión y registrar la conexión en el mapa
    for (Class<?> clazz : classes) {
        if (clazz != null) {
            newConnection.addDao(clazz);
            conections.put(clazz, newConnection);
        } else {
            throw new IllegalArgumentException("Una de las clases proporcionadas es nula.");
        }
    }
}
    
    /***
     * 
     * @param clazz
     * @return la conexion relacionada a esa classe
     */
    public static SqliteConnection getConection(Class clazz) {
        return conections.get(clazz);
    }
    
    public static GenericDao getDao(Class clazz) {
        return getConection(clazz).getDao(clazz);
    }
    
    public static void clearCacheOf(Class clazz){
        conections.get(clazz).getDao(clazz).clearCache();
    }
    

}
