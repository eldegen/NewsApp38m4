package com.example.newsapp38m4;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.newsapp38m4.ui.board.BoardAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.newsapp38m4.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NavController navController;
    private String bufferText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            navController.navigate(R.id.loginFragment);
        }

        Prefs prefs = new Prefs(this);
        if (!prefs.isBoardShown()) navController.navigate(R.id.boardFragment);

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (destination.getId() == R.id.boardFragment)
                    getSupportActionBar().hide();
                else
                    getSupportActionBar().show();

                if (destination.getId() == R.id.navigation_home || destination.getId() == R.id.navigation_dashboard || destination.getId() == R.id.navigation_notifications || destination.getId() == R.id.profileFragment) {
                    binding.navView.setVisibility(View.VISIBLE);
                    binding.tvSkipBoard.setVisibility(View.GONE);
                } else {
                    binding.navView.setVisibility(View.GONE);
                    if (destination.getId() != R.id.newsFragment) {
                        binding.tvSkipBoard.setVisibility(View.VISIBLE);
                    }
                }

                if (destination.getId() == R.id.loginFragment) {
                    getSupportActionBar().hide();
                    binding.tvSkipBoard.setVisibility(View.GONE);
                }
            }
        });

        binding.tvSkipBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Prefs(MainActivity.this).saveBoardState();
                navController.navigateUp();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}