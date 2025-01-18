package com.example.edutask.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.edutask.R;
import com.example.edutask.data.DatabaseHelper;
import com.example.edutask.MainActivity;
import com.example.edutask.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;
    private TextView textViewSignUp;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Inisialisasi DatabaseHelper
        dbHelper = new DatabaseHelper(this);
        dbHelper.seedLecturer();

        // Inisialisasi Views
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        // Menangani klik pada teks Sign Up
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pindah ke RegisterActivity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        // Menangani klik tombol Login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mengambil data dari EditText
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                // Validasi user login
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Username dan password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isValid = dbHelper.validateUser(username, password);
                    if (isValid) {
                        // Jika login valid, redirect ke MainActivity
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // Menutup LoginActivity agar tidak bisa kembali dengan tombol back
                    } else {
                        // Tampilkan pesan error jika login gagal
                        Toast.makeText(LoginActivity.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
