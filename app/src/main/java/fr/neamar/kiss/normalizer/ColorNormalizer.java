package fr.neamar.kiss.normalizer;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;

/**
 * Created by longrin on 13.03.2016.
 */
public class ColorNormalizer {
    public static Drawable convertToGrayscale(Drawable drawable)
    {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

        drawable.setColorFilter(filter);

        return drawable;
    }
}
