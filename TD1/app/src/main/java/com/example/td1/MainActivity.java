package com.example.td1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.td1.modele.Pull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button prevBtn;
    private Button nextBtn;
    private TextView priceTextView;
    private TextView descriptionTextView;
    private TextView titleTextView;
    private ImageView pullImageView;
    private ImageView pullImageViewZoomed;
    private View whiteBackgroundView;
    private int index;
    private ArrayList<Pull> listPull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // -- BUTTONS --
        this.prevBtn = this.findViewById(R.id.prev_btn);
        this.nextBtn = this.findViewById(R.id.next_btn);

        // -- TEXTVIEWS --
        this.priceTextView = this.findViewById(R.id.textView3);
        this.descriptionTextView = this.findViewById(R.id.textView);
        this.titleTextView = this.findViewById(R.id.textViewRennes);

        // -- IMAGEVIEWS --
        this.pullImageView = this.findViewById(R.id.imageView);
        this.pullImageViewZoomed = this.findViewById(R.id.expanded_image);

        // -- VIEWS --
        this.whiteBackgroundView = this.findViewById(R.id.view);

        if (savedInstanceState != null) {
            this.listPull = (ArrayList<Pull>) savedInstanceState.getSerializable("listPull");
            this.index = savedInstanceState.getInt("index");
            boolean imageZoomed = savedInstanceState.getBoolean("isImageZoomed");

            if (imageZoomed) {
                zoomImage();
            }
        }

        // -- INITIALIZE ARRAYLIST --
        this.listPull = new ArrayList<Pull>();
        this.listPull.add(new Pull(45, "jigglypuff", "Waw ça c'est du pull tu peux me croire.", "title1"));
        this.listPull.add(new Pull(22, "sweatshirt", "description", "title2"));
        this.listPull.add(new Pull(33, "bunny_hoodie", "description", "title3"));
        this.listPull.add(new Pull(26, "bear_hoodie", "description", "title4"));
        this.listPull.add(new Pull(12, "christmas_pullover", "description", "title5"));

        // -- SET VIEWS ON FIRST PULL --
        showPullInfo(0);
        enableButtons(0);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("index", this.index);
        outState.putSerializable("listPull", this.listPull);

        if (this.pullImageViewZoomed.getVisibility() == View.VISIBLE) {
            outState.putBoolean("isImageZoomed", true);
        } else {
            outState.putBoolean("isImageZoomed", false);
        }
    }

    public void onClickBtnNext(View v) {
        this.index++;
        showPullInfo(this.index);
    }

    public void onClickBtnPrev(View v) {
        this.index--;
        showPullInfo(this.index);
    }

    public void showPullInfo(int index) {
        this.priceTextView.setText(String.valueOf(this.listPull.get(index).getPrice()) + '€');
        this.descriptionTextView.setText(this.listPull.get(index).getDescription());
        this.titleTextView.setText(this.listPull.get(index).getTitle());
        changeImageView(index);

        enableButtons(index);
    }

    public void changeImageView(int index) {
        int id = getResources().getIdentifier(this.listPull.get(index).getImgSrc(), "drawable", getPackageName());
        this.pullImageView.setImageResource(id);
    }

    public void enableButtons(int index) {
        if (index == 0) {
            this.prevBtn.setEnabled(false);
        } else if (index == (this.listPull.size() - 1)) {
            this.nextBtn.setEnabled(false);
        } else {
            this.prevBtn.setEnabled(true);
            this.nextBtn.setEnabled(true);
        }
    }

    public void onClickBtnBasket(View v) {
        Toast.makeText(this, String.format(getString(R.string.ajout_panier), this.index), Toast.LENGTH_SHORT).show();
    }

    public void onClickImage(View v) {
        zoomImage();
    }

    public void onClickImageZoomed(View v) {
        unzoomImage();
    }

    public void zoomImage() {
        this.whiteBackgroundView.setVisibility(View.VISIBLE);
        this.whiteBackgroundView.bringToFront();

        int id = getResources().getIdentifier(this.listPull.get(this.index).getImgSrc(), "drawable", getPackageName());
        this.pullImageViewZoomed.setImageResource(id);
        this.pullImageViewZoomed.setVisibility(View.VISIBLE);
        this.pullImageViewZoomed.bringToFront();
    }

    public void unzoomImage() {
        this.whiteBackgroundView.setVisibility(View.INVISIBLE);
        this.pullImageViewZoomed.setVisibility(View.INVISIBLE);
    }
}