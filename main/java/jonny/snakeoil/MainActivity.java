package jonny.snakeoil;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    static List<String> characters=new ArrayList<String>();
    static List<String> cards=new ArrayList<String>();
    static int redColor=0xFFFF0000;
    static int backColor=0xFFFFFFFF;

    public View firstView;
    public View secondView;
    public String firstWord = "";
    public String secondWord = "";
    public View viewPressed;

    public int buttonsPressed = 0;
    public int pointsScored = 0;

    /**
     * Function loads character cards
     * @param context should just be this for this one class app
     * @return ArrayList of character cards drawn from assets/chars.txt
     * @throws IOException if the bufferedreader doesn't work
     */
    private static void loadChars(Context context) throws IOException{
        String line;
            InputStream is = context.getAssets().open("chars.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while((line = br.readLine()) != null){
                characters.add(line);
            }
    }
    /**
     * Function loads descriptor cards
     * @param context should just be this for this one class app
     * @return ArrayList of descriptor cards drawn from assets/cards.txt
     * @throws IOException if the bufferedreader doesn't work
     */
    private static void loadCards(Context context) throws IOException{
        String line;
        InputStream is = context.getAssets().open("cards.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while((line = br.readLine()) != null){
            cards.add(line);
        }
    }

    /**
     * Function draws a card (String) at random and removes it from the pile (ArrayList)
     * @param buttonView is the button to be overwritten
     * @param pile is the ArrayList of strings to be drawn from
     */
    private static void drawToButton(Button buttonView, List<String> pile) {
        if (pile.size() != 0) {
            Random randomGenerator = new Random();
            int index = randomGenerator.nextInt(pile.size());
            String card = pile.get(index);
            pile.remove(index);

            Button button = (Button) buttonView;
            String replace = (String) card;
            button.setText(replace);
        }
        else{
            buttonView.setText("cards empty");
        }
    }
    /**
     * Function is called on app opening, prepares piles of cards and draws the first six
     * descriptor cards
     * @param savedInstanceState (DUNNO!)
     */
    //TODO get the card drawing to work
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            //load cards
            loadCards(this);
            loadChars(this);

            //define buttons and draw over them
            Button b1 = (Button)findViewById(R.id.button1);
            Button b2 = (Button)findViewById(R.id.button2);
            Button b3 = (Button)findViewById(R.id.button3);
            Button b4 = (Button)findViewById(R.id.button4);
            Button b5 = (Button)findViewById(R.id.button5);
            Button b6 = (Button)findViewById(R.id.button6);

            drawToButton(b1, cards);
            drawToButton(b2, cards);
            drawToButton(b3, cards);
            drawToButton(b4, cards);
            drawToButton(b5, cards);
            drawToButton(b6, cards);
        }
        catch(IOException e){
            e.printStackTrace();
            Button test = (Button)findViewById(R.id.button1);
            test.setText("onCreate exception");

        }


    }

    /**
     * drawRole draws a role from the chars.txt list and writes it in the top right
     * @param view
     */
    public void drawRole(View view){
        TextView roleText =(TextView) this.findViewById(R.id.role_holder);
        if(characters.size()!=0){
            Random randomGenerator = new Random();
            int index = randomGenerator.nextInt(characters.size());
            String card = characters.get(index);
            characters.remove(index);
            roleText.setText(card);
        }
        else{
            roleText.setText("Deck Empty");
        }
    }

    /**
     * addScore simply adds one to the score in the top left. Don't go over 20!
     * @param view
     */
    public void addScore(View view){
        TextView score = (TextView) this.findViewById(R.id.score_counter);
        pointsScored++;
        score.setText(Integer.toString(pointsScored));
        if(pointsScored>20){
            score.setText("Stop tooling around");
        }
    }

    //todo: ensure you can't press a button twice!
    /**
     * selectButton is called when a button is pressed
     * It changes the colour and commits the word chosen to memory
     * @param view
     */
    public void selectButton(View view) {
        if(buttonsPressed<2){
            Button but = (Button) view;
            String selection = but.getText().toString();
            if(buttonsPressed==0){
                buttonsPressed++;
                firstWord=selection;
                view.getBackground().setColorFilter(redColor, PorterDuff.Mode.MULTIPLY);
                firstView=view;
            }
            else if(buttonsPressed==1 && !selection.equals(firstWord)){
                buttonsPressed++;
                secondWord=selection;
                view.getBackground().setColorFilter(redColor, PorterDuff.Mode.MULTIPLY);
                secondView=view;
            }
        }
    }
//need to:
    //Pass words to activity (hmm, needs this?)
    //reset selections
    //draw new cards
    public void submitCards(View view){
        if(buttonsPressed==2) {
            Intent intent = new Intent(this, DisplayCards.class);
            intent.putExtra("SnakeOil.word1", firstWord);
            intent.putExtra("SnakeOil.word2", secondWord);
            drawToButton((Button) firstView, cards);
            drawToButton((Button) secondView, cards);


            resetSelection(findViewById(R.id.resetButton));
            startActivity(intent);
            }
    }

    public void resetSelection(View view){
        buttonsPressed=0;
        viewPressed=null;
        firstWord="";
        secondWord="";
        firstView=null;
        secondView=null;

        Button b1 = (Button)findViewById(R.id.button1);
        Button b2 = (Button)findViewById(R.id.button2);
        Button b3 = (Button)findViewById(R.id.button3);
        Button b4 = (Button)findViewById(R.id.button4);
        Button b5 = (Button)findViewById(R.id.button5);
        Button b6 = (Button)findViewById(R.id.button6);

        b1.getBackground().setColorFilter(backColor, PorterDuff.Mode.MULTIPLY);
        b2.getBackground().setColorFilter(backColor, PorterDuff.Mode.MULTIPLY);
        b3.getBackground().setColorFilter(backColor, PorterDuff.Mode.MULTIPLY);
        b4.getBackground().setColorFilter(backColor, PorterDuff.Mode.MULTIPLY);
        b5.getBackground().setColorFilter(backColor, PorterDuff.Mode.MULTIPLY);
        b6.getBackground().setColorFilter(backColor, PorterDuff.Mode.MULTIPLY);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
