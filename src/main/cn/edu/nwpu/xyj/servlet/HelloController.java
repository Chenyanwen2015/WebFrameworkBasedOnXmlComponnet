package cn.edu.nwpu.xyj.servlet;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyj on 2016/4/10.
 */
@Controller
public class HelloController {
    private static Logger log = Logger.getLogger(HelloController.class);
    @RequestMapping("/greeting.action")
    public  ModelAndView greeting(@RequestParam(value="name", defaultValue="World") String name) {
        System.out.println("Hello " + name);
        log.info("测试日志");
        log.debug("debug");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userName", name);
        map.put("userName", "test");
        ModelAndView mav = new ModelAndView("/servlet/ServletDemo1");
        mav.addObject("account", "account -1");
        return mav;
    }

    @RequestMapping("/greetings.action")
    public  ModelAndView greetingHtml(@RequestParam(value="name", defaultValue="World") String name) {
        ModelAndView mav = new ModelAndView("/servlet/ServletDemo1");
        mav.addObject("account", "account greeting.html");
        return mav;
    }
}
