package com.example.recouture.StartUpPage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recouture.HomePage.HomepageActivity;
import com.example.recouture.R;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSignIn;
    private EditText editTextUsernmae;
    private EditText getEditTextPassword;
    private TextView textViewSignUp;

    private ActivityIndicator dialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(LoginActivity.this, HomepageActivity.class));

        }

        buttonSignIn = findViewById(R.id.signIn);
        editTextUsernmae = findViewById(R.id.username);
        getEditTextPassword = findViewById(R.id.password);
        textViewSignUp = findViewById(R.id.signUp);

        buttonSignIn.setOnClickListener(this);

        textViewSignUp.setOnClickListener(this);

        dialog = new ActivityIndicator(this);

        ThreeBounce threeBounce = new ThreeBounce();

    }


    @Override
    public void onClick(View view) {
        if (view == buttonSignIn) {
            userLogin();
        }
        if (view == textViewSignUp) {
            finish();
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        }
    }

    private void userLogin() {
        String email = editTextUsernmae.getText().toString().trim();
        String password = getEditTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            // email is empty
            Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        dialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            // start profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
                        }
                    }
                });
    }
}
