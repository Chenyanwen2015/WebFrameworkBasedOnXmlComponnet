package cn.edu.nwpu.xyj.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xyj on 2016/5/21.
 */
public class LogUtil {
    public static void printURIInfoDefault(Logger log,HttpServletRequest request,String pName,String ppName){
        log.info("请求的地址："+request.getRequestURI());
        log.info("当前的项目："+pName);
        log.info("当前的业务："+ppName);
    }
}
