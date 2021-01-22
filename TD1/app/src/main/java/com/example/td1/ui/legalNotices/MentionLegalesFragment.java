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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class MentionLegalesFragment extends Fragment implements com.android.volley.Response.Listener<JSONArray>, com.android.volley.Response.ErrorListener {

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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_legal_notices, container, false);
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

        if (Locale.getDefault().getDisplayLanguage().equals("français"))
            LegalNoticesDAO.getLegalNoticesByLang(this, "fr");
        else {
            LegalNoticesDAO.getLegalNoticesByLang(this, "en");
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
    public void onResponse(JSONArray answer) {
        try {
            for (int i = 0; i < answer.length(); i++) {
                JSONObject o = answer.getJSONObject(i);
                String legalNotices = o.getString("mentions");
                String[] legalNoticesTab = cutout(legalNotices, "###");

                beginningTextView.setText(legalNoticesTab[0]);
                titleEditAppTextView.setText(legalNoticesTab[1]);
                contentEditAppTextView.setText(legalNoticesTab[2]);
                titleRespPublicationTextView.setText(legalNoticesTab[3]);
                contentRespPublicationTextView.setText(legalNoticesTab[4]);
                titleHostTextView.setText(legalNoticesTab[5]);
                contentHostTextView.setText(legalNoticesTab[6]);
                titleContactUsTextView.setText(legalNoticesTab[7]);
                contentContactUsTextView.setText(legalNoticesTab[8]);
                titleCNILTextView.setText(legalNoticesTab[9]);
                contentCNILTextView.setText(legalNoticesTab[10]);
                titleDisputesTextView.setText(legalNoticesTab[11]);
                contentDisputesTextView.setText(legalNoticesTab[12]);
                titleIntPropTextView.setText(legalNoticesTab[13]);
                contentIntPropTextView.setText(legalNoticesTab[14]);
                titleServProvidedTextView.setText(legalNoticesTab[15]);
                contentServProvidedTextView.setText(legalNoticesTab[16]);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}