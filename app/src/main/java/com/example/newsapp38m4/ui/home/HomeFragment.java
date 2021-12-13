package com.example.newsapp38m4.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.newsapp38m4.NewsFragment;
import com.example.newsapp38m4.NewsItemModel;
import com.example.newsapp38m4.NewsRecyclerAdapter;
import com.example.newsapp38m4.OnItemClickListener;
import com.example.newsapp38m4.R;
import com.example.newsapp38m4.databinding.FragmentHomeBinding;
import com.example.newsapp38m4.databinding.FragmentNewsBinding;
import com.example.newsapp38m4.databinding.ItemNewsBinding;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private NewsRecyclerAdapter recyclerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerAdapter = new NewsRecyclerAdapter();
        recyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                NewsItemModel newsItemModel = recyclerAdapter.getItem(position);
                Log.e("f_home", "got String from model: " + newsItemModel.getNewsTitle());
                Bundle bundle = new Bundle();
                bundle.putString("news.edittext", newsItemModel.getNewsTitle());
                openFragment();
            }

            @Override
            public void onLongClick(int position) {

            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment();
            }
        });
        getParentFragmentManager().setFragmentResultListener("rk.news", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                NewsItemModel newsItemModel = (NewsItemModel) result.getSerializable("news.content");

                recyclerAdapter.addItem(newsItemModel);
            }
        });

        initList();
    }

    private void initList() {
        binding.rvHome.setAdapter(recyclerAdapter);
    }

    private void openFragment() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigate(R.id.newsFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}