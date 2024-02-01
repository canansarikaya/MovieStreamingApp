package com.example.filmonerisitem;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class VideoPlayerActivity extends AppCompatActivity {
    private SimpleExoPlayer simpleExoPlayer;
    private PlayerView videoPlayer;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tam ekran görünümü için gerekli ayarlar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Activity'nin layout dosyasını belirleme
        setContentView(R.layout.activity_video_player);

        // Video oynatıcı ve ilgili öğelerin referanslarını alıyoruz
        videoPlayer = findViewById(R.id.exo_player);
        progressBar = findViewById(R.id.progress_bar);

        // ExoPlayer'ı kurma metodu, video URL'sini alıyor
        setUpExoPlayer(getIntent().getStringExtra("url"));
    }

    private void setUpExoPlayer(String url) {
        // ExoPlayer için LoadControl ve TrackSelector oluşturma
        LoadControl loadControl = new DefaultLoadControl();
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);

        // SimpleExoPlayer oluşturma ve gerekli ayarları yapma
        simpleExoPlayer = new SimpleExoPlayer.Builder(this)
                .setTrackSelector(trackSelector)
                .setLoadControl(loadControl)
                .build();

        // Video oynatıcıya ExoPlayer'ı bağlama
        videoPlayer.setPlayer(simpleExoPlayer);

        // Veri kaynağı oluşturma ve MediaSource'e ekleme
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "movieapp"));
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(url));

        simpleExoPlayer.addMediaSource(mediaSource);

        // ExoPlayer olay dinleyicisi ekleme
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onPlayerError(PlaybackException error) {
                // Video oynatma sırasında bir hata olursa burada işlemler yapabilirsiniz.
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                // Oynatma durumu değiştiğinde işlemleri gerçekleştirme
                if (playbackState == Player.STATE_BUFFERING) {
                    progressBar.setVisibility(View.VISIBLE);
                } else if (playbackState == Player.STATE_READY) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

        // ExoPlayer'ı hazırlama ve otomatik oynatmayı başlatma
        simpleExoPlayer.prepare();
        simpleExoPlayer.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Activity sonlandığında SimpleExoPlayer'ı serbest bırakma
        if (simpleExoPlayer != null) {
            simpleExoPlayer.release();
        }
    }
}
