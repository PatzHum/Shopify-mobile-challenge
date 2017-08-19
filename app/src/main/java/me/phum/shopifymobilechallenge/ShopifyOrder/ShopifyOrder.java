package me.phum.shopifymobilechallenge.ShopifyOrder;

import java.util.List;

/**
 * Created by patri on 2017-08-19.
 */

public class ShopifyOrder {
    public static class Item{
        private long id;
        private String title;

        private int quantity;
        private int fulfillable_quantity;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getFulfillable_quantity() {
            return fulfillable_quantity;
        }

        public void setFulfillable_quantity(int fulfillable_quantity) {
            this.fulfillable_quantity = fulfillable_quantity;
        }
    }
    public static class Customer{
        private long id;
        private String first_name;
        private String last_name;
        private float total_spent;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public float getTotal_spent() {
            return total_spent;
        }

        public void setTotal_spent(float total_spent) {
            this.total_spent = total_spent;
        }
    }

    public static final int NO_USER_ID = -1;
    private long id;
    private long user_id = NO_USER_ID;
    private String email;
    private List<Item> line_items;
    private Customer customer;
    private float total_price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Item> getLine_items() {
        return line_items;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setLine_items(List<Item> line_items) {
        this.line_items = line_items;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

}
