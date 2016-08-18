package model;

/**
 * Created by xyj on 2016/3/28.
 */
public class User {

    private String email;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    static void test() throws Exception {
        try{
            throw new Exception("test");
        }catch(Exception e){
            System.out.println("first");
            e.printStackTrace();
            throw e;
        }
    }
    public static void main(String[] args) {
        Short temp = 12;
        int a = 'A';
        User u = new User();
        u.email = "sdf";
        System.out.println(u.email);
        System.out.println(a);
        try {
            test();
        } catch (Exception e) {
            System.out.println("second");
            e.printStackTrace();
        }
    }

}
