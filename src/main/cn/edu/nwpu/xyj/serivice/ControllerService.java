package cn.edu.nwpu.xyj.serivice;

import cn.edu.nwpu.xyj.sql.dao.BasicDao;
import cn.edu.nwpu.xyj.util.PathAndFile;
import cn.edu.nwpu.xyj.xml.parser.base.method.BootStrapXML;
import cn.edu.nwpu.xyj.xml.parser.base.method.LogicGenerator;
import cn.edu.nwpu.xyj.xml.parser.html.*;
import cn.edu.nwpu.xyj.xml.parser.logic.IfLogic;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ObjectInput;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xyj on 2016/4/25.
 */
@Component
public class ControllerService {
    @Resource
    private BasicDao bd;

    public BasicDao getBd() {
        return bd;
    }

    public void setBd(BasicDao bd) {
        this.bd = bd;
    }

    public final void setHtmlBody(HttpServletRequest request, PrintWriter out, String pName, String ppName){
        String path= PathAndFile.getXMLPathByURI(request,pName,ppName);
        if(path.contains("404")){
            out.println(path);
            out.flush();
            out.close();
            return ;
        }

        BootStrapXML bsx = new BootStrapXML(path,pName,ppName);
        String type = bsx.getContainerType();
        if(type !=null) {
            if (type.equals("table")) {
                out.write(new TableXML(bsx).getHtmlStr());
            } else if (type.equals("form")) {
                HtmlUtil.setHtmlJsCssDefault(out,ppName);
                out.println((new FormXML(path,pName,ppName)).generateHTML().toString());
                out.println("  </BODY>");
                out.println("</HTML>");

            } else if (type.equals("frame")) {
                out.write(new FrameXML(bsx).getHtmlStr());
            } else if (type.equals("collapse_menu")) {
                out.write(new CollapseMenuXML(bsx).getHtmlStr());
            } else {
                out.write("type无法解析！");
                out.flush();
                out.close();
                return;
            }
        }
        else{
            out.write("配置错误，请联系管理员！");
        }
        out.flush();
        out.close();
    }

    public final Map<String,Object> setAjaxBody(HttpServletRequest request,String business, String fString, String pName, String ppName){
        String path= PathAndFile.getXMLPathByURI(request,pName,ppName);
        if(path.contains("404")){
            Map<String,Object> res = new HashMap<String,Object>();
            res.put("success",true);
            res.put("or","配置错误，请联系管理员！");
            res.put("type","html");
            return res;
        }
        BootStrapXML bsx = new BootStrapXML(path,pName,ppName);
        String targetName =null;
        LogicGenerator lg = null;
        if(fString.contains("login")){
            targetName = "login";
            lg = new IfLogic(targetName,fString,bsx);
        }
        else{
            targetName="";
        }

        return lg.doLogic();
    }

    public final List<Map<String,Object>> getDataFind(String tName){
        return this.bd.findByTableName(tName);
    }
}
