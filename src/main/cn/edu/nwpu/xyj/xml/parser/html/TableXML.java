package cn.edu.nwpu.xyj.xml.parser.html;

import cn.edu.nwpu.xyj.xml.parser.base.method.BootStrapXML;
import cn.edu.nwpu.xyj.xml.parser.base.method.HtmlGenerator;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by xyj on 2016/5/25.
 */
public class TableXML implements HtmlGenerator{
        private BootStrapXML bsx;
    private StringBuffer finalString;
    private List<Map<String ,String>> col = new ArrayList<Map<String, String>>();
    private String url;
    private static Logger log = Logger.getLogger(TableXML.class);

    public TableXML(BootStrapXML bsx) {
        this.bsx = bsx;
        this. finalString = new StringBuffer("");

    }

    public String  getJsonObject(){
        StringBuffer sb = new StringBuffer("[");
        for( Map map :this.col){
            sb.append("{");
            sb.append("field:\""+map.get("field")+"\",");
            sb.append("title:\""+map.get("title")+"\"");
            sb.append("},\n");
        }
        sb.delete(sb.lastIndexOf(","),sb.lastIndexOf(",")+1);
        sb.append("]");
        return sb.toString();
    }

    private boolean parserTable(org.dom4j.Element currentRoot){
        String tname = BootStrapXML.getNodeProperty(currentRoot,"tname");
        if(tname !=null){
            this.url="/"+this.bsx.getProjectName()+"/"+this.bsx.getApplicationName()+"/"+tname+".find";
            return true;
        }
        return false;
    }

    private boolean parserColumn(org.dom4j.Element currentRoot){
        return true;
    }
    public boolean checkComponent(org.dom4j.Element currentRoot,int i){
        if(currentRoot.getName().equals("container")){
            return parserTable(currentRoot);
        }
        else if(currentRoot.getName().equals("column")){
            String title = BootStrapXML.getNodeProperty(currentRoot,"title");
            String field = BootStrapXML.getNodeProperty(currentRoot,"field");
            if(title !=null && field !=null){
                Map<String,String> res = new HashMap<String, String>();
                res.put("title",title);
                res.put("field",field);
                this.col.add(res);
                return true;
            }
            return false;
        }
        return false;
    }

    public void recursiveTraverse(org.dom4j.Element currentRoot,int i){
        System.out.println("level:"+i+currentRoot.toString());
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

    public static void main(String[] args) {
        BootStrapXML bsx = new BootStrapXML("F:\\validations\\src\\main\\xml\\demo\\table.xml","demo","table");
        TableXML tx = new TableXML(bsx);
        tx.recursiveTraverse(bsx.getDoc().getRootElement(),0);
        System.out.println(tx.getHtmlStr());
    }

    public String getHtmlStr() {
        this.recursiveTraverse(this.bsx.getDoc().getRootElement(),0);

        this.finalString.append("<script type=\"application/javascript\">\n" +
                "\n" +
                "var url=\""+this.url+"\" ;\n"+
                "var columns="+this.getJsonObject()+";\n"+
                "    </script>");

        return HtmlUtil.getTable(this.finalString.toString());
    }

}
