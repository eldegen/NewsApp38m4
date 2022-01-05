package com.example.newsapp38m4.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.newsapp38m4.App;
import com.example.newsapp38m4.ui.news.NewsFragment;
import com.example.newsapp38m4.ui.news.NewsItemModel;
import com.example.newsapp38m4.ui.news.NewsRecyclerAdapter;
import com.example.newsapp38m4.ui.news.OnItemClickListener;
import com.example.newsapp38m4.R;
import com.example.newsapp38m4.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private NewsRecyclerAdapter recyclerAdapter;
    private NewsItemModel newsItemModel;
    private int pos;

    private boolean sort = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerAdapter = new NewsRecyclerAdapter();

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<NewsItemModel> list = App.getInstance().getDatabase().newsDao().getAll();
        Log.e("f_global", "HomeFragment: List: " + list);
        recyclerAdapter.addItems(list);
//        recyclerAdapter.sortByDate();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFragment(null);
            }
        });

        getParentFragmentManager().setFragmentResultListener("rk.news", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                NewsItemModel newsItemModel = (NewsItemModel) result.getSerializable("news.content");
                recyclerAdapter.addItem(newsItemModel);
                recyclerAdapter.listReload();

                List<NewsItemModel> list = App.getInstance().getDatabase().newsDao().getAll();
                recyclerAdapter.addItems(list);
                recyclerAdapter.sortByDate();
            }
        });
        getParentFragmentManager().setFragmentResultListener("rk.news.update", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                NewsItemModel newsItemModel = (NewsItemModel) result.getSerializable("news.content");
//                recyclerAdapter.editItem(newsItemModel);
                Log.d("f_global", "HomeFragment: got position: " + pos);
                newsItemModel = recyclerAdapter.getItem(pos);
                try {
                    App.getInstance().getDatabase().newsDao().update(newsItemModel);
                } catch (SQLiteConstraintException e) {
                    
                }
                recyclerAdapter.listReload();

                List<NewsItemModel> list = App.getInstance().getDatabase().newsDao().getAll();
                recyclerAdapter.addItems(list);
                recyclerAdapter.sortByDate();
            }
        });

        initList();
        clickChecker();
        binding.btnSorting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sorting();
            }
        });
    }

    // Checks the type of click
    private void clickChecker() {
        recyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onClick(int position) {
                pos = position;
                newsItemModel = recyclerAdapter.getItem(position);
                openFragment(newsItemModel);
            }

            @Override
            public void onLongClick(int position) {
                NewsItemModel newsItemModel = recyclerAdapter.getItem(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Delete?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        recyclerAdapter.deleteItem(position);
                        App.getInstance().getDatabase().newsDao().delete(newsItemModel);
                        Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.show();
            }
        });
    }

    // Articles sorting system
    private void sorting() {
        if(sort) {
            sort = false;
            Log.d("f_global", "HomeFragment: sorting by date");
            binding.btnSorting.setBackgroundResource(R.drawable.ic_sorting_date);
            recyclerAdapter.sortByDate();
        } else {
            sort = true;
            Log.d("f_global", "HomeFragment: sorting by A to Z");
            binding.btnSorting.setBackgroundResource(R.drawable.ic_sorting_alpha);
            recyclerAdapter.sortByAlpha();
        }
    }

    // /////////////////////////////////////////

    private void initList() {
        binding.rvHome.setAdapter(recyclerAdapter);
    }

    private void openFragment(NewsItemModel newsItemModel) {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);

        Bundle bundle = new Bundle();
        bundle.putSerializable("news.edit", newsItemModel);

        navController.navigate(R.id.newsFragment, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}