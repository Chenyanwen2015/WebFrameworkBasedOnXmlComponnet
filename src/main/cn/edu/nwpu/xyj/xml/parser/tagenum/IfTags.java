package cn.edu.nwpu.xyj.xml.parser.tagenum;

import org.dom4j.Element;

/**
 * Created by xyj on 2016/5/23.
 */
public enum IfTags {
    IF("if"),ElSEIF("elseif"),ELSE("else"),ASSIGN("assign"),RESULT("result"),
    EXPR("expr"),TYPE("type"),HREF("href"),TARGET("target");
    private String tagName;
    private String description;
    private IfTags(String name){
        this.tagName = name;
    }

    private IfTags(String description, String tagName) {
        this.description = description;
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public String getDescription() {
        return description;
    }

    public boolean equals(String another){
        return this.tagName.equals(another);
    }

    public boolean equals(Element another){
        return this.tagName.equals(another.getName());
    }

    @Override
    public String toString(){
        return this.tagName;
    }
}
