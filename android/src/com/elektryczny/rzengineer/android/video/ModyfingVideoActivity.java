package com.elektryczny.rzengineer.android.video;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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


public class ModyfingVideoActivity extends Activity implements View.OnClickListener, SurfaceHolder.Callback {
    private DrawingView drawView;
    private ImageButton currPaint, drawBtn, eraseBtn, newBtn, saveBtn;
    private float smallBrush, mediumBrush, largeBrush;
    private Boolean isStarted = false;
    Menu menu;

    private final MediaPlayer mediaPlayer = new MediaPlayer();
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private boolean pausing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modyfing_video);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceVideoEditView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        drawView = (DrawingView) findViewById(R.id.drawing_video);
        LinearLayout paintLayout = (LinearLayout) findViewById(R.id.paint_colors);
        currPaint = (ImageButton) paintLayout.getChildAt(0);
        currPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);

        drawBtn = (ImageButton) findViewById(R.id.draw_btn);
        drawBtn.setOnClickListener(this);
        drawView.setBrushSize(mediumBrush);

        eraseBtn = (ImageButton) findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(this);

        newBtn = (ImageButton) findViewById(R.id.new_layer_btn);
        newBtn.setOnClickListener(this);

        saveBtn = (ImageButton) findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                isStarted = false;
            }
        });

        final Button backVideoB = (Button) findViewById(R.id.videoModyfingBackButton);
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
        if (view.getId() == R.id.draw_btn) {
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
        } else if (view.getId() == R.id.erase_btn) {
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
        } else if (view.getId() == R.id.new_layer_btn) {
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("Nowa warstwa");
            newDialog.setMessage("Rozpoczynając od nowa stracisz dotychczasowy postęp. Chcesz to zrobić?");
            newDialog.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    drawView.startNew();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            newDialog.show();
        } else if (view.getId() == R.id.save_btn) {
            AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
            saveDialog.setTitle("Zapisz warstwę");
            saveDialog.setMessage("Zapisać warstwę?");
            saveDialog.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    drawView.setDrawingCacheEnabled(true);

                    String filename = MultimediaFileManager.getPathToFile(FileEnum.VIDEO_LAYER_FILE);
                    FileOutputStream out = null;
                    Bitmap bmp = drawView.getDrawingCache();
                    try {
                        out = new FileOutputStream(filename);
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                        // PNG is a lossless format, the compression factor (100) is ignored
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (out != null) {
                                Toast savedToast = Toast.makeText(getApplicationContext(),
                                        "Zapisano!", Toast.LENGTH_SHORT);
                                savedToast.show();
                                out.close();
                            } else {
                                Toast unsavedToast = Toast.makeText(getApplicationContext(),
                                        "Nie zapisano", Toast.LENGTH_SHORT);
                                unsavedToast.show();
                                out.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    drawView.destroyDrawingCache();
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
        getMenuInflater().inflate(R.menu.modyfing_video, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_restart_video) {
            if (pausing) {
                MenuItem p = menu.findItem(R.id.action_play_pause_video);
                p.setIcon(R.drawable.ic_action_pause_over_video);
            }
            pausing = false;
            isStarted = true;
            mediaPlayer.reset();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDisplay(surfaceHolder);

            Uri ourVideoClip;
            if (VideoActivity.fileUri == null) {
                File mediaFile = new File(MultimediaFileManager.getPathToFile(FileEnum.VIDEO_FILE));
                ourVideoClip = Uri.fromFile(mediaFile);
            } else {
                ourVideoClip = VideoActivity.fileUri;
            }

            try {
                mediaPlayer.setDataSource(getApplicationContext(), ourVideoClip);
                mediaPlayer.prepare();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
            return true;
        } else if (id == R.id.action_play_pause_video) {
            if (isStarted) {
                if (pausing) {
                    pausing = false;
                    mediaPlayer.start();
                    item.setIcon(R.drawable.ic_action_pause_over_video);
                } else {
                    pausing = true;
                    mediaPlayer.pause();
                    item.setIcon(R.drawable.ic_action_play_over_video);
                }
            }
        }
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

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}
