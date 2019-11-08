package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.views;


import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Book;

public class BookTypeConverter {
    
    @TypeConverter
    public static List<Book> stringToBookList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Book>>() {}.getType();
        Gson gson = new Gson();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String bookListToString(List<Book> books) {
        Gson gson = new Gson();
        return gson.toJson(books);
    }
}