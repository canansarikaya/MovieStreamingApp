package com.example.filmonerisitem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.filmonerisitem.R;
import com.example.filmonerisitem.model.AllCategory;
import com.example.filmonerisitem.model.CategoryItem;

import java.util.List;


// MainRecyclerAdapter sınıfı, ana RecyclerView içinde farklı kategorileri temsil eden alt RecyclerView'ları yönetir.
// Her bir alt RecyclerView, bir kategori adını (categoryName) ve bu kategoriye ait film öğelerini (itemRecycler) gösterir.

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    Context context;  // Adapter'in bağlı olduğu context
    List<AllCategory> allCategoryList;  // Ana RecyclerView'da gösterilecek kategorilerin listesi

    // Yapıcı metot, Adapter'i oluştururken gerekli parametreleri alır.
    public MainRecyclerAdapter(Context context, List<AllCategory> allCategoryList) {
        this.context = context;
        this.allCategoryList = allCategoryList;
    }

    // ViewHolder'ın oluşturulduğu metot
    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // main_recycler_row_item layout'unu temsil eden ViewHolder'ı oluştur
        return new MainViewHolder(LayoutInflater.from(context).inflate(R.layout.main_recycler_row_item, parent, false));
    }

    // ViewHolder'ın belirli bir pozisyondaki verilerle doldurulduğu metot
    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        // Her bir ViewHolder'a, kategori adını (categoryName) ve kategoriye ait film öğelerini (itemRecycler) set et
        holder.categoryName.setText(allCategoryList.get(position).getCategoryTitle());
        setItemRecycler(holder.itemRecycler, allCategoryList.get(position).getCategoryItemList());
    }

    // Toplam öğe sayısını döndüren metot
    @Override
    public int getItemCount() {
        return allCategoryList.size();
    }

    // ViewHolder sınıfı, RecyclerView içinde her bir kategori için kullanılacak olan view'ları içerir.
    public static final class MainViewHolder extends RecyclerView.ViewHolder {

        TextView categoryName;  // Kategori adını gösteren TextView
        RecyclerView itemRecycler;  // Kategoriye ait film öğelerini gösteren alt RecyclerView

        // ViewHolder'ın yapıcı metodu
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            // Layout dosyasındaki View'lara erişim
            categoryName = itemView.findViewById(R.id.item_category);
            itemRecycler = itemView.findViewById(R.id.item_recycler);
        }
    }

    // Alt RecyclerView'i (itemRecycler) ayarlayan özel metot
    private void setItemRecycler(RecyclerView recyclerView, List<CategoryItem> categoryItemList) {
        // Alt RecyclerView için özel bir adapter oluştur
        ItemRecyclerAdapter itemRecyclerAdapter = new ItemRecyclerAdapter(context, categoryItemList);
        // Alt RecyclerView'e yatay yönlü bir LinearLayoutManager ve oluşturulan adapter'ı set et
        recyclerView.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(itemRecyclerAdapter);
    }
}