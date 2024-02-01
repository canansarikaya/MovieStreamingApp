package com.example.filmonerisitem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

// MovieDetails sınıfı, bir filmın detaylarını gösteren ekranı temsil eder.
public class MovieDetails extends AppCompatActivity {

    private ImageView movieImage;   // Film resmini gösteren ImageView
    private TextView movieName;     // Film adını gösteren TextView
    private Button playButton;      // Filmı oynatmak için kullanılan düğme

    private String mName, mImage, mId, mFileUrl;  // Filmin adı, resim URL'si, kimliği ve dosya URL'si

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // XML dosyasındaki bileşenleri Java koduna bağlama
        movieImage = findViewById(R.id.movie_image);
        movieName = findViewById(R.id.movie_name);
        playButton = findViewById(R.id.play_button);

        // Intent üzerinden gelen verileri alma
        mId = getIntent().getStringExtra("movieId");
        mName = getIntent().getStringExtra("movieName");
        mImage = getIntent().getStringExtra("movieImageUrl");
        mFileUrl = getIntent().getStringExtra("movieFile");

        // Glide kütüphanesi ile film resmini ImageView'e yükleme
        Glide.with(this).load(mImage).into(movieImage);

        // Film adını TextView'e set etme
        movieName.setText(mName);

        // "Oynat" düğmesine tıklanma durumunu dinleme
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // VideoPlayerActivity'ye geçiş yapma ve video URL'sini gönderme
                Intent i = new Intent(MovieDetails.this, VideoPlayerActivity.class);
                i.putExtra("url", mFileUrl);
                startActivity(i);
            }
        });
    }
}