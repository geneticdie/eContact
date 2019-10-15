package xyz.voltwilz.employee;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import xyz.voltwilz.employee.ClassOnly.UserProfile;

import static xyz.voltwilz.employee.FragmentManageUser.EXTRA_KEYVALUE;

public class Profile extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private final int PICK_IMAGE_REQUEST = 1;


    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference usersRef = mRootRef.child("Staffs");
    DatabaseReference budgetRef = mRootRef.child("Budget");
    StorageReference profpicStoreRef = FirebaseStorage.getInstance().getReference("Profile_Picture");
    StorageReference delRef = FirebaseStorage.getInstance().getReference();
    String currentUserUID;
    Uri uri;
    UploadTask uploadTask;

    RelativeLayout profileRootLayout;
    LinearLayout profile_wholeLayout;
    ProgressBar profile_progressBar;
    //MaterialBetterSpinner materialBetterSpinner;
    Spinner spinner, spinnerTypeBudget1, spinnerTypeBudget2;
    TextView tv_TypeBudget2;
    EditText mFirstname, mLastname, mNickname, mAddress, mSalary, mCtcNum1, mCtcNum2, mOrganization,
            mOrgaDetail, mBudget1, mBudget2;
    ImageView profileProfPic;
    String profileFirstName, profileLastName, profileNickname, profileAddress, profileCtcNum1,
            profileCtcNum2, profileOrganization, profileOrgaDetail, profileTypeBudget1, profileTypeBudget2;
    String keyValue, colourRelationValue, typeBudget1Value, typeBudget2Value, currentYearString, currentDateString;
    Integer profileSalary, profileBudget1, profileBudget2;
    ArrayAdapter<String> arrayAdapter, arrayAdapterTypeBudget;

    Button mBtnSave, mBtnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Edit Staff's Profile");

        String[] colourRelationList = {"Green", "Yellow", "Blue"};
        String[] typeBudget = {"Monthly", "Yearly", "Periodic"};

        profileRootLayout = findViewById(R.id.profile_MainLayout);
        profile_wholeLayout = findViewById(R.id.profile_wholeLayout);
        profile_progressBar = findViewById(R.id.profile_progressBar);
        profileProfPic = findViewById(R.id.profile_profPicture);
        mFirstname = findViewById(R.id.profile_firstName);
        mLastname = findViewById(R.id.profile_lastName);
        mNickname = findViewById(R.id.profile_nickname);
        mAddress = findViewById(R.id.profile_address);
        mSalary = findViewById(R.id.profile_salary);
        mCtcNum1 = findViewById(R.id.profile_contactNum1);
        mCtcNum2 = findViewById(R.id.profile_contactNum2);
        mOrganization = findViewById(R.id.profile_organization);
        mOrgaDetail = findViewById(R.id.profile_orgDetail);
        mBudget1 = findViewById(R.id.profile_budget1);
        mBudget2 = findViewById(R.id.profile_budget2);
        spinnerTypeBudget1 = findViewById(R.id.profile_SpinnerTypeBudget1);
        spinnerTypeBudget2 = findViewById(R.id.profile_SpinnerTypeBudget2);
        tv_TypeBudget2 = findViewById(R.id.profile_tvTypeBudget2);
        mBtnSave = findViewById(R.id.profile_btnSave);
        mBtnDelete = findViewById(R.id.profile_btnDelete);

        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, colourRelationList);
        arrayAdapterTypeBudget = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, typeBudget);

        System.out.println("a "+ arrayAdapter.getPosition("Yellow"));

        spinner = findViewById(R.id.profile_colourRelation);
        spinner.setAdapter(arrayAdapter);
        spinnerTypeBudget1.setAdapter(arrayAdapterTypeBudget);
        spinnerTypeBudget2.setAdapter(arrayAdapterTypeBudget);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                colourRelationValue = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerTypeBudget1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeBudget1Value = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerTypeBudget2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeBudget2Value = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Intent intent = getIntent();
        keyValue = intent.getStringExtra(EXTRA_KEYVALUE);

        //Toast.makeText(this, currentUser.getUid() , Toast.LENGTH_SHORT).show();

        profileProfPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        mBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput();
            }
        });

        mBtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteConfirmationMessage();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void deleteConfirmationMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("WARNING")
                .setMessage("All personal data about this person will be permanently deleted. Are you sure?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        usersRef.child(keyValue).setValue(null);
                        budgetRef.child(currentYearString).child(keyValue).setValue(null);
                        profpicStoreRef.child(keyValue).delete();
                        onBackPressed();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void saveConfirmationMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("Do you want to save this user's data?")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        writeToDatabase();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        setCurrentDate();
        mAuth = FirebaseAuth.getInstance();
        currentUserUID = mAuth.getInstance().getCurrentUser().getUid();

        typeBudget2Value = "";

        mBudget2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mBudget2.getText().toString().isEmpty()) {
                    mBudget2.setText("0");
                    typeBudget2Value = "";
                    System.out.println("kosong");
                } else if (mBudget2.getText().toString().equals("0")) {
                    tv_TypeBudget2.setVisibility(View.GONE);
                    spinnerTypeBudget2.setVisibility(View.GONE);
                } else  {
                    tv_TypeBudget2.setVisibility(View.VISIBLE);
                    spinnerTypeBudget2.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        System.out.println("lo "+profpicStoreRef);

        readFromDatabase();
        retrieveProfpicFromDatabase();

        profile_wholeLayout.setVisibility(View.INVISIBLE);
        profile_progressBar.setVisibility(View.VISIBLE);
    }

    private void chooseImage() {
        CropImage.startPickImageActivity(Profile.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE
        && resultCode == Activity.RESULT_OK) {
            Uri imageURI = CropImage.getPickImageResultUri(this, data);
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageURI)) {
                uri = imageURI;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                startCrop(imageURI);
                System.out.println("Masuk startCrop");
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uri = result.getUri();
                profileProfPic.setImageURI(uri);
                uploadProfpicToDatabase();
            }
        }
    }

    private void startCrop(Uri imageURI) {
        CropImage.activity(imageURI)
                .setAspectRatio(1,1)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);

    }

    private void validateInput(){
        final Animation shake = AnimationUtils.loadAnimation(Profile.this, R.anim.shake);
        Boolean validateSucceed = true;

        profileFirstName = mFirstname.getText().toString();
        profileLastName = mLastname.getText().toString();
        profileNickname = mNickname.getText().toString();
        profileAddress = mAddress.getText().toString();
        profileCtcNum1 = mCtcNum1.getText().toString();
        profileCtcNum2 = mCtcNum2.getText().toString();
        profileOrganization = mOrganization.getText().toString();
        profileOrgaDetail = mOrgaDetail.getText().toString();
        profileTypeBudget1 = typeBudget1Value;
        profileTypeBudget2 = typeBudget2Value;

        // Checking if Salary, Budget1, and Budget2 is blank or not
        if (mSalary.getText().toString().equals("")) {
            profileSalary = 0;
        } else {
            profileSalary = Integer.valueOf(mSalary.getText().toString());
        }
        if (mBudget1.getText().toString().equals("")) {
            profileBudget1 = 0;
        } else {
            profileBudget1 = Integer.valueOf(mBudget1.getText().toString());
        }
        if (mBudget2.getText().toString().equals("")) {
            profileBudget2 = 0;
        } else {
            profileBudget2 = Integer.valueOf(mBudget2.getText().toString());
        }

        if (profileFirstName.equals("")) {
            mFirstname.startAnimation(shake);
            mFirstname.requestFocus();
            validateSucceed = false;
        } else if (profileNickname.equals("")) {
            mNickname.startAnimation(shake);
            mNickname.requestFocus();
            validateSucceed = false;
        } else if (profileAddress.equals("")) {
            mAddress.startAnimation(shake);
            mAddress.requestFocus();
            validateSucceed = false;
        } else if (profileCtcNum1.equals("")) {
            mCtcNum1.startAnimation(shake);
            mCtcNum1.requestFocus();
            validateSucceed = false;
        } else if (profileOrganization.equals("")) {
            mOrganization.startAnimation(shake);
            mOrganization.requestFocus();
            validateSucceed = false;
        } else if (profileOrgaDetail.equals("")) {
            mOrgaDetail.startAnimation(shake);
            mOrgaDetail.requestFocus();
            validateSucceed = false;
        } else if (profileBudget1.equals("")) {
            mBudget1.startAnimation(shake);
            mBudget1.requestFocus();
            validateSucceed = false;
        } else if (profileTypeBudget1.equals("")) {
            spinnerTypeBudget1.startAnimation(shake);
            spinnerTypeBudget1.requestFocus();
            validateSucceed = false;
        }

        if (validateSucceed) {
            saveConfirmationMessage();
        }
    }

    private void writeToDatabase(){

        // Refresh current time
        setCurrentDate();

        Map<String, Object> updateBio = new HashMap<>();
        Map<String, Object> budgetInfo = new HashMap<>();

        updateBio.put("address", profileAddress);
        updateBio.put("ctcNum1", profileCtcNum1);
        updateBio.put("ctcNum2", profileCtcNum2);
        updateBio.put("firstName", profileFirstName);
        updateBio.put("lastName", profileLastName);
        updateBio.put("nickname", profileNickname);
        updateBio.put("orgDetail", profileOrgaDetail);
        updateBio.put("organization", profileOrganization);
        updateBio.put("colourRelation", colourRelationValue);
        updateBio.put("salary", profileSalary);
        updateBio.put("date_entry", currentDateString);

        budgetInfo.put("budget1", profileBudget1);
        budgetInfo.put("budget2", profileBudget2);
        budgetInfo.put("typeBudget1", profileTypeBudget1);
        budgetInfo.put("typeBudget2", profileTypeBudget2);

        usersRef.child(keyValue).updateChildren(updateBio, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    System.out.println("Data could not be saved " + databaseError.getMessage());
                } else {
                    Toast.makeText(Profile.this, "Data has been successfully updated", Toast.LENGTH_LONG).show();
                    onBackPressed();
                }
            }
        });

        budgetRef.child(currentYearString).child(keyValue).updateChildren(budgetInfo);
    }

    private void readFromDatabase() {

        usersRef.child(keyValue).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                System.out.println(dataSnapshot.getValue());
                if (dataSnapshot.getValue() != null) {
                    mFirstname.setText(userProfile.getFirstName());
                    mLastname.setText(userProfile.getLastName());
                    mNickname.setText(userProfile.getNickname());
                    mAddress.setText(userProfile.getAddress());
                    mCtcNum1.setText(userProfile.getCtcNum1());
                    mCtcNum2.setText(userProfile.getCtcNum2());
                    mOrganization.setText(userProfile.getOrganization());
                    mOrgaDetail.setText(userProfile.getOrgDetail());
                    mSalary.setText(userProfile.getSalary().toString());

                    if (userProfile.getColourRelation().equals("Green")) {
                        spinner.setSelection(arrayAdapter.getPosition("Green"));
                    } else if (userProfile.getColourRelation().equals("Yellow")) {
                        spinner.setSelection(arrayAdapter.getPosition("Yellow"));
                    } else if (userProfile.getColourRelation().equals("Blue")) {
                        spinner.setSelection(arrayAdapter.getPosition("Blue"));
                    }

                }
                profile_progressBar.setVisibility(View.INVISIBLE);
                profile_wholeLayout.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        budgetRef.child(currentYearString).child(keyValue).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile budgets = dataSnapshot.getValue(UserProfile.class);
                if (dataSnapshot.getValue() != null) {
                    mBudget1.setText(budgets.getBudget1().toString());
                    mBudget2.setText(budgets.getBudget2().toString());

                    System.out.println(budgets.getTypeBudget1());
                    if (budgets.getTypeBudget1().equals("Monthly")) {
                        spinnerTypeBudget1.setSelection(arrayAdapterTypeBudget.getPosition("Monthly"));
                    } else if (budgets.getTypeBudget1().equals("Yearly")) {
                        spinnerTypeBudget1.setSelection(arrayAdapterTypeBudget.getPosition("Yearly"));
                    } else if (budgets.getTypeBudget1().equals("Periodic")) {
                        spinnerTypeBudget1.setSelection(arrayAdapterTypeBudget.getPosition("Periodic"));
                    }

                    if (budgets.getTypeBudget2().isEmpty()) {
                        tv_TypeBudget2.setVisibility(View.GONE);
                        spinnerTypeBudget2.setVisibility(View.GONE);
                    } else {
                        if (budgets.getTypeBudget2().equals("Monthly")) {
                            spinnerTypeBudget2.setSelection(arrayAdapterTypeBudget.getPosition("Monthly"));
                        } else if (budgets.getTypeBudget2().equals("Yearly")) {
                            spinnerTypeBudget2.setSelection(arrayAdapterTypeBudget.getPosition("Yearly"));
                        } else if (budgets.getTypeBudget2().equals("Periodic")) {
                            spinnerTypeBudget2.setSelection(arrayAdapterTypeBudget.getPosition("Periodic"));
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getFileExtension (Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadProfpicToDatabase() {
        if (uri != null) {

            final StorageReference ref = profpicStoreRef.child(keyValue);
            uploadTask = ref.putFile(uri);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            DatabaseReference profpicDBRef = usersRef.child(keyValue);
                            Map<String, Object> updates = new HashMap<String, Object>();
                            updates.put("profPicUrl", task.getResult().toString());
                            profpicDBRef.updateChildren(updates);
                            System.out.println("ini updates " + updates);
                        }
                    });
                }
            });

            /*Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return profpicStoreRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        System.out.println("onComplete URI: " + downloadUri.toString());
                    }
                }
            });*/

            /*profpicStoreRef.child(currentUserUID)
                    .putFile(uri)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            profpicStoreRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    System.out.println("Ini dia: " + uri);
                                }
                            });

                            DatabaseReference profpicDBRef = usersRef.child(currentUserUID);
                            Map<String, Object> updates = new HashMap<String, Object>();
                            updates.put("profPicUrl", task.getResult().toString());
                            profpicDBRef.updateChildren(updates);
                            System.out.println("DownloadURL: " + profpicStoreRef.getDownloadUrl().toString());

                            Task<Uri> uriTask = task.getResult().getStorage().getDownloadUrl();
                            System.out.println("Tes 2" + uriTask);
                        }
                    })
                    .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return profpicStoreRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                System.out.println("onComplete URI: " + downloadUri.toString());
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("Failed to upload Profile Picture");
                        }
                    });*/
        }
    }

    private void retrieveProfpicFromDatabase() {
        DatabaseReference profpicDBRef = usersRef.child(keyValue);
        profpicDBRef.child("profPicUrl").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String imageURL = dataSnapshot.getValue(String.class);

                System.out.println("Ambil gambar dari DB");
                Glide.with(Profile.this)
                        .load(imageURL)
                        .placeholder(R.drawable.boy)
                        .into(profileProfPic);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy 'at' HH:mm");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        Date currentDate = new Date(System.currentTimeMillis());
        currentDateString = dateFormat.format(currentDate);
        currentYearString = yearFormat.format(currentDate);
    }
}
