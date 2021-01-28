package speak;

import Dao.DBUtil;
import Dao.PushServicesDao;
import push.PushContent;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//用于把提交的文章储存到数据库中
@WebServlet("/SpeakSubmit")
public class SpeakSubmit extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){

        String password;
        String content;
        String time;
        String img;
        int hidename;

        try {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

            password = request.getParameter("password");
            content = request.getParameter("content");
            time = dateFormat.format(date);
            img = request.getParameter("img");//在不用上传图片的情况下img代替imgSelect
            hidename = Integer.parseInt(request.getParameter("hidename"));

            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8;");
            PrintWriter out = response.getWriter();
            PushServicesDao contentDao = new PushServicesDao();

            PushContent pushContent = new PushContent();
            if (password == null)pushContent.setPassword("0 0");
            else pushContent.setPassword(password);
            pushContent.setContent(content);
            pushContent.setTime(time);
            pushContent.setImgURL(img);
            pushContent.setHidename(hidename);

            if (content != null){
                //返回code：-1
                if (content.trim().equals("")){
                    DBUtil.getJSONObject(out,-1,false,"错误：表单不完全");
                } else if (contentDao.selectPassword(password)){//返回code：-4
                    DBUtil.getJSONObject(out,1,true,"密语已被使用过了");
                }else if (password.contains(" ")){
                    DBUtil.getJSONObject(out,-2,false,"错误：表单无效");
                } else {
                    DBUtil.getJSONObject(out,0,true,DBUtil.getJSONArray(contentDao.addArticle(pushContent)));
                }
            }else {
                DBUtil.getJSONObject(out,-1,false,"表单不完全");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
