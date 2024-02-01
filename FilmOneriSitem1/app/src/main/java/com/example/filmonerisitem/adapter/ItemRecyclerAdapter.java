package com.example.filmonerisitem.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.filmonerisitem.MovieDetails;
import com.example.filmonerisitem.R;
import com.example.filmonerisitem.model.CategoryItem;

import java.util.List;

// ItemRecyclerAdapter sınıfı, bir RecyclerView içinde belirli bir kategorideki film öğelerini göstermek için kullanılır.

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder> {
    Context context;  // Adapter'in bağlı olduğu context
    List<CategoryItem> categoryItemList;  // Bir kategori içindeki film öğelerinin listesi

    // Yapıcı metot, Adapter'i oluştururken gerekli parametreleri alır.
    public ItemRecyclerAdapter(Context context, List<CategoryItem> categoryItemList) {
        this.context = context;
        this.categoryItemList = categoryItemList;
    }

    // ViewHolder'ın oluşturulduğu metot
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.cat_recycler_row_items, parent, false));
    }

    // ViewHolder'ın belirli bir pozisyondaki verilerle doldurulduğu metot
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        // Glide kütüphanesi ile ImageView'a film öğesinin resmini yüklemek
        Glide.with(context).load(categoryItemList.get(position).getImageUrl()).into(holder.itemImage);

        // Film öğesine tıklandığında MovieDetails aktivitesine geçiş için onClickListener
        holder.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // MovieDetails aktivitesine geçiş için Intent oluşturma ve verileri ekleyerek başlatma
                Intent i = new Intent(context, MovieDetails.class);
                i.putExtra("movieId", categoryItemList.get(position).getId());
                i.putExtra("movieName", categoryItemList.get(position).getMovieName());
                i.putExtra("movieImageUrl", categoryItemList.get(position).getImageUrl());
                i.putExtra("movieFile", categoryItemList.get(position).getFileUrl());
                context.startActivity(i);
            }
        });
    }

    // Toplam öğe sayısını döndüren metot
    @Override
    public int getItemCount() {
        return categoryItemList.size();
    }

    // ViewHolder sınıfı, RecyclerView içinde her bir film öğesi için kullanılacak olan view'ları içerir.
    public static final class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;  // Film öğesinin resmini gösteren ImageView

        // ViewHolder'ın yapıcı metodu
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            // Layout dosyasındaki ImageView'a erişim
            itemImage = itemView.findViewById(R.id.item_image);
        }
    }
}