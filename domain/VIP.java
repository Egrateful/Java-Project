/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.resort.domain;

/**
 *
 * @author PattyTai
 */
public class VIP extends Customer{
    private int discount = 5;
    
    public VIP(){        
    }

    public VIP(String id, String name, String password) throws ResortException {
        super(id, name, password);
    }

    public VIP(String id, String name, char gender, String password, String email) throws ResortException {
        super(id, name, gender, password,email);        
    }
    
    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        if(discount>=0 && discount<=100){
            this.discount = discount;
        }else{
            System.out.println("折扣必須在0~100之間");
        }
    }

    @Override
    public String toString() {
        return "VIP{" + super.toString() + "discount=" + discount + '}';
    }
}
