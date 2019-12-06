package id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.data.entity.Quote;
import id.ac.ui.cs.mobileprogramming.hemamittakalyani.learningcompanion.retrofit.ApiRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuoteViewModel extends ViewModel {
    private MutableLiveData<Quote> quote;

    public LiveData<Quote> getRandomQuote() {
        if (quote == null) {
            quote = new MutableLiveData<>();
            loadQuote();
        }
        return quote;
    }

    private void loadQuote() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiRequest.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiRequest api = retrofit.create(ApiRequest.class);
        Call<Quote> call = api.getRandomQuote();


        call.enqueue(new Callback<Quote>() {
            @Override
            public void onResponse(Call<Quote> call, Response<Quote> response) {
                quote.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t) {

            }
        });
    }
}
