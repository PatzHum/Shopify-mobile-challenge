package me.phum.shopifymobilechallenge.ShopifyOrder;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

/**
 * Created by patri on 2017-08-19.
 */

public class ShopifyOrderManager {
    public interface OrdersRecievedCallback{
        void onOrdersReceived(ShopifyOrderList orders);
    }
    private Context mContext;
    private static final String TAG = "ShopifyOrderManager";
    public ShopifyOrderManager(Context context){
        mContext = context;
    }
    public void getOrders(String url, final OrdersRecievedCallback callback){
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest orderRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                ShopifyOrderList ordersList = gson.fromJson(response, ShopifyOrderList.class);
                callback.onOrdersReceived(ordersList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());
            }
        });

        queue.add(orderRequest);
    }
}
