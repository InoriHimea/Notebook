package application.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper {

    private static final String DB_NAME = "notebook.db";

    private static Connection connection = null;
    private static PreparedStatement psStatement = null;
    private static ResultSet resultSet = null;
    private static String driver = "org.sqlite.JDBC";// MySQL: com.mysql.jdbc.Driver

    static {
		try {
			// ��������
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

    /** ��ȡ���� */
    private static Connection getConnection(){
    	try {
    		Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);//����Ҫ�û��� ����    jdbc:mysql://localhost:3306/test,û��test.db����Զ������ļ�
    		return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }

    /** �ر���Դ */
  	private static void close(){
  		try {
  			if(resultSet != null) resultSet.close();
  			if(psStatement != null) psStatement.close();
  			if(connection != null) connection.close();
  		} catch (Exception e) {
  			e.printStackTrace();
  		}
  	}

  	/** rawSQL(�ɶ� ->��֧�ֲ�ѯ) */
    public static List<Map<String,String>> rawSQLMapList(String sql,String ... params){
    	connection = getConnection();
    	ArrayList<Map<String,String>> list = new ArrayList<>();
    	try {
    		psStatement = connection.prepareStatement(sql);
    		if(params != null){
    			for(int i = 0;i < params.length;i++){
    				psStatement.setObject(i+1, params[i]);
    			}
    		}
    		resultSet = psStatement.executeQuery();
    		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
    		int column = resultSetMetaData.getColumnCount();// ���ݿ���ֶ�����

    		Map<String,String> map = null;// ÿһ��map����һ������(��¼)
    		while(resultSet.next()){
    			map = new HashMap<>();
        		for(int i =  1;i <= column;i++){
        			// String fieldClass = resultSetMetaData.getColumnClassName(i);
        			// if(fieldClass.equals("java.lang.String")) resultSet.getString(i);
        			String fieldName = resultSetMetaData.getColumnName(i).toLowerCase();
        			map.put(fieldName, resultSet.getString(i));
        		}

        		list.add(map);
    		}

		} catch (Exception e) {
			e.printStackTrace();
		}
    	return list;
    }

 	/** rawSQL(�ɶ� ->��֧�ֲ�ѯ) */
    public static List<Object[]> rawSQLObjsList(String sql,String ... params){
    	connection = getConnection();
    	List<Object[]> list = new ArrayList<>();
    	try {
    		psStatement = connection.prepareStatement(sql);
    		if(params != null){
    			for(int i = 0;i < params.length;i++){
    				psStatement.setString(i+1, params[i]);
    			}
    		}
    		resultSet = psStatement.executeQuery();
    		ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
    		int column = resultSetMetaData.getColumnCount();// ���ݿ���ֶ�����

    		while(resultSet.next()){
    			Object [] obj = new Object[column];// ÿ�������ʾһ�����ݿ��¼,�����ŵ����ֶ�����
    			for(int i = 0;i < column;i++){
    				obj[i] = resultSet.getObject(i+1);
    			}
    			list.add(obj);
    		}
    	}catch (Exception e) {
    		e.printStackTrace();
		}finally {
			close();
		}
    	return list;
    }

    /** execSQL(��д ->:֧�� create,insert,update,delete,drop�����ݿ������صĲ��� ) */
    public static boolean execSQL(String sql,String ... params){
    	boolean flag = false;
    	connection = getConnection();
    	try {
    		psStatement = connection.prepareStatement(sql);

    		if(params != null){
    			for(int i = 0;i < params.length;i++){
    				psStatement.setString(i+1, params[i]);
    			}
    		}
    		//
    		if(psStatement.executeUpdate() == 1){
    			flag = true;
    		}

    	}catch (Exception e) {
    		e.printStackTrace();
		}finally {
			close();
		}
    	return flag;
    }

    /** execSQL(��д ->:֧�� create,insert,update,delete,drop�����ݿ������صĲ��� ) ����SQL��Ҫ��������� */
    public static boolean execSQLAll(String [] sqls,String [][] params) throws SQLException{
    	boolean flag = false;
    	connection = getConnection();
    	connection.setAutoCommit(false);// ����ʼ �൱�� beginTransation();

    	try {
    		for(int i = 0;i < sqls.length;i++){
    			if(params[i] != null){
    				psStatement = connection.prepareStatement(sqls[i]);

    				for(int j = 0;j < params[i].length;j++){
    					psStatement.setString(j + 1, params[i][j]);
    				}
    				psStatement.executeUpdate();

    			}
    		}

    		connection.commit();
    		flag = true;

    	}catch (Exception e) {
    		connection.rollback();// ����ع�
    		e.printStackTrace();
		}finally {
			close();
		}
    	return flag;
    }

}