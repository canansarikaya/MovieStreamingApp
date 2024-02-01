package com.example.filmonerisitem.model;

import java.util.List;

// AllCategory sınıfı, farklı film kategorilerini temsil eden verileri içerir.
public class AllCategory {

    private String categoryTitle;  // Kategorinin adını tutan değişken
    private Integer categoryId;  // Kategorinin kimliğini tutan değişken
    private List<CategoryItem> categoryItemList = null;  // Kategoriye ait film öğelerini tutan liste

    // Yapıcı metot, bir AllCategory nesnesi oluştururken gerekli parametreleri alır.
    public AllCategory(Integer categoryId, String categoryTitle, List<CategoryItem> categoryItemList) {
        this.categoryTitle = categoryTitle;
        this.categoryId = categoryId;
        this.categoryItemList = categoryItemList;
    }

    // Kategoriye ait film öğelerini döndüren metot
    public List<CategoryItem> getCategoryItemList() {
        return categoryItemList;
    }

    // Kategoriye ait film öğelerini set eden metot
    public void setCategoryItemList(List<CategoryItem> categoryItemList) {
        this.categoryItemList = categoryItemList;
    }

    // Kategorinin adını döndüren metot
    public String getCategoryTitle() {
        return categoryTitle;
    }

    // Kategorinin adını set eden metot
    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    // Kategorinin kimliğini döndüren metot
    public Integer getCategoryId() {
        return categoryId;
    }

    // Kategorinin kimliğini set eden metot
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}