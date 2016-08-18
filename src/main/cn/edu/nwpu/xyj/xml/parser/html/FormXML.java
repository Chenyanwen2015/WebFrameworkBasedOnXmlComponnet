package cn.edu.nwpu.xyj.xml.parser.html;

import cn.edu.nwpu.xyj.xml.parser.base.method.BootStrapXML;
import cn.edu.nwpu.xyj.xml.parser.base.method.HtmlGenerator;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.List;

/**
 * Created by xyj on 2016/4/25.
 */
public class FormXML implements HtmlGenerator {
    private BootStrapXML bsx;
    private StringBuffer finalString;
    private String idPrefix="id_";
    private int idCount=0;

    public FormXML(String sName){
        this.bsx = new BootStrapXML(sName);
        this.finalString = new StringBuffer();
        this.finalString.append("");
    }

    public FormXML(String sName,String pName,String ppName){
        this.bsx = new BootStrapXML(sName,pName,ppName);
        this.finalString = new StringBuffer();
        this.finalString.append("");
    }

    public FormXML(BootStrapXML bsx){
        this.bsx = bsx;
        this.finalString = new StringBuffer();
        this.finalString.append("");
    }

    private void directSetProperty(Element currentRoot,String pName){
        if(currentRoot.attributeValue(pName) !=null )
            this.finalString.append(currentRoot.attributeValue(pName));
    }

    private boolean directSetProperty(Element currentRoot,String pName,boolean ifConstrain){
        if(currentRoot.attributeValue(pName) !=null && ifConstrain){
            this.finalString.append(currentRoot.attributeValue(pName));
            return true;
        }
        else if(!ifConstrain){
            this.finalString.append(currentRoot.attributeValue(pName));
            return true;
        }
        return false;
    }

    private void finalComplete(){
        this.finalString.append("</form>\n</div>");
    }

    private String getNodeID(){
        this.idCount++;
        return this.idPrefix+idCount;
    }

    //solve layout:
    private int checkLayout(Element currentRoot){
        String describe = BootStrapXML.getNodeProperty(currentRoot,"display");
        if(describe !=null){
            if(describe.equals("vertical_inner")){
                return 0;
            }
            else if(false){

            }
            return -1;
        }
        return -1;
    }

    public void parserContainer(Element currentRoot){
        if(this.checkLayout(currentRoot) ==0){
            this.finalString.append("<div class=\"container\" style=\"");
            this.directSetProperty(currentRoot,"style");
            this.finalString.append("\">\n");
            this.finalString.append("<form class=\"form-signin\">");
            this.finalString.append("<h2 class=\"form-signin-heading\">");
            this.directSetProperty(currentRoot,"title");
            this.finalString.append("</h2>\n");
        }
        else if(false){

        }
    }

    public void parserItem(Element currentRoot){
        String nodeId = this.getNodeID();
        this.finalString.append("<label for=\""+nodeId+"\" class=\"sr-only\">");
        this.directSetProperty(currentRoot,"tip");
        this.finalString.append("</label>");
        this.finalString.append("<input type=\"");
        if(this.directSetProperty(currentRoot,"type",true)){
            //<input type="email" id="inputEmail" class="form-control" placeholder="Email address" style="width: 100px;" required autofocus>
            this.finalString.append("\" id=\""+nodeId+"\" class=\"form-control\" placeholder=\"");
            this.directSetProperty(currentRoot,"tip");
            this.finalString.append("\" name=\"");
            this.directSetProperty(currentRoot,"name");
            this.finalString.append("\" ");
            if(BootStrapXML.compareNodeProperty(currentRoot,"required","true")){
                this.finalString.append("required ");
            }
            if(this.idCount == 1){
                this.finalString.append("autofocus ");
            }
            this.finalString.append(">\n");
        }
    }

    public void parserButton(Element currentRoot){
        //<button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        this.finalString.append("<button class=\"btn btn-lg btn-primary btn-block\" type=\"");
        if(this.directSetProperty(currentRoot,"type",true)){
            this.finalString.append("\" ");
            if(BootStrapXML.compareNodeProperty(currentRoot,"name","login")){
                this.finalString.append("name=\"");
                this.directSetProperty(currentRoot,"name");
                String url = "/"+this.bsx.getProjectName()+"/"+this.bsx.getApplicationName()+".action";
                this.finalString.append("\" onclick=\"ajaxFormFunction(this,'"+url+"');\" ");
            }
            else if(false){

            }
            this.finalString.append(">");
            this.directSetProperty(currentRoot,"tip");
            this.finalString.append("</button>\n");
        }
    }

    public boolean checkComponent(org.dom4j.Element currentRoot,int i){
        if(currentRoot.getName().equals("container")){
            parserContainer(currentRoot);
            return true;
        }
        else if(currentRoot.getName().equals("item")){
            parserItem(currentRoot);
            return true;
        }
        else if(currentRoot.getName().equals("button")){
            parserButton(currentRoot);
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
            }
        }
    }

    public StringBuffer generateHTML(){
        this.recursiveTraverse(this.bsx.getDoc().getRootElement(),0);
        this.finalComplete();
        return this.finalString;
    }

    public String getHtmlStr(){
        return this.generateHTML().toString();
    }
    public static void main(String[] args) {
        FormXML fx = new FormXML("F:\\validations\\src\\main\\xml\\demo\\login.xml");
        System.out.println(fx.generateHTML().toString());
    }
}
