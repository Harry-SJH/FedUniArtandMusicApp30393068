package au.edu.federation.feduniartandmusicapp30393068;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class DrawActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private DrawView drawView;
    private Context context;
    private ImageView undo,redo,size,paint,background,clear,eraser;


    // Get the height and width
    @Override
    protected void onStart() {
        super.onStart();
        frameLayout.post(new Runnable() {
            @Override
            public void run() {
                int width = frameLayout.getWidth();
                int height = frameLayout.getHeight();
                if (MainActivity.drawView == null) {
                    drawView = new DrawView(context, width, height);
                }else {
                    drawView = MainActivity.drawView;
                }
                frameLayout.addView(drawView);
            }
        });
    }

    //Clear function
    public void clear(View view) {
        drawView.clear();
        Toast.makeText(DrawActivity.this, "Clear", Toast.LENGTH_SHORT).show();
    }

    //Undo function
    public void Undo(View view) {
        drawView.undo();
        Toast.makeText(DrawActivity.this, "Undo", Toast.LENGTH_SHORT).show();
    }

    //Redo function
    public void Redo(View view) {
        drawView.redo();
        Toast.makeText(DrawActivity.this, "Redo", Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 1, 1, "Save");
        Toast.makeText(DrawActivity.this, "Saved", Toast.LENGTH_SHORT).show();
        return true;
    }

    private static String saveImage(Bitmap bmp, int quality) {
        if (bmp == null) {
            return null;
        }
        File appDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (appDir == null) {
            return null;
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, quality, fos);
            fos.flush();
            return file.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    //Check if there is a stored paintView, and restore the paintView if there is
    @Override
    protected void onRestart() {
        super.onRestart();
        if (MainActivity.drawView != null){
            drawView = MainActivity.drawView;
        }
    }

    //Store the paintingView after the activity pause
    @Override
    protected void onPause() {
        super.onPause();
        frameLayout.removeAllViews();
        MainActivity.drawView = drawView;
    }

    @Override
    protected void onStop() {
        super.onStop();
        frameLayout.removeAllViews();
        MainActivity.drawView = drawView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        frameLayout.removeAllViews();
        MainActivity.drawView = drawView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        context = this;

        frameLayout = findViewById(R.id.paintingLayout);
        undo = findViewById(R.id.undo);
        redo = findViewById(R.id.redo);
        size = findViewById(R.id.size);
        paint = findViewById(R.id.paint);
        background = findViewById(R.id.background);
        clear = findViewById(R.id.clear);
        eraser = findViewById(R.id.eraser);

        //Choose the pen size
        size.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(context);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Please choose the thickness!");
                builder.setView(editText);
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        drawView.setSize(Integer.parseInt(editText.getText().toString()));
                        Toast.makeText(context,"Succeed",Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
            }
        });

        //Color
        paint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder =new AlertDialog.Builder(context);
                builder.setTitle("Please select the color!");
                builder.setItems(new String[]{"Blue","Green","Black","Red","Yellow"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                drawView.setPaintColor(getColor(R.color.blue));
                                Toast.makeText(DrawActivity.this, "Blue", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                drawView.setPaintColor(getColor(R.color.green));
                                Toast.makeText(DrawActivity.this, "Green", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                drawView.setPaintColor(getColor(R.color.black));
                                Toast.makeText(DrawActivity.this, "Black", Toast.LENGTH_SHORT).show();
                                break;
                            case 3:
                                drawView.setPaintColor(getColor(R.color.red));
                                Toast.makeText(DrawActivity.this, "Red", Toast.LENGTH_SHORT).show();
                                break;
                            case 4:
                                drawView.setPaintColor(getColor(R.color.yellow));
                                Toast.makeText(DrawActivity.this, "Yellow", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }).show();
            }
        });


        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Choose the background color");
                builder.setPositiveButton("Teal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        drawView.setBackGround(false);
                        Toast.makeText(DrawActivity.this, "Teal", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNeutralButton("White", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        drawView.setBackGround(true);
                        Toast.makeText(DrawActivity.this, "White", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

        eraser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawView.getEraser();
                Toast.makeText(DrawActivity.this, "Eraser", Toast.LENGTH_SHORT).show();
            }
        });
    }


}