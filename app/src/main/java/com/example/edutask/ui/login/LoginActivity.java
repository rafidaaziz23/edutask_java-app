package com.example.edutask.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
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
    private SharedPreferences.Editor editor;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        SharedPreferences sharedPreferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        executorService = Executors.newSingleThreadExecutor();

        // Pastikan DatabaseHelper dipanggil di thread terpisah
        executorService.execute(() -> databaseHelper = DatabaseHelper.getInstance(getApplicationContext()));

        // Inisialisasi Views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        textViewSignUp.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });

        buttonLogin.setOnClickListener(v -> validateLogin());
    }

    private void validateLogin() {
        String username = editTextUsername.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Username dan password tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        executorService.submit(() -> {
            try {
                Log.d("LoginActivity", "Validating user in background thread");

                int isValid = databaseHelper.userDao().validateUser(username, password);

                if (isValid > 0) {
                    String userRole = databaseHelper.userDao().getUserRole(username);

                    runOnUiThread(() -> {
                        saveUserSession(username, userRole);
                        navigateToMain();
                    });
                } else {
                    showToast("Username atau password salah");
                }
            } catch (Exception e) {
                Log.e("LoginActivity", "Error during user validation", e);
                showToast("Terjadi kesalahan saat login");
            }
        });
    }

    private void saveUserSession(String username, String userRole) {
        editor.putString("username", username);
        editor.putString("role", userRole);
        editor.apply();
    }

    private void navigateToMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private void showToast(String message) {
        runOnUiThread(() -> Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
