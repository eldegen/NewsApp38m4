package com.example.newsapp38m4;

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

import com.example.newsapp38m4.databinding.FragmentNewsBinding;

public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;

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
                openEdit();
            }
        });
    }

    private void openEdit() {
        Bundle bundle = new Bundle();
        if (bundle.getBoolean("news.is.editing")) {
            String text = bundle.getString("news.edititem");
            NewsItemModel newsItemModel = new NewsItemModel(text, System.currentTimeMillis());
//            TODO: finish that, you need to get String, then put it in adapter and change text on item
            bundle.putSerializable("news.setedit.content", newsItemModel);
            getParentFragmentManager().setFragmentResult("rk.news", bundle);
            Log.e("f_home", "reached 2");
            close();
        }
    }

    private void open() {
        String text = binding.editText.getText().toString();
        NewsItemModel newsItemModel = new NewsItemModel(text, System.currentTimeMillis());
        Bundle bundle = new Bundle();
        bundle.putSerializable("news.content", newsItemModel);
        getParentFragmentManager().setFragmentResult("rk.news", bundle);
        Log.e("f_home", "reached");
        close();
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }
}