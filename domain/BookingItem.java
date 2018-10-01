package uuu.resort.domain;

/**
 *
 * @author Angie Lin
 */
public class BookingItem {
    private int orderId; //0
//    private Product product; //null
    private Room room;
    private double price; //0.0
    private int discount; //0
    private int quantity; //0

    /**
     * @return the orderId
     */
    public int getOrderId() {
        return orderId;
    }

    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    /**
     * @return the product
     */
    public Room getRoom() {
        return room;
    }

    /**
     * @ room reservation setting
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the discount
     */
    public int getDiscount() {
        return discount;
    }

    /**
     * @param discount the discount to set
     */
    public void setDiscount(int discount) {
        this.discount = discount;
    }

    /**
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * @param quantity the quantity to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "明細{" + "編號=" + orderId + ", 房型=" + room + ", 單價=" + price + ", 折扣=" + discount + ", 數量=" + quantity + '}';
    }
    
    
}
