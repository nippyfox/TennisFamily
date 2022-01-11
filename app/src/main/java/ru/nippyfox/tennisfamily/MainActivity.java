package ru.nippyfox.tennisfamily;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    static final String namePlayer1 = "игрок 1";
    public static final String SHARED_PREFS_2 = "sharedPrefs";
    static final String namePlayer2 = "игрок 2";
    static final int finishPitch = 5;
    static final int finishGame = 21;

    Button btnStart;
    Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = findViewById(R.id.btnStart);
        btnSettings = findViewById(R.id.btnSettings);

        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ControlActivity.class);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}