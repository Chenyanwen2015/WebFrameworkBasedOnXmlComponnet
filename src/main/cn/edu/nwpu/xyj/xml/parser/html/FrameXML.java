package cn.edu.nwpu.xyj.xml.parser.html;

import cn.edu.nwpu.xyj.xml.parser.base.method.BootStrapXML;
import cn.edu.nwpu.xyj.xml.parser.base.method.HtmlGenerator;
import org.apache.log4j.Logger;
import org.dom4j.Element;

import java.util.Iterator;
import java.util.List;

/**
 * Created by xyj on 2016/5/23.
 */
public class FrameXML implements HtmlGenerator {
    private BootStrapXML bsx;
    private StringBuffer finalString;
    private static Logger log = Logger.getLogger(FrameXML.class);

    public FrameXML(BootStrapXML bsx) {
        this.bsx = bsx;
        this.finalString = new StringBuffer();
        this.finalString.append("");
    }

    private void parserFrameSet(org.dom4j.Element currentRoot){
        String rows =  BootStrapXML.getNodeProperty(currentRoot,"rows");
        String cols =  BootStrapXML.getNodeProperty(currentRoot,"cols");
        if(rows == null && cols ==null || rows != null && cols !=null){
            log.info("rows或者cols两个属性必须要存在一个："+currentRoot.toString());
            return;
        }
        if(rows !=null){
            this.finalString.append("<frameset rows=\""+rows+"\">");
            return ;
        }
        if(cols !=null){
            this.finalString.append("<frameset cols=\""+cols+"\">");
            return ;
        }
    }

    private void parserFrame(org.dom4j.Element currentRoot){
        String src = BootStrapXML.getNodeProperty(currentRoot,"src");
        String name = BootStrapXML.getNodeProperty(currentRoot,"name");
        if(src == null){
            log.info("frame的src属性不存在："+currentRoot.toString());
            return ;
        }
        if(name == null){
            log.info("frame的name属性不存在："+currentRoot.toString());
            return ;
        }
        this.finalString.append("<frame src=\""+src+"\" name=\""+name+"\">");
    }

    public boolean checkComponent(org.dom4j.Element currentRoot,int i){
        if(currentRoot.getName().equals("frameset")){
            parserFrameSet(currentRoot);
            return true;
        }
        else if(currentRoot.getName().equals("frame")){
            parserFrame(currentRoot);
            return true;
        }
        else if(currentRoot.getName().equals("container")){
            return true;
        }
        return false;
    }

    public void recursiveTraverse(org.dom4j.Element currentRoot,int i){
        log.info("level:"+i+currentRoot.getName());
        if(this.checkComponent(currentRoot, i)) {
            List<Element> list = currentRoot.elements();
            if (list.size() > 0 ) {
                //this.newPage(currentRoot);
                for (Element chileEle:list ) {
                    this.recursiveTraverse(chileEle, i + 1);
                }
                if(currentRoot.getName().equals("frameset"))
                    this.finalString.append("</frameset>");
            }
        }
    }
    public String getHtmlStr() {
        this.recursiveTraverse(this.bsx.getDoc().getRootElement(),0);
        return this.finalString.toString();
    }

    public static void main(String[] args) {
        BootStrapXML bsx = new BootStrapXML("F:\\validations\\src\\main\\xml\\demo\\df.xml","demo","frame");
        FrameXML tx = new FrameXML(bsx);
        System.out.println(tx.getHtmlStr());
    }

}
