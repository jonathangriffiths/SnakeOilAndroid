package jonny.snakeoil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DisplayCards extends AppCompatActivity {


    String word1="";
    String word2="";
    int swapPressed;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_cards);
        Intent intent = getIntent();


        swapPressed=0;
        TextView text1 = (TextView)this.findViewById(R.id.word1);
        TextView text2 = (TextView)this.findViewById(R.id.word2);

        word1=intent.getStringExtra("SnakeOil.word1");
        word2=intent.getStringExtra("SnakeOil.word2");

        text1.setText(word1);
        text2.setText(word2);
    }

    public void swapWords(View view){
        TextView text1 = (TextView)this.findViewById(R.id.word1);
        TextView text2 = (TextView)this.findViewById(R.id.word2);
        swapPressed++;
        if(swapPressed%2==0){
            text1.setText(word1);
            text2.setText(word2);
        }
        else{
            text1.setText(word2);
            text2.setText(word1);
        }
    }

    public void exitPage(View view){
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_cards, menu);
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
