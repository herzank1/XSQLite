
import com.monge.xsqlite.connectors.PostGreeConection;
import com.monge.xsqlite.connectors.SqliteConection;
import com.monge.xsqlite.xsqlite.ConnectionPoolManager;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DeliveryExpress
 */
public class PostgreTest {
    
    public static void main(String args[]){
      PostGreeConection pg;
        pg = new PostGreeConection("//209.46.123.107/","test_db","postgres","dvvm1516");
        ConnectionPoolManager.addConnection(pg, TestObject.class);

        String objId = "278a4060-c21e-4a9b-8721-e1fbf9838f29";
        TestObject obj = TestObject.read(TestObject.class, objId);
        if (obj == null) {

            System.out.println(objId +" no existe! creando objeto..");
            obj = new TestObject();
            obj.setId(objId);
            obj.create();
            System.out.println(obj.toString());
        }else{
         System.out.println("objecto ya existe en la base de datos (SELECT)"+obj);
        }
    }
    
    
       
    
}
