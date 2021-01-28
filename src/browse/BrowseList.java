package browse;

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
import java.util.ArrayList;
import java.util.Random;

@WebServlet("/BrowseList")
public class BrowseList extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        ArrayList<String> list;
        PushServicesDao pushServicesDao;

        try {
            PrintWriter out = response.getWriter();
            pushServicesDao = new PushServicesDao();
            list = pushServicesDao.getArticle("id","password","BINARY\"0 0\"");
            Random random = new Random();
            int r = random.nextInt((list.size() * (list.size() + 1))/2);
            int gotId = (int) (Math.pow(2 * r + 0.25,0.5) - 0.5);
            DBUtil.getJSONObject(out,0,true,DBUtil.getJSONArray(Integer.parseInt(list.get(gotId))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}