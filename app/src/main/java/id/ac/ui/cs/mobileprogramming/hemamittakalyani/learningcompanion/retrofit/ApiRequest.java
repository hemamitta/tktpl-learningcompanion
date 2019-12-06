package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.retrofit;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Quote;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiRequest {
    String BASE_URL = "https://api.quotable.io/";

    @GET("random/")
    Call<Quote> getRandomQuote();
}
