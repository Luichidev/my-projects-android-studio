package uoc.edu.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Random;

public class TresEnRaya extends AppCompatActivity {
		Button[][] board = new Button[3][3];
		TextView msg;
		private final int MAX_COL = 3;
		private final int MAX_ROW = 3;
		private final String EMPTY = "";
		Button btn_play;
		private boolean turnOfX = false;
		private boolean withIA;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_tres_en_raya);
				msg = findViewById(R.id.msg);
				btn_play = findViewById(R.id.btn_play);
				btn_play.setVisibility(View.INVISIBLE);
				Intent intent = getIntent();
				withIA = intent.getBooleanExtra(Menu.AI_ENABLED, false);



				msg.setText("Juegan las X");
				updateUserTurn();

				for (int col = 0; col < MAX_COL; col++) {
					for (int row = 0; row < MAX_ROW; row++) {
						String btnId = "btn_"+col+row;
						int resID = getResources().getIdentifier(btnId,"id", getPackageName());
						board[col][row] = findViewById(resID);
						board[col][row].setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								Button btn = (Button) view;
								String value = btn.getText().toString();
								boolean winner = false;
								if(value.compareTo(EMPTY) == 0){
									if(turnOfX){
										btn.setText("X");
									} else {
										btn.setText("O");
									}
									winner = checkWinner();
								}
								if(!winner){
									turnOfX = !turnOfX;
									updateUserTurn();
									if (withIA){
										updateAI();
									}
								}
							}
						});
					}
				}

				resetBoard();
				btn_play.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View view) {
								resetBoard();
								updateUserTurn();
						}
				});

		}

		private void resetBoard(){
			btn_play.setVisibility(View.INVISIBLE);
			for (int col = 0; col < MAX_COL; col++) {
					for (int row = 0; row < MAX_ROW; row++) {
							board[col][row].setText(EMPTY);
					}
			}
		}

		private boolean checkWinner(){
			boolean winner = false;
			String[][] field = new String[3][3];

			for (int col = 0; col < MAX_COL; col++) {
				for (int row = 0; row < MAX_ROW; row++) {
					field[col][row] = board[col][row].getText().toString();
				}
			}

			for (int col = 0; col < MAX_COL; col++) {
				if(field[col][0].equals(field[col][1]) && field[col][0].equals(field[col][2]) && !field[col][0].equals(EMPTY)){
					winner = true;
				}
			}

			for (int row = 0; row < MAX_ROW; row++) {
				if (field[0][row].equals(field[1][row])
								&& field[0][row].equals(field[2][row])
								&& !field[0][row].equals(EMPTY)) {
					winner = true;
				}
			}

			//Diagonal 00
			if(field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2]) && !field[0][0].equals(EMPTY)){
				winner = true;
			}
			//diagonal 02
			if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0]) && !field[0][2].equals(EMPTY)){
				winner = true;
			}

			if (winner){
				if (turnOfX) {
					if (withIA){
						msg.setText("Ganan las X (AI)");
					} else {
						msg.setText("Ganan las X");
					}
				}else{
					msg.setText("Ganan las O");
				}
				btn_play.setVisibility(View.VISIBLE);
			}
			return winner;
		}

		private void updateUserTurn(){
			if(turnOfX){
				msg.setText("Turno de las X");
			} else {
				msg.setText("Turno de las O");
			}
		}

		private void updateAI(){
			int min = 0;
			int max = 2;
			Random random = new Random();
			int col;
			int row;

			do {
					col = random.nextInt(max + 1 - min) + min;
					row = random.nextInt(max + 1 - min) + min;
			} while(board[col][row].getText().toString().equals(EMPTY));

			board[col][row].performClick();
		}
}