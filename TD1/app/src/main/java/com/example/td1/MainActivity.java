package com.example.td1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.example.td1.modele.Pull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button prevBtn;
    private Button nextBtn;
    ArrayList<Pull> listPull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.prevBtn = this.findViewById(R.id.prev_btn);
        this.nextBtn = this.findViewById(R.id.next_btn);

        listPull.push(new Pull(25, "src", "description", "title1"));
        listPull.push(new Pull(25, "src", "description", "title2"));
        listPull.push(new Pull(25, "src", "description", "title3"));
        listPull.push(new Pull(25, "src", "description", "title4"));
        listPull.push(new Pull(25, "src", "description", "title5"));
    }
}