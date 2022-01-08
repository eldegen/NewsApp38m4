package com.example.newsapp38m4.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.newsapp38m4.R;
import com.example.newsapp38m4.databinding.FragmentDashboardBinding;
import com.example.newsapp38m4.ui.news.NewsItemModel;
import com.example.newsapp38m4.ui.news.NewsRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class DashboardFragment extends Fragment {
    private FragmentDashboardBinding binding;
    private NewsRecyclerAdapter recyclerAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        recyclerAdapter = new NewsRecyclerAdapter();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerAdapter = new NewsRecyclerAdapter();
        binding.rvDashboard.setAdapter(recyclerAdapter);

        getData();
    }

    public void getData() {
        FirebaseFirestore.getInstance().collection("news").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show();
                List<NewsItemModel> list = queryDocumentSnapshots.toObjects(NewsItemModel.class);
                Log.d("f_global", "Got data from Firestore: " + list);
                recyclerAdapter.addItems(list);
                recyclerAdapter.reload();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}