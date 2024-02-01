package com.example.filmonerisitem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.filmonerisitem.MovieDetails;
import com.example.filmonerisitem.R;
import com.example.filmonerisitem.model.BannerMovies;

import java.util.List;


// BannerMoviesPagerAdapter sınıfı, bir ViewPager içinde döngüsel olarak gösterilecek olan banner filmlerini yönetir.
public class BannerMoviesPagerAdapter extends PagerAdapter {
    Context context;  // Adapter'in bağlı olduğu context
    List<BannerMovies> bannerMoviesList;  // Banner filmlerinin listesi

    // Yapıcı metot, Adapter'i oluştururken gerekli parametreleri alır.
    public BannerMoviesPagerAdapter(Context context, List<BannerMovies> bannerMoviesList) {
        this.context = context;
        this.bannerMoviesList = bannerMoviesList;
    }

    // Sayfa sayısını döndüren metot
    @Override
    public int getCount() {
        return bannerMoviesList.size();
    }

    // Belirli bir view'un belirli bir objeyle ilişkilendirilip ilişkilendirilmediğini kontrol eden metot
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    // Sayfa destroy olduğunda çağrılan metot, view'ı container'dan kaldırır
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    // Belirli bir pozisyondaki sayfa view'ını oluşturan metot
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // Layout dosyasını inflater ile view'a dönüştürme
        View view = LayoutInflater.from(context).inflate(R.layout.banner_movie_layout, null);

        // Banner filmin resmini gösteren ImageView
        ImageView bannerImage = view.findViewById(R.id.banner_image);

        // Glide kütüphanesi ile resmi ImageView'a yüklemek
        Glide.with(context).load(bannerMoviesList.get(position).getImageUrl()).into(bannerImage);
        container.addView(view);  // View'ı container'a eklemek

        // Banner resmine tıklandığında MovieDetails aktivitesine geçişi sağlayan onClickListener
        bannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // MovieDetails aktivitesine geçiş için Intent oluşturma ve verileri ekleyerek başlatma
                Intent i = new Intent(context, MovieDetails.class);
                i.putExtra("movieId", bannerMoviesList.get(position).getId());
                i.putExtra("movieName", bannerMoviesList.get(position).getMovieName());
                i.putExtra("movieImageUrl", bannerMoviesList.get(position).getImageUrl());
                i.putExtra("movieFile", bannerMoviesList.get(position).getFileUrl());
                context.startActivity(i);
            }
        });

        return view;
    }
}