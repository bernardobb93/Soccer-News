package com.bbb.soccernews.ui.news;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.bbb.soccernews.data.SoccerNewsRepository;
import com.bbb.soccernews.data.local.SoccerNewsDb;
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
    public enum State{
        DOING,DONE,ERROR
    }
    private final MutableLiveData<State> state=new MutableLiveData<>();
    private final MutableLiveData<List<News>> news=new MutableLiveData<>();

    public NewsViewModel() {
        this.findNews();
    }

    public void findNews() {
        state.setValue(State.DOING);
        SoccerNewsRepository.getInstance().getRemoteApi().getNews().enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
               if (response.isSuccessful()){
                   news.setValue(response.body());
                   state.setValue(State.DONE);
               }
               else{
                   state.setValue(State.ERROR);
               }
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable error) {
                error.printStackTrace();
                state.setValue(State.ERROR);
            }
        });
    }
    public void saveNews(News news) {
        AsyncTask.execute(() -> SoccerNewsRepository.getInstance().getLocalDb().newsDao().save(news));
    }

    public LiveData<List<News>> getNews() {
        return this.news;
    }

    public LiveData<State> getState() {
        return this.state;
    }
}