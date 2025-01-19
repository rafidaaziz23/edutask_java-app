package com.example.edutask.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.edutask.MainActivity;
import com.example.edutask.R;
import com.example.edutask.data.helper.DatabaseHelper;
import com.example.edutask.ui.register.RegisterActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView textViewSignUp;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        deleteDatabase("education.db");
        // Inisialisasi ExecutorService untuk operasi database
        executorService = Executors.newSingleThreadExecutor();

        // Inisialisasi Views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        // Menangani klik pada teks Sign Up
        textViewSignUp.setOnClickListener(v -> {
            // Pindah ke RegisterActivity
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Menangani klik tombol Login
        buttonLogin.setOnClickListener(v -> {
            // Mengambil data dari EditText
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Validasi user login
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Username dan password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                // Lakukan validasi di thread terpisah
                executorService.execute(() -> {
                    Log.d("LoginActivity", "Validating user in background thread");
                    DatabaseHelper.seedLecturer(LoginActivity.this);
                    int isValid = DatabaseHelper.getInstance(LoginActivity.this).userDao().validateUser(username, password);

                    Log.d("LoginActivity", "User validation completed: " + isValid);
                    runOnUiThread(() -> {
                        if (isValid > 0) {
                            // If valid
                            Log.d("LoginActivity", "Login successful");
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If invalid
                            Log.d("LoginActivity", "Login failed");
                            Toast.makeText(LoginActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                        }
                    });
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown(); // Tutup executor untuk mencegah kebocoran memori
    }
}
