package cn.edu.nwpu.xyj.xml.parser.html;

import cn.edu.nwpu.xyj.xml.parser.base.method.BootStrapXML;
import cn.edu.nwpu.xyj.xml.parser.base.method.HtmlGenerator;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.List;

/**
 * Created by xyj on 2016/5/25.
 */
public class CollapseMenuXML implements HtmlGenerator {
    private BootStrapXML bsx;
    private StringBuffer finalString;
    private static Logger log = Logger.getLogger(CollapseMenuXML.class);
    private int ids = 1000;
    public CollapseMenuXML(BootStrapXML bsx) {
        this.bsx = bsx;
        this.finalString = new StringBuffer();
        this.finalString.append("<html>");
    }

    private boolean parserContainer(org.dom4j.Element currentRoot){
        String type =  BootStrapXML.getNodeProperty(currentRoot,"type");
        log.info("type:"+type);
        return true;
    }

    private boolean parserMenu(org.dom4j.Element currentRoot){
        String title =  BootStrapXML.getNodeProperty(currentRoot,"title");
        String classes =  BootStrapXML.getNodeProperty(currentRoot,"class");
        this.ids++;
        if(title !=null ){
            if(classes == null)
                classes = "nav-header collapsed";
            this.finalString.append("<a href=\"#"+String.valueOf("menu_id_"+this.ids)+"\" class=\""+classes+"\" data-toggle=\"collapse\">\n" +
                    "                        <i class=\"glyphicon glyphicon-cog\"></i>\n" +title+
                    "\n" +
                    "                        <span class=\"pull-right glyphicon glyphicon-chevron-down\"></span>\n" +
                    "                    </a>\n" +
                    "                    <ul id=\""+String.valueOf("menu_id_"+this.ids)+"\" class=\"nav nav-list collapse secondmenu\" style=\"height: 0px;\">");
            return true;
        }
        log.info("属性字段可能为空："+currentRoot.toString());
        return false;
    }

    private boolean parserItem(org.dom4j.Element currentRoot){
        String title =  BootStrapXML.getNodeProperty(currentRoot,"title");
        String classes =  BootStrapXML.getNodeProperty(currentRoot,"class");
        String name =  BootStrapXML.getNodeProperty(currentRoot,"name");
        String target =  BootStrapXML.getNodeProperty(currentRoot,"target");
        String result =  BootStrapXML.getNodeProperty(currentRoot,"result");
        if(target ==null)
            target ="";
        if(result == null)
            result ="";
        if(title !=null  && name !=null){
            if(classes == null)
                classes = "glyphicon glyphicon-user";
            this.finalString.append("<li><a target=\""+target+"\" href=\""+result+"\"  ><i class=\""+classes+"\" name='"+name+"'></i>"+title+"</a></li>\n");
            return true;
        }
        log.info("属性字段可能为空："+currentRoot.toString());
        return false;
    }

    public boolean checkComponent(org.dom4j.Element currentRoot,int i){
        if(currentRoot.getName().equals("container")){
            parserContainer(currentRoot);
            return true;
        }
        else if(currentRoot.getName().equals("menu")){
            parserMenu(currentRoot);
            return true;
        }
        else if(currentRoot.getName().equals("item")){
            parserItem(currentRoot);
            return true;
        }
        return false;
    }

    public void recursiveTraverse(org.dom4j.Element currentRoot,int i){
        System.out.println("level:"+i+currentRoot.getName());
        if(this.checkComponent(currentRoot, i)) {
            List list = currentRoot.elements();
            if (list.size() > 0) {
                for (Iterator its = list.iterator(); its.hasNext(); ) {
                    org.dom4j.Element chileEle = (org.dom4j.Element) its.next();
                    this.recursiveTraverse(chileEle, i + 1);
                }
                //addend
                if (currentRoot.getName().equals("menu")) {
                    this.finalString.append("</ul>");
                    return ;
                }
                //add total end
                if (currentRoot.getName().equals("container")) {
                    this.finalString.append(HtmlUtil.endMenu());
                    return ;
                }
            }
        }
    }

    public String getHtmlStr() {
        this.finalString.append(HtmlUtil.getMenuHead());
        this.finalString.append(HtmlUtil.getpreMenu());
        this.recursiveTraverse(this.bsx.getDoc().getRootElement(),0);
        return this.finalString.toString();
    }

    public static void main(String[] args) {
        BootStrapXML bsx = new BootStrapXML("F:\\validations\\src\\main\\xml\\demo\\leftmenu.xml","demo","leftmenu");
        CollapseMenuXML tx = new CollapseMenuXML(bsx);
        System.out.println(tx.getHtmlStr());
    }
}


