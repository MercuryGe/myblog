package services;

import dao.ArticleInfoDao;
import models.ArticleInfo;
import models.vo.ArticleInfoVO;
import utils.ResultJSONUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/list")
public class ListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int succ = -1;
        String msg = "";
        List<ArticleInfoVO> list = null;
        // 1、获取参数
        int page = Integer.parseInt(req.getParameter("page"));
        // 每页显示的条数
        int psize = Integer.parseInt(req.getParameter("psize"));
        // 2、处理业务逻辑
        ArticleInfoDao articleInfoDao = new ArticleInfoDao();
        try {
            list = articleInfoDao.getListByPage(page,psize);
            succ = 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // 3、返回结果给前端
        HashMap<String, Object> result = new HashMap<>();
        result.put("succ", succ);
        result.put("msg",msg);
        result.put("list",list);
        ResultJSONUtils.write(resp,result);
    }
}
