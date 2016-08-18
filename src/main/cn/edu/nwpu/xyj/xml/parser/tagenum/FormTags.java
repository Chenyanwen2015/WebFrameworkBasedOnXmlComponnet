package cn.edu.nwpu.xyj.xml.parser.tagenum;

import org.dom4j.Element;

/**
 * Created by xyj on 2016/5/23.
 */
public enum FormTags {
    CONTAINER("container","基本前端组件标示"),
    DISPLAY("display"),TITLE("title"),STYLE("style"),
    ITEM("item","一般input标签"),
    TYPE("type"),TIP("tip"),REQUIRE("require"),NAME("name"),
    BUTTON("button","具有ajax动作的按钮");

    private String tagName;
    private String description;

    private FormTags(String name){
        this.tagName = name;
    }

    private FormTags(String description, String tagName) {
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
