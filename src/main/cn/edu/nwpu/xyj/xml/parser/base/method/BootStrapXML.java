package cn.edu.nwpu.xyj.xml.parser.base.method;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.dom4j.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xyj on 2016/5/21.
 */
public class BootStrapXML {
    private Document doc;
    private String projectName;
    private String applicationName;

    public BootStrapXML(String filePath, String projectName, String applicationName) {
        SAXReader saxReader = new SAXReader();
        saxReader.setEncoding("UTF-8");
        try {
            this.doc = saxReader.read(new File(filePath));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        this.projectName = projectName;
        this.applicationName = applicationName;
    }

    public BootStrapXML(String filePath) {
        SAXReader saxReader = new SAXReader();
        saxReader.setEncoding("UTF-8");
        try {
            this.doc = saxReader.read(new File(filePath));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    public static String getNodeProperty(Element currentRoot,String pName){
        if(currentRoot.attributeValue(pName) !=null )
            return currentRoot.attributeValue(pName);
        return null;
    }

    public String getContainerType(){
        String type =  BootStrapXML.getNodeProperty(this.getDoc().getRootElement(),"type");
        if(type !=null && this.getDoc().getRootElement().getName().equals("container")){
            return type;
        }
        return null;
    }

    public static boolean compareNodeProperty(Element currentRoot,String pName,String self){
        if(getNodeProperty(currentRoot,pName)!=null)
            return getNodeProperty(currentRoot,pName).equals(self);
        return false;
    }

    public static void recursiveFindByAttr(org.dom4j.Element currentRoot,int i,String attr,String value,List<org.dom4j.Element> res){
        System.out.println("level:"+i+currentRoot.getName());
        if(currentRoot !=null) {
            if(compareNodeProperty(currentRoot,attr,value))
                res.add(currentRoot);
            List list = currentRoot.elements();
            if (list.size() > 0) {
                //this.newPage(currentRoot);
                for (Iterator its = list.iterator(); its.hasNext(); ) {
                    org.dom4j.Element chileEle = (org.dom4j.Element) its.next();
                    recursiveFindByAttr(chileEle, i + 1,attr,value,res);
                }
            }
        }
    }

    public static List<org.dom4j.Element> searchByAttr(org.dom4j.Element currentRoot,String attr,String value){
        List<org.dom4j.Element> res= new ArrayList<org.dom4j.Element>();
        recursiveFindByAttr(currentRoot,0,attr,value,res);
        return res;
    }

    public BootStrapXML() {

    }



    public Document getDoc() {
        return doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
