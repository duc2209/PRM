package com.example.quizzz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    private EditText email,pass;
    private Button loginB;
    private TextView forgotPassB,singupB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        loginB = findViewById(R.id.loginB);
        forgotPassB = findViewById(R.id.forgot_pass);
        singupB = findViewById(R.id.singupB);

        loginB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData()){
                    login();
                }
            }
        });

        singupB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateData(){
      if(email.getText().toString().isEmpty()){
          email.setError("Enter E-Mail Id");
          return false;
      }
      if(pass.getText().toString().isEmpty()){
          pass.setError("Enter Password");
          return false;
      }
      return true;
    }

    private void login(){
        String enteredEmail = email.getText().toString();
        String enteredPassword = pass.getText().toString();

        if (isLoginSuccessful(enteredEmail, enteredPassword)) {
            // Đăng nhập thành công, điều hướng đến trang MainActivity
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            showLoginFailedDialog();
        }
    }

    private boolean isLoginSuccessful(String email, String password) {
        return "admin".equals(email) && "admin".equals(password);
    }

    private void showLoginFailedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đăng nhập thất bại")
                .setMessage("Sai email hoặc mật khẩu. Vui lòng thử lại.")
                .setPositiveButton("OK", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }




}