package luke.com.fiirapp;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    Button tab1;
    Button tab2;
    Button tab3;
    ArrayList<Button> tabs;
    TextView infoText;
    TextView buttonText;
    ScrollView scrollView;

    private GestureDetectorCompat detector;

    int state;
    int FIIR_Red;

    public MainActivity(){
        state = 0;
        FIIR_Red = Color.parseColor("#820000");
        tabs = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab1 = (Button) findViewById(R.id.tab1);
        tab2 = (Button) findViewById(R.id.tab2);
        tab3 = (Button) findViewById(R.id.tab3);
        tabs.add(tab1);
        tabs.add(tab2);
        tabs.add(tab3);

        scrollView = (ScrollView) findViewById(R.id.scrollView);
        buttonText = (TextView) findViewById(R.id.button_text);
        infoText = (TextView) findViewById(R.id.textView);
        infoText.setTypeface(Typeface.createFromAsset(getAssets(),"hnt.ttf"));
        buttonText.setTypeface(Typeface.createFromAsset(getAssets(),"hn.ttf"));

        tab1.setOnClickListener(this::tabOne);
        tab2.setOnClickListener(this::tabTwo);
        tab3.setOnClickListener(this::tabThree);

        for(Button b : tabs){
            b.setTypeface(Typeface.createFromAsset(getAssets(),"hn.ttf"));
        }

        detector = new GestureDetectorCompat(this, new gestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    void tabOne(View view){
        tabs.get(state).setTextColor(Color.WHITE);
        tabs.get(state).setBackgroundColor(0);
        state = 0;
        tabs.get(state).setBackgroundColor(Color.WHITE);
        tabs.get(state).setTextColor(FIIR_Red);

        infoText.setText("$1 stream");
    }

    void tabTwo(View view){
        tabs.get(state).setTextColor(Color.WHITE);
        tabs.get(state).setBackgroundColor(0);
        state = 1;
        tabs.get(state).setBackgroundColor(Color.WHITE);
        tabs.get(state).setTextColor(FIIR_Red);

        infoText.setText("$100 stream");
    }

    void tabThree(View view){
        tabs.get(state).setTextColor(Color.WHITE);
        tabs.get(state).setBackgroundColor(0);
        state = 2;
        tabs.get(state).setBackgroundColor(Color.WHITE);
        tabs.get(state).setTextColor(FIIR_Red);

        infoText.setText("$1k stream");
    }

    void openSettings(){
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        scrollView.getContext().startActivity(intent);

        if(android.os.Build.VERSION.SDK_INT >= 21) {
            overridePendingTransition(R.anim.slide_in_right, android.R.anim.fade_out);
        }
    }

    class gestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            String DEBUG_TAG = "onFling";
            if(Math.abs(velocityX) > Math.abs(velocityY)){
                if(velocityX > 0){
                    Log.d(DEBUG_TAG, "Fling right");
                    switch (state){
                        case 0:
                            openSettings();
                            break;
                        case 1:
                            tabOne(null);
                            break;
                        case 2:
                            tabTwo(null);
                            break;
                    }
                }else{
                    Log.d(DEBUG_TAG, "Fling left");
                    switch (state){
                        case 0:
                            tabTwo(null);
                            break;
                        case 1:
                            tabThree(null);
                            break;
                        case 2:
                            break;
                    }
                }
            }

            return true;
        }// end onFling method
    }// end of gestureListener subclass
}
