package com.example.newsapp38m4.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.newsapp38m4.databinding.FragmentDashboardBinding;
import com.example.newsapp38m4.ui.news.NewsItemModel;
import com.example.newsapp38m4.ui.news.NewsRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerAdapter = new NewsRecyclerAdapter();
        binding.rvDashboard.setAdapter(recyclerAdapter);
        binding.tvReloadText.setText("Loading... Don't close fragment!");

        disableButtons();
        getData();
        reload();
    }

    private void reload() {
        binding.btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableButtons();
                recyclerAdapter.listReload();
                getData();
            }
        });

        binding.btnReloadSecond.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                disableButtons();
                binding.tvReloadText.setText("Reloading...");
                recyclerAdapter.listReload();
                getData();
            }
        });
    }

    public void getData() {
        FirebaseFirestore.getInstance().collection("news").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                List<NewsItemModel> list = queryDocumentSnapshots.toObjects(NewsItemModel.class);
                Log.d("f_global", "Got data from Firestore: " + list);
                binding.tvReloadText.setText("Reload");
                recyclerAdapter.addItems(list);
                recyclerAdapter.reload();

                if (recyclerAdapter.getItemCount() < 1) {
                    binding.linearLayout.setVisibility(View.VISIBLE);
                    binding.btnReload.setVisibility(View.VISIBLE);

                    binding.llReload.setVisibility(View.GONE);
                } else {
                    binding.linearLayout.setVisibility(View.GONE);
                    binding.btnReload.setVisibility(View.GONE);

                    binding.llReload.setVisibility(View.VISIBLE);
                }

                enableButtons();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.tvReloadText.setText("Something goes wrong... Try again");
                enableButtons();
            }
        });
    }

    private void enableButtons() {
        binding.btnReload.setEnabled(true);
        binding.btnReloadSecond.setEnabled(true);
    }

    private void disableButtons() {
        binding.btnReload.setEnabled(false);
        binding.btnReloadSecond.setEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}