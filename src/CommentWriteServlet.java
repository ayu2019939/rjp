import postapp.DataBaseAccess;
import postapp.OracleAccess;
import postapp.exception.EmptyInputtedException;
import properties.CreateDataBaseInstance;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CommentWriteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String replyNumber = request.getParameter("replynumber");
        String postNumber = request.getParameter("postnumber");
        String content = request.getParameter("content");
        String pw = request.getParameter("pw");
        String author = request.getParameter("author");

        // 入力されたパラメータが空白の場合例外発生
        if(postNumber.equals("") || content.equals("") || pw.equals("")) {
            throw new EmptyInputtedException();
        }

        try {
        	 DataBaseAccess access = (DataBaseAccess)CreateDataBaseInstance.getInstance("instance"); 

            if(author.equals("")) {
                access.commentInsert(postNumber, "名無し", pw, content);
            } else {
                access.commentInsert(postNumber, author, pw, content);
            }

            access.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("postview?postnumber="+postNumber+"&isedit=false");
    }
}
