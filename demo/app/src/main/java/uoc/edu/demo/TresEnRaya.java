package uoc.edu.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class TresEnRaya extends AppCompatActivity {
		ImageButton[][] board = new ImageButton[3][3];
		TextView msg;
		Button btn_play;
		private final int MAX_COL = 3;
		private final int MAX_ROW = 3;
		private final int EMPTY = 0;
		private final int X = 1;
		private final int O = 2;
		private boolean turnOfX = false;
		private boolean withIA = false;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_tres_en_raya);
				msg = findViewById(R.id.msg);
				btn_play = findViewById(R.id.btn_play);
				btn_play.setVisibility(View.INVISIBLE);
				Intent intent = getIntent();
				withIA = intent.getBooleanExtra(Menu.AI_ENABLED, false);
				updateUserTurn();

				for (int row = 0; row < MAX_COL; row++) {
					for (int col = 0; col < MAX_ROW; col++) {
						String btnId = "btn_"+row+col;
						int resID = getResources().getIdentifier(btnId,"id", getPackageName());
						board[row][col] = findViewById(resID);
						board[row][col].setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View view) {
								ImageButton btn = (ImageButton) view;
								int value = (int) btn.getTag();
								boolean winner = false;

								if(value == EMPTY){
									if(turnOfX){
										btn.setImageResource(R.drawable.tictactoe_cell_x);
										btn.setTag(X);
									} else {
										btn.setImageResource(R.drawable.tictactoe_cell_o);
										btn.setTag(O);
									}
									winner = checkWinner();
								}
								if(!winner){
									turnOfX = !turnOfX;
									updateUserTurn();
									if (turnOfX && withIA){
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
			Drawable icon= getApplicationContext().getResources().getDrawable( R.drawable.tictactoe_empty_cell);
			for (int row = 0; row < MAX_COL; row++) {
					for (int col = 0; col < MAX_ROW; col++) {
							board[row][col].setTag(EMPTY);
							board[row][col].setImageResource(R.drawable.tictactoe_empty_cell);
					}
			}
		}

		private boolean checkWinner(){
			boolean winner = false;
			int[][] status = new int[3][3];

			for (int row = 0; row < MAX_COL; row++) {
				for (int col = 0; col < MAX_ROW; col++) {
					status[row][col] = (int) board[row][col].getTag();
				}
			}

			for (int col = 0; col < MAX_COL; col++) {
				if(status[col][0] == status[col][1] && status[col][0] == status[col][2] && status[col][0] != EMPTY){
					winner = true;
				}
			}

			for (int row = 0; row < MAX_ROW; row++) {
				if (status[0][row] == status[1][row] && status[0][row] == status[2][row] && status[0][row] != EMPTY) {
					winner = true;
				}
			}

			//Diagonal 00
			if(status[0][0] == status[1][1] && status[0][0] == status[2][2] && status[0][0] != EMPTY){
				winner = true;
			}
			//diagonal 02
			if(status[0][2] == status[1][1] && status[0][2] == status[2][0] && status[0][2] != EMPTY){
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
				if(withIA){
					msg.setText("Turno de las X (IA)");
				} else {
					msg.setText("Turno de las X");
				}
			} else {
				msg.setText("Turno de las O");
			}
		}

		private void updateAI(){
			int Min = 0;
			int Max = 2;
			int col;
			int row;
			int tag;

			do {
					col = Min + (int)(Math.random() * ((Max - Min) + 1));
					row = Min + (int)(Math.random() * ((Max - Min) + 1));
					tag = (int) board[row][col].getTag();
			} while(tag != EMPTY);

			board[row][col].performClick();
		}


}