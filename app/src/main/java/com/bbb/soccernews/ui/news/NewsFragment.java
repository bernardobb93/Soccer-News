package com.bbb.soccernews.ui.news;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.bbb.soccernews.data.local.AppDatabase;
import com.bbb.soccernews.databinding.FragmentNewsBinding;
import com.bbb.soccernews.ui.adapter.NewsAdapter;

public class NewsFragment extends Fragment {
    private AppDatabase db;
    private FragmentNewsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NewsViewModel newsViewModel =
                new ViewModelProvider(this).get(NewsViewModel.class);

        binding = FragmentNewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = Room.databaseBuilder(getContext(),
                AppDatabase.class, "soccer-news")
                .allowMainThreadQueries()
                .build();

        binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
        newsViewModel.getNews().observe(getViewLifecycleOwner(),news ->{
        binding.rvNews.setAdapter(new NewsAdapter(news,favoritedNews->{
            db.newsDao().insert(favoritedNews);

            }));
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}