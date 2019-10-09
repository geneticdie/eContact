package xyz.voltwilz.employee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import xyz.voltwilz.employee.ClassOnly.UserProfile;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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

public class NewEmployee extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private final int PICK_IMAGE_REQUEST = 1;


    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference usersRef = mRootRef.child("Staffs");
    StorageReference profpicStoreRef = FirebaseStorage.getInstance().getReference("Profile_Picture");
    String currentUserUID;
    Uri uri;
    UploadTask uploadTask;

    RelativeLayout newEmployeeRootLayout;
    LinearLayout newEmployee_wholeLayout;
    ProgressBar newEmployee_progressBar;
    MaterialBetterSpinner mColourRelation;
    ArrayAdapter<String> arrayAdapterTypeBudget;
    Spinner spinnerTypeBudget1, spinnerTypeBudget2;
    TextView tv_TypeBudget2;
    EditText mFirstname, mLastname, mNickname, mAddress, mSalary, mCtcNum1, mCtcNum2, mOrganization,
            mOrgaDetail, mBudget1, mBudget2;
    ImageView newEmployeeProfPic;
    String newEmployeeFirstname, newEmployeeLastname, newEmployeeNickname, newEmployeeAddress, newEmployeeCtcNum1,
            newEmployeeCtcNum2, newEmployeeOrganization, newEmployeeOrgaDetail, newEmployeeTypeBudget1, newEmployeeTypeBudget2;
    String newKeyValue, colourRelationValue, typeBudget1Value, typeBudget2Value;
    Integer newEmployeeSalary, newEmployeeBudget1, newEmployeeBudget2;

    Button mBtnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_employee);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("New Employee");

        String[] colourRelationList = {"Green", "Yellow", "Blue"};
        String[] typeBudget = {"Monthly", "Yearly", "Periodic"};

        newEmployeeRootLayout = findViewById(R.id.newEmployee_MainLayout);
        newEmployee_wholeLayout = findViewById(R.id.newEmployee_wholeLayout);
        newEmployee_progressBar = findViewById(R.id.newEmployee_progressBar);
        newEmployeeProfPic = findViewById(R.id.newEmployee_profPicture);
        mFirstname = findViewById(R.id.newEmployee_firstName);
        mLastname = findViewById(R.id.newEmployee_lastName);
        mNickname = findViewById(R.id.newEmployee_nickname);
        mAddress = findViewById(R.id.newEmployee_address);
        mSalary = findViewById(R.id.newEmployee_salary);
        mCtcNum1 = findViewById(R.id.newEmployee_contactNum1);
        mCtcNum2 = findViewById(R.id.newEmployee_contactNum2);
        mOrganization = findViewById(R.id.newEmployee_organization);
        mOrgaDetail = findViewById(R.id.newEmployee_orgDetail);
        mColourRelation = findViewById(R.id.newEmployee_colourRelation);
        mBudget1 = findViewById(R.id.newEmployee_budget1);
        mBudget2 = findViewById(R.id.newEmployee_budget2);
        spinnerTypeBudget1 = findViewById(R.id.newEmployee_SpinnerTypeBudget1);
        spinnerTypeBudget2 = findViewById(R.id.newEmployee_SpinnerTypeBudget2);
        tv_TypeBudget2 = findViewById(R.id.newEmployee_tvTypeBudget2);
        mBtnCreate = findViewById(R.id.newEmployee_btnCreate);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, colourRelationList);

        MaterialBetterSpinner materialBetterSpinner = findViewById(R.id.newEmployee_colourRelation);
        materialBetterSpinner.setAdapter(arrayAdapter);

        materialBetterSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                colourRelationValue = adapterView.getItemAtPosition(i).toString();
            }
        });

        arrayAdapterTypeBudget = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, typeBudget);

        spinnerTypeBudget1.setAdapter(arrayAdapterTypeBudget);
        spinnerTypeBudget2.setAdapter(arrayAdapterTypeBudget);

        spinnerTypeBudget1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeBudget1Value = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                typeBudget1Value = "";
            }
        });

        spinnerTypeBudget2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                typeBudget2Value = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                typeBudget2Value = "";
            }
        });

        tv_TypeBudget2.setVisibility(View.GONE);
        spinnerTypeBudget2.setVisibility(View.GONE);
        mBudget2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mBudget2.getText().toString().isEmpty()) {
                    mBudget2.setText("0");
                    if (Integer.valueOf(mBudget2.getText().toString()) == 0) {
                        tv_TypeBudget2.setVisibility(View.GONE);
                        spinnerTypeBudget2.setVisibility(View.GONE);
                    }
                    typeBudget2Value = "";
                } else {
                    tv_TypeBudget2.setVisibility(View.VISIBLE);
                    spinnerTypeBudget2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        newEmployeeProfPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        mBtnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        colourRelationValue = "";
        newKeyValue = usersRef.push().getKey();

        newEmployee_progressBar.setVisibility(View.INVISIBLE);
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

    private void createConfirmationMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("Do you want to create new employee using this user's data?")
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        writeToDatabase();
                        uploadProfpicToDatabase();
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

    private void validateInput(){
        final Animation shake = AnimationUtils.loadAnimation(NewEmployee.this, R.anim.shake);
        Boolean validateSucceed = true;

        newEmployeeFirstname = mFirstname.getText().toString();
        newEmployeeLastname = mLastname.getText().toString();
        newEmployeeNickname = mNickname.getText().toString();
        newEmployeeAddress = mAddress.getText().toString();
        newEmployeeCtcNum1 = mCtcNum1.getText().toString();
        newEmployeeCtcNum2 = mCtcNum2.getText().toString();
        newEmployeeOrganization = mOrganization.getText().toString();
        newEmployeeOrgaDetail = mOrgaDetail.getText().toString();
        newEmployeeTypeBudget1 = typeBudget1Value;
        newEmployeeTypeBudget2 = typeBudget2Value;

        // Checking if Salary, Budget1, and Budget2 is blank or not
        if (mSalary.getText().toString().equals("")) {
            newEmployeeSalary = 0;
        } else {
            newEmployeeSalary = Integer.valueOf(mSalary.getText().toString());
        }
        if (mBudget1.getText().toString().equals("")) {
            newEmployeeBudget1 = 0;
        } else {
            newEmployeeBudget1 = Integer.valueOf(mBudget1.getText().toString());
        }
        if (mBudget2.getText().toString().equals("")) {
            newEmployeeBudget2 = 0;
        } else {
            newEmployeeBudget2 = Integer.valueOf(mBudget2.getText().toString());
        }

        if (newEmployeeFirstname.equals("")) {
            mFirstname.startAnimation(shake);
            mFirstname.requestFocus();
            validateSucceed = false;
        } else if (newEmployeeNickname.equals("")) {
            mNickname.startAnimation(shake);
            mNickname.requestFocus();
            validateSucceed = false;
        } else if (newEmployeeAddress.equals("")) {
            mAddress.startAnimation(shake);
            mAddress.requestFocus();
            validateSucceed = false;
        } else if (newEmployeeCtcNum1.equals("")) {
            mCtcNum1.startAnimation(shake);
            mCtcNum1.requestFocus();
            validateSucceed = false;
        } else if (newEmployeeOrganization.equals("")) {
            mOrganization.startAnimation(shake);
            mOrganization.requestFocus();
            validateSucceed = false;
        } else if (newEmployeeOrgaDetail.equals("")) {
            mOrgaDetail.startAnimation(shake);
            mOrgaDetail.requestFocus();
            validateSucceed = false;
        } else if (colourRelationValue.equals("")) {
            mColourRelation.startAnimation(shake);
            mColourRelation.requestFocus();
            validateSucceed = false;
        } else if (newEmployeeBudget1.equals("")) {
            mBudget1.startAnimation(shake);
            mBudget1.requestFocus();
            validateSucceed = false;
        } else if (newEmployeeTypeBudget1.equals("")) {
            spinnerTypeBudget1.startAnimation(shake);
            spinnerTypeBudget1.requestFocus();
            validateSucceed = false;
        }

        if (validateSucceed) {
            createConfirmationMessage();
        }
    }

    private void writeToDatabase(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy 'at' HH:mm");
        Date currentDate = new Date(System.currentTimeMillis());
        System.out.println(dateFormat.format(currentDate));
        String currentDateString = dateFormat.format(currentDate);

        ObjectMapper objectMapper = new ObjectMapper();
        //Map<String, Object> map = objectMapper.convertValue(userProfile, Map.class);
        Map<String, Object> updateBio = new HashMap<>();
        updateBio.put("address", newEmployeeAddress);
        updateBio.put("budget1", newEmployeeBudget1);
        updateBio.put("budget2", newEmployeeBudget2);
        updateBio.put("ctcNum1", newEmployeeCtcNum1);
        updateBio.put("ctcNum2", newEmployeeCtcNum2);
        updateBio.put("firstName", newEmployeeFirstname);
        updateBio.put("lastName", newEmployeeLastname);
        updateBio.put("nickname", newEmployeeNickname);
        updateBio.put("orgDetail", newEmployeeOrgaDetail);
        updateBio.put("organization", newEmployeeOrganization);
        updateBio.put("salary", newEmployeeSalary);
        updateBio.put("typeBudget1", newEmployeeTypeBudget1);
        updateBio.put("typeBudget2", newEmployeeTypeBudget2);
        updateBio.put("colourRelation", colourRelationValue);
        updateBio.put("date_entry", currentDateString);

        usersRef.child(newKeyValue).updateChildren(updateBio);
    }
    private void chooseImage() {
        CropImage.startPickImageActivity(NewEmployee.this);
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
                newEmployeeProfPic.setImageURI(uri);
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

    private void uploadProfpicToDatabase() {
        if (uri != null) {

            final StorageReference ref = profpicStoreRef.child(newKeyValue);
            uploadTask = ref.putFile(uri);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            DatabaseReference profpicDBRef = usersRef.child(newKeyValue);
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
}
