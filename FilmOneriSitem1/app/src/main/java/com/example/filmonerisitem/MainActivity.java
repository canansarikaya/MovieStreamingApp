package com.example.filmonerisitem;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.filmonerisitem.adapter.BannerMoviesPagerAdapter;
import com.example.filmonerisitem.adapter.MainRecyclerAdapter;
import com.example.filmonerisitem.model.AllCategory;
import com.example.filmonerisitem.model.BannerMovies;
import com.example.filmonerisitem.model.CategoryItem;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    BannerMoviesPagerAdapter bannerMoviesPagerAdapter;
    TabLayout indicatorTab, categoryTab;
    ViewPager bannerMoviesViewPager;
    List<BannerMovies> homeBannerList;
    List<BannerMovies> tvShowBannerList;
    List<BannerMovies> movieBannerList;
    List<BannerMovies> kidsBannerList;
    Timer sliderTimer;

    NestedScrollView nestedScrollView;
    AppBarLayout appBarLayout;

    MainRecyclerAdapter mainRecyclerAdapter;
    RecyclerView mainRecycler;
    List<AllCategory> allCategoryList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // XML dosyasındaki bileşenleri Java'da kullanılabilir hale getirme
        indicatorTab = findViewById(R.id.tab_indicator);
        categoryTab = findViewById(R.id.tabLayout);
        nestedScrollView = findViewById(R.id.nested_scroll);
        appBarLayout = findViewById(R.id.appbar);

        // Veri listelerini oluşturma
        homeBannerList = new ArrayList<>();
        homeBannerList.add(new BannerMovies(1, "LUKAS", "https://m.media-amazon.com/images/M/MV5BMWYwZGYyYTktM2FkZC00MjVjLWIxMTItNDg3OTc0MjEyODgzXkEyXkFqcGdeQXVyNTI5NjIyMw@@._V1_.jpg", "https://video.blender.org/download/videos/3d95fb3d-c866-42c8-9db1-fe82f48ccb95-804.mp4"));
        homeBannerList.add(new BannerMovies(2, "NEW MOON", "https://static.boxofficeturkiye.com/movie/poster/300x429/63/2010063-772619171.jpg", "https://video.blender.org/download/videos/d06afb85-ecea-4df6-be75-005a7954a93c-2160.mp4"));
        homeBannerList.add(new BannerMovies(3, "AVATAR", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTj_jlmtJFQjMPb81LAOfYVCNLAP0OiENClppIYYiYQvJejCNf4lG_hWEHyDf5tE_WeO4s&usqp=CAU", "https://video.blender.org/download/videos/97b04642-4d8a-449c-b7bc-c01f927ced00-1080.mp4"));

        tvShowBannerList = new ArrayList<>();
        tvShowBannerList.add(new BannerMovies(1, "FRIENDS", "https://qph.cf2.quoracdn.net/main-qimg-fa5cb0cf86708aaebdbd814f35f07d16-lq", "https://video.blender.org/download/videos/ac71efff-5e26-440b-8c38-dd1f5b598e02-1080.mp4"));
        tvShowBannerList.add(new BannerMovies(2, "SHARK TANK", "https://ntvb.tmsimg.com/assets/p3560383_b_h8_au.jpg?w=1280&h=720", "https://video.blender.org/download/videos/43c66e88-d05b-4aa7-af52-5818bf1198de-2160.mp4"));
        tvShowBannerList.add(new BannerMovies(3, "the BIGBANG THEORY", "https://hips.hearstapps.com/vader-prod.s3.amazonaws.com/1589368660-118568992-1589368650.jpg?crop=1xw:0.9375xh;center,top&resize=980:*", "https://video.blender.org/download/videos/81f40b20-4a5b-450e-b68b-cfa02f1b50ea-1080.mp4"));
        tvShowBannerList.add(new BannerMovies(4, "UPLOAD", "https://www.digitaltrends.com/wp-content/uploads/2023/10/xHA48xeftf7hZmjXNZqDghCZM1a.jpg", "https://video.blender.org/download/videos/d06afb85-ecea-4df6-be75-005a7954a93c-2160.mp4"));

        movieBannerList = new ArrayList<>();
        movieBannerList.add(new BannerMovies(1, "DOG", "https://img.memurlar.net/galeri/21996/cedc9cdb-eba3-ec11-810c-a0369f7d1486.jpg?width=800", "https://video.blender.org/download/videos/04da454b-9893-4184-98f3-248d00625efe-1608.mp4"));
        movieBannerList.add(new BannerMovies(2, "LITTLE WOMEN", "https://cdn1.ntv.com.tr/gorsel/0_xboVeudEyw2GqkpDoUdw.jpg?width=1000&mode=both&scale=both&v=1581134860549", "https://video.blender.org/download/videos/43c66e88-d05b-4aa7-af52-5818bf1198de-2160.mp4"));


        kidsBannerList = new ArrayList<>();
        kidsBannerList.add(new BannerMovies(1, "WOODY WOODPECKER", "https://cdn1.ntv.com.tr/gorsel/IyYqtJhCJUKhfvHGGjfbDQ.jpg?width=1000&mode=both&scale=both&v=1655277555609", "https://video.blender.org/download/videos/ee623d80-fd8d-4a2a-8aae-1f5acf796f27-1080.mp4"));
        kidsBannerList.add(new BannerMovies(2, "REGULER SHOW", "https://static.wikia.nocookie.net/cizgi-film-izle/images/7/78/S%C3%BCrekli_Dizi_Filmi.jpg/revision/latest?cb=20181101114610&path-prefix=tr", "https://video.blender.org/download/videos/7b2eff2a-35f2-4403-9d88-d0dd6e4b5ba1-1080.mp4"));
        kidsBannerList.add(new BannerMovies(3, "NICKELODEON", "https://cdn1.ntv.com.tr/gorsel/sLeVRhJXj0ys3IYWbh7ZaA.jpg?width=640&height=480&mode=crop&scale=both&v=20220615071915609", "https://video.blender.org/download/videos/23f3ef79-15dc-44c5-aa45-cf92e78a4509-1080.mp4"));
        kidsBannerList.add(new BannerMovies(4, "THE WILLOUGHBYS", "https://hthayat.haberturk.com/im/2014/07/21/1022541_dbd8fc0ac7ed8d324a3418f33d570766_600x600.jpg", "https://video.blender.org/download/videos/6b9ba62e-f379-43c2-9786-bea297f55904-1080.mp4"));
        kidsBannerList.add(new BannerMovies(5, "THE GOONIES", "https://www.psikohayatterapi.com/wp-content/uploads/2020/11/Resim27-1.jpg", "https://video.blender.org/download/videos/bf1f3fb5-b119-4f9f-9930-8e20e892b898-720.mp4"));


        // Varsayılan olarak ana sayfa banner'ını ayarlama
        setBannerMoviesPagerAdapter(homeBannerList);


        // Kategori sekmesindeki seçimleri dinleme
        categoryTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 1:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(tvShowBannerList);
                        return;
                    case 2:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(movieBannerList);
                        return;
                    case 3:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(kidsBannerList);
                        return;
                    default:
                        setScrollDefaultState();
                        setBannerMoviesPagerAdapter(homeBannerList);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Kategori listelerini oluşturma
        List<CategoryItem> homeCatListItem1 = new ArrayList<>();
        homeCatListItem1.add(new CategoryItem(1, "THE CONJURING", "https://img-s3.onedio.com/id-61c4755adcc37c93523cf9c2/rev-0/w-600/h-889/f-jpg/s-bebdc83763a035c6ea0aed196abd5efdff35e843.jpg", "https://video.blender.org/download/videos/3d95fb3d-c866-42c8-9db1-fe82f48ccb95-804.mp4"));
        homeCatListItem1.add(new CategoryItem(2, "BEYOND THE WOODS", "https://tr.web.img4.acsta.net/medias/nmedia/18/85/93/89/19813374.jpg", "https://video.blender.org/download/videos/43c66e88-d05b-4aa7-af52-5818bf1198de-2160.mp4"));
        homeCatListItem1.add(new CategoryItem(3, "THE COLLECTİON", "https://image.cnnturk.com/i/cnnturk/75/0x555/5977234361361f098cafe01d.jpg", "https://video.blender.org/download/videos/ac71efff-5e26-440b-8c38-dd1f5b598e02-1080.mp4"));

        List<CategoryItem> homeCatListItem2 = new ArrayList<>();
        homeCatListItem2.add(new CategoryItem(1, "50 FIRST DATES", "https://www.chip.com.tr/images/content/2020/05/25/20200525132028624119.jpg", "https://video.blender.org/download/videos/47448bc1-0cc0-4bd1-b6c8-9115d8f7e08c-720.mp4"));
        homeCatListItem2.add(new CategoryItem(2, "the HOLIDAY", "https://www.chip.com.tr/images/content/2020/05/20/20200520181425328119.jpg", "https://video.blender.org/download/videos/ff427260-9631-40d9-bd39-65f21f95e9ad-720.mp4"));
        homeCatListItem2.add(new CategoryItem(3, "LEAP YEAR", "https://img-s3.onedio.com/id-60b8873d58737bd527e26ed5/rev-0/w-600/h-800/f-jpg/s-627b553857ed22a08217c343fcc94338acd39560.jpg", "https://video.blender.org/download/videos/ff427260-9631-40d9-bd39-65f21f95e9ad-720.mp4"));

        List<CategoryItem> homeCatListItem3 = new ArrayList<>();
        homeCatListItem3.add(new CategoryItem(1, "DADDY DAY CARE", "https://www.boredpanda.com/blog/wp-content/uploads/2022/06/family-movies-11-62b1a7d5df381__700.jpg", "https://video.blender.org/download/videos/7b2eff2a-35f2-4403-9d88-d0dd6e4b5ba1-1080.mp4"));
        homeCatListItem3.add(new CategoryItem(2, "IVY + BEAN", "https://hips.hearstapps.com/hmg-prod/images/best-family-movies-netflix-ivy-bean-64026f7963a2e.jpeg", "https://video.blender.org/download/videos/ee623d80-fd8d-4a2a-8aae-1f5acf796f27-1080.mp4"));
        homeCatListItem3.add(new CategoryItem(3, "HOME", "https://hips.hearstapps.com/hmg-prod/images/best-family-movies-home-640266409232e.jpeg", "https://video.blender.org/download/videos/23f3ef79-15dc-44c5-aa45-cf92e78a4509-1080.mp4"));

        List<CategoryItem> homeCatListItem4 = new ArrayList<>();
        homeCatListItem4.add(new CategoryItem(1, "THE WHELL OF TIME", "https://www.dizidoktoru.com/images/upload/1635752401_Zaman_Carki.jpg", "https://video.blender.org/download/videos/04da454b-9893-4184-98f3-248d00625efe-1608.mp4"));
        homeCatListItem4.add(new CategoryItem(2, "JUNGLE CRUISE", "https://faze.b-cdn.net/cms/wp-content/uploads/2021/08/jungle-cruise-3.jpg", "https://video.blender.org/download/videos/8533ea43-4271-4a57-9694-e9d0b35e1aa1-800.mp4"));
        homeCatListItem4.add(new CategoryItem(3, "THE WINDOW", "https://221bdergi.com/wp-content/uploads/2021/06/the_widow-680x510.jpg", "https://video.blender.org/download/videos/97b04642-4d8a-449c-b7bc-c01f927ced00-1080.mp4"));

        // Ana kategori listesini oluşturma ve içeriğini doldurma
        allCategoryList = new ArrayList<>();
        allCategoryList.add(new AllCategory(1, "Horror movie", homeCatListItem1));
        allCategoryList.add(new AllCategory(2, "Romantic and comedy ", homeCatListItem2));
        allCategoryList.add(new AllCategory(3, "Kids and family movies", homeCatListItem3));
        allCategoryList.add(new AllCategory(4, "Amazon Original series", homeCatListItem4));



        // Ana RecyclerView'i ayarlama
        setMainRecycler(allCategoryList);

    }

    // Banner ViewPager'ı ayarlamak için kullanılan metot
    private void setBannerMoviesPagerAdapter(List<BannerMovies> bannerMoviesList) {
        bannerMoviesViewPager = findViewById(R.id.banner_viewPager);
        bannerMoviesPagerAdapter = new BannerMoviesPagerAdapter(this, bannerMoviesList);
        bannerMoviesViewPager.setAdapter(bannerMoviesPagerAdapter);
        indicatorTab.setupWithViewPager(bannerMoviesViewPager);

        Timer sliderTimer = new Timer();
        sliderTimer.scheduleAtFixedRate(new AutoSlider(), 4000, 6000);
        indicatorTab.setupWithViewPager(bannerMoviesViewPager, true);


    }

    // Otomatik slider işlemi için TimerTask sınıfı
    class AutoSlider extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (bannerMoviesViewPager.getCurrentItem() < homeBannerList.size() - 1) {

                        bannerMoviesViewPager.setCurrentItem(bannerMoviesViewPager.getCurrentItem() + 1);

                    } else {

                        bannerMoviesViewPager.setCurrentItem(0);

                    }
                }

            });
        }
    }

    // Ana RecyclerView'i ayarlamak için kullanılan metot
    public void setMainRecycler(List<AllCategory> allCategoryList) {

        mainRecycler = findViewById(R.id.main_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        mainRecycler.setLayoutManager(layoutManager);
        mainRecyclerAdapter = new MainRecyclerAdapter(this, allCategoryList);
        mainRecycler.setAdapter(mainRecyclerAdapter);
    }

    // Scroll durumunu varsayılana döndürmek için kullanılan metot
    private void setScrollDefaultState() {

        nestedScrollView.fullScroll(View.FOCUS_UP);
        nestedScrollView.scrollTo(0, 0);
        appBarLayout.setExpanded(true, true);
    }
}