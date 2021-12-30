package au.edu.federation.feduniartandmusicapp30393068;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SoundActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    SoundView soundView;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paino);

        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        frameLayout = findViewById(R.id.pianoLayout);
        type = getIntent().getIntExtra("type",1);

        //Pass the width and height to the pianoView
        soundView = new SoundView(this,null,width,height);
        soundView.loadSound(type);
        soundView.setClickable(true);
        frameLayout.addView(soundView);
    }
}