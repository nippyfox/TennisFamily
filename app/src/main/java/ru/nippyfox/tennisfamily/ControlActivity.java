package ru.nippyfox.tennisfamily;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ControlActivity extends AppCompatActivity {
    int score1, score2, finishGame, finishPitch;
    TextView txtScore1, txtScore2, txtWinned, txtLosed, txtLeftPitcher, txtRightPitcher;
    Button btnScore1, btnScore2;
    String playerOne, playerTwo;

    final int MENU_RESET_FIRST = 1;
    final int MENU_RESET_SECOND = 2;

    private long backPressedTime;

    @Override
    public void onBackPressed() { //
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finish();
            System.exit(0);
        } else {
            Toast.makeText(getBaseContext(), "Нажмите ещё раз для выхода", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    private void startNewGame() {
        score1 = 0;
        score2 = 0;
        txtScore1.setText(R.string.score_00);
        txtScore2.setText(R.string.score_00);

        final String[] winNamesDialog = {
                String.format("Выиграл %s", playerOne),
                String.format("Выиграл %s", playerTwo)
        };

        AlertDialog startDlg = new AlertDialog.Builder(ControlActivity.this)
                .setTitle("Розыгрыш партии")
                .setItems(winNamesDialog, (dialog, which) -> {
                    if (which == 0) {
                        txtWinned = txtLeftPitcher; txtLosed = txtRightPitcher;
                    } else {
                        txtWinned = txtRightPitcher; txtLosed = txtLeftPitcher;
                    }
                    txtWinned.setText(R.string.arrow_pitcher);
                    txtLosed.setText(R.string.empty);
                }).create();
        startDlg.show();

        // TODO: dismiss AlertDialog startDlg to close
    }

    public void finishPart() {
        String winAlertMsg;
        String anotherGame = "\nСыграть ещё одну партию?";
        if (score1 == finishGame) {
            winAlertMsg = "Победу одержал " + playerOne + " со счётом " + score1 + ":" + score2 + anotherGame;
        } else {
            winAlertMsg = "Победу одержал " + playerTwo + " со счётом " + score1 + ":" + score2 + anotherGame;
        }

        AlertDialog dlg = new AlertDialog.Builder(ControlActivity.this)
                .setTitle("Партия завершена")
                .setMessage(winAlertMsg)
                .setPositiveButton("Играть дальше", (dialog, which) -> {
                    dialog.dismiss();
                    startNewGame();
                })
                .setNegativeButton("В меню", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                    System.exit(0);
                })
                .create();
        dlg.show();
    }

    private String toScore(int score) {
        String textScore = "";
        if (score < 10) {
            textScore = "0";
        }
        textScore += String.valueOf(score);
        return textScore;
    }

    public void checkPitcher() {
        int test = (score1 + score2) / finishPitch;
        if (test % 2 == 0) {
            txtWinned.setText(R.string.arrow_pitcher);
            txtLosed.setText(R.string.empty);
        } else {
            txtWinned.setText(R.string.empty);
            txtLosed.setText(R.string.arrow_pitcher);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        txtScore1 = findViewById(R.id.txtScore1);
        txtScore2 = findViewById(R.id.txtScore2);
        txtLeftPitcher = findViewById(R.id.txtLeftPitcher);
        txtRightPitcher = findViewById(R.id.txtRightPitcher);

        btnScore1 = findViewById(R.id.btnPlayerLeft);
        btnScore2 = findViewById(R.id.btnPlayerRight);

        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        playerOne = sharedPreferences.getString("namePlayer1","игрок 1");
        btnScore1.setText(String.format("Забил %s", playerOne));

        playerTwo = sharedPreferences.getString("namePlayer2", "игрок 2");
        btnScore2.setText(String.format("Забил %s", playerTwo));

        finishPitch = sharedPreferences.getInt("finishPitch", 5);
        finishGame = sharedPreferences.getInt("finishGame", 21);

        startNewGame();

        btnScore1.setOnClickListener(v -> {
            score1 += 1;
            txtScore1.setText(toScore(score1));
            if (score1 == finishGame) {
                finishPart();
            }
            else if ((score1 + score2) % finishPitch == 0) {
                Toast.makeText(this, "Переход подачи", Toast.LENGTH_SHORT).show();
                checkPitcher();
            }
            else {
                checkPitcher();
            }
        });

        btnScore2.setOnClickListener(v -> {
            score2 += 1;
            txtScore2.setText(toScore(score2));
            if (score2 == finishGame) {
                finishPart();
            }
            else if ((score1 + score2) % finishPitch == 0) {
                Toast.makeText(this, "Переход подачи", Toast.LENGTH_SHORT).show();
                checkPitcher();
            }
            else {
                checkPitcher();
            }
        });

        registerForContextMenu(txtScore1);
        registerForContextMenu(txtScore2);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        switch (v.getId()) {
            case R.id.txtScore1:
                menu.add(0, MENU_RESET_FIRST, 0, String.format("Отменить присвоение очка игроку %s", playerOne));
                break;
            case R.id.txtScore2:
                menu.add(0, MENU_RESET_SECOND, 0, String.format("Отменить присвоение очка игроку %s", playerTwo));
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case MENU_RESET_FIRST:
                if (score1 >= 1) {
                    score1 -= 1;
                    txtScore1.setText(toScore(score1));
                    checkPitcher();
                } else {
                    Toast.makeText(this, "Невозможно выполнить действие!", Toast.LENGTH_LONG).show();
                }
                break;
            case MENU_RESET_SECOND:
                if (score2 >= 1) {
                    score2 -= 1;
                    txtScore2.setText(toScore(score2));
                    checkPitcher();
                } else {
                    Toast.makeText(this, "Невозможно выполнить действие!", Toast.LENGTH_LONG).show();
                }
                break;
        }

        return super.onContextItemSelected(item);
    }
}