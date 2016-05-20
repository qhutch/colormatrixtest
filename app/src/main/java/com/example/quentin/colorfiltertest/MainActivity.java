package com.example.quentin.colorfiltertest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    public static final int defaultSat = 10;

    private Bitmap bitmapBottom;
    private ImageView topImageView;
    private ImageView bottomImageView;

    private SeekBar seekBar;
    private Bitmap bitmapTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topImageView = (ImageView) findViewById(R.id.image_view_top);
        bottomImageView = (ImageView) findViewById(R.id.image_view_bottom);
        seekBar = (SeekBar) findViewById(R.id.seekbar);

        bitmapTop = BitmapFactory.decodeResource(getResources(), R.drawable.test_image);
        bitmapBottom = BitmapFactory.decodeResource(getResources(), R.drawable.test_image);

        topImageView.setImageBitmap(bitmapTop);
        bottomImageView.setImageBitmap(bitmapBottom);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ColorMatrix colorMatrix = getSatMatrix(progress);
                setColorMatrix(colorMatrix);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private static ColorMatrix getSatMatrix(Integer value) {
        if (value == null) {
            return new ColorMatrix();
        }
        float s = value/(float)defaultSat;
        //Log.d("Saturation", "Value = "+s);
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(s);
        return colorMatrix;
    }

    private void setColorMatrix(ColorMatrix colorMatrix) {
        setImageViewColorFilter(colorMatrix);
        setBitmapColorFilter(colorMatrix);
    }

    private void setImageViewColorFilter(ColorMatrix colorMatrix) {
        ColorMatrixColorFilter cf = new ColorMatrixColorFilter(colorMatrix);
        topImageView.setColorFilter(cf);
    }


    private void setBitmapColorFilter(ColorMatrix colorMatrix) {
        Bitmap bitmap = bitmapBottom.copy(bitmapBottom.getConfig(), true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        bottomImageView.setImageBitmap(bitmap);
    }
}
