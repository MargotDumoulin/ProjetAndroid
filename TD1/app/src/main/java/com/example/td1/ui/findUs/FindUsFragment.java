package com.example.td1.ui.findUs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.td1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FindUsFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private View root;

    // For the "unknown fragment" error
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
        mMap = googleMap;

        // Add markers
        LatLng metz = new LatLng(49.119316234091514, 6.162745805048774);
        LatLng nancy = new LatLng(48.692493197272846, 6.182190035924221);
        LatLng paris = new LatLng(48.857644176650005, 2.3432683247462442);
        LatLng lille = new LatLng(50.62953781296629, 3.0555714809088697);
        LatLng lyon = new LatLng(45.76381840665436, 4.832373960260015);
        LatLng strasbourg = new LatLng(48.571591257454465, 7.751106118589273);

        mMap.addMarker(new MarkerOptions().position(metz).title("Shop at Metz"));
        mMap.addMarker(new MarkerOptions().position(nancy).title("Shop at Nancy"));
        mMap.addMarker(new MarkerOptions().position(paris).title("Shop at Paris"));
        mMap.addMarker(new MarkerOptions().position(lille).title("Shop at Lille"));
        mMap.addMarker(new MarkerOptions().position(lyon).title("Shop at Lyon"));
        mMap.addMarker(new MarkerOptions().position(strasbourg).title("Shop at Strasbourg"));

        // Center camera on marker
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(paris, 5));
    }
}