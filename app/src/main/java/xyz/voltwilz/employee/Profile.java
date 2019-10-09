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
    DatabaseReference usersRef = mRootRef.child("Users");
    StorageReference profpicStoreRef = FirebaseStorage.getInstance().getReference("Profile_Picture");
    StorageReference delRef = FirebaseStorage.getInstance().getReference();
    String currentUserUID;
    Uri uri;
    UploadTask uploadTask;

    RelativeLayout profileRootLayout;
    LinearLayout profile_wholeLayout;
    ProgressBar profile_progressBar;
    //MaterialBetterSpinner materialBetterSpinner;
    Spinner spinner;
    EditText mName, mAddress, mSalary, mCtcNum1, mCtcNum2, mOrganization,
            mOrgaDetail, mBudget1, mBudget2, mTypeBudget1, mTypeBudget2;
    ImageView profileProfPic;
    String profileName, profileAddress, profileCtcNum1,
            profileCtcNum2, profileOrganization, profileOrgaDetail, profileTypeBudget1, profileTypeBudget2;
    String keyValue, colourRelationValue;
    Integer profileSalary, profileBudget1, profileBudget2;
    ArrayAdapter<String> arrayAdapter;

    Button mBtnSave, mBtnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Edit Employee's Profile");

        String[] colourRelationList = {"Green","Yellow","Blue"};

        profileRootLayout = findViewById(R.id.profile_MainLayout);
        profile_wholeLayout = findViewById(R.id.profile_wholeLayout);
        profile_progressBar = findViewById(R.id.profile_progressBar);
        profileProfPic = findViewById(R.id.profile_profPicture);
        mName = findViewById(R.id.profile_name);
        mAddress = findViewById(R.id.profile_address);
        mSalary = findViewById(R.id.profile_salary);
        mCtcNum1 = findViewById(R.id.profile_contactNum1);
        mCtcNum2 = findViewById(R.id.profile_contactNum2);
        mOrganization = findViewById(R.id.profile_organization);
        mOrgaDetail = findViewById(R.id.profile_orgDetail);
        mBudget1 = findViewById(R.id.profile_budget1);
        mBudget2 = findViewById(R.id.profile_budget2);
        mTypeBudget1 = findViewById(R.id.profile_typeBudget1);
        mTypeBudget2 = findViewById(R.id.profile_typeBudget2);
        mBtnSave = findViewById(R.id.profile_btnSave);
        mBtnDelete = findViewById(R.id.profile_btnDelete);

        arrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, colourRelationList);

        System.out.println("a "+ arrayAdapter.getPosition("Yellow"));

        spinner = findViewById(R.id.profile_colourRelation);
        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                colourRelationValue = adapterView.getItemAtPosition(i).toString();
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

        mAuth = FirebaseAuth.getInstance();
        currentUserUID = mAuth.getInstance().getCurrentUser().getUid();

        System.out.println("lo "+profpicStoreRef);

        readFromDatabase();
        retrieveProfpicFromDatabase();

        profile_wholeLayout.setVisibility(View.INVISIBLE);
        profile_progressBar.setVisibility(View.VISIBLE);

        /*conditionRef.setValue("");
        conditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);
                mName.setText(text);
                System.out.println(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Failed to read");
            }
        });*/
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
        /*if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                profileProfPic.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
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

        profileName = mName.getText().toString();
        profileAddress = mAddress.getText().toString();
        profileCtcNum1 = mCtcNum1.getText().toString();
        profileCtcNum2 = mCtcNum2.getText().toString();
        profileOrganization = mOrganization.getText().toString();
        profileOrgaDetail = mOrgaDetail.getText().toString();
        profileTypeBudget1 = mTypeBudget1.getText().toString();
        profileTypeBudget2 = mTypeBudget2.getText().toString();

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

        if (profileName.equals("")) {
            mName.startAnimation(shake);
            mName.requestFocus();
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
            mTypeBudget1.startAnimation(shake);
            mTypeBudget1.requestFocus();
            validateSucceed = false;
        }

        if (validateSucceed) {
            saveConfirmationMessage();
        }
    }

    private void writeToDatabase(){

        UserProfile userProfile = new UserProfile(profileName, profileAddress, profileCtcNum1,
                profileCtcNum2, profileOrganization, profileOrgaDetail,
                profileTypeBudget1, profileTypeBudget2, profileSalary,
                profileBudget1, profileBudget2, 1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy 'at' HH:mm");
        Date currentDate = new Date(System.currentTimeMillis());
        System.out.println(dateFormat.format(currentDate));
        String currentDateString = dateFormat.format(currentDate);

        /*usersRef.child(currentUserUID)
                .setValue(userProfile, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        System.out.println("Data could not be saved " + databaseError.getMessage());
                    } else {
                        Snackbar snackbar = Snackbar.make(profileRootLayout, "Data has been successfully updated", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
        });*/

        ObjectMapper objectMapper = new ObjectMapper();
        //Map<String, Object> map = objectMapper.convertValue(userProfile, Map.class);
        Map<String, Object> updateBio = new HashMap<>();
        updateBio.put("address", profileAddress);
        updateBio.put("budget1", profileBudget1);
        updateBio.put("budget2", profileBudget2);
        updateBio.put("ctcNum1", profileCtcNum1);
        updateBio.put("ctcNum2", profileCtcNum2);
        updateBio.put("name", profileName);
        updateBio.put("orgDetail", profileOrgaDetail);
        updateBio.put("organization", profileOrganization);
        updateBio.put("colourRelation", colourRelationValue);
        updateBio.put("salary", profileSalary);
        updateBio.put("typeBudget1", profileTypeBudget1);
        updateBio.put("typeBudget2", profileTypeBudget2);
        updateBio.put("date_entry", currentDateString);

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
    }

    private void readFromDatabase() {
        usersRef.child(keyValue).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                System.out.println(dataSnapshot.getValue());
                if (dataSnapshot.getValue() != null) {
                    mName.setText(userProfile.getName());
                    mAddress.setText(userProfile.getAddress());
                    mCtcNum1.setText(userProfile.getCtcNum1());
                    mCtcNum2.setText(userProfile.getCtcNum2());
                    mOrganization.setText(userProfile.getOrganization());
                    mOrgaDetail.setText(userProfile.getOrgDetail());
                    mSalary.setText(userProfile.getSalary().toString());
                    mBudget1.setText(userProfile.getBudget1().toString());
                    mBudget2.setText(userProfile.getBudget2().toString());
                    mTypeBudget1.setText(userProfile.getTypeBudget1());
                    mTypeBudget2.setText(userProfile.getTypeBudget2());

                    System.out.println(userProfile.getColourRelation());
                    System.out.println("b "+ arrayAdapter);
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

}
