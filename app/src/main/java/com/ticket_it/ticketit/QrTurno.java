package com.ticket_it.ticketit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QrTurno extends AppCompatActivity {

    private final String TURNO_ID = "turnoId", TAG = "generarCodigoQr";
    private ImageView qrImage;
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;

    private EntidadColletion entidadColletion;
    private String turnoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_turno);

        turnoId = getIntent().getExtras().getString(TURNO_ID);

        qrImage = findViewById(R.id.qrcode);

        if (turnoId != null) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = Math.min(width, height);
            smallerDimension = smallerDimension * 3 / 4;

            qrgEncoder = new QRGEncoder(turnoId, null, QRGContents.Type.TEXT, smallerDimension);
            qrgEncoder.setColorBlack(Color.BLUE);
            // Getting QR-Code as Bitmap
            bitmap = qrgEncoder.getBitmap();
            // Setting Bitmap to ImageView
            qrImage.setImageBitmap(bitmap);
        }
    }
}