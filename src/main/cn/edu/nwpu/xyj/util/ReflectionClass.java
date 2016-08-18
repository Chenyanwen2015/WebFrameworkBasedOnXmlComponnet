package cn.edu.nwpu.xyj.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class ReflectionClass {
	public static Object getInstanceConstrucst(String field,Class[] ptypes,Object[] params){
		try {
			Class c = Class.forName(field);
			Constructor<?> con = c.getDeclaredConstructor(ptypes);  //以数组为参数获得Test1的私有构造函数
			Object t = con.newInstance(params);  //通过构造函数构造Test1实例。
			return t;
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getField2String(Object model,String fName){
		//"get" + name.substring(0,1).toUpperCase() + name.substring(1)
		//System.out.println(executeSelfMethod(model,"get" + fName.substring(0,1).toUpperCase() + fName.substring(1),null,null));
		if(executeSelfMethod(model,"get" + fName.substring(0,1).toUpperCase() + fName.substring(1),null,null) != null)
			return String.valueOf(executeSelfMethod(model,"get" + fName.substring(0,1).toUpperCase() + fName.substring(1),null,null));
		else
			return "";
	}
	
	public static Object getFieldSelf(Object model,String fName){
		//"get" + name.substring(0,1).toUpperCase() + name.substring(1)
		return executeSelfMethod(model,"get" + fName.substring(0,1).toUpperCase() + fName.substring(1),null,null);
	}
	
	public static Object executeSelfMethod(Object obj,String mName,Class[] ptypes,Object[] params){
		try {
			Class clazz = obj.getClass(); 
			Method m2 = clazz.getDeclaredMethod(mName, ptypes); 
			//System.out.println(m2.invoke(obj, params));
			return m2.invoke(obj, params);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object getStaticMethod(String field,Class[] ptypes,Object[] params){
		try {
			int s= field.lastIndexOf(".");
			Class<?> threadClazz = Class.forName(field.substring(0, s));  
			Method method = threadClazz.getMethod(field.substring(s+1, field.length()), ptypes);
			method.invoke(ptypes,params);
			return method.invoke(ptypes,params);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}
	
	public static Object getStaticValue(String field){
		Object property = null;
		try {
			int s= field.lastIndexOf(".");
			Class ownerClass = Class.forName(field.substring(0,s));
			Field f = ownerClass.getField(field.substring(s+1, field.length()));
			property = f.get(ownerClass);
			//System.out.println(property);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return property;
	}

    public static Method getMethodByName(String cName,String mName){
        try {
			Class ownerClass = Class.forName(cName);
            Method m[] = ownerClass.getMethods();
            for(Method temp:m ){
				if(temp.getName().equals(mName))
                    return temp;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object[] fitTypes(Method me,Object[] params){
        if(params !=null){
            Object[] res =new Object[params.length];
            for(int i=0;i<res.length;i++){
                res[i] =  changeType(me.toString(),params[i]);
            }
            return res;
        }
        return null;
    }

    public static Object changeType(String cNmae,Object obj){
        if(cNmae.equals("class java.lang.String"))
            return String.valueOf(obj);
        else if (cNmae.equals("class java.util.Date")){
            DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINA);
            return new Date(String.valueOf(obj));
        }
        else if(cNmae.equals("class java.lang.Long")){
            return Long.valueOf(String.valueOf(obj));
        }
        else if(cNmae.equals("class java.lang.Integer")){
            return Integer.valueOf(String.valueOf(obj));
        }
        else if(cNmae.equals("class java.lang.Double")){
            return Double.valueOf(String.valueOf(obj));
        }
        else if(cNmae.equals("class java.lang.Float")){
            return Float.valueOf(String.valueOf(obj));
        }
        else if(cNmae.equals("class java.lang.Boolean")){
            return Boolean.valueOf(String.valueOf(obj));
        }
        return String.valueOf(obj);
    }

    public static Object findVlueByName(Map<String,Object> fString,Map<String,Object> variables,String name){
        Object value=null;
		value = fString.get(name);
		if(value == null)
        	value = variables.get(name);
		return value;
    }



    private static Object[] findParamsValues(String expression,Map<String,Object> fString,Map<String,Object> variables){
		String[] method_params=expression.replaceAll(" ","").split("\\(");
        if(method_params.length == 2){
            String[] pNames = method_params[1].replaceAll("\\)","").split(",");
			if(pNames !=null && pNames.length>1){
                Object[] res= new Object[pNames.length];
                int i=0;
                for(String temp:pNames){
					//System.out.println(temp);
					res[i] = findVlueByName(fString,variables,temp);
                    i++;
                }
                return res;
            }
        }
        return null;
    }

    private static Object executeSelfMethod(String expression,Object[] params){
		String[] method_params=expression.replaceAll(" ","").split("\\(");
		if(method_params.length == 2){
			int s= method_params[0].lastIndexOf(".");
			try {
				Class ownerClass = Class.forName(method_params[0].substring(0,s));
				Object obj = ownerClass.newInstance();
                Method target = getMethodByName(method_params[0].substring(0,s),method_params[0].substring(s+1,method_params[0].length()));
				if(params !=null)
					return target.invoke(obj,params);
				return target.invoke(obj);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
		return null;
	}

	public static Object getLocalMethodReturn(String expression,Map<String,Object> fString,Map<String,Object> variables){
		return executeSelfMethod(expression,findParamsValues(expression,fString,variables));
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*ReflectionClass.getInstanceConstrucst("com.itextpdf.text.Document",
				new Class[]{com.itextpdf.text.Rectangle.class,float.class,float.class,float.class,float.class},
				new Object[]{getStaticValue("com.itextpdf.text.PageSize.A4"),80,70,70,70});*/
		Object res = ReflectionClass.getInstanceConstrucst("com.nwpu.font.MyFont",
				null,
				null);

		ReflectionClass.executeSelfMethod(res, "getS", null, null);
		ReflectionClass.getStaticMethod("com.nwpu.font.MyFont.getStatic", null, null);
	}

}
