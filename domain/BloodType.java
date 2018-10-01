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
public enum BloodType {
    O, A(10), B(20), AB(30); //0, 10, 20, 30

    private final int code;

    public int getCode() {
        return code;
    }
    
    private BloodType(){
        this(0);
    }

    private BloodType(int code){
        this.code = code;
    }
    
    @Override
    public String toString() {
        return super.toString()+"型"; //To change body of generated methods, choose Tools | Templates.
    }
    
}
