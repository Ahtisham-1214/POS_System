package Controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Sales {
    private String orderId;
    private String customerName;
    private Date date;
    private float total;
    private ArrayList<CartItem> cartItems;
    private static ArrayList<Sales> sales = new ArrayList<Sales>();

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public static ArrayList<Sales> getSales() {
        return sales;
    }

    public Sales(String orderId, String customerName, Date date) {
        this.setOrderId(orderId);
        this.setCustomerName(customerName);
        this.setDate(date);
        cartItems = new ArrayList<>();
    }


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        if (customerName == null || customerName.isBlank()) {
            this.customerName = "walk-in";
        }else
            this.customerName = customerName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }
}
