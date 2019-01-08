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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ResetPassword extends AppCompatActivity  implements View.OnClickListener{



        private EditText edtEmail;
        private Button btnSendPassword;
        private Button btnBackLogin;
        private FirebaseAuth firebaseAuth;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reset_password);
            firebaseAuth = FirebaseAuth.getInstance();


            edtEmail = findViewById(R.id.edEmailReset);
            btnSendPassword = findViewById(R.id.buttonSend);
            btnSendPassword.setOnClickListener(this);

        }




        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.buttonSend){
                String email = edtEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter your email!", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPassword.this, "Check email to reset your password!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ResetPassword.this, "Check internet connection or email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }



        }
    }


