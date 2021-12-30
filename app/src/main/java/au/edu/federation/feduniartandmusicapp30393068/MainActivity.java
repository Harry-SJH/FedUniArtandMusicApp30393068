package au.edu.federation.feduniartandmusicapp30393068;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static DrawView drawView;
    private Button bt1,bt2;
    Context context;

    //Direct to the SketchBook
    public void onClick (View v){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, DrawActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        context = this;

        //Direct to instrument page
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //An AlertDialog is used to select the audio source

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Please select an instrument!");
                builder.setItems(new String[]{"Piano", "Xylophone", "Flute"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(MainActivity.this, SoundActivity.class);
                        switch (i) {
                            case 0:
                                intent.putExtra("type", 0);
                                break;
                            case 1:
                                intent.putExtra("type", 1);
                                break;
                            case 2:
                                intent.putExtra("type", 2);
                                break;
                        }
                        startActivity(intent);
                    }
                });
                builder.show();
            }
        });
    }
}