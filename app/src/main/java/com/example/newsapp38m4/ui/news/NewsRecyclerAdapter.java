package com.example.newsapp38m4.ui.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp38m4.databinding.ItemNewsBinding;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {
    private ArrayList<NewsItemModel> list;
    private OnItemClickListener onItemClickListener;

    public NewsRecyclerAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewsBinding binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // Adds article
    public void addItem(NewsItemModel newsItemModel) {
        list.add(0, newsItemModel);
        notifyItemInserted(0);
    }

    // Adds articles (Databases)
    public void addItems(List<NewsItemModel> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    // Article click listener
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    // For "zebra" style. (upd: bruh it's not working)
    public int setItemColor(int bgColor) {
        return bgColor;
    }

    // Gets article
    public NewsItemModel getItem(int position) {
        return list.get(position);
    }

    // Deletes article
    public void deleteItem(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    // Edits article
    public void editItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("news.edititem", list.get(position).toString());
    }

    // idk what is this
    public String getItemString(int position) {
        String text = list.get(position).getNewsTitle();
        return text;
    }

    // Debug
    public ArrayList<NewsItemModel> getListDebug() {
        return list;
    }

    // News ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemNewsBinding binding;
        public ViewHolder(@NonNull ItemNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*Bundle bundle = new Bundle();
                    bundle.putString("news.edit", );*/
                    onItemClickListener.onClick(getAdapterPosition());
                }
            });
            binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onItemClickListener.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

        public void onBind(NewsItemModel newsItemModel) {
            long bufferDate = newsItemModel.getNewsDate();
            // String putDate = String.valueOf(bufferDate);
            String newPutDate = DateFormat.getInstance().format(bufferDate);

            binding.tvNewsTitle.setText(newsItemModel.getNewsTitle());
            binding.tvNewsDate.setText(newPutDate);
        }
    }
}
