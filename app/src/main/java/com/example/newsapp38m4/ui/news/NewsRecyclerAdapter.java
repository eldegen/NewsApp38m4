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

public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {
    private ArrayList<NewsItemModel> list;
//    private ArrayList<NewsItemModel> date;
    private OnItemClickListener onItemClickListener;

    public NewsRecyclerAdapter() {
        list = new ArrayList<>();
//        date = new ArrayList<>();
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

    public void addItem(NewsItemModel newsItemModel) {
        list.add(0, newsItemModel);
        notifyItemInserted(0);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public NewsItemModel getItem(int position) {
        return list.get(position);
    }

    public void deleteItem(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }

    public void editItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("news.edititem", list.get(position).toString());
    }

    public String getItemString(int position) {
        String text = list.get(position).getNewsTitle();
        return text;
    }

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
