package cn.edu.nwpu.xyj.xml.parser.html;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by xyj on 2016/5/21.
 */
public class HtmlUtil {
    public static void setHtmlHeaderDefault(HttpServletResponse response){
        response.setContentType("text/html");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
    }

    public static void setHtmlJsCssDefault(PrintWriter out,String ppName){
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println("  <HEAD>");
        out.println("<meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n");
        out.println("<TITLE>"+ppName+"</TITLE>");
        out.println("<link href=\"/bootstrap-3.3.5/dist/css/bootstrap.min.css\" rel=\"stylesheet\">\n"+
                "<link href=\"/selfdefine/css/default.css\" rel=\"stylesheet\">\n"+
                "<script src=\"//cdn.bootcss.com/jquery/1.11.3/jquery.min.js\"></script>\n"+
                "<script src=\"/bootstrap-3.3.5/dist/js/bootstrap.min.js\"></script>\n"+
                "<script src=\"/selfdefine/js/default.js\"></script>\n");
        out.println("</HEAD>");
        out.println("  <BODY>");
    }

    public static String getMenuHead(){
        return "<head>\n" +
                "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\n" +
                "    <title></title>\n" +
                "    <link href=\"http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                "    <link href=\"/selfdefine/css/left_menu.css\" rel=\"stylesheet\">\n" +
                "    <style>\n" +
                "\n" +
                "    </style>\n" +
                "<script src=\"http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js\"></script>\n" +
                "<script src=\"http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js\"></script>"+
                "</head>";
    }

    public static String getpreMenu(){
        return "<div class=\"container-fluid\">\n" +
                "    <div class=\"row\">\n" +
                "        <div class=\"col-md-2\">\n" +
                "            <ul id=\"main-nav\" class=\"nav nav-tabs nav-stacked\" style=\"\">\n" +
                "                <li class=\"active\">\n" +
                "                    <a href=\"#\">\n" +
                "                        <i class=\"glyphicon glyphicon-th-large\"></i>\n" +
                "                        首页\n" +
                "                    </a>\n" +
                "                </li>";
    }

    public static String endMenu(){
        return
                "        </div>\n" +
                "        <div class=\"col-md-10\">\n" +
                "            主窗口\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
    }

    public static String getTable(String js){
        return "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "    <link href=\"http://cdn.bootcss.com/bootstrap/3.2.0/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                "    <link rel=\"stylesheet\" href=\"http://cdn.bootcss.com/bootstrap-table/1.10.1/bootstrap-table.min.css\">\n" +js+
                "    <script src=\"http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js\"></script>\n" +
                "    <script src=\"http://cdn.bootcss.com/bootstrap/3.2.0/js/bootstrap.min.js\"></script>\n" +
                "    <!-- Latest compiled and minified JavaScript -->\n" +
                "    <script src=\"http://cdn.bootcss.com/bootstrap-table/1.10.1/bootstrap-table.min.js\"></script>\n" +
                "\n" +
                "    <!-- Latest compiled and minified Locales -->\n" +
                "    <script src=\"http://cdn.bootcss.com/bootstrap-table/1.10.1/locale/bootstrap-table-zh-CN.min.js\"></script>\n" +
                "    <script src=\"/selfdefine/js/table.js\"></script>"+
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<div id=\"toolbar\"></div>\n" +
                "<table id=\"tradeList\" data-pagination=‘true’>\n" +
                "</table>\n" +
                "<ul class=\"pagination\">\n" +
                "  <li><a href=\"#\">&laquo;</a></li>\n" +
                "  <li><a href=\"#\">1</a></li>\n" +
                "  <li><a href=\"#\">2</a></li>\n" +
                "  <li><a href=\"#\">3</a></li>\n" +
                "  <li><a href=\"#\">4</a></li>\n" +
                "  <li><a href=\"#\">5</a></li>\n" +
                "  <li><a href=\"#\">&raquo;</a></li>\n" +
                "</ul>"+
                "</body>\n" +
                "</html>";
    }
}
