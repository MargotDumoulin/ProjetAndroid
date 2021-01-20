package com.example.td1.ui.mentionLegales;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.VolleyError;
import com.example.td1.DAO.MentionLegalesDAO;
import com.example.td1.DAO.ProductDAO;
import com.example.td1.ImageFromURL;
import com.example.td1.R;
import com.example.td1.modele.Produit;
import com.example.td1.modele.Taille;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MentionLegalesFragment extends Fragment implements com.android.volley.Response.Listener<JSONArray>, com.android.volley.Response.ErrorListener {
    String[] tableName;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mention_legales, container, false);
        MentionLegalesDAO.findMension(this.getContext(), "teste");

        final TextView explante_legal_mention = this.root.findViewById(R.id.explante_legal_mention);
        final TextView title_editing_the_application_legal_mention = this.root.findViewById(R.id.title_editing_the_application_legal_mention);
        final TextView content_editing_the_application_legal_mention = this.root.findViewById(R.id.content_editing_the_application_legal_mention);
        final TextView title_responsible_for_publication_legal_mention = this.root.findViewById(R.id.title_responsible_for_publication_legal_mention);
        final TextView content_responsible_for_publication_legal_mention = this.root.findViewById(R.id.content_responsible_for_publication_legal_mention);
        final TextView title_host_legal_mention = this.root.findViewById(R.id.title_host_legal_mention);
        final TextView content_host_legal_mention = this.root.findViewById(R.id.content_host_legal_mention);
        final TextView title_contact_us_legal_mention = this.root.findViewById(R.id.title_contact_us_legal_mention);
        final TextView content_contact_us_legal_mention = this.root.findViewById(R.id.content_contact_us_legal_mention);
        final TextView title_CNIL_legal_mention = this.root.findViewById(R.id.title_CNIL_legal_mention);
        final TextView content_CNIL_legal_mention = this.root.findViewById(R.id.content_CNIL_legal_mention);
        final TextView title_disputes_legal_mention = this.root.findViewById(R.id.title_disputes_legal_mention);
        final TextView content_disputes_legal_mention = this.root.findViewById(R.id.content_disputes_legal_mention);
        final TextView title_intellectual_property_legal_mention = this.root.findViewById(R.id.title_intellectual_property_legal_mention);
        final TextView content_intellectual_property_legal_mention = this.root.findViewById(R.id.content_intellectual_property_legal_mention);
        final TextView title_services_provided_property_legal_mention = this.root.findViewById(R.id.title_services_provided_property_legal_mention);
        final TextView content_services_provided_property_legal_mention = this.root.findViewById(R.id.content_services_provided_property_legal_mention);

        explante_legal_mention.setText("tatata");
        title_editing_the_application_legal_mention.setText("tatata");
        content_editing_the_application_legal_mention.setText("tatata");
        title_responsible_for_publication_legal_mention.setText("tatata");
        content_responsible_for_publication_legal_mention.setText("tatata");
        title_host_legal_mention.setText("tatata");
        content_host_legal_mention.setText("tatata");
        title_contact_us_legal_mention.setText("tatata");
        content_contact_us_legal_mention.setText("tatata");
        title_CNIL_legal_mention.setText("tatata");
        content_CNIL_legal_mention.setText("tatata");
        title_disputes_legal_mention.setText("tatata");
        content_disputes_legal_mention.setText("tatata");
        title_intellectual_property_legal_mention.setText("tatata");
        content_intellectual_property_legal_mention.setText("tatata");
        title_services_provided_property_legal_mention.setText("tatata");
        content_services_provided_property_legal_mention.setText("tatata");
        return root;
    }

    public void onStart() {
        super.onStart();

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("test", error + "là");
        Toast.makeText(this.getContext(), R.string.error_db, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONArray response) {
        try {
            Log.e("tatatata","ttatta");
            for (int i = 0; i < response.length(); i++) {
                JSONObject o = response.getJSONObject(i);
                Log.e("tatatata",o.hashCode()+"");
                if (o.has("libelle")){

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}