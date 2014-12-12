package com.elektryczny.rzengineer.android.picture;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.elektryczny.rzengineer.android.DrawingView;
import com.elektryczny.rzengineer.android.FileEnum;
import com.elektryczny.rzengineer.android.MultimediaFileManager;
import com.elektryczny.rzengineer.android.R;

public class ModyfingPictureActivity extends Activity implements OnClickListener {
    private DrawingView drawView;
    private ImageView normalView;
    private Bitmap tmpBitmap = null;
    private ImageButton currPaint, drawBtn, eraseBtn, newBtn, saveBtn;
    private float smallBrush, mediumBrush, largeBrush;
    private Boolean isRepaeted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modyfing_picture);

        drawView = (DrawingView) findViewById(R.id.drawing_picture);
        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.picture_paint_colors);
        currPaint = (ImageButton) paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        normalView = (ImageView) findViewById(R.id.pictureEditView);
        normalView.setImageURI(Uri.fromFile(new File(MultimediaFileManager.getPathToFile(FileEnum.IMAGE_FILE))));

        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);

        drawBtn = (ImageButton) findViewById(R.id.picture_draw_btn);
        drawBtn.setOnClickListener(this);
        drawView.setBrushSize(mediumBrush);

        eraseBtn = (ImageButton) findViewById(R.id.picture_erase_btn);
        eraseBtn.setOnClickListener(this);

        newBtn = (ImageButton) findViewById(R.id.picture_new_layer_btn);
        newBtn.setOnClickListener(this);

        saveBtn = (ImageButton) findViewById(R.id.picture_save_btn);
        saveBtn.setOnClickListener(this);

        final Button backVideoB = (Button) findViewById(R.id.pictureModyfingBackButton);
        backVideoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.picture_draw_btn) {
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setTitle("Rozmiar:");
            brushDialog.setContentView(R.layout.brush_chooser);
            ImageButton smallBtn = (ImageButton) brushDialog.findViewById(R.id.small_brush);
            smallBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setBrushSize(smallBrush);
                    drawView.setLastBrushSize(smallBrush);
                    drawView.setErase(false);
                    brushDialog.dismiss();
                }
            });
            ImageButton mediumBtn = (ImageButton) brushDialog.findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setBrushSize(mediumBrush);
                    drawView.setLastBrushSize(mediumBrush);
                    drawView.setErase(false);
                    brushDialog.dismiss();
                }
            });

            ImageButton largeBtn = (ImageButton) brushDialog.findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setBrushSize(largeBrush);
                    drawView.setLastBrushSize(largeBrush);
                    drawView.setErase(false);
                    brushDialog.dismiss();
                }
            });
            brushDialog.show();
        } else if (view.getId() == R.id.picture_erase_btn) {
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setTitle("Rozmiar:");
            brushDialog.setContentView(R.layout.brush_chooser);
            ImageButton smallBtn = (ImageButton) brushDialog.findViewById(R.id.small_brush);
            smallBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(smallBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton mediumBtn = (ImageButton) brushDialog.findViewById(R.id.medium_brush);
            mediumBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(mediumBrush);
                    brushDialog.dismiss();
                }
            });
            ImageButton largeBtn = (ImageButton) brushDialog.findViewById(R.id.large_brush);
            largeBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawView.setErase(true);
                    drawView.setBrushSize(largeBrush);
                    brushDialog.dismiss();
                }
            });
            brushDialog.show();
        } else if (view.getId() == R.id.picture_new_layer_btn) {
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("Nowa warstwa");
            newDialog.setMessage("Rozpoczynając od nowa stracisz dotychczasowy postęp. Chcesz to zrobić?");
            newDialog.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    drawView.startNew();
                    normalView.setImageURI(Uri.fromFile(new File(MultimediaFileManager.getPathToFile(FileEnum.IMAGE_FILE))));
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            newDialog.show();
        } else if (view.getId() == R.id.picture_save_btn) {
            AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
            saveDialog.setTitle("Zapisz obrazek");
            saveDialog.setMessage("Zapisać obrazek?");
            saveDialog.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    drawView.setDrawingCacheEnabled(true);
                    normalView.setDrawingCacheEnabled(true);
                    FileOutputStream outLayer = null;
                    FileOutputStream outNormal = null;
                    Bitmap bmpLayer = drawView.getDrawingCache();
                    Bitmap bmpNormal = normalView.getDrawingCache();
                    try {
                        outLayer = new FileOutputStream(MultimediaFileManager.getPathToFile(FileEnum.IMAGE_LAYER_FILE));
                        bmpLayer.compress(Bitmap.CompressFormat.PNG, 100, outLayer);

                        outNormal = new FileOutputStream(MultimediaFileManager.getPathToFile(FileEnum.IMAGE_FILE));
                        bmpNormal.compress(Bitmap.CompressFormat.JPEG, 100, outNormal);

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (bmpLayer != null && bmpNormal != null) {
                                Toast savedToast = Toast.makeText(getApplicationContext(),
                                        "Zapisano!", Toast.LENGTH_SHORT);
                                savedToast.show();
                                outLayer.close();
                                outNormal.close();
                            } else {
                                Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                        "Nie zapisano", Toast.LENGTH_SHORT);
                                unsavedToast.show();
                                outLayer.close();
                                outNormal.close();

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    drawView.destroyDrawingCache();
                    normalView.destroyDrawingCache();

                }
            });
            saveDialog.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            saveDialog.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.modyfing_picture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Integer id = item.getItemId();
        Drawable toMakeEffect = normalView.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) toMakeEffect).getBitmap();
        if (id != R.id.action_picture_modification_back) {
            tmpBitmap = bitmap;
        }
        Bitmap ef = null;
        switch (id) {
            case R.id.action_picture_saturation:
                ef = PictureEffectManager.applySaturationFilter(bitmap, 5);
                isRepaeted = false;
                break;
            case R.id.action_picture_brightness:
                ef = PictureEffectManager.SetBrightness(bitmap, 60);
                isRepaeted = false;
                break;
            case R.id.action_picture_contrast:
                ef = PictureEffectManager.takeColorContrast(bitmap, 100);
                isRepaeted = false;
                break;
            case R.id.action_picture_greyscale:
                ef = PictureEffectManager.grayScaleImage(bitmap);
                isRepaeted = false;
                break;
            case R.id.action_picture_modification_back:
                if(isRepaeted){
                    isRepaeted = !isRepaeted;
                    ef = bitmap;
                } else if (tmpBitmap != null) {
                    isRepaeted = true;
                    ef = tmpBitmap;
                }
                break;
        }
        normalView.setImageBitmap(ef);
        return super.onOptionsItemSelected(item);
    }

    public void paintClicked(View view) {
        if (view != currPaint) {
            drawView.setErase(false);
            ImageButton imgView = (ImageButton) view;
            String color = view.getTag().toString();
            drawView.setColor(color);
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currPaint = (ImageButton) view;
            drawView.setBrushSize(drawView.getLastBrushSize());
        }
    }

    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}

