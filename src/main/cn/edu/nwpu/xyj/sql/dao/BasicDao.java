package cn.edu.nwpu.xyj.sql.dao;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by xyj on 2016/5/26.
 */
@Component
public class BasicDao {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Map<String,Object>> findByTableName(String tName){
        String sql = "select * from "+tName;
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }

    public static void main(String[] args) {
         ApplicationContext ctx = new FileSystemXmlApplicationContext("F:\\validations\\src\\main\\webapp\\WEB-INF\\applicationContext.xml");
         BasicDao bd = (BasicDao)ctx.getBean("basicDao");
    }
}
