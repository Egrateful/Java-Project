package uuu.resort.model;
import uuu.resort.domain.Customer;

public class HelloService {
    public String sayHello(){
        return "哈囉";
    }
    
    /**
     * 用傳入的姓名字串回傳打招呼的字串結果
     */ 
    public String sayHello(String name){
        String helloMsg = sayHello() + ", " + name;
        return helloMsg;
    }

//    public String sayHello(String email){
//        String helloMsg = "哈囉, " + email;
//        return helloMsg;
//    }

    
    public String sayHello(Customer c){
        String helloMsg = sayHello(c.getName());
        return helloMsg;        
    }
}
