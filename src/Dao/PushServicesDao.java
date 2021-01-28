package Dao;

import com.mysql.jdbc.Statement;
import push.PushContent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class PushServicesDao {

    public ArrayList<PushContent> getArticle(String s, String value) throws Exception {
        ArrayList<PushContent> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from push_content where " + s + "  = " + value;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()){
                list.add(getContent(rs));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            finalClose(connection,ps,rs);
        }
        return list;
    }
    public ArrayList<PushContent> getArticle() throws Exception {
        ArrayList<PushContent> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select * from push_content";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()){
                list.add(getContent(rs));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            finalClose(connection,ps,rs);
        }
        return list;
    }
    public ArrayList<String> getArticle(String getValue,String column, String value) throws Exception {
        ArrayList<String> list = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select " + getValue +" from push_content where " + column + " = " + value;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()){
                list.add(rs.getString(getValue));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            finalClose(connection,ps,rs);
        }
        return list;
    }

    public PushContent getArticleById (int id) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            connection = DBUtil.getConnection();
            String sql = "select * from push_content where id =" + id;
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()){
                return getContent(rs);
            }else {
                return null;
            }
        }catch (Exception e){

        }finally {
            finalClose(connection,ps,rs);
        }
        return null;
    }

    public int addArticle (PushContent push) throws Exception {
        Connection connection = null;

        try {
            connection = DBUtil.getConnection();

            String sql = "insert into push_content (password,content,time,imgURL)" + "value (?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1,push.getPassword());
            ps.setString(2,push.getContent());
            ps.setString(3,push.getTime());
            ps.setString(4 ,push.getImgURL());
            ps.executeUpdate();

//            String sql1 = "select last_insert_id()";
            ResultSet rs = ps.getGeneratedKeys();
            int id = 0;
            while (rs.next()){
                id = rs.getInt(1);
            }
            return id;
        }catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }finally {
            try {
                if (connection != null){
                    connection.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void deleteItemsById(String id) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = DBUtil.getConnection();

            String sql = "delete from push_content where id = " + id;
            ps = connection.prepareStatement(sql);
            ps.execute();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (connection != null){
                    connection.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public ArrayList<PushContent> selectPageNumber(int pageNumber){
        ArrayList<PushContent> list = new ArrayList<>();
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            String sql = "select * from push_content limit " + 10 *(pageNumber - 1) + "," + 10;//开始位置，每次条数

            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()){
                list.add(getContent(rs));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public boolean selectPassword(String password){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean temp = false;

        try{
            String sql = "select password from push_content";

            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()){
                temp = rs.getString("password").equals(password);
                if (temp) break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            finalClose(connection,ps,rs);
        }
        return temp;
    }
    public PushContent selectPasswordGetArticle(String password){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            String sql = "select * from push_content where password = " + password;

            connection = DBUtil.getConnection();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            if (rs.next()){
                return getContent(rs);
            }else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            finalClose(connection,ps,rs);
        }
        return null;
    }

    private void finalClose(Connection connection, PreparedStatement ps, ResultSet rs){
        try {
            if (rs != null){
                rs.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            if (ps != null){
                ps.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            if (connection != null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private PushContent getContent(ResultSet rs){
        PushContent push = new PushContent();

        try {
            push.setId(rs.getInt("id"));
            push.setContent(rs.getString("content"));
            push.setTime(rs.getString("time"));
            push.setImgURL(rs.getString("imgURL"));
            push.setHidename(rs.getInt("hidename"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return push;
    }

    public static void main(String[] args) throws Exception {
        PushServicesDao pushServicesDao = new PushServicesDao();
        PushContent pushContent = new PushContent();
        pushContent.setContent("content");
        pushContent.setTime("2018-01-01 23:23:23");
        pushContent.setHidename(0);

        System.out.println(pushServicesDao.addArticle(pushContent));
    }
}

