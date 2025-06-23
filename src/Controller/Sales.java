package Controller;

import java.sql.Time;
import java.util.Date;

public class Sales {
    private String orderId;
    private String customerName;
    private Date date;
    private float total;


    public Sales(String orderId, String customerName) {
        this.setOrderId(orderId);
        this.setCustomerName(customerName);
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
