package cn.edu.nwpu.xyj.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyj on 2016/5/22.
 */
public class ActionUtil {
    public static final Map<String,Object> str2Map(String fString){
        Map<String,Object> res = new HashMap<String,Object>();
        if(fString !=null && !fString.equals("")){
            for(String kv:fString.split("&")){
                String[] key_value = kv.split("=");
                if(key_value.length == 2){
                    res.put(key_value[0],key_value[1]);
                }
            }
        }
        return res;
    }
}
