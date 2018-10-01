/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.resort.domain;

/**
 *
 * @author Angie Lin
 */
public class Promotion extends Room {

    private int discount;

    public Promotion() {
    }

    public Promotion(String id, String name, double unitPrice) {
        super(id, name, unitPrice);
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public double getUnitPrice() {
        double rtn = super.getUnitPrice();
        rtn = rtn * (100 - this.discount) / 100;
        return rtn; //To change body of generated methods, choose Tools | Templates.
    }

    public double getListPrice() {
        return super.getUnitPrice();
    }

    @Override
    public String toString() {
        return "Promotion{" + super.toString() + ", discount=" + discount + '}';
    }

}
