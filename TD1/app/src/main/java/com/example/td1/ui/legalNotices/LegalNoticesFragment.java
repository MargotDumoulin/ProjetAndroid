package com.example.td1.ui.legalNotices;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.example.td1.DAO.LegalNoticesDAO;
import com.example.td1.R;
import com.example.td1.modele.Produit;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class LegalNoticesFragment extends Fragment implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener {

    private View root;

    private TextView beginningTextView;
    private TextView titleEditAppTextView;
    private TextView contentEditAppTextView;
    private TextView titleRespPublicationTextView;
    private TextView contentRespPublicationTextView;
    private TextView titleHostTextView;
    private TextView contentHostTextView;
    private TextView titleContactUsTextView;
    private TextView contentContactUsTextView;
    private TextView titleCNILTextView;
    private TextView contentCNILTextView;
    private TextView titleDisputesTextView;
    private TextView contentDisputesTextView;
    private TextView titleIntPropTextView;
    private TextView contentIntPropTextView;
    private TextView titleServProvidedTextView;
    private TextView contentServProvidedTextView;

    private String[] legalNoticesTab = null;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_legal_notices, container, false);

        if (savedInstanceState != null) {
            legalNoticesTab = (String[]) savedInstanceState.getSerializable("legalNoticesTab");
        }
        return root;
    }

    public void onStart() {
        super.onStart();

        beginningTextView = this.root.findViewById(R.id.beginningLegalNoticesTextView);
        titleEditAppTextView = this.root.findViewById(R.id.titleEditAppLegalNoticesTextView);
        contentEditAppTextView = this.root.findViewById(R.id.contentEditAppLegalNoticesTextView);
        titleRespPublicationTextView = this.root.findViewById(R.id.titleRespPublicationLegalNoticesTextView);
        contentRespPublicationTextView = this.root.findViewById(R.id.contentRespPublicationLegalNoticesTextView);
        titleHostTextView = this.root.findViewById(R.id.titleHostLegalNoticesTextView);
        contentHostTextView = this.root.findViewById(R.id.contentHostLegalNoticesTextView);
        titleContactUsTextView = this.root.findViewById(R.id.titleContactUsLegalNoticesTextView);
        contentContactUsTextView = this.root.findViewById(R.id.contentContactUsLegalNoticesTextView);
        titleCNILTextView = this.root.findViewById(R.id.titleCNILLegalNoticesTextView);
        contentCNILTextView = this.root.findViewById(R.id.contentCNILLegalNoticesTextView);
        titleDisputesTextView = this.root.findViewById(R.id.titleDisputesLegalNoticesTextView);
        contentDisputesTextView = this.root.findViewById(R.id.contentDisputesLegalNotices);
        titleIntPropTextView = this.root.findViewById(R.id.titleIntPropLegalNoticesTextView);
        contentIntPropTextView = this.root.findViewById(R.id.contentIntPropLegalNoticesTextView);
        titleServProvidedTextView = this.root.findViewById(R.id.titleServProvidedLegalNoticesTextView);
        contentServProvidedTextView = this.root.findViewById(R.id.contentServProvidedLegalNoticesTextView);

        if (legalNoticesTab == null) {
            if (Locale.getDefault().getDisplayLanguage().equals("français")) {
                LegalNoticesDAO.getLegalNoticesByLang(this, "fr");
            } else {
                LegalNoticesDAO.getLegalNoticesByLang(this, "en");
            }
        } else {
            integrateValueIntoTextView();
        }
    }

    @Override
    public void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("legalNoticesTab", this.legalNoticesTab);
    }

    public void integrateValueIntoTextView() {
        if (this.legalNoticesTab != null) {
            beginningTextView.setText(this.legalNoticesTab[0]);
            titleEditAppTextView.setText(this.legalNoticesTab[1]);
            contentEditAppTextView.setText(this.legalNoticesTab[2]);
            titleRespPublicationTextView.setText(this.legalNoticesTab[3]);
            contentRespPublicationTextView.setText(this.legalNoticesTab[4]);
            titleHostTextView.setText(this.legalNoticesTab[5]);
            contentHostTextView.setText(this.legalNoticesTab[6]);
            titleContactUsTextView.setText(this.legalNoticesTab[7]);
            contentContactUsTextView.setText(this.legalNoticesTab[8]);
            titleCNILTextView.setText(this.legalNoticesTab[9]);
            contentCNILTextView.setText(this.legalNoticesTab[10]);
            titleDisputesTextView.setText(this.legalNoticesTab[11]);
            contentDisputesTextView.setText(this.legalNoticesTab[12]);
            titleIntPropTextView.setText(this.legalNoticesTab[13]);
            contentIntPropTextView.setText(this.legalNoticesTab[14]);
            titleServProvidedTextView.setText(this.legalNoticesTab[15]);
            contentServProvidedTextView.setText(this.legalNoticesTab[16]);
        }

    }

    public static String[] cutout(String text, String sep) {
        return text.split(sep);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Volley error", error.getMessage());
        Toast.makeText(this.getContext(), R.string.error_db, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject o) {
        String legalNotices = null;
        try {
            legalNotices = o.getString("mentions");
            this.legalNoticesTab = cutout(legalNotices, "###");
            integrateValueIntoTextView();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}