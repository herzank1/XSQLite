package com.monge.xsqlite.xsqlite;


/**
 *
 * @author DeliveryExpress
 */
public class XSQLite {

    public static void main(String[] args) {
        ConnectionPoolManager.addConnection("db.sqlite", TestObject.class);

 
        TestObject obj = new TestObject();
        obj = (TestObject) obj.read("278a4060-c21e-4a9b-8721-e1fbf9838f29");
        System.out.println(obj);
        
    
      
    }
}
