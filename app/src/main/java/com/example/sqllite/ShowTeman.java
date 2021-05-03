package com.example.sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ShowTeman extends AppCompatActivity {

    TextView tvNama, tvNoTelepon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_teman);

        tvNama = findViewById(R.id.tvDetailNama);
        tvNoTelepon = findViewById(R.id.tvDetailTelepon);

        Bundle bundle = getIntent().getExtras();

        String nama = bundle.getString("nama");
        String noTelepon = bundle.getString("telpon");

        tvNama.setText(nama);
        tvNoTelepon.setText(noTelepon);
    }
}