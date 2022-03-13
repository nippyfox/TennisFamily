package ru.nippyfox.tennisfamily;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    EditText editPlayerLeft;
    Button btnChangeLeft;
    EditText editPlayerRight;
    Button btnChangeRight;

    EditText editCountPitch;
    Button btnCountPitch;
    EditText editCountGame;
    Button btnCountGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        editPlayerLeft = findViewById(R.id.editPlayerLeft);
        editPlayerRight = findViewById(R.id.editPlayerRight);
        btnChangeLeft = findViewById(R.id.btnChangeLeft);
        btnChangeRight = findViewById(R.id.btnChangeRight);

        editCountPitch = findViewById(R.id.editCountPitch);
        editCountGame = findViewById(R.id.editCountGame);
        btnCountPitch = findViewById(R.id.btnCountPitch);
        btnCountGame = findViewById(R.id.btnCountGame);

        SharedPreferences sp = getSharedPreferences("data", Context.MODE_PRIVATE);
        editPlayerLeft.setText(sp.getString("namePlayer1","игрок 1"));
        editPlayerRight.setText(sp.getString("namePlayer2","игрок 2"));
        int finishPitch = sp.getInt("finishPitch",5);
        editCountPitch.setText(String.valueOf(finishPitch));
        int finishGame = sp.getInt("finishGame",21);
        editCountGame.setText(String.valueOf(finishGame));

        btnChangeLeft.setOnClickListener(v -> {
            if (editPlayerLeft.getText().toString().length() > 0)
                changeField(1);
            else
                Toast.makeText(this, "Введите имя игрока", Toast.LENGTH_SHORT).show();
        });

        btnChangeRight.setOnClickListener(v -> {
            if (editPlayerRight.getText().toString().length() > 0)
                changeField(2);
            else
                Toast.makeText(this, "Введите имя игрока", Toast.LENGTH_SHORT).show();
        });

        btnCountPitch.setOnClickListener(v -> {
            int getNewPitch = Integer.parseInt(editCountPitch.getText().toString());
            if (String.valueOf(getNewPitch).length() > 0) {
                if (getNewPitch > 0) {
                    if (getNewPitch < finishGame && getNewPitch < 100)
                        changeField(3);
                    else
                        Toast.makeText(this, "Значение не может быть таким", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this, "Значение не может быть равно 0", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Введите количество очков", Toast.LENGTH_SHORT).show();
        });

        btnCountGame.setOnClickListener(v -> {
            int getNewFinish = Integer.parseInt(editCountGame.getText().toString());
            if (String.valueOf(getNewFinish).length() > 0) {
                if (getNewFinish > 0) {
                    if (getNewFinish > finishPitch && getNewFinish < 100)
                        changeField(4);
                    else
                        Toast.makeText(this, "Значение не может быть таким", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(this, "Значение не может быть равно 0", Toast.LENGTH_SHORT).show();
            }
            else
                Toast.makeText(this, "Введите количество очков", Toast.LENGTH_SHORT).show();
        });
    }

    public void changeField(int field) {
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (field) {
            case 1:
                editor.putString("namePlayer1", editPlayerLeft.getText().toString());
                break;
            case 2:
                editor.putString("namePlayer2", editPlayerRight.getText().toString());
                break;
            case 3:
                editor.putInt("finishPitch", Integer.parseInt(editCountPitch.getText().toString()));
                break;
            case 4:
                editor.putInt("finishGame", Integer.parseInt(editCountGame.getText().toString()));
                break;
        }
        editor.apply();

        Toast.makeText(this, "Изменения сохранены", Toast.LENGTH_SHORT).show();
    }
}