package com.bbb.soccernews.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bbb.soccernews.R;
import com.bbb.soccernews.databinding.NewsItemBinding;
import com.bbb.soccernews.domain.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{
    private final List<News> news;
    private final FavoriteListener favoriteListener;
    public NewsAdapter(List<News>news, FavoriteListener favoriteListener){
        this.favoriteListener = favoriteListener;
        this.news=news;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(layoutInflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return this.news.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        News news = this.news.get(position);
        holder.binding.tvNewTitle.setText(news.title);
        holder.binding.tvNewDescription.setText(news.description);
        Picasso.get().load(news.image)
                .fit()
                .into(holder.binding.ivNew);
        //Implementação de abrir link pelo botao
        holder.binding.btOpenLink.setOnClickListener(view ->{
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(news.link));
            holder.itemView.getContext().startActivity(i);
        });
        //Implementação de compartilhamento
        holder.binding.ivShare.setOnClickListener(view->{
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, news.title);
            i.putExtra(Intent.EXTRA_TEXT, news.link);
            holder.itemView
                    .getContext()
                    .startActivity(Intent.createChooser(i,""));
        });
        //Implementação de favoritar
        holder.binding.ivFavorite.setOnClickListener(view -> {
            news.favorite = !news.favorite;
            this.favoriteListener.onClick(news);
            notifyItemChanged(position);
        });
    if (news.favorite){
        holder.binding.ivFavorite.setColorFilter(context.getResources().getColor(R.color.red));
    }
    else{
        holder.binding.ivFavorite.setColorFilter(context.getResources().getColor(R.color.gray));
    }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final NewsItemBinding binding;

        public ViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public interface FavoriteListener{
        void onClick(News news);
    }
}
