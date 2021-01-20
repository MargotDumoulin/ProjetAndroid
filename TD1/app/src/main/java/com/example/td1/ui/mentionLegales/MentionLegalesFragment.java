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
import java.util.Locale;

public class MentionLegalesFragment extends Fragment implements com.android.volley.Response.Listener<JSONArray>, com.android.volley.Response.ErrorListener {

    private View root;

    private TextView explante_legal_mention;
    private TextView title_editing_the_application_legal_mention;
    private TextView content_editing_the_application_legal_mention;
    private TextView title_responsible_for_publication_legal_mention;
    private TextView content_responsible_for_publication_legal_mention;
    private TextView title_host_legal_mention;
    private TextView content_host_legal_mention;
    private TextView title_contact_us_legal_mention;
    private TextView content_contact_us_legal_mention;
    private TextView title_CNIL_legal_mention;
    private TextView content_CNIL_legal_mention;
    private TextView title_disputes_legal_mention;
    private TextView content_disputes_legal_mention;
    private TextView title_intellectual_property_legal_mention;
    private TextView content_intellectual_property_legal_mention;
    private TextView title_services_provided_property_legal_mention;
    private TextView content_services_provided_property_legal_mention;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_mention_legales, container, false);


        return root;
    }

    public void onStart() {
        super.onStart();

        explante_legal_mention = this.root.findViewById(R.id.explante_legal_mention);
        title_editing_the_application_legal_mention = this.root.findViewById(R.id.title_editing_the_application_legal_mention);
        content_editing_the_application_legal_mention = this.root.findViewById(R.id.content_editing_the_application_legal_mention);
        title_responsible_for_publication_legal_mention = this.root.findViewById(R.id.title_responsible_for_publication_legal_mention);
        content_responsible_for_publication_legal_mention = this.root.findViewById(R.id.content_responsible_for_publication_legal_mention);
        title_host_legal_mention = this.root.findViewById(R.id.title_host_legal_mention);
        content_host_legal_mention = this.root.findViewById(R.id.content_host_legal_mention);
        title_contact_us_legal_mention = this.root.findViewById(R.id.title_contact_us_legal_mention);
        content_contact_us_legal_mention = this.root.findViewById(R.id.content_contact_us_legal_mention);
        title_CNIL_legal_mention = this.root.findViewById(R.id.title_CNIL_legal_mention);
        content_CNIL_legal_mention = this.root.findViewById(R.id.content_CNIL_legal_mention);
        title_disputes_legal_mention = this.root.findViewById(R.id.title_disputes_legal_mention);
        content_disputes_legal_mention = this.root.findViewById(R.id.content_disputes_legal_mention);
        title_intellectual_property_legal_mention = this.root.findViewById(R.id.title_intellectual_property_legal_mention);
        content_intellectual_property_legal_mention = this.root.findViewById(R.id.content_intellectual_property_legal_mention);
        title_services_provided_property_legal_mention = this.root.findViewById(R.id.title_services_provided_property_legal_mention);
        content_services_provided_property_legal_mention = this.root.findViewById(R.id.content_services_provided_property_legal_mention);

        if (Locale.getDefault().getDisplayLanguage().equals("français"))
            MentionLegalesDAO.findMension(this, "fr");

        else {
            MentionLegalesDAO.findMension(this, "en");

        }

    }

    public static String[] cutout(String text, String sep) {
        return text.split(sep);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("test", error + "là");
        Toast.makeText(this.getContext(), R.string.error_db, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONArray answer) {
        try {
            for (int i = 0; i < answer.length(); i++) {
                JSONObject o = answer.getJSONObject(i);
                String mention = o.getString("mentions");
                String[] tab = cutout(mention, "###");

                explante_legal_mention.setText(tab[0]);
                title_editing_the_application_legal_mention.setText(tab[1]);
                content_editing_the_application_legal_mention.setText(tab[2]);
                title_responsible_for_publication_legal_mention.setText(tab[3]);
                content_responsible_for_publication_legal_mention.setText(tab[4]);
                title_host_legal_mention.setText(tab[5]);
                content_host_legal_mention.setText(tab[6]);
                title_contact_us_legal_mention.setText(tab[7]);
                content_contact_us_legal_mention.setText(tab[8]);
                title_CNIL_legal_mention.setText(tab[9]);
                content_CNIL_legal_mention.setText(tab[10]);
                title_disputes_legal_mention.setText(tab[11]);
                content_disputes_legal_mention.setText(tab[12]);
                title_intellectual_property_legal_mention.setText(tab[13]);
                content_intellectual_property_legal_mention.setText(tab[14]);
                title_services_provided_property_legal_mention.setText(tab[15]);
                content_services_provided_property_legal_mention.setText(tab[16]);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}