package uuu.resort.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Angie Lin
 */
public class Booking {

    private int id;
    private Customer customer;
    private Date orderTime = new Date();
//    private PaymentType paymentType;
//    private double paymentFee;
//    private String paymentNote;
//    private ShippingType shippingType;
//    private double shippingFee;
//    private String shippingNote;
//    private String receiverName;
//    private String receiverPhone;
//    private String receiverEmail;
//    private String shippingAddress;
//    private Status status = Status.NEW;

    private List<BookingItem> orderItemList;

    private double totalAmount; //derived column

    public Booking() {
    }

    public Booking(Customer customer) {
        this.customer = customer;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

//    public PaymentType getPaymentType() {
//        return paymentType;
//    }
//
//    public void setPaymentType(PaymentType paymentType) {
//        this.paymentType = paymentType;
//    }
//
//    public double getPaymentFee() {
//        return paymentFee;
//    }
//
//    public void setPaymentFee(double paymentFee) {
//        this.paymentFee = paymentFee;
//    }
//
//    public String getPaymentNote() {
//        return paymentNote;
//    }
//
//    public void setPaymentNote(String paymentNote) {
//        this.paymentNote = paymentNote;
//    }

//    public ShippingType getShippingType() {
//        return shippingType;
//    }
//
//    public void setShippingType(ShippingType shippingType) {
//        this.shippingType = shippingType;
//    }
//
//    public double getShippingFee() {
//        return shippingFee;
//    }
//
//    public void setShippingFee(double shippingFee) {
//        this.shippingFee = shippingFee;
//    }
//
//    public String getShippingNote() {
//        return shippingNote;
//    }
//
//    public void setShippingNote(String shippingNote) {
//        this.shippingNote = shippingNote;
//    }
//
//    public String getReceiverName() {
//        return receiverName;
//    }
//
//    public void setReceiverName(String receiverName) {
//        this.receiverName = receiverName;
//    }
//
//    public String getReceiverPhone() {
//        return receiverPhone;
//    }
//
//    public void setReceiverPhone(String receiverPhone) {
//        this.receiverPhone = receiverPhone;
//    }
//
//    public String getReceiverEmail() {
//        return receiverEmail;
//    }
//
//    public void setReceiverEmail(String receiverEmail) {
//        this.receiverEmail = receiverEmail;
//    }
//
//    public String getShippingAddress() {
//        return shippingAddress;
//    }
//
//    public void setShippingAddress(String shippingAddress) {
//        this.shippingAddress = shippingAddress;
//    }
//
//    public Status getStatus() {
//        return status;
//    }
//
//    public void setStatus(Status status) {
//        this.status = status;
//    }

    //derived column 的 getter/setter
    public double getTotalAmount() {
        if (orderItemList == null || orderItemList.size() == 0) {
            return totalAmount;
        } else {
            double amount = 0;
            for (BookingItem item : orderItemList) {
                double data = item.getPrice() * item.getQuantity();
                if (item.getDiscount() > 0) {
                    data = data * (100 - item.getDiscount()) / 100;
                }
                amount += data;
            }
            
            return amount;
//            return amount + getPaymentFee() + getShippingFee();
        }
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    //集合的管理
    public List<BookingItem> getOrderItemList() {
        return new ArrayList(orderItemList);
        //return Collections.unmodifiableList(orderItemList);
    }

    public void addOrderItem(BookingItem item) {//for database select
        if (orderItemList == null) {
            orderItemList = new ArrayList<>();
        }
        
        orderItemList.add(item);
    }

    public void add(ShoppingCart cart) { //make new order
        if (orderItemList == null) {
            orderItemList = new ArrayList<>();
        } else {
            orderItemList.clear();
        }

        for(Room p:cart.getKeySet()){
            BookingItem item = new BookingItem();
            item.setRoom(p);
          
            if(p instanceof Promotion){
                item.setPrice(((Promotion)p).getListPrice());
            }else{
                item.setPrice(p.getUnitPrice());
            }
            
            if(p instanceof Promotion){
                item.setDiscount((((Promotion)p).getDiscount()));
            }else{
                if(this.customer instanceof VIP){
                    item.setDiscount((((VIP)this.customer).getDiscount()));
                }
            }
            
//            item.setQuantity(cart.getQuantity(p));
            
            orderItemList.add(item);
        }
    }

//    @Override
//    public String toString() {
//        return "訂單{" + "編號=" + id + ", 客戶=" + customer + ", 訂購時間=" + orderTime 
//                + ", 付款方式金額=" + paymentType + ", " + paymentFee + "元, " + paymentNote 
//                + ", 貨運方式金額=" + shippingType + ", " + shippingFee + "元, " + shippingNote 
//                + ", 收件人=" + receiverName + ", 電話=" + receiverPhone + ", Email=" + receiverEmail + ", 地址=" + shippingAddress 
//                + ", 狀態=" + status 
//                + ", 明細=" + orderItemList + ", 總金額=" + getTotalAmount()+  '}';
//    }
//    
//    

    public enum Status {
        NEW("新訂單"), PAID("已付款"), PROCESSED("處理中"), 
            SHIPPED("已出貨"), CHECKED("已簽收"), COMPLETED("已完成");
        //...
        
        private final String description;

        private Status(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
