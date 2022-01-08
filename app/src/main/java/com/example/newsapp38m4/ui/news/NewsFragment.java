package com.example.newsapp38m4.ui.news;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newsapp38m4.App;
import com.example.newsapp38m4.R;
import com.example.newsapp38m4.databinding.FragmentNewsBinding;
import com.example.newsapp38m4.ui.news.NewsItemModel;
import com.example.newsapp38m4.ui.news.NewsRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class NewsFragment extends Fragment {
    private FragmentNewsBinding binding;
    private NewsRecyclerAdapter recyclerAdapter;
    private NewsItemModel newsItemModel;

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
        newsItemModel = (NewsItemModel) requireArguments().getSerializable("news.edit");
        try {
            Log.d("f_global", "NewsFragment: got model, String: " + newsItemModel.getNewsTitle());
        } catch (NullPointerException e) {

        }
        if (newsItemModel != null) binding.editText.setText(newsItemModel.getNewsTitle());

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                open();
            }
        });
    }

    private void open() {
        Log.e("f_news", "opened");

        String text = binding.editText.getText().toString();
        if (newsItemModel == null) {
            Bundle bundle = new Bundle();
            NewsItemModel newsItemModel = new NewsItemModel(text, System.currentTimeMillis());
            bundle.putSerializable("news.content", newsItemModel);
            getParentFragmentManager().setFragmentResult("rk.news", bundle);
            Log.d("f_global", "millis: " + newsItemModel.getNewsDate());
            App.getInstance().getDatabase().newsDao().insert(newsItemModel);

            addToFirestore(newsItemModel);
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.editText.setEnabled(false);
            binding.btnSave.setEnabled(false);
        } else {
            Bundle bundle = new Bundle();
            newsItemModel.setNewsTitle(text);
            bundle.putSerializable("news.content", newsItemModel);
            getParentFragmentManager().setFragmentResult("rk.news.update", bundle);

            close();
        }

        Log.e("f_home", "reached");
    }

    private void addToFirestore(NewsItemModel newsItemModel) {
        FirebaseFirestore.getInstance().collection("news").add(newsItemModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(requireActivity(), "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireActivity(), "Failure", Toast.LENGTH_SHORT).show();
                }
                close();
            }
        });
    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
        navController.navigateUp();
    }

    public void callParentMethod(){
        getActivity().onBackPressed();
    }
}