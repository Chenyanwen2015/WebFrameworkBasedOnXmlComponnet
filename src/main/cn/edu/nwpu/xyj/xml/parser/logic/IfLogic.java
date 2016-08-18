package cn.edu.nwpu.xyj.xml.parser.logic;

import cn.edu.nwpu.xyj.util.ActionUtil;
import cn.edu.nwpu.xyj.util.ReflectionClass;
import cn.edu.nwpu.xyj.xml.parser.base.method.BootStrapXML;
import cn.edu.nwpu.xyj.xml.parser.base.method.LogicGenerator;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.Object;

/**
 * Created by xyj on 2016/5/21.
 */
public class IfLogic implements LogicGenerator {
    private String business;
    private Map<String,Object> fString;
    private BootStrapXML bsx;
    private Element targetNode =null;
    private Element resultNode = null;
    private Map<String,Object> variables;
    private static Logger log = Logger.getLogger(IfLogic.class);
    public String getBusiness() {
        return business;
    }
    private Map<String,Object> resMap;

    public void setBusiness(String business) {
        this.business = business;
    }

    public IfLogic(){

    }

    public IfLogic(String business, String fString, BootStrapXML bsx) {
        this.business = business;
        this.fString = ActionUtil.str2Map(fString);
        this.bsx = bsx;

        List<Element> res = BootStrapXML.searchByAttr(this.bsx.getDoc().getRootElement(),"name",business);
        if(res !=null && res.size()>0)
            this.targetNode = res.get(0);
        this.variables = new HashMap<String, Object>();
        resMap = new HashMap<String, Object>();
    }

    public IfLogic(String filePath, String projectName, String applicationName, String business, String fString) {
        this.bsx = new BootStrapXML(filePath,projectName,applicationName);
        this.business = business;
        this.fString = ActionUtil.str2Map(fString);
        List<Element> res = BootStrapXML.searchByAttr(this.bsx.getDoc().getRootElement(),"name",business);
        if(res !=null && res.size()>0)
            this.targetNode = res.get(0);
        this.variables = new HashMap<String, Object>();
        resMap = new HashMap<String, Object>();
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }

    public Map<String, Object> getfString() {
        return fString;
    }

    public void setfString(Map<String, Object> fString) {
        this.fString = fString;
    }

    public BootStrapXML getBsx() {
        return bsx;
    }

    public void setBsx(BootStrapXML bsx) {
        this.bsx = bsx;
    }

    public Element getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(Element targetNode) {
        this.targetNode = targetNode;
    }

    public Element getResultNode() {
        return resultNode;
    }

    public void setResultNode(Element resultNode) {
        this.resultNode = resultNode;
    }

    private void putVariables(String key, Object value){
        this.variables.put(key,value);
    }

    private boolean parserExpr(String split,String expr) {
        if (split.equals("=") || split.equals("==")) {
            String[] name_value = expr.split(split);
            if (name_value.length == 2) {
                if (split.equals("=")) {
                    this.putVariables(name_value[0], ReflectionClass.getLocalMethodReturn(name_value[1], this.fString, this.variables));
                    return true;
                }
                if (split.equals("==")) {
                    Object res1 = ReflectionClass.findVlueByName(this.fString, this.variables, name_value[0]);
                    Object res2 = ReflectionClass.getLocalMethodReturn(name_value[1], this.fString, this.variables);
                    if (res1 != null)
                        return res1.equals(res2);
                }
            }
        }
        return false;
    }

    private boolean checkExpr(Element ele){
        if(ele.attributeValue("expr")!=null){
            return true;
        }
        return false;
    }



    private boolean findAllIf(Element currentRoot){
        List<Element> list = currentRoot.elements();
        String nodeName = "none";
        boolean currentExpr = true;
        for(Element temp : list){
            log.info("解析的XML标签："+temp.toString());
            if(temp.getName().equals("assign")){
                handleAssign(temp);
            }
            else if(temp.getName().equals("if")){
                if(nodeName.equals("none") || nodeName.equals("if")||
                        nodeName.equals("elseif") || nodeName.equals("else")){
                    nodeName="if";
                    if(handleIf(temp) == 1){
                        currentExpr = true;
                        //result在其子元素，递归后直接返回;否则需要往下遍历寻找result
                        boolean res = findAllIf(temp);
                        if(res)
                            return res;
                    }
                    else{
                        currentExpr = false;
                    }
                }
                else{
                    log.info("if语句位置错误："+temp.toString());
                    return false;
                }
            }
            else if(temp.getName().equals("elseif")){
                if(nodeName.equals("elseif") || nodeName.equals("if")){
                    if(!currentExpr){
                        nodeName="elseif";
                        if(handleElseIf(temp) == 1){
                            currentExpr = true;
                            boolean res = findAllIf(temp);
                            if(res)
                                return res;
                        }
                        else{
                            currentExpr = false;
                        }
                    }

                    //判断条件为真时说明result在其子元素，递归后直接返回
                }
                else{
                    log.info("elseif语句位置错误："+temp.toString());
                    return false;
                }
            }
            else if(temp.getName().equals("else")){
                if(nodeName.equals("elseif") || nodeName.equals("if")){
                    if(!currentExpr){
                        nodeName="elseif";
                        if(handleElse(temp) == 1){
                            boolean res = findAllIf(temp);
                            if(res)
                                return res;
                        }
                        //不需要判断，递归后直接返回
                    }
                }
                else{
                    log.info("else语句位置错误："+temp.toString());
                    return false;
                }
            }
            else if(temp.getName().equals("result")){
                this.resultNode = temp;
                this.handleResult(temp);
                return true;
            }
            else{
                log.info("上述标签已经被忽略！");
            }
        }
        return false;
    }

    private int handleAssign(Element temp){
        if(this.checkExpr(temp)){
            if(this.parserExpr("=",temp.attributeValue("expr")))
                return 1;
        }
        return 0;
    }

    private int handleIf(Element temp){
        if(this.checkExpr(temp)){
            if(this.parserExpr("==",temp.attributeValue("expr")))
                return 1;
        }
        return 0;
    }

    private int handleElseIf(Element temp){
        if(this.checkExpr(temp)){
            if(this.parserExpr("==",temp.attributeValue("expr")))
                return 1;
        }
        return 0;
    }

    private int handleElse(Element temp){
        return 1;
    }

    private Map<String,Object> getAjaxJsonReturn(String type,String or){
        resMap.put("success",true);
        resMap.put("type",type);
        resMap.put("or",or);
        return resMap;
    }
    private int handleResult(Element temp){
        String type = BootStrapXML.getNodeProperty(temp,"type");
        String content = BootStrapXML.getNodeProperty(temp,"content");
        if(type != null && content !=null){
            this.getAjaxJsonReturn(type,content);
            return 1;
        }
        return 0;
    }


    public Map<String, Object> doLogic() {
        if(this.targetNode != null){
            if(this.findAllIf(this.getTargetNode())){
                return this.resMap;
            }
        }
        return null;
    }

    public Map<String, Object> getResMap() {
        return resMap;
    }

    public void setResMap(Map<String, Object> resMap) {
        this.resMap = resMap;
    }

    public static void main(String[] args) {
        IfLogic il = new IfLogic("F:\\validations\\src\\main\\xml\\demo\\login.xml","demo","login" ,"login","user_name=xuyuanjia&password=123456");
        System.out.println(il.doLogic());
    }
}
