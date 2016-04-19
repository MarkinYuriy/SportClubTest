package sportclub.json;

import java.sql.Types;

import org.hibernate.dialect.MySQL5Dialect;
 
public class JsonMySQLDialect extends MySQL5Dialect {
 
    public JsonMySQLDialect() {
        super();
        this.registerColumnType(Types.JAVA_OBJECT, "json");
    }
 
}
