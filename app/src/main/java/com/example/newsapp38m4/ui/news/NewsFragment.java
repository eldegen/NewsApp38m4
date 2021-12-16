package com.example.newsapp38m4.ui.news;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newsapp38m4.R;
import com.example.newsapp38m4.databinding.FragmentNewsBinding;
import com.example.newsapp38m4.ui.news.NewsItemModel;
import com.example.newsapp38m4.ui.news.NewsRecyclerAdapter;

public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;
    private NewsRecyclerAdapter recyclerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
            }
        });
    }

    private void edit() {
        // TODO: use SaveArgs to do this
    }

    private void open() {
        Log.e("f_news", "opened");

        String text = binding.editText.getText().toString();
        NewsItemModel newsItemModel = new NewsItemModel(text, System.currentTimeMillis());
        Bundle bundle = new Bundle();
        bundle.putSerializable("news.content", newsItemModel); //
        getParentFragmentManager().setFragmentResult("rk.news", bundle);
        Log.e("f_home", "reached");
        close();
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}