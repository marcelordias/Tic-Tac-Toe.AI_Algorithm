package com.example.jogodogalo;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    private TabAdapter adapter;
    private TextView gameOverMessage;
    private Button startRestartGameButton;
    private GifImageView gifgameOver;

    // Image of player turn
    private ImageView playerImageTime;

    /*
     * 0 -> Is multiPlayer
     * 1 -> Is singlePlayer
     */
    private int isSinglePlayer;

    /*
     * Players
     * 0 -> O (AI)
     * 1 -> X
     */
    private int PLAYER;
    // Positions Images (O <-> X)
    private final int PLAYER_0_IMAGE = R.drawable.o, PLAYER_1_IMAGE = R.drawable.x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportActionBar().hide();

        playerImageTime = (ImageView) findViewById(R.id.imageViewPlayer);
        gifgameOver = (GifImageView) findViewById(R.id.gifgameOver);
        gameOverMessage = (TextView) findViewById(R.id.textViewGameOverMessage);
        startRestartGameButton = (Button) findViewById(R.id.buttonRestartGame);

        GridView tabuleiro = (GridView) findViewById(R.id.TabuleiroId);
        adapter = new TabAdapter(this);
        tabuleiro.setAdapter(adapter);

        tabuleiro.setOnItemClickListener(this::onItemClick);
        startRestartGameButton.setOnClickListener(v -> start());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle extras = getIntent().getExtras();
        isSinglePlayer = extras.getInt("IS_SINGLE_PLAYER", 0);
        adapter.setAllItemDisabled();
    }

    public void start() {
        for (int i = 0; i < 9; i++) {
            adapter.setItemClickable(i, true);
            ((ImageView) findViewById(i + 1)).setBackgroundColor(Color.GRAY);
            ((ImageView) findViewById(i + 1)).setImageResource(0);
            ((ImageView) findViewById(i + 1)).setContentDescription("-1");
        }

        // random
        PLAYER = ((int) (Math.random() * 2));
        if (PLAYER == 0) {
            playerImageTime.setImageResource(PLAYER_0_IMAGE);
            if (isSinglePlayer == 1) {
                // computerPlay();
                setMove((int) (Math.random() * 8));
            }
        } else {
            playerImageTime.setImageResource(PLAYER_1_IMAGE);
        }

        gifgameOver.setVisibility(View.GONE);
        gameOverMessage.setVisibility(View.GONE);
        startRestartGameButton.setVisibility(View.GONE);

    }

    private void gameOver() {
        //
        adapter.setAllItemDisabled();
        gifgameOver.setVisibility(View.VISIBLE);
        gameOverMessage.setVisibility(View.VISIBLE);
        startRestartGameButton.setVisibility(View.VISIBLE);

    }

    public void setPosition(int position) {
        ImageView pop = findViewById(position + 1);
        if (PLAYER == 0) {
            // Change for O image
            pop.setImageResource(PLAYER_0_IMAGE);
        } else {
            // Change for 1 image
            pop.setImageResource(PLAYER_1_IMAGE);
        }
        pop.setContentDescription(String.valueOf(PLAYER));
        adapter.setItemClickable(position, false);

    }

    public void setPlayer() {
        if (PLAYER == 0) {
            playerImageTime.setImageResource(PLAYER_1_IMAGE);
            PLAYER = 1;
        } else {
            playerImageTime.setImageResource(PLAYER_0_IMAGE);
            PLAYER = 0;
        }
    }

    public void highlightWin(int pos1, int pos2, int pos3) {
        ((ImageView) findViewById(pos1)).setBackgroundColor(Color.RED);
        ((ImageView) findViewById(pos2)).setBackgroundColor(Color.RED);
        ((ImageView) findViewById(pos3)).setBackgroundColor(Color.RED);
    }

    public int checkGame(boolean isMinimaxAlgorithm) {
        //
        int[][] positionsMultiArray = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        // Horizontal's check
        for (int i = 0; i < 3; i++) {
            if (!findViewById(positionsMultiArray[i][0]).getContentDescription().toString().equals("-1") && !findViewById(positionsMultiArray[i][1]).getContentDescription().toString().equals("-1") && !findViewById(positionsMultiArray[i][2]).getContentDescription().toString().equals("-1")) {
                if (findViewById(positionsMultiArray[i][0]).getContentDescription().toString().equals(findViewById(positionsMultiArray[i][1]).getContentDescription().toString()) && findViewById(positionsMultiArray[i][1]).getContentDescription().toString().equals(findViewById(positionsMultiArray[i][2]).getContentDescription().toString())) {
                    if (!isMinimaxAlgorithm) {
                        highlightWin(positionsMultiArray[i][0], positionsMultiArray[i][1], positionsMultiArray[i][2]);
                    }

                    if (findViewById(positionsMultiArray[i][0]).getContentDescription().toString().equals("0")) {
                        gameOverMessage.setText("Jogador <O> ganhou!");
                        return 1;
                    } else {
                        gameOverMessage.setText("Jogador <X> ganhou!");
                        return -1;
                    }


                }
            }
        }

        // Vertical's check
        for (int i = 0; i < 3; i++) {
            if (!findViewById(positionsMultiArray[0][i]).getContentDescription().toString().equals("-1") && !findViewById(positionsMultiArray[1][i]).getContentDescription().toString().equals("-1") && !findViewById(positionsMultiArray[2][i]).getContentDescription().toString().equals("-1")) {
                if (findViewById(positionsMultiArray[0][i]).getContentDescription().toString().equals(findViewById(positionsMultiArray[1][i]).getContentDescription().toString()) && findViewById(positionsMultiArray[1][i]).getContentDescription().toString().equals(findViewById(positionsMultiArray[2][i]).getContentDescription().toString())) {
                    if (!isMinimaxAlgorithm) {
                        highlightWin(positionsMultiArray[0][i], positionsMultiArray[1][i], positionsMultiArray[2][i]);
                    }
                    if (findViewById(positionsMultiArray[0][i]).getContentDescription().toString().equals("0")) {
                        gameOverMessage.setText("Jogador <O> ganhou!");
                        return 1;
                    } else {
                        gameOverMessage.setText("Jogador <X> ganhou!");
                        return -1;
                    }
                }
            }
        }

        // Diagonal's check
        if ((!findViewById(positionsMultiArray[0][0]).getContentDescription().toString().equals("-1") && !findViewById(positionsMultiArray[1][1]).getContentDescription().toString().equals("-1") && !findViewById(positionsMultiArray[2][2]).getContentDescription().toString().equals("-1")) || (!findViewById(positionsMultiArray[2][0]).getContentDescription().toString().equals("-1") && !findViewById(positionsMultiArray[1][1]).getContentDescription().toString().equals("-1") && !findViewById(positionsMultiArray[0][2]).getContentDescription().toString().equals("-1"))) {
            if (findViewById(positionsMultiArray[0][0]).getContentDescription().toString().equals(findViewById(positionsMultiArray[1][1]).getContentDescription().toString()) && findViewById(positionsMultiArray[1][1]).getContentDescription().toString().equals(findViewById(positionsMultiArray[2][2]).getContentDescription().toString())) {
                if (!isMinimaxAlgorithm) {
                    highlightWin(positionsMultiArray[0][0], positionsMultiArray[1][1], positionsMultiArray[2][2]);
                }

                if (findViewById(positionsMultiArray[0][0]).getContentDescription().toString().equals("0")) {
                    gameOverMessage.setText("Jogador <O> ganhou!");
                    return 1;
                } else {
                    gameOverMessage.setText("Jogador <X> ganhou!");
                    return -1;
                }

            }

            if (findViewById(positionsMultiArray[2][0]).getContentDescription().toString().equals(findViewById(positionsMultiArray[1][1]).getContentDescription().toString()) && findViewById(positionsMultiArray[1][1]).getContentDescription().toString().equals(findViewById(positionsMultiArray[0][2]).getContentDescription().toString())) {
                if (!isMinimaxAlgorithm) {
                    highlightWin(positionsMultiArray[2][0], positionsMultiArray[1][1], positionsMultiArray[0][2]);
                }
                if (findViewById(positionsMultiArray[2][0]).getContentDescription().toString().equals("0")) {
                    gameOverMessage.setText("Jogador <O> ganhou!");
                    return 1;
                } else {
                    gameOverMessage.setText("Jogador <X> ganhou!");
                    return -1;
                }


            }
        }

        // Tie
        if (adapter.isAllDisabled()) {
            gameOverMessage.setText("Empate!");
            return 0;
        }

        return 2;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        setMove(position);
    }

    public void setMove(int position) {
        setPosition(position);
        // Check game
        if (checkGame(false) != 2) {
            gameOver();
        } else {
            setPlayer();
            if (isSinglePlayer == 1) {
                if (PLAYER == 0) {
                    computerPlay();
                }
            }
        }
    }

    public void computerPlay() {
        int move = 0, bestScore = Integer.MIN_VALUE;

        // Verificar espaço disponivel (Ordem 1->9)
        for (int i = 1; i <= 9; i++) {
            ImageView position = findViewById(i);
            if (position.getContentDescription().toString().equals("-1")) {

                position.setContentDescription("0");
                adapter.setItemClickable(i - 1, false);

                int score = minimax(0, false);

                adapter.setItemClickable(i - 1, true);
                position.setContentDescription("-1");

                if (score > bestScore) {
                    bestScore = score;
                    move = i - 1;
                }

                // Log.d("Test",i+"->"+score);
            }
        }

        setMove(move);

    }

    /*
     * Status
     *  O -> 1
     *  tie -> 0
     *  X -> -1
     */

    public int minimax(int depth, boolean isMaximizing) {
        int result = checkGame(true);

        if (result != 2) {
            return result;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 1; i <= 9; i++) {
                ImageView position = findViewById(i);
                if (position.getContentDescription().toString().equals("-1")) {

                    adapter.setItemClickable(i - 1, false);
                    position.setContentDescription("0");

                    int score = minimax(depth + 1, false);

                    adapter.setItemClickable(i - 1, true);
                    position.setContentDescription("-1");
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int worstScore = Integer.MAX_VALUE;
            for (int i = 1; i <= 9; i++) {
                ImageView position = findViewById(i);
                if (position.getContentDescription().toString().equals("-1")) {

                    adapter.setItemClickable(i - 1, false);
                    position.setContentDescription("1");

                    int score = minimax(depth + 1, true);

                    adapter.setItemClickable(i - 1, true);
                    position.setContentDescription("-1");
                    worstScore = Math.min(score, worstScore);
                }
            }
            return worstScore;
        }
    }

}