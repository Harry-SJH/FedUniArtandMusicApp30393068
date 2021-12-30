package au.edu.federation.feduniartandmusicapp30393068;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DrawView extends View {

    private Canvas canvas;
    private Paint paint;
    private Bitmap bitmap;
    private int i,width,height,paintStokerWidth,eraserStokerWidth;
    private Path path;
    private List<DrawTrack> drawTracks;
    private List<Path> paths;
    private List<Integer> position;
    private List<DrawTrack> SavePaths;
    private boolean background,backgroundChangeFlag;

    public DrawView(Context context, int width, int height) {
        super(context);
        this.width = width;
        this.height = height;
        init();
    }

    private abstract static class DrawingInfo {
        Paint paint;
    }

    private void init(){
        i = 0;
        paintStokerWidth = 5;
        eraserStokerWidth = 20;
        paint = new Paint();
        drawTracks = new ArrayList<>();
        SavePaths = new ArrayList<>();
        bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint.setColor(getContext().getColor(R.color.black));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(paintStokerWidth);
        background = true;
        backgroundChangeFlag = false;
    }


    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //Undo
    public void undo(){
        if (drawTracks.size()>0){
            SavePaths.add(drawTracks.get(drawTracks.size()-1));
            drawTracks.remove(drawTracks.size()-1);
        }
        invalidate();
    }

    public void redo(){
        if (SavePaths.size()>0){
            drawTracks.add(SavePaths.get(SavePaths.size()-1));
            SavePaths.remove(SavePaths.size()-1);
            invalidate();
            invalidate();
        }
    }

    //setPaintColor
    public void setPaintColor(int color){
        paint.setColor(color);
    }

    //getEraser
    public void getEraser(){
        if (background){
            paint.setColor(getContext().getColor(R.color.white));
        }else {
            paint.setColor(getContext().getColor(R.color.teal_700));
        }
        paint.setStrokeWidth(eraserStokerWidth);
    }

    //setBackGround
    public void setBackGround(boolean f){
        backgroundChangeFlag = true;
        background = f;
        clear();
    }

    //changeBackGround
    private void changeBackGround(boolean f){
        if (backgroundChangeFlag){
            paint.setColor(getContext().getColor(R.color.black));
            paint.setStrokeWidth(paintStokerWidth);
            drawTracks = new ArrayList<>();
            SavePaths = new ArrayList<>();
            backgroundChangeFlag = false;
        }
    }

    //setSize
    public void setSize(int i){
        paintStokerWidth = i;
        paint.setStrokeWidth(paintStokerWidth);
    }

    //clear
    public void clear(){
        drawTracks = new ArrayList<>();
        SavePaths = new ArrayList<>();
        invalidate();
    }

    //copyPaint
    private void copyPaint(Paint tempPaint){
        tempPaint.setColor(paint.getColor());
        tempPaint.setStrokeWidth(paint.getStrokeWidth());
        tempPaint.setStyle(paint.getStyle());
    }

    //onDraw If you need to change the background, use the Boolean value
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        changeBackGround(background);
        if (background){
            canvas.drawColor(getContext().getColor(R.color.white));
        }else {
            canvas.drawColor(getContext().getColor(R.color.teal_700));
        }
        if (drawTracks.size()>0) {
            for (DrawTrack drawTrack : drawTracks) {
                canvas.drawPath(drawTrack.getPath(), drawTrack.getPaint());
            }
        }
    }

    //onTouchEvent Multi-touch support requires the getActionMasked() class to retrieve click events
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int pointerIndex = event.getActionIndex();

        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                paths = new ArrayList<>();
                position = new ArrayList<>();
                SavePaths = new ArrayList<>();
                path = new Path();
                paths.add(path);
                position.add(0);
                paths.get(0).moveTo(event.getX(), event.getY());
                Paint tempPaint = new Paint();
                copyPaint(tempPaint);
                drawTracks.add(new DrawTrack(tempPaint,path));
                return true;
            case MotionEvent.ACTION_POINTER_DOWN:
                i = i + 1;
                position.add(i);
                Path path = new Path();
                paths.add(path);
                paths.get(i).moveTo(event.getX(pointerIndex),event.getY(pointerIndex));
                Paint paintTemp = new Paint();
                copyPaint(paintTemp);
                drawTracks.add(new DrawTrack(paintTemp,paths.get(i)));
                return true;
            case MotionEvent.ACTION_MOVE:
                for (int size = event.getPointerCount(),i=0;i<size;i++) {
                    Path path1 = paths.get(position.get(i));
                    if (path1!=null) {
                        path1.lineTo(event.getX(i), event.getY(i));
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                position.remove(pointerIndex);
            case MotionEvent.ACTION_UP:
                i = 0;
                break;
        }
        return super.onTouchEvent(event);
    }

}
