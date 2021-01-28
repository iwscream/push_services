package Dao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import push.PushContent;

import java.io.PrintWriter;
import java.sql.*;
import java.util.*;

public class DBUtil {

    public static Connection getConnection(){
        Connection connection = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/master";
        String user = "root";
        String password = "123456";

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static AbstractList<ArrayList<String>> select(String sql) throws Exception{
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;
        AbstractList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();

        try {
            connection = getConnection();
            statement = connection.createStatement();
            rs = statement.executeQuery(sql);
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            int columns = resultSetMetaData.getColumnCount();
            int i;
            while (rs.next()){
                ArrayList<String> row = new ArrayList<String>();
                for (i = 1; i <= columns; ++i){
                    if (rs.getString(i) == null){
                        row.add("");
                    }else {
                        row.add(rs.getString(i));
                    }
                }
                list.add(row);
            }
        }catch (SQLException e){
            throw new Exception(e.getMessage());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }finally {
            try {
                if (rs != null){
                    rs.close();
                }
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
            try {
                if (statement != null){
                    statement.close();
                }
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
            try {
                if (connection != null){
                    connection.close();
                }
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
        }
        return list;
    }

    public static void insert(String sql) throws Exception{
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = getConnection();
            ps = (PreparedStatement) connection.prepareStatement(sql);
            ps.executeUpdate();
        }catch (SQLException e){
            throw new Exception(e.getMessage());
        }finally {
            try {
                if (ps != null){
                    ps.close();
                }
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }

            try {
                if (connection != null){
                    connection.close();
                }
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
        }
    }

    public static void Update(String sql)throws Exception{
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = getConnection();
            ps = (PreparedStatement) connection.prepareStatement(sql);
            ps.executeUpdate();
        }catch (SQLException e){
            throw new Exception(e.getMessage());
        }finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
    }

    public static void delete(String sql)throws Exception{
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = getConnection();
            ps = (PreparedStatement) connection.prepareStatement(sql);
            ps.executeUpdate();
        }catch (SQLException e){
            throw new Exception(e.getMessage());
        }finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }

            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
        }
    }

    public static void getJSONObject(PrintWriter out, int code, boolean status, Object obj){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",code);
        if (status) jsonObject.put("status","success");
        else jsonObject.put("status","fail");
        jsonObject.put("obj",obj);
        out.println(jsonObject);
    }

    public static JSONArray getJSONArray(int id) throws Exception {
        JSONArray jsonArray = new JSONArray();
        PushServicesDao pushServicesDao = new PushServicesDao();
        PushContent pushContent = pushServicesDao.getArticleById(id);

        jsonArray.add(0,pushContent.getId());
        jsonArray.add(1,pushContent.getPassword());
        jsonArray.add(2,pushContent.getContent());
        jsonArray.add(3,pushContent.getImgURL());

        return jsonArray;
    }

    public static void main(String[] args) {
        Connection c = null;
        try{
            c = getConnection();
            if (c != null){
                insert("insert into push_content (title,username,password,content,time,imgURL) value(111,111,111,111,111,0)");
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("qwq");
        }
    }
}
