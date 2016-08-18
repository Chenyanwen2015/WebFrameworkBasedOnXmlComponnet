package cn.edu.nwpu.xyj.standard.action;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyj on 2016/4/25.
 */
@Controller
public class FormGeneratorAction {
    private static Logger log = Logger.getLogger(FormGeneratorAction.class);

    @RequestMapping("/form_generator.action")
    public void greeting(@RequestParam(value="name", defaultValue="World") String name) {

    }
}
