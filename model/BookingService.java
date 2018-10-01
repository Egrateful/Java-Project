/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uuu.resort.model;

import java.util.List;
import uuu.resort.domain.Customer;
import uuu.resort.domain.Booking;
import uuu.resort.domain.ShoppingCart;
import uuu.resort.domain.ResortException;

/**
 *
 * @author PattyTai
 */
public class BookingService {
    RDBOrdersDAO dao = new RDBOrdersDAO();
    
    public Booking makeOrder(ShoppingCart cart){
        Booking order = new Booking();
        
        order.setCustomer(cart.getCustomer());
        order.add(cart);
        return order;
    }
    
    public void saveOrder(Booking order) throws ResortException{
        dao.insert(order);
    }
    
    public List<Booking> getOrderHistoryByCustomer(Customer c)throws ResortException{
        return dao.getOrdersByCustomers(c);
    }
    
    public Booking getOrder(int orderId) throws ResortException{
        return dao.get(orderId);
    }
    
    
    
//    public double order(Customer c, ShoppingCart cart){
//        
//    }

//    public double order(Customer c, Product p,
//            int quantity) {
//        double rtn = 0;
//        if (c != null && p != null && quantity > 0) {
//            rtn = p.getUnitPrice() * quantity;
//            if(!(p instanceof Outlet) && c instanceof VIP){
//                //VIP v = (VIP)c;
//                //rtn *= (100-v.getDiscount())/100D;
//                rtn *= (100-((VIP)c).getDiscount())/100D;
//            }
//        }else{
//            //拋出錯誤
//        }
//        return rtn;
//    }
}
