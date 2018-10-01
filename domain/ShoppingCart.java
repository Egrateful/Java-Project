package uuu.resort.domain;
import java.util.*;

public class ShoppingCart {
    private Customer customer;
    private Map<Room, String> cart = new HashMap<>();

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    //getters
    public Set<Room> getKeySet() {
        return cart.keySet();
    }

//    public int getQuantity(Room r) {
//        Integer q = cart.get(r);
//        return q != null ? q : 0;
//    }

    public void add(Room r) {
        if (r != null) {
            cart.put(r, r.getId());
        }
    }

    public void update(Room r) {
        cart.clear();
    }

    public void remove(Room r) {
        cart.remove(r);
    }

    //business methods
    public double getTotalAmout() {
        double sum = 0;
        for (Room r : cart.keySet()) {
            double price = r.getUnitPrice();
            sum = sum + price ;//sum += price;
        }
        return sum;
    }

    public double getVIPTotalAmout() {
        if (customer instanceof VIP) {
            double sum = 0;
            for (Room r : cart.keySet()) {
                double price = r.getUnitPrice();
                if(!(r instanceof Promotion)){                    
                    price *= (100D-((VIP)customer).getDiscount())/100;
                }                
                sum+=price;
            }
            return sum;
        } else {
            return getTotalAmout();
        }
    }

    @Override
    public String toString() {
        return "購物車{" + "customer=" + customer + ", cart=" + cart + "}\n"
         + getTotalAmout() + ", VIP金額:" + getVIPTotalAmout();
    }
    
    //delegate method
    public int size() {
        return cart.size();
    }
    
}