package com.example.td1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

    public void addProduct(View v) {
        Intent intent = new Intent(NewProductActivity.this, MainActivity.class);
        intent.putExtra("newProduct", new Pull(5, 0, Double.parseDouble(String.valueOf(this.priceEditText.getText())), "img", String.valueOf(this.descriptionEditText.getText()), String.valueOf(this.titleEditText.getText())));
        intent.putExtra("id_categ", 0);
        intent.putExtra("requestCode", MAIN_CATALOGUE);
        startActivity(intent);
        this.finish();
    }

    public void onClickGoBack (View v) {
        this.finish();
    }
}