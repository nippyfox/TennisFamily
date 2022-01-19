package ru.nippyfox.tennisfamily;

import static ru.nippyfox.tennisfamily.MainActivity.SHARED_PREFS;
import static ru.nippyfox.tennisfamily.MainActivity.SHARED_PREFS_2;
import static ru.nippyfox.tennisfamily.MainActivity.namePlayer1;
import static ru.nippyfox.tennisfamily.MainActivity.namePlayer2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    EditText editPlayerLeft;
    Button btnChangeLeft;
    EditText editPlayerRight;
    Button btnChangeRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editPlayerLeft = findViewById(R.id.editPlayerLeft);
        editPlayerRight = findViewById(R.id.editPlayerRight);
        btnChangeLeft = findViewById(R.id.btnChangeLeft);
        btnChangeRight = findViewById(R.id.btnChangeRight);

        editPlayerLeft.setText(namePlayer1);
        editPlayerRight.setText(namePlayer2);

        btnChangeLeft.setOnClickListener(v -> {
            if (editPlayerLeft.getText().toString().length() > 0)
                saveFirstPlayer();
            else
                Toast.makeText(this, "Введите имя игрока", Toast.LENGTH_SHORT).show();
        });

        btnChangeRight.setOnClickListener(v -> {
            if (editPlayerRight.getText().toString().length() > 0)
                saveSecondPlayer();
            else
                Toast.makeText(this, "Введите имя игрока", Toast.LENGTH_SHORT).show();
        });
    }

    public void saveFirstPlayer() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(namePlayer1, editPlayerLeft.getText().toString());
        editor.apply();

        Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
    }

    public void saveSecondPlayer() {
        SharedPreferences sharedPreferencesSec = getSharedPreferences(SHARED_PREFS_2, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesSec.edit();

        editor.putString(namePlayer2, editPlayerRight.getText().toString());
        editor.apply();

        Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
    }
}