package sportclub.controller;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
 
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.json.JSONException;
import org.json.JSONObject;
 
/**
 * Hibernate user type to persist JSONObject
 *
 * @see http://www.json.org/javadoc/org/json/JSONObject.html
 * @see http://docs.jboss.org/hibernate/stable/annotations/api/org/hibernate/usertype/UserType.html
 * @author "Jan Jonas <mail@janjonas.net>"
 */
public class JSONObjectUserType implements UserType {
 
  private static final int[] SQL_TYPES = { Types.LONGVARCHAR };
 
  @Override
  public Object assemble(Serializable cached, Object owner) throws HibernateException {
    return deepCopy(cached);
  }
 
  @Override
  public Object deepCopy(Object value) throws HibernateException {
    if (value == null) return value;
    try {
      return new JSONObject(((JSONObject)value).toString());
    } catch (JSONException e) {
      throw new RuntimeException(e);
    }
  }
 
  @Override
  public Serializable disassemble(Object value) throws HibernateException {
    return ((JSONObject)value).toString();
  }
 
  @Override
  public boolean equals(Object x, Object y) throws HibernateException {
    if (x == null) return (y != null);
    return (x.equals(y));
  }
 
  @Override
  public int hashCode(Object x) throws HibernateException {
    return ((JSONObject)x).toString().hashCode();
  }
 
  @Override
  public boolean isMutable() {
    return true;
  }
   
  @Override
  public Object replace(Object original, Object target, Object owner) throws HibernateException {
    return deepCopy(original);
  }
 
  @Override
  @SuppressWarnings("unchecked")
  public Class returnedClass() {
    return JSONObject.class;
  }
 
  @Override
  public int[] sqlTypes() {
    return SQL_TYPES;
  }

@Override
public Object nullSafeGet(ResultSet arg0, String[] arg1, SessionImplementor arg2, Object arg3)
		throws HibernateException, SQLException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public void nullSafeSet(PreparedStatement arg0, Object arg1, int arg2, SessionImplementor arg3)
		throws HibernateException, SQLException {
	// TODO Auto-generated method stub
	
}
 
}
