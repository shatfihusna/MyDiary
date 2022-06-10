package com.shatfi.mydiary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.shatfi.mydiary.dashboard.Dashboard;
import com.shatfi.mydiary.databinding.ActivitySigninBinding;

public class Signin extends AppCompatActivity {
    ActivitySigninBinding binding;
    FirebaseAuth auth;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySigninBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        auth = FirebaseAuth.getInstance();
        preferences = getSharedPreferences("diaryapp",MODE_PRIVATE);
        editor = preferences.edit();

        binding.sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.email.getText().toString();
                String password = binding.password.getText().toString();
               storeLoginUser(email, password);
            }
        });

        binding.reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signin.this, Forgot.class));
            }
        });

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Signin.this, Signup.class));
            }
        });
    }
    private  void storeLoginUser(String email, String password){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    editor.putBoolean("autologin", true);
                    editor.apply();
                    startActivity(new Intent(Signin.this, Dashboard.class));
                    Toast.makeText(Signin.this,"Selamat datang", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        });
    }
}
