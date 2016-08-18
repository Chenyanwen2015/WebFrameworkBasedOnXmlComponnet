package cn.edu.nwpu.xyj.test;

import cn.edu.nwpu.xyj.xml.parser.tagenum.FormTags;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by xyj on 2016/4/1.
 */
public class Test {
    public static int count = 0;
    public static int copy = 0;
    public Test(){

    }
    public static void getAll(List<Integer> test,int i){
        if(i == test.size() - 1){
            count++;
            for(int j = 0;j<=i&i==test.size()-1;j++){
                System.out.print(test.get(j)+" ");

            }

            System.out.println();
        }
        for(int j = i;j<test.size();j++){
            if(test.get(i) != test.get(j)){
                Integer  temp = test.get(i);
                test.set(i,test.get(j));
                test.set(j,temp);
                getAll(test,i+1);
                temp = test.get(i);
                test.set(i,test.get(j));
                test.set(j,temp);
            }
            else{
                //getAll(test,i+1);
                copy++;
            }

        }
    }

    public static int simpleReturn(){
        return 100;
    }

    public String checkLogin(String userName,String password){
        if(userName.equals("xuyuanjia") && password.equals("123456"))
            return "xuyuanjia";
        return "";
    }

    public String getName(){
            return "xuyuanjia";
    }

    public String getRole(){
        return "管理员";
    }


    public static void main(String[] args) {
        /*String s1 = "aaaa";
        String s2 ="aaaa";
        Map<String,Object> res = new HashMap<String,Object>();
        res.put("a",s1);
        res.put("b",s2);
        System.out.println(res.get("a").toString());
        System.out.println( res.get("a").equals(res.get("b")));
        System.out.println( s1.equals(s2));*/
        System.out.println(FormTags.BUTTON.equals("button"));

        Test foo = new Test();
        Class clazz = foo.getClass();
        Method m2 = null;
        try {
            m2 = clazz.getDeclaredMethod("checkLogin", String.class,String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            m2.invoke(foo, "重新设置msg信息！","");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
