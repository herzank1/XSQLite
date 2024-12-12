
package com.monge.xsqlite.xsqlite;

import com.j256.ormlite.field.DatabaseField;
import lombok.Data;

/**
 *
 * @author DeliveryExpress
 */
@Data
public class TestObject extends BaseDao{
    
    @DatabaseField(id = true)
    String id;
    @DatabaseField
    String data;
    
    
    
}
