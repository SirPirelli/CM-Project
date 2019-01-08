package goldenegg.detectivephiladelphia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.ProviderQueryResult;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edtPassword;
    private EditText edtEmail;
    private Button btnSignup;
    private Button btnResetPasword;
    private Button btnLogin;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        progressDialog = new ProgressDialog(this);
        edtPassword = findViewById(R.id.edPassword);
        edtEmail = findViewById(R.id.edEmail);
        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(this);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnResetPasword = findViewById(R.id.btnresetPassword);
        btnResetPasword.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null)
        {
            String s = user.getEmail();

            firebaseAuth.fetchSignInMethodsForEmail(s).addOnCompleteListener(this, new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                    if(task.isSuccessful())
                    {
                        SignInMethodQueryResult result = task.getResult();

                        if(result.getSignInMethods().size() == 0)
                        {
                            Toast.makeText(LoginActivity.this, "Account not registered.", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "User already logged in.", Toast.LENGTH_LONG).show();
                            Intent startMenu = new Intent(LoginActivity.this,Menu.class);
                            startActivity(startMenu);
                            finish();
                        }
                    }
                }
            });



        }
    }


    @Override
    public void onClick(View v)
    {

        int id = v.getId();

        switch (id)
        {
            case R.id.btnSignup:
                RegisterUser();
                break;

            case R.id.btnLogin:
                LoginUser();
                break;

            case R.id.btnresetPassword:
                Intent startMedia = new Intent(this,ResetPassword.class);
                startActivity(startMedia);
                break;
        }
    }

    private void RegisterUser() {

        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email is empty", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password is empty", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Registering User ");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,"User registered",Toast.LENGTH_LONG).show();
                    Intent startMenu = new Intent(LoginActivity.this,Menu.class);
                    startActivity(startMenu);
                    finish();
                }
                else {

                    Toast.makeText(LoginActivity.this,"User was unable to register",Toast.LENGTH_LONG).show();
                }
                progressDialog.cancel();
            }
        });

    }

    private void LoginUser() {

        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email is empty", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password is empty", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Logging in ");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success
                            progressDialog.cancel();
                            Toast.makeText(LoginActivity.this, "Logged In",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, Menu.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            progressDialog.cancel();
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginActivity.this, "Log In Failed",
                                Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }
}
