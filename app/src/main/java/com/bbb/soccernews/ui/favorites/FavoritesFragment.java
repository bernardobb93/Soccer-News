package com.bbb.soccernews.ui.favorites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bbb.soccernews.MainActivity;
import com.bbb.soccernews.databinding.FragmentFavoritesBinding;
import com.bbb.soccernews.databinding.FragmentNewsBinding;
import com.bbb.soccernews.domain.News;
import com.bbb.soccernews.ui.adapter.NewsAdapter;

import java.util.List;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavoritesViewModel favoritesViewModel =
                new ViewModelProvider(this).get(FavoritesViewModel.class);
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);

        findFavoritedNews();


        return binding.getRoot();
    }

    private void findFavoritedNews() {
        MainActivity activity=(MainActivity) getActivity();
        if (activity != null) {
            List<News> favoritedNews = activity.getDb().newsDao().loadFavoriteNews();
            binding.rvNews.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rvNews.setAdapter(new NewsAdapter(favoritedNews, updatedNews->{
                activity.getDb().newsDao().save(updatedNews);
                findFavoritedNews();
            }));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}