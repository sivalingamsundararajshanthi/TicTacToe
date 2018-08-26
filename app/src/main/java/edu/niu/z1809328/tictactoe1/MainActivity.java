package edu.niu.z1809328.tictactoe1;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button [][] buttons;

    private TicTacToe game;

    private TextView gameStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //create the TicTacToe object
        game = new TicTacToe();



        buildGUI();
    }//end of onCreate

    public void buildGUI(){
        //Get the width of the screen
        Point size = new Point();

        getWindowManager().getDefaultDisplay().getSize(size);

        //calculate the width of the button
        int width = size.x / TicTacToe.SIDE;

        //Create a gridlayout programatically
        GridLayout gridLayout = new GridLayout(this);

        //set the number of rows and column for the grid
        gridLayout.setRowCount(TicTacToe.SIDE+1);
        gridLayout.setColumnCount(TicTacToe.SIDE);

        //create the array of buttons
        buttons = new Button[TicTacToe.SIDE][TicTacToe.SIDE];

        //create a buttonhandler
        ButtonHandler buttonHandler = new ButtonHandler();

        //Add a button to the grid layout
        for(int row = 0; row <TicTacToe.SIDE; row++)
            for ( int col = 0; col< TicTacToe.SIDE; col++)
        {
            //create an individual button and put it in the array of button
            buttons[row][col] = new Button(this);

            //change the textsize for the button
            buttons[row][col].setTextSize((int)(width*0.2));

            //associate the buttonhandler with the button
            buttons[row][col].setOnClickListener(buttonHandler);


            //put the button in the gridLayout
            gridLayout.addView(buttons[row][col],width,width);
        }

        //Add in the textview

        //create the textview
        gameStatus = new TextView(this);

        //create specifications for the textview that will be added to the gridlayout
        GridLayout.Spec rowSpec= GridLayout.spec(TicTacToe.SIDE, 1);
        GridLayout.Spec colspec= GridLayout.spec(0,TicTacToe.SIDE);

        //Create layout parameters for the textview
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(rowSpec,colspec);

        //apply the layout parameters to the textview
        gameStatus.setLayoutParams(params);

        //center the text in the textview
        gameStatus.setGravity(Gravity.CENTER);

        //set the color of the textview
        gameStatus.setBackgroundColor(Color.MAGENTA);

        //set the width
        gameStatus.setWidth(TicTacToe.SIDE*width);

        //set the textsize
        gameStatus.setTextSize((int)(width*0.15));

        //put the status of the game in the textview
        gameStatus.setText(game.result());

        //add the textview to the grid layout
        gridLayout.addView(gameStatus);

        setContentView(gridLayout);
    }//end of buildGUI

    private class ButtonHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            //display a Toast message that shows which button was touched
            Toast.makeText(MainActivity.this,"ButtonHandler onClick: "+view,Toast.LENGTH_SHORT).show();

            //Determine the Button was clicked
            for (int row = 0; row < TicTacToe.SIDE; row++)
                for (int col = 0; col < TicTacToe.SIDE; col++)
                {
                    if (view == buttons [row][col]){
                        update(row,col);
                    }
                }
        }//end of onClick
    }//end of Button Handler


    public void update(int row, int column)
    {
        //temporary Toast message
        //Toast.makeText(MainActivity.this,"update: row is "+row+"and column is "+column, Toast.LENGTH_SHORT).show();

        //put an X on the Button that was clicked
       // buttons[row][column].setText("X");

        int currentPlayer = game.play(row,column);

        if (currentPlayer == 1)
            buttons[row][column].setText("X");
        else if (currentPlayer == 2 )
            buttons[row][column].setText("O");

        //check if the game is over
        if (game.isGameOver())
        {

            gameStatus.setBackgroundColor(Color.CYAN);
            //disable all the buttons
            enableButtons(false);
            gameStatus.setText(game.result());
            showNewGameDialog();
        }
    }//end of update
    public void enableButtons(boolean enabled)
    {
        for (int row=0; row <TicTacToe.SIDE; row++)
            for(int col=0;col<TicTacToe.SIDE; col++)
                buttons[row][col].setEnabled(enabled);
    }//end of enable button

    public void resetButton()
    {
        for(int row = 0; row<TicTacToe.SIDE; row++)
            for(int col= 0; col<TicTacToe.SIDE;col++)
                buttons[row][col].setText("");
    }

    public void showNewGameDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        //set the title for the dialog box
        alert.setTitle("Tic Tac Toe");

        //set the message for the dialog box
        alert.setMessage("Play Again?");

        //set the positive button
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //reset the Tic Tac Toe
                game.resetGame();

                //enable all the buttons
                enableButtons(true);

                //reset the buttons for a new game
                resetButton();

                gameStatus.setBackgroundColor(Color.MAGENTA);
                gameStatus.setText(game.result());
            }
        });

        //Create the negative button
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //end the game
                MainActivity.this.finish();
            }
        });

        //Display the dialog box
        alert.show();

    }//end of showNewGameDialog

}//end of MainActivity
