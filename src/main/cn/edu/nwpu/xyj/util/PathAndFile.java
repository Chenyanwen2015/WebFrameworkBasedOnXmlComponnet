package cn.edu.nwpu.xyj.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by xyj on 2016/4/25.
 */
public class PathAndFile {
    private static Logger log = Logger.getLogger(PathAndFile.class);
    public static String getXMLPathByURI(HttpServletRequest request,String pName,String ppName){
        StringBuffer sb = new StringBuffer();
        sb.append(request.getSession().getServletContext().getRealPath("/"));
        sb.append("WEB-INF\\classes\\xml\\"+pName+"\\"+ppName+".xml");
        log.info(request.getRequestURI()+"对应的xml是："+sb.toString());
        if((new File(sb.toString())).exists())
            return sb.toString();
        return "404，请联系系统管理员！";
    }
}
