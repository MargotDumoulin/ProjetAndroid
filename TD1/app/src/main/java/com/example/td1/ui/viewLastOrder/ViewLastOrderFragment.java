package com.example.td1.ui.viewLastOrder;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.android.volley.VolleyError;
import com.example.td1.ActivityLogin;
import com.example.td1.DAO.OrderDAO;
import com.example.td1.OrderLinesAdapter;
import com.example.td1.R;
import com.example.td1.WaitingData;
import com.example.td1.modele.Order;
import com.example.td1.modele.OrderLine;
import com.example.td1.ui.myAccount.MyAccountFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ViewLastOrderFragment extends MyAccountFragment implements WaitingData, com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {

    private View root;

    private Order order;

    private ListView lvOrderLines;

    private TextView orderNumberTextView;
    private TextView orderDateTextView;
    private TextView orderTotalTextView;
    private TextView noOrderFoundTextView;

    private View noOrderFoundWhiteBlankView;
    private View progressBarView;

    private ProgressBar progressBar;

    private boolean noOrderFound = false;

    private ArrayAdapter<OrderLine> orderLinesAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        root = inflater.inflate(R.layout.fragment_last_order, container, false);

        this.order = null;

        if (savedInstanceState != null) {
            this.noOrderFound = savedInstanceState.getBoolean("noOrderFound");
            if (!savedInstanceState.getBoolean("noOrder") && !this.noOrderFound) {
                this.order = (Order) savedInstanceState.getSerializable("order");
            }
        } else  {
            OrderDAO.getOrder(this, ((ActivityLogin) this.getActivity()).getLoggedInCustomer().getId());
        }

        
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        this.orderNumberTextView = this.root.findViewById(R.id.orderNumberTextView);
        this.orderDateTextView = this.root.findViewById(R.id.orderDateTextView);
        this.orderTotalTextView = this.root.findViewById(R.id.orderTotalTextView);
        this.noOrderFoundTextView = this.root.findViewById(R.id.noOrderFoundTextView);
        this.noOrderFoundWhiteBlankView = this.root.findViewById(R.id.noOrderFoundWhiteBlankView);
        this.progressBarView = this.root.findViewById(R.id.loadingView);
        this.progressBar = this.root.findViewById(R.id.loading);

        if (this.order != null) {
            this.setOrderLinesAdapter();
            this.updateTexts();
            this.noOrderFoundTextView.setVisibility(View.INVISIBLE);
            this.noOrderFoundWhiteBlankView.setVisibility(View.INVISIBLE);
            this.hideProgressBar();
        }

        if (this.noOrderFound) {
            this.showNoProductsFound();
        }
    }

    // ------ ROTATION ---------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (this.order != null) {
            outState.putSerializable("order", this.order);
        } else {
            outState.putBoolean("noOrder", true); // maybe the user flipped the screen while it's charging, so it has no order YET but maybe will have one when it's done charging
            outState.putBoolean("noOrderFound", this.noOrderFound); // test if we DO certainly know that no order was found after searching into the db :)
        }
    }

    public void setOrderLinesAdapter() {
        this.lvOrderLines = this.root.findViewById(R.id.ordersListView);
        this.orderLinesAdapter = new OrderLinesAdapter(this.getContext(), this.order.getLines());
        this.lvOrderLines.setAdapter(this.orderLinesAdapter);
        this.orderLinesAdapter.notifyDataSetChanged();
    }

    public void updateTexts() {
        String dateStr = new SimpleDateFormat("dd/MM/yyyy").format(this.order.getDate());
        this.orderNumberTextView.setText(String.format(getString(R.string.order_number), this.order.getId()));
        this.orderDateTextView.setText(String.format(getString(R.string.order_date), dateStr));
        this.orderTotalTextView.setText(String.format(getString(R.string.order_total), this.order.getTotal()));
    }

    public void hideProgressBar() {
        this.progressBar.animate().alpha(0.0f).setDuration(250).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                progressBar.setVisibility(View.GONE);
            }
        });

        this.progressBarView.animate().alpha(0.0f).setDuration(400).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                progressBarView.setVisibility(View.GONE);
            }
        });
    }

    public void showNoProductsFound() {
        this.noOrderFound = true;
        this.noOrderFoundTextView.setVisibility(View.VISIBLE);
        this.noOrderFoundWhiteBlankView.setVisibility(View.VISIBLE);
        this.hideProgressBar();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Volley error", error.getMessage());
        Toast.makeText(this.getContext(), R.string.error_db, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {

            if (response.has("id_commande")) {
                String dateStr = response.getString("date_commande");
                java.util.Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);

                this.order = new Order(
                        response.getInt("id_commande"),
                        date,
                        new ArrayList<OrderLine>()
                );

                JSONArray linesJson = response.getJSONArray("lines");
                ArrayList<OrderLine> lines = new ArrayList<OrderLine>();

                for (int i = 0; i < linesJson.length(); i++) {
                    JSONObject orderLineObj = linesJson.getJSONObject(i);
                    OrderLine orderLine = new OrderLine(orderLineObj.getString("description"), orderLineObj.getString("taille"), orderLineObj.getInt("quantite"), orderLineObj.getDouble("tarif"));
                    lines.add(orderLine);
                }

                this.order.setLines(lines);
                this.setOrderLinesAdapter();
                this.updateTexts();
                this.noOrderFoundTextView.setVisibility(View.INVISIBLE);
                this.noOrderFoundWhiteBlankView.setVisibility(View.INVISIBLE);
                this.hideProgressBar();
            } else {
               this.showNoProductsFound();
            }

        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }
}
