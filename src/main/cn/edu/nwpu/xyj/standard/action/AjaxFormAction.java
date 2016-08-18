package cn.edu.nwpu.xyj.standard.action;

import org.omg.CORBA.Object;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyj on 2016/4/26.
 */
@Controller
public class AjaxFormAction {
    @RequestMapping("/standard/ajax_form.action")
    public @ResponseBody Map<String,String> login(String fstring) {
        Map<String,String> map = new HashMap<String, String>();
        map.put("key",fstring);
        return map;
    }
}
