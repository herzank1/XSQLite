
import com.monge.xsqlite.connectors.SqliteConection;
import com.monge.xsqlite.xsqlite.ConnectionPoolManager;

/**
 *
 * @author DeliveryExpress
 */
public class XSQLite {

    public static void main(String[] args) {

        SqliteConection sql;
        sql = new SqliteConection("db.sqlite");
        ConnectionPoolManager.addConnection(sql, TestObject.class);

        String objId = "278a4060-c21e-4a9b-8721-e1fbf9838f29";
        TestObject obj = TestObject.read(TestObject.class, objId);
        if (obj == null) {

            System.out.println(objId + " no existe! creando objeto..");
            obj = new TestObject();
            obj.setId(objId);
            obj.create();
            System.out.println(obj.toString());
        } else {
            System.out.println("objecto ya existe en la base de datos (SELECT)" + obj);
        }

    }
}