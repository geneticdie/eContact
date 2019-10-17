package xyz.voltwilz.employee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import xyz.voltwilz.employee.ClassOnly.UserProfile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignUpPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference usersRef = mRootRef.child("Users");

    String currentUID = "";

    TextView mEmail, mPassword, mConfirmPass, mToSignIn;
    TextInputLayout TIL_email, TIL_password, TIL_passConfirm;
    Button btn_SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        mAuth = FirebaseAuth.getInstance();

        mEmail = findViewById(R.id.signUp_email);
        mPassword = findViewById(R.id.signUp_password);
        mConfirmPass = findViewById(R.id.signUp_passwordConfirm);
        TIL_email = findViewById(R.id.signUp_TIL_email);
        TIL_password = findViewById(R.id.signUp_TIL_password);
        TIL_passConfirm = findViewById(R.id.signUp_TIL_passConfirm);
        mToSignIn = findViewById(R.id.tv_goToSignIn);
        btn_SignUp = findViewById(R.id.signUp_btnSignUp);

        mToSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpPage.this, LoginPage.class);
                startActivity(intent);
            }
        });

        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Animation shake = AnimationUtils.loadAnimation(SignUpPage.this, R.anim.shake);
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String confirmPass = mConfirmPass.getText().toString().trim();

                if (email.equals("")) {
                    mEmail.startAnimation(shake);
                    TIL_email.startAnimation(shake);
                    mEmail.requestFocus();
                } else if (password.length() < 6) {
                    mPassword.startAnimation(shake);
                    TIL_password.startAnimation(shake);
                    mPassword.requestFocus();
                    Toast.makeText(SignUpPage.this, "Minimum length for Password is 6 characters", Toast.LENGTH_LONG).show();
                } else if (confirmPass.equals("")) {
                    mConfirmPass.startAnimation(shake);
                    TIL_passConfirm.startAnimation(shake);
                    mConfirmPass.requestFocus();
                } else {
                    if (password.equals(confirmPass)) {
                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(SignUpPage.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information

                                            FirebaseUser user = mAuth.getCurrentUser();
                                            user.sendEmailVerification()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpPage.this);
                                                                builder.setMessage("Please verified your account via link that sent to your email").setTitle("Email Verification");
                                                                builder.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        currentUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                                        createNewBiodata();
                                                                        mAuth.signOut();
                                                                        Intent intent = new Intent(SignUpPage.this, LoginPage.class);
                                                                        startActivity(intent);
                                                                    }
                                                                });
                                                                AlertDialog dialog = builder.create();
                                                                dialog.show();
                                                            }
                                                        }
                                                    });
                                        } else {
                                            // If sign in fails, display a message to the user.

                                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();

                                        }

                                        // ...
                                    }
                                });
                    } else {
                        Toast.makeText(SignUpPage.this, "Password Confirmation didn't match the Password you inputed", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void createNewBiodata() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date currentDate = new Date();

        UserProfile userProfile = new UserProfile("", "", "", "", "", "", "",
                "", "", "",
                "", "", 0,
                0, 0, 1, dateFormat.format(currentDate), "");

        usersRef.child(currentUID)
                .setValue(userProfile, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            //System.out.println("Data could not be saved " + databaseError.getMessage());
                        } else {
                            //Snackbar snackbar = Snackbar.make(profileRootLayout, "Data has been successfully updated", Snackbar.LENGTH_LONG);
                            //snackbar.show();
                        }
                    }
                });

        System.out.println("Masok sini");

    }

    @Override
    protected void onStart() {
        super.onStart();

        currentUID = "";
    }
}
