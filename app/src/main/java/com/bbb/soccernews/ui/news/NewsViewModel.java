package com.bbb.soccernews.ui.news;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.bbb.soccernews.data.local.AppDatabase;
import com.bbb.soccernews.data.remote.SoccerNewsApi;
import com.bbb.soccernews.domain.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsViewModel extends ViewModel {
    private final SoccerNewsApi api;

    private final MutableLiveData<List<News>> news=new MutableLiveData<>();

    public NewsViewModel() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://bernardobb93.github.io/soccer-news-Api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(SoccerNewsApi.class);


        this.findNews();
    }

    private void findNews() {
        api.getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
               if (response.isSuccessful()){
                   news.setValue(response.body());
               }
               else{

               }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {

            }
        });
    }

    public LiveData<List<News>> getNews() {
        return news;
    }
}