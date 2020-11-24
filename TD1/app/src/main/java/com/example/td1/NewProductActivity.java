package com.example.td1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.td1.modele.Pull;

public class NewProductActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText priceEditText;
    private EditText descriptionEditText;

    private static final int MAIN_VENTE = 0;
    private static final int MAIN_CATALOGUE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.priceEditText = this.findViewById(R.id.productPriceEditText);
        this.titleEditText = this.findViewById(R.id.productTitleEditText);
        this.descriptionEditText = this.findViewById(R.id.productDescriptionEditText);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("MainActivity", "test");
        this.onClickGoBack(null);
    }

    public void onClickAddProduct(View v) {
        if (!this.titleEditText.getText().toString().equals("") && !this.descriptionEditText.getText().toString().equals("") && !this.priceEditText.getText().toString().equals("")) {
            Intent intent = new Intent(NewProductActivity.this, MainActivity.class);
            intent.putExtra("newProduct", new Pull(5, 0, Double.parseDouble(String.valueOf(this.priceEditText.getText())), "img", String.valueOf(this.descriptionEditText.getText()), String.valueOf(this.titleEditText.getText())));
            intent.putExtra("id_categ", 0);
            intent.putExtra("requestCode", MAIN_CATALOGUE);
            startActivity(intent);
            this.finish();
        } else {
            Toast.makeText(this, getString(R.string.fill_all_fields), Toast.LENGTH_LONG).show();
        }
    }


    public void onClickGoBack (View v) {
        this.finish();
    }
}