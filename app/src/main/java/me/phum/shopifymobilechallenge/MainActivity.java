package me.phum.shopifymobilechallenge;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import me.phum.shopifymobilechallenge.ShopifyOrder.ShopifyOrderList;
import me.phum.shopifymobilechallenge.ShopifyOrder.ShopifyOrderManager;

public class MainActivity extends AppCompatActivity implements ShopifyOrderManager.OrdersRecievedCallback{
    private static final String PROBLEM_RESOURCE_ENDPOINT = "https://shopicruit.myshopify.com/admin/orders.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";

    private static final int PERMISSIONS_REQUEST_ALL = 1;
    private static final String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.INTERNET
    };

    private ShopifyOrderList mOrders;

    /*
    * Views*/
    private View mRootView;
    private Button mFindTotalButton, mItemsSoldButton;
    private EditText mFindTotalFirstName, mFindTotalLastName, mItemsSoldName;
    private TextView mTotalSpentRepsonse, mItemCountResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Grab views from layout
        mRootView = findViewById(R.id.root);
        mFindTotalButton = (Button) findViewById(R.id.findTotalButton);
        mItemsSoldButton = (Button) findViewById(R.id.itemsSoldButton);

        mFindTotalFirstName = (EditText) findViewById(R.id.totalFirstName);
        mFindTotalLastName = (EditText) findViewById(R.id.totalLastName);

        mItemsSoldName = (EditText) findViewById(R.id.searchItem);

        mTotalSpentRepsonse = (TextView) findViewById(R.id.totalSpentResponse);
        mItemCountResponse = (TextView) findViewById(R.id.numItemsResponse);

        //If we didn't request permissions, we can go ahead with network requests
        if (!requestPermissions()) {
            initiateShopifyOrdersRequest();
        }

    }

    private boolean requestPermissions(){
        //Check permissions
        //If we are missing any request all of them, android igonores permissions we already have
        boolean shouldRequest = false;
        for (String permission : REQUIRED_PERMISSIONS){
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                shouldRequest = true;
                break;
            }
        }

        if (shouldRequest) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_ALL);
            return true;
        }
        return false;
    }

    private void initiateShopifyOrdersRequest(){
        //Initiate request for orders
        ShopifyOrderManager orderManager = new ShopifyOrderManager(this);
        orderManager.getOrders(PROBLEM_RESOURCE_ENDPOINT, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_ALL) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    final Snackbar snackbar = Snackbar.make(mRootView, R.string.permissions_failed_warning, Snackbar.LENGTH_INDEFINITE);

                    snackbar.setAction(R.string.try_again, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            snackbar.dismiss();
                            requestPermissions();
                        }
                    });

                    snackbar.show();
                    return;
                }
            }
            initiateShopifyOrdersRequest();
        }
    }

    @Override
    public void onOrdersReceived(ShopifyOrderList orders) {
        mOrders = orders;

        /*Only after we get our orders do we bind actions to our buttons*/
        mFindTotalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float totalSpent = mOrders.findSpendingTotalByUser(
                        mFindTotalFirstName.getText().toString(),
                        mFindTotalLastName.getText().toString());
                mTotalSpentRepsonse.setText(String.format("%s %s",
                        getString(R.string.totalCaptionText),
                        String.valueOf(totalSpent)));
            }
        });

        mItemsSoldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemsSold = mOrders.findTotalItemsSold(mItemsSoldName.getText().toString());
                mItemCountResponse.setText(String.format("%s %s",
                        getString(R.string.totalItemsText),
                        String.valueOf(itemsSold)));
            }
        });
    }
}
