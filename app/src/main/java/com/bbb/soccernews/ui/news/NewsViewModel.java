package com.bbb.soccernews.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bbb.soccernews.domain.News;

import java.util.ArrayList;
import java.util.List;

public class NewsViewModel extends ViewModel {

    private final MutableLiveData<List<News>> news;

    public NewsViewModel() {
        this.news = new MutableLiveData<>();
        List<News> news =new ArrayList<>();
        news.add(new News("Brasil X Colombia","Jogou Sabado"));
        news.add(new News("Brasil X Uruguai","Jogara terça"));
        news.add(new News("Brasil X Argentina","Jogou semana passada"));
        this.news.setValue(news);
    }

    public LiveData<List<News>> getNews() {
        return news;
    }
}