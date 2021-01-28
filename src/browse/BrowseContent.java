package browse;

import Dao.DBUtil;
import Dao.PushServicesDao;
import push.PushContent;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/BrowseContent")
public class BrowseContent extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        doGet(request,response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        String password;
        PushServicesDao pushServicesDao = null;
        PrintWriter out = null;
        PushContent pushContent = null;

        try {
            out = response.getWriter();
            pushServicesDao = new PushServicesDao();
            password = request.getParameter("password");
            pushContent = pushServicesDao.selectPasswordGetArticle(password);

            if (password != null) {
                if (pushServicesDao.selectPassword(password)) {
                    DBUtil.getJSONObject(out, 1, true, "密语错误呢，什么也找不到哦！");
                }else if (password.trim().equals("")){
                    DBUtil.getJSONObject(out,-1,false,"错误：表单不完全");
                }else {
                    DBUtil.getJSONObject(out,0,true,"恭喜，该密语可用");
                }
            }else {
                DBUtil.getJSONObject(out, -1, false, "错误：表单不完全");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
