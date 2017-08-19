package me.phum.shopifymobilechallenge.ShopifyOrder;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patri on 2017-08-19.
 */

public class ShopifyOrderList {
    private List<ShopifyOrder> orders;
    private static final String TAG = "ShopifyOrderList";

    public float findSpendingTotalByUser(String firstName, String lastName){
        float spendingTotal = 0;
        long user_id = ShopifyOrder.NO_USER_ID;
        ArrayList<ShopifyOrder> POSOrders = new ArrayList<>();

        for (ShopifyOrder order : orders){
            //If the order was created from the Shopify POS, it may be null
            //We store this and see if we can cross-reference with user_id
            if (order.getCustomer() == null){
                POSOrders.add(order);
            }else if (order.getCustomer().getFirst_name().toLowerCase().equals(firstName.toLowerCase()) &&
                    order.getCustomer().getLast_name().toLowerCase().equals(lastName.toLowerCase())){
                if (order.getUser_id() != ShopifyOrder.NO_USER_ID){
                    user_id = order.getUser_id();
                }
                spendingTotal += order.getTotal_price();
            }
        }

        //Cross reference user ids with the POS orders
        if (user_id == ShopifyOrder.NO_USER_ID){
            Log.w(TAG, "Could not find user id, POS orders may not register under this user.");
        }else{
            for (ShopifyOrder order : POSOrders){
                if (order.getUser_id() == user_id){
                    spendingTotal += order.getTotal_price();
                    Log.d(TAG, String.valueOf(order.getTotal_price()));
                }
            }
        }

        return spendingTotal;
    }

    public int findTotalItemsSold(String itemName){
        int itemsSold = 0;
        for (ShopifyOrder order : orders){
            for (ShopifyOrder.Item item : order.getLine_items()){
                if (item.getTitle().toLowerCase().equals(itemName.toLowerCase())){
                    itemsSold += item.getQuantity();
                    if (item.getQuantity() != item.getFulfillable_quantity())
                        Log.w(TAG, "Quantity/FulfillableQuantity mismatch");
                }
            }
        }
        return itemsSold;
    }

    public List<ShopifyOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<ShopifyOrder> orders) {
        this.orders = orders;
    }
}
