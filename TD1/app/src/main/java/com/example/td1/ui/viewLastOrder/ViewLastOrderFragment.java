package com.example.td1.ui.viewLastOrder;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.example.td1.ActivityLogin;
import com.example.td1.DAO.OrderDAO;
import com.example.td1.OrderLinesAdapter;
import com.example.td1.R;
import com.example.td1.modele.Order;
import com.example.td1.modele.OrderLine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ViewLastOrderFragment extends Fragment implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {

    private View root;

    private Order order;

    private ListView lvOrderLines;

    private TextView orderNumberTextView;
    private TextView orderDateTextView;
    private TextView orderTotalTextView;

    private ArrayAdapter<OrderLine> orderLinesAdapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        root = inflater.inflate(R.layout.fragment_last_order, container, false);

        this.order = null;
        OrderDAO.getOrder(this, ((ActivityLogin) this.getActivity()).getLoggedInCustomer().getId());
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        this.orderNumberTextView = this.root.findViewById(R.id.orderNumberTextView);
        this.orderDateTextView = this.root.findViewById(R.id.orderDateTextView);
        this.orderTotalTextView = this.root.findViewById(R.id.orderTotalTextView);
    }

    public void setOrderLinesAdapter() {
        this.lvOrderLines = this.root.findViewById(R.id.ordersListView);
        this.orderLinesAdapter = new OrderLinesAdapter(this.getContext(), this.order.getLines());
        this.lvOrderLines.setAdapter(this.orderLinesAdapter);
        this.orderLinesAdapter.notifyDataSetChanged();
    }

    public void updateTexts() {
        this.orderNumberTextView.setText(String.format(getString(R.string.order_number), this.order.getId()));
        this.orderDateTextView.setText(String.format(getString(R.string.order_date), this.order.getDate()));
        this.orderTotalTextView.setText(String.format(getString(R.string.order_total), this.order.getTotal()));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Volley error", error.getMessage());
        Toast.makeText(this.getContext(), R.string.error_db, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {

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
                OrderLine orderLine = new OrderLine(orderLineObj.getString("description"), orderLineObj.getString("size"), orderLineObj.getInt("quantite"), orderLineObj.getDouble("tarif"));
                lines.add(orderLine);
            }

            this.order.setLines(lines);
            this.setOrderLinesAdapter();
            this.updateTexts();
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }
}
