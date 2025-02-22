package com.example.edutask;

import android.app.AlertDialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;

import com.example.edutask.broadcast.NetworkChangeReceiver;
import com.example.edutask.data.helper.DatabaseHelper;
import com.example.edutask.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private NetworkChangeReceiver networkChangeReceiver;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Seed data untuk memastikan lecturer tersedia
        DatabaseHelper.seedLecturer(this);

        // Setup Bottom Navigation
        setupBottomNavigation();

        // Inisialisasi BroadcastReceiver untuk mendeteksi koneksi internet
        networkChangeReceiver = new NetworkChangeReceiver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChangeReceiver);
    }

    // menampilkan pop up koneksi
    public void showNoInternetDialog() {
        if (alertDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Koneksi Terputus")
                    .setMessage("Tidak ada koneksi internet. Periksa jaringan Anda.")
                    .setCancelable(false)
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

            alertDialog = builder.create();
        }

        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
    }

   // disable pop up
    public void dismissNoInternetDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    /**
     * Metode untuk setup Bottom Navigation
     */
    private void setupBottomNavigation() {
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController;
        try {
            navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        } catch (IllegalStateException e) {
            Log.e("MainActivity", "NavController tidak ditemukan: " + e.getMessage());
            throw e;
        }

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }
}
