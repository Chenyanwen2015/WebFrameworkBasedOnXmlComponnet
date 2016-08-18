package cn.edu.nwpu.xyj.controller;

import cn.edu.nwpu.xyj.serivice.ControllerService;
import cn.edu.nwpu.xyj.util.LogUtil;
import cn.edu.nwpu.xyj.xml.parser.html.HtmlUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xyj on 2016/4/25.
 */
@Controller
public class URLDispatchAction {
    private static Logger log = Logger.getLogger(URLDispatchAction.class);

    @Resource
    private ControllerService cs;

    public ControllerService getCs() {
        return cs;
    }

    public void setCs(ControllerService cs) {
        this.cs = cs;
    }

    @RequestMapping(value="/{pName}/{ppName}.html")
    public void getHtmlPage(HttpServletRequest request, HttpServletResponse response,@PathVariable("pName")String pName,
                            @PathVariable("ppName")String ppName) {

        LogUtil.printURIInfoDefault(log,request,pName,ppName);
        if(!ppName.equals("frame"))
            HtmlUtil.setHtmlHeaderDefault(response);

        try {
            if(!ppName.equals("welcome")){
                if(!ppName.equals("frame") && !ppName.equals("table"))
                    HtmlUtil.setHtmlJsCssDefault(response.getWriter(),ppName);
                this.cs.setHtmlBody(request,response.getWriter(),pName,ppName);
            }
            else
                response.getWriter().write("欢迎您，请点击左侧目录！");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value="/{pName}/{ppName}.action")
    public @ResponseBody Map<String,Object> handleAction(HttpServletRequest request, HttpServletResponse response,
                                    @PathVariable("pName")String pName,@PathVariable("ppName")String ppName,
                                                         String fString,String business) {
        LogUtil.printURIInfoDefault(log,request,pName,ppName);
        return this.cs.setAjaxBody(request,business,fString,pName,ppName);
    }

    @RequestMapping(value="/{pName}/{ppName}/{tName}.find")
    public @ResponseBody
    Map<String,Object> handleJsonData(HttpServletRequest request, HttpServletResponse response,
                                            @PathVariable("pName")String pName, @PathVariable("ppName")String ppName,
                                            @PathVariable("tName")String tName,
                                            String fString, String business) {
        LogUtil.printURIInfoDefault(log,request,pName,ppName);
        Map<String,Object> res = new HashMap<String, Object>();
        res.put("rows",this.cs.getDataFind(tName));
        res.put("success",true);
        return res;
    }

    public static void main(String[] args) {
    }
}
