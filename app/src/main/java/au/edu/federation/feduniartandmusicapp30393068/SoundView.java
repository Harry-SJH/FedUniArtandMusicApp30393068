package au.edu.federation.feduniartandmusicapp30393068;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoundView extends FrameLayout {

    private SoundPool soundPool;
    private Button button;
    private Context context;
    private int width, height;
    private int blackWidth, whiteWidth, blackHeight;
    //Gamut
    private int c, c_up, d, d_up, e, f, f_up, g, g_up, a, b_b, b;

    //the layout of the upper and lower parts is also different
    // so take two lists to store the int of the sound source
    private List<Integer> List, List2;

    //Record whether or not it was played
    private Map<Integer, Boolean> maps;

    private void init() {
        List2 = new ArrayList<>();
        List = new ArrayList<>();
        maps = new HashMap<>();
        blackHeight = height * 3 / 5;
        whiteWidth = width / 7;
        blackWidth = width / 28;

        SoundPool.Builder builder = new SoundPool.Builder();
        builder.setMaxStreams(12);

        //AudioAttributes is a way to encapsulate various AudioAttributes
        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
        builder.setAudioAttributes(attrBuilder.build());
        soundPool = builder.build();
    }

    public SoundView(Context context) {
        super(context);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.button_view, this);
        init();
    }

    public SoundView(Context context, @Nullable AttributeSet attrs, int width, int height) {
        super(context, attrs);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.button_view, this);
        this.width = width;
        this.height = height;
        init();
    }

    public SoundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void loadSound(int i) {
        switch (i) {
            case 0:
                //loading each gamut
                c = soundPool.load(context, R.raw.piano_c4, 1);
                c_up = soundPool.load(context, R.raw.piano_c_up4, 1);
                d = soundPool.load(context, R.raw.piano_d4, 1);
                d_up = soundPool.load(context, R.raw.piano_dup4, 1);
                e = soundPool.load(context, R.raw.piano_e4, 1);
                f = soundPool.load(context, R.raw.piano_f4, 1);
                f_up = soundPool.load(context, R.raw.piano_fup4, 1);
                g = soundPool.load(context, R.raw.piano_g4, 1);
                g_up = soundPool.load(context, R.raw.piano_gup4, 1);
                a = soundPool.load(context, R.raw.piano_a4, 1);
                b_b = soundPool.load(context, R.raw.piano_bb4, 1);
                b = soundPool.load(context, R.raw.piano_b4, 1);
                break;
            case 1:
                c = soundPool.load(context, R.raw.xylophone_c4, 1);
                c_up = soundPool.load(context, R.raw.xylophone_c_up_4, 1);
                d = soundPool.load(context, R.raw.xylophone_d4, 1);
                d_up = soundPool.load(context, R.raw.xylophone_dup4, 1);
                e = soundPool.load(context, R.raw.xylophone_e4, 1);
                f = soundPool.load(context, R.raw.xylophone_f4, 1);
                f_up = soundPool.load(context, R.raw.xylophone_fup4, 1);
                g = soundPool.load(context, R.raw.xylophone_g4, 1);
                g_up = soundPool.load(context, R.raw.xylophone_gup4, 1);
                a = soundPool.load(context, R.raw.xylophone_a4, 1);
                b_b = soundPool.load(context, R.raw.xylophone_bb4, 1);
                b = soundPool.load(context, R.raw.xylophone_b4, 1);
                break;
            case 2:
                c = soundPool.load(context, R.raw.flute_c4, 1);
                c_up = soundPool.load(context, R.raw.flute_c_up4, 1);
                d = soundPool.load(context, R.raw.flute_d4, 1);
                d_up = soundPool.load(context, R.raw.flute_dup4, 1);
                e = soundPool.load(context, R.raw.flute_e4, 1);
                f = soundPool.load(context, R.raw.flute_f4, 1);
                f_up = soundPool.load(context, R.raw.flute_fup4, 1);
                g = soundPool.load(context, R.raw.flute_g4, 1);
                g_up = soundPool.load(context, R.raw.flute_gup4, 1);
                a = soundPool.load(context, R.raw.flute_a4, 1);
                b_b = soundPool.load(context, R.raw.flute_bb4, 1);
                b = soundPool.load(context, R.raw.flute_b4, 1);
                break;
        }

        //white keys lists
        List.add(c);List.add(d);List.add(e);List.add(f);
        List.add(g);List.add(a);List.add(b);

        //Black keys plus half a list of white keys
        List2.add(c);List2.add(c);List2.add(c);List2.add(c_up);
        List2.add(c_up);List2.add(d);List2.add(d);List2.add(d_up);
        List2.add(d_up);List2.add(e);List2.add(e);List2.add(e);List2.add(f);
        List2.add(f);List2.add(f);List2.add(f_up);List2.add(f_up);List2.add(g);
        List2.add(g);List2.add(g_up);List2.add(g_up);List2.add(a);List2.add(a);List2.add(b_b);
        List2.add(b_b);List2.add(b);List2.add(b);List2.add(b);

        //Record whether or not it was played
        maps.put(c, false);maps.put(c_up, false);maps.put(d, false);
        maps.put(d_up, false);maps.put(e, false);maps.put(f, false);
        maps.put(f_up, false);maps.put(g, false);maps.put(g_up, false);
        maps.put(a, false);maps.put(b_b, false);maps.put(b, false);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            int id = event.getActionIndex();
            if (event.getY() > blackHeight) {
                //The number is determined by dividing the x-coordinate at the point of click by the width of the white key
                int position = (int) (event.getX(id) / whiteWidth);
                int sound = List.get(position);
                if (!maps.get(sound)) {
                    soundPool.play(sound, 1, 1, 1, 0, 1);
                    maps.put(sound,true);
                }
            } else {
                int position = (int) ((event.getX(id)-0.5*blackWidth) / blackWidth);
                if (position < 24) {
                    int sound = List2.get(position);
                    if (!maps.get(sound)) {
                        soundPool.play(sound, 1, 1, 1, 0, 1);
                        maps.put(sound, true);
                    }
                }
            }
        }else if (event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN){
            int id = event.getActionIndex();
            if (event.getY() > blackHeight) {
                int position = (int) (event.getX(id) / whiteWidth);
                int sound = List.get(position);
                if (!maps.get(sound)) {
                    soundPool.play(sound, 1, 1, 1, 0, 1);
                    maps.put(sound,true);
                }
            } else {
                int position = (int) ((event.getX(id)-0.5*blackWidth) / blackWidth);
                if (position < 24) {
                    int sound = List2.get(position);
                    if (!maps.get(sound)) {
                        soundPool.play(sound, 1, 1, 1, 0, 1);
                        maps.put(sound, true);
                    }
                }
            }
        }else if (event.getActionMasked() == MotionEvent.ACTION_MOVE){
            int id = event.getActionIndex();

            if (event.getY() > blackHeight) {
                int position = (int) (event.getX(id) / whiteWidth);
                int sound = List.get(position);
                if (!maps.get(sound)) {
                    soundPool.play(sound, 1, 1, 1, 0, 1);
                    maps.put(sound,true);
                }
            } else {
                int position = (int) ((event.getX(id)-0.5*blackWidth) / blackWidth);
                if (position < 24) {
                    int sound = List2.get(position);
                    if (!maps.get(sound)) {
                        soundPool.play(sound, 1, 1, 1, 0, 1);
                        maps.put(sound, true);
                    }
                }
            }
        }else if (event.getActionMasked() == MotionEvent.ACTION_UP){
            //Reset whether or not it was played
            maps.put(c, false);maps.put(c_up, false);
            maps.put(d, false);maps.put(d_up, false);
            maps.put(e, false);maps.put(f, false);
            maps.put(f_up, false);maps.put(g, false);
            maps.put(g_up, false);maps.put(a, false);
            maps.put(b_b, false);maps.put(b, false);
        }
        return super.onTouchEvent(event);
    }

}
