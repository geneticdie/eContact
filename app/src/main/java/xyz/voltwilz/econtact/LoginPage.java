package xyz.voltwilz.econtact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginPage extends AppCompatActivity {
    private FirebaseAuth mAuth;

    DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();

    EditText mEmail, mPassword;
    TextView mCreateAccount;
    TextInputLayout textInputLayoutEmail, textInputLayoutPassword;
    Button btn_SignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(new Intent(LoginPage.this, MainApp.class));
            finish();
        }

        mEmail = findViewById(R.id.signIn_Email);
        mPassword = findViewById(R.id.signIn_Password);
        btn_SignIn = findViewById(R.id.signIn_btnSignIn);
        mCreateAccount = findViewById(R.id.signIn_CreateAccount);
        textInputLayoutEmail = findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = findViewById(R.id.textInputLayoutPassword);

        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginPage.this, SignUpPage.class);
                startActivity(intent);
            }
        });

        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation shake = AnimationUtils.loadAnimation(LoginPage.this, R.anim.shake);
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();

                if (email.equals("")) {
                    mEmail.startAnimation(shake);
                    textInputLayoutEmail.startAnimation(shake);
                    mEmail.requestFocus();
                } else if (password.equals("")) {
                    mPassword.startAnimation(shake);
                    textInputLayoutPassword.startAnimation(shake);
                    mPassword.requestFocus();
                } else {
                    mAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(LoginPage.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Boolean status = mAuth.getCurrentUser().isEmailVerified();

                                        // Go to CurrentLocation

                                        if (status.equals(true)) {
                                            Intent intent = new Intent(LoginPage.this, MainApp.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Toast.makeText(LoginPage.this, "Please verified your email.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                        Toast.makeText(LoginPage.this, "Password Wrong.", Toast.LENGTH_LONG).show();
                                        mPassword.startAnimation(shake);
                                        textInputLayoutPassword.startAnimation(shake);
                                        mPassword.setText("");
                                        mPassword.requestFocus();
                                    }
                                    else if (e instanceof FirebaseAuthInvalidUserException) {
                                        Toast.makeText(LoginPage.this, "Email Wrong.", Toast.LENGTH_LONG).show();
                                        mEmail.startAnimation(shake);
                                        textInputLayoutEmail.startAnimation(shake);
                                        mEmail.setText("");
                                        mEmail.requestFocus();
                                    }
                                }
                            });
                }
            }
        });
    }
}
