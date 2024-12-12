package com.monge.xsqlite.xsqlite;

import com.j256.ormlite.dao.Dao;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DeliveryExpress extiende un objeto de esta interfaz para acceder a
 * los metodos SQL CRUD del mismo
 * @param <T>
 */
public class BaseDao<T> extends DataBaseMethods{
    
 
    public T read(String id) {
        return (T) getGenericDao().read(id);
    }
    /**
     * *
     * Registra/guarda/inserta el objeto en la base de datos
     */
    public void create() {
        getGenericDao().create(this);
    }

    /*elimina el objeto de la base de datos*/
    public void delete() {
        getGenericDao().delete(this);
    }

    /*actualiza el objeto en la base de datos*/
    public void update() {
        getGenericDao().update(this);
    }

     public GenericDao getGenericDao() {
        return DataBaseMethods.getDao(this.getClass());
    }
     
     public Dao<T, String> getFinalDao() {
        return (Dao<T, String>) DataBaseMethods.getDao(this.getClass()).getFinalDao();
    }
 
  
  
 
}
