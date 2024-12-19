


import com.j256.ormlite.field.DatabaseField;
import com.monge.xsqlite.utils.BaseDao;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author DeliveryExpress
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class TestObject extends BaseDao{
    
    @DatabaseField(id = true)
    String id;
    @DatabaseField
    String data;
    
    
    
}
