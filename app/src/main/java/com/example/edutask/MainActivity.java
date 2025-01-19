package com.example.edutask;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.edutask.databinding.ActivityMainBinding;
import com.example.edutask.data.helper.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Seed data untuk memastikan lecturer tersedia
        DatabaseHelper.seedLecturer(this);

        // Setup Bottom Navigation
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Pastikan ID ini sama dengan ID FragmentContainerView di layout
        NavController navController;
        try {
            navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        } catch (IllegalStateException e) {
            Log.e("MainActivity", "NavController tidak ditemukan: " + e.getMessage());
            throw e;
        }

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


    /**
     * Metode untuk setup Bottom Navigation
     */
    private void setupBottomNavigation() {
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Konfigurasi AppBar dengan ID menu utama
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();

        // Menghubungkan Bottom Navigation dengan NavController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }
}
