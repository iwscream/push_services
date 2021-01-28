package speak;

import Dao.DBUtil;
import Dao.PushServicesDao;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/CheckOutPassword")
public class CheckOutPassword extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        String password;
        password = request.getParameter("password");
        try {
            PrintWriter out = response.getWriter();
            PushServicesDao pushServicesDao = new PushServicesDao();
            if (password != null) {
                if (pushServicesDao.selectPassword(password)) {
                    DBUtil.getJSONObject(out, 1, false, "密语已被使用过了");
                }else {
                    DBUtil.getJSONObject(out,0,true,"恭喜，该密语可用");
                }
            }else {
                DBUtil.getJSONObject(out, -2, false, "错误：表单无效");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
