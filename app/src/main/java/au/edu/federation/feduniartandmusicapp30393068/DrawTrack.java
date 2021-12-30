package au.edu.federation.feduniartandmusicapp30393068;

import android.graphics.Paint;
import android.graphics.Path;

//This class is an entity class where each draw represents a drawPath, which holds the path handwriting and the paint brush
public class DrawTrack {
    Paint paint;
    Path path;

    public DrawTrack(Paint paint, Path path) {
        this.paint = paint;
        this.path = path;
    }

    public Path getPath() {
        return path;
    }

    public Paint getPaint() {
        return paint;
    }

}
