package com.example.td1.ui.findUs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.td1.DAO.ShopDAO;
import com.example.td1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FindUsFragment extends Fragment implements OnMapReadyCallback, com.android.volley.Response.Listener<JSONArray>, com.android.volley.Response.ErrorListener {

    private GoogleMap mMap;
    private View root;

    // For the "unknown fragment" error in XML
    // https://stackoverflow.com/questions/23898992/android-studio-google-map-v2-fragment-rendering

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_find_us, container, false);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.google_maps);

        mapFragment.getMapAsync(this);
        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng franceCenter = new LatLng(48.864716, 2.349014);
        mMap = googleMap;

        ShopDAO.findAll(this);

        // Center camera on marker
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(franceCenter, 5));
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Volley error", error + "");
        Toast.makeText(this.getContext(), R.string.error_db, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            for (int i = 0; i < response.length(); i++) {
                JSONObject o = response.getJSONObject(i);

                String shopName = o.getString("nom");
                double latitude = o.getDouble("latitude");
                double longitude = o.getDouble("longitude");

                LatLng coordinates = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(coordinates).title(shopName));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}