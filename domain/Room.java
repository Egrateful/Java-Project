package uuu.resort.domain;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Room {
    public static final DateFormat querydateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    private String id;
    private String name;
    private double unit_price;
    private Date querydate; //須import java DateFormat
    private String url;
    /**
     * status 為必要欄位, M: male, F: female
     */

    private boolean unoccupied;// = true;
 
   
    public Room() {
    }

    public Room(String id, String name, double unitPrice) {
        this.id = id;
        this.name = name;
        this.unit_price = unitPrice;
    }
    
    public Room(String id, String name, double unitPrice, Date querydate,  boolean unoccupied) {
        this.id = id;
        this.name = name;
        this.unit_price = unitPrice;
        this.querydate = querydate;
        this.unoccupied = unoccupied;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unit_price;
    }

    public void setUnitPrice(double unit_price) {
        this.unit_price = unit_price;
    }

    public Date getQuerydate() {
        return querydate;
    }

    public void setQuerydate(Date querydate) {
        this.querydate = querydate;
    }

    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUnoccupied() {
        return unoccupied;
    }

    public void setUnoccupied(boolean unoccupied) {
        this.unoccupied = unoccupied;
    }

//    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
        //System.out.println(toString() + " finallize.....");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
       
        final Room other = (Room) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
