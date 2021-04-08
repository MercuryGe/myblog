package services;

import com.sun.deploy.net.HttpResponse;
import dao.ArticleInfoDao;
import utils.ResultJSONUtils;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/mydel")
public class MyDeleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int succ = -1;
        String msg = "";

        // 1、从前端获取参数
        int id = Integer.parseInt(request.getParameter("id"));
        // 2、逻辑业务处理
        ArticleInfoDao articleInfoDao = new ArticleInfoDao();
        try{
            succ = articleInfoDao.del(id);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

        // 3、将结果返回给前端
        HashMap<String, Object> result = new HashMap<>();
        result.put("succ", succ);
        result.put("msg",msg);
        ResultJSONUtils.write(response,result);
    }
}
