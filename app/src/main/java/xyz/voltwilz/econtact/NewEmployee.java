package xyz.voltwilz.econtact;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import me.abhinay.input.CurrencyEditText;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NewEmployee extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private final int PICK_IMAGE_REQUEST = 1;

    Locale localeID = new Locale("in", "ID");

    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference usersRef = mRootRef.child("Staffs");
    DatabaseReference budgetRef = mRootRef.child("Budget");
    DatabaseReference masterRef = mRootRef.child("Master");
    DatabaseReference organizationRef = masterRef.child("Organization");
    DatabaseReference colourRef = masterRef.child("Colour");
    DatabaseReference relationshipRef = masterRef.child("Relationship");
    DatabaseReference titleRef = masterRef.child("Title");
    StorageReference profpicStoreRef = FirebaseStorage.getInstance().getReference("Profile_Picture");
    String currentUserUID;
    Uri uri;
    UploadTask uploadTask;

    RelativeLayout newEmployeeRootLayout;
    LinearLayout newEmployee_wholeLayout, newEmployee_layoutOrganization, newEmployee_layoutTitleOrg,
            newEmployee_layoutColourRelation;
    ProgressBar newEmployee_progressBar;
    ArrayAdapter<String> arrayAdapterTypeBudget1, arrayAdapterTypeBudget2;
    Spinner spinnerTypeBudget1, spinnerTypeBudget2;
    SpinnerDialog spinnerDialogOrganization, spinnerDialogColour, spinnerDialogTitle, spinnerDialogRelationship;
    TextView tv_TypeBudget1, tv_TypeBudget2, tv_workInfo;
    TextView mColourRelation, mOrganization, mTitleOrg;
    EditText mFirstname, mLastname, mNickname, mAddress, mCtcNum1, mCtcNum2,
            mOrgaDetail, mHobbies;
    CurrencyEditText mBudget1, mBudget2, mSalary;
    ImageView newEmployeeProfPic;
    String newEmployeeFirstname, newEmployeeLastname, newEmployeeNickname, newEmployeeAddress, newEmployeeCtcNum1, newEmployeeColourRelation,
            newEmployeeCtcNum2, newEmployeeOrganization, newEmployeeTitleOrg , newEmployeeOrgaDetail, newEmployeeTypeBudget1, newEmployeeTypeBudget2, newEmployeeHobbies;
    String newKeyValue, colourRelationValue, typeBudget1Value, typeBudget2Value, currentDateString, currentYearString;
    Integer newEmployeeSalary, newEmployeeBudget1, newEmployeeBudget2;
    Double valSalary = 0.0, valBudgetMonthly = 0.0, valBudgetYearly = 0.0;
    Boolean filledOrganization, filledTitleOrg, filledColourRelation;

    Button mBtnCreate;

    ArrayList<String> listOrganization = new ArrayList<>();
    ArrayList<String> listTitle = new ArrayList<>();
    ArrayList<String> listColour = new ArrayList<>();
    ArrayList<String> listRelationship = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_employee);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("New Employee");

        String[] colourRelationList = {"Green", "Yellow", "Blue"};
        String[] typeBudget = {"Monthly", "Yearly", "Periodic"};
        String[] typeBudgetMonthly = {"Monthly"};
        String[] typeBudgetYearly = {"Yearly"};


        mSalary = findViewById(R.id.newEmployee_salary);
        mSalary.setCurrency("Rp");
        mSalary.setSeparator(".");
        mSalary.setDecimals(false);

        mBudget1 = findViewById(R.id.newEmployee_budget1);
        mBudget1.setCurrency("Rp");
        mBudget1.setSeparator(".");
        mBudget1.setDecimals(false);


        mBudget2 = findViewById(R.id.newEmployee_budget2);
        mBudget2.setCurrency("Rp");
        mBudget2.setSeparator(".");
        mBudget2.setDecimals(false);

        newEmployeeRootLayout = findViewById(R.id.newEmployee_MainLayout);
        newEmployee_wholeLayout = findViewById(R.id.newEmployee_wholeLayout);
        newEmployee_layoutOrganization = findViewById(R.id.newEmployee_layoutOrganization);
        newEmployee_layoutColourRelation = findViewById(R.id.newEmployee_layoutColour);
        newEmployee_layoutTitleOrg = findViewById(R.id.newEmployee_layoutTitle);
        newEmployee_progressBar = findViewById(R.id.newEmployee_progressBar);
        newEmployeeProfPic = findViewById(R.id.newEmployee_profPicture);
        mFirstname = findViewById(R.id.newEmployee_firstName);
        mLastname = findViewById(R.id.newEmployee_lastName);
        mNickname = findViewById(R.id.newEmployee_nickname);
        mAddress = findViewById(R.id.newEmployee_address);
        mHobbies = findViewById(R.id.newEmployee_hobbies);
        mCtcNum1 = findViewById(R.id.newEmployee_contactNum1);
        mCtcNum2 = findViewById(R.id.newEmployee_contactNum2);
        mOrganization = findViewById(R.id.newEmployee_organization);
        mOrgaDetail = findViewById(R.id.newEmployee_orgDetail);
        mTitleOrg = findViewById(R.id.newEmployee_TitleOrganization);
        mColourRelation = findViewById(R.id.newEmployee_colourRelation);
        spinnerTypeBudget1 = findViewById(R.id.newEmployee_SpinnerTypeBudget1);
        spinnerTypeBudget2 = findViewById(R.id.newEmployee_SpinnerTypeBudget2);
        tv_workInfo = findViewById(R.id.newEmployee_workInfo);
        tv_TypeBudget1 = findViewById(R.id.newEmployee_tvTypeBudget1);
        tv_TypeBudget2 = findViewById(R.id.newEmployee_tvTypeBudget2);
        mBtnCreate = findViewById(R.id.newEmployee_btnCreate);

        tv_TypeBudget1.setVisibility(View.GONE);
        tv_TypeBudget2.setVisibility(View.GONE);

        spinnerDialogOrganization = new SpinnerDialog(NewEmployee.this, listOrganization, "Search Organization", R.style.DialogAnimations_SmileWindow , "Close");
        spinnerDialogOrganization.setCancellable(true);
        spinnerDialogOrganization.setShowKeyboard(false);
        spinnerDialogOrganization.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                mOrganization.setText(item);
                filledOrganization = true;
            }
        });

        spinnerDialogColour = new SpinnerDialog(NewEmployee.this, listColour, "Search Colour", R.style.DialogAnimations_SmileWindow , "Close");
        spinnerDialogColour.setCancellable(true);
        spinnerDialogColour.setShowKeyboard(false);
        spinnerDialogColour.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                mColourRelation.setText(item);
                filledColourRelation = true;
            }
        });

        spinnerDialogTitle= new SpinnerDialog(NewEmployee.this, listTitle, "Search Title Organization", R.style.DialogAnimations_SmileWindow , "Close");
        spinnerDialogTitle.setCancellable(true);
        spinnerDialogTitle.setShowKeyboard(false);
        spinnerDialogTitle.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                mTitleOrg.setText(item);
                filledTitleOrg = true;
            }
        });

        arrayAdapterTypeBudget1 = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, typeBudgetMonthly);

        arrayAdapterTypeBudget2 = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, typeBudgetYearly);

        spinnerTypeBudget1.setAdapter(arrayAdapterTypeBudget1);
        spinnerTypeBudget2.setAdapter(arrayAdapterTypeBudget2);

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

        mSalary.addTextChangedListener(new TextWatcher() {
            boolean maySetText_Salary = true;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (maySetText_Salary) {
                    if (mSalary.length() == 2) {
                        mSalary.setText("0");
                    }
                }

                valSalary = (mSalary.getCleanDoubleValue());
                System.out.println(valSalary);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mBudget1.addTextChangedListener(new TextWatcher() {
            boolean maySetText_BudgetMonthly = true;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /*valBudgetMonthly = Double.valueOf(mBudget1.getText().toString().substring(2));
                if (maySetText_BudgetMonthly) {
                    maySetText_BudgetMonthly = false;
                    mBudget1.setText(formatRupiah.format(valBudgetMonthly));
                    int nextCunsorPos = mBudget1.getText().length() - mBudget1.getSelectionEnd();
                    System.out.println(formatRupiah.format(valBudgetMonthly));
                    System.out.println(nextCunsorPos);
                    mBudget1.setSelection(nextCunsorPos);
                } else {
                    maySetText_BudgetMonthly = true;
                }*/

                if (maySetText_BudgetMonthly) {
                    if (mBudget1.length() == 2) {
                        mBudget1.setText("0");
                    }
                }

                valBudgetMonthly = (mBudget1.getCleanDoubleValue());
                System.out.println(valBudgetMonthly);

                if (valBudgetMonthly == 0) {
                    typeBudget1Value = "";
                    tv_TypeBudget1.setVisibility(View.GONE);
                    spinnerTypeBudget1.setVisibility(View.GONE);
                } else {
                    typeBudget1Value = "Monthly";
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mBudget2.addTextChangedListener(new TextWatcher() {
            boolean maySetText_BudgetYearly = true;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                /*if (mBudget2.getText().toString().isEmpty()) {
                    mBudget2.setText("0");
                    typeBudget2Value = "";
                } else if (mBudget2.getText().toString().equals("0")) {
                    tv_TypeBudget2.setVisibility(View.GONE);
                    spinnerTypeBudget2.setVisibility(View.GONE);
                } else  {
                    tv_TypeBudget2.setVisibility(View.VISIBLE);
                    spinnerTypeBudget2.setVisibility(View.VISIBLE);
                }*/

                if (maySetText_BudgetYearly) {
                    if (mBudget2.length() == 2) {
                        mBudget2.setText("0");
                    }
                }

                valBudgetYearly = (mBudget2.getCleanDoubleValue());
                System.out.println(valBudgetYearly);

                if (valBudgetYearly == 0) {
                    typeBudget2Value = "";
                    tv_TypeBudget2.setVisibility(View.GONE);
                    spinnerTypeBudget2.setVisibility(View.GONE);
                } else {
                    typeBudget2Value = "Yearly";
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        newEmployee_layoutOrganization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialogOrganization.showSpinerDialog();
            }
        });

        newEmployee_layoutTitleOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialogTitle.showSpinerDialog();
            }
        });

        newEmployee_layoutColourRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialogColour.showSpinerDialog();
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

        filledTitleOrg = false;
        filledColourRelation = false;
        filledOrganization = false;

        getListFromMaster();

        mSalary.setText(valSalary.toString());
        mBudget1.setText(valBudgetMonthly.toString());
        mBudget2.setText(valBudgetYearly.toString());

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
        newEmployeeHobbies = mHobbies.getText().toString();
        newEmployeeOrganization = mOrganization.getText().toString();
        newEmployeeOrgaDetail = mOrgaDetail.getText().toString();
        newEmployeeTitleOrg = mTitleOrg.getText().toString();
        newEmployeeColourRelation = mColourRelation.getText().toString();
        newEmployeeTypeBudget1 = typeBudget1Value;
        newEmployeeTypeBudget2 = typeBudget2Value;

        // Checking if Salary, Budget1, and Budget2 is blank or not

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
        } else if (!filledOrganization) {
            mOrganization.startAnimation(shake);
            tv_workInfo.requestFocus();
            tv_workInfo.clearFocus();
            validateSucceed = false;
        } else if (newEmployeeOrgaDetail.equals("")) {
            mOrgaDetail.startAnimation(shake);
            mOrgaDetail.requestFocus();
            validateSucceed = false;
        } else if (!filledTitleOrg) {
            mTitleOrg.startAnimation(shake);
            tv_workInfo.requestFocus();
            validateSucceed = false;
        } else if (!filledColourRelation) {
            mColourRelation.startAnimation(shake);
            tv_workInfo.requestFocus();
            validateSucceed = false;
        } /*else if (newEmployeeBudget1.equals("")) {
            mBudget1.startAnimation(shake);
            mBudget1.requestFocus();
            validateSucceed = false;
        } else if (newEmployeeTypeBudget1.equals("")) {
            spinnerTypeBudget1.startAnimation(shake);
            spinnerTypeBudget1.requestFocus();
            validateSucceed = false;
        }*/

        if (validateSucceed) {
            createConfirmationMessage();
        }
    }

    private void writeToDatabase(){

        // Refresh current time
        setCurrentDate();

        Map<String, Object> updateBio = new HashMap<>();
        Map<String, Object> budgetInfo = new HashMap<>();

        updateBio.put("address", newEmployeeAddress);
        updateBio.put("ctcNum1", newEmployeeCtcNum1);
        updateBio.put("ctcNum2", newEmployeeCtcNum2);
        updateBio.put("firstName", newEmployeeFirstname);
        updateBio.put("lastName", newEmployeeLastname);
        updateBio.put("nickname", newEmployeeNickname);
        updateBio.put("orgDetail", newEmployeeOrgaDetail);
        updateBio.put("organization", newEmployeeOrganization);
        updateBio.put("salary", valSalary);
        updateBio.put("colourRelation", newEmployeeColourRelation);
        updateBio.put("title_organization", newEmployeeTitleOrg);
        updateBio.put("hobbies", newEmployeeHobbies);
        updateBio.put("date_entry", currentDateString);

        budgetInfo.put("budget1", valBudgetMonthly);
        budgetInfo.put("budget2", valBudgetYearly);
        budgetInfo.put("typeBudget1", typeBudget1Value);
        budgetInfo.put("typeBudget2", typeBudget2Value);

        usersRef.child(newKeyValue).updateChildren(updateBio);

        budgetRef.child(currentYearString).child(newKeyValue).updateChildren(budgetInfo);
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

    public void setCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy 'at' HH:mm");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        Date currentDate = new Date(System.currentTimeMillis());
        currentDateString = dateFormat.format(currentDate);
        currentYearString = yearFormat.format(currentDate);
    }

    public void getListFromMaster() {
        // Organization's Master
        organizationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listOrganization.clear();
                if (dataSnapshot != null) {
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                        if (dataSnapshot1.getValue().toString().equals("true")) {
                            listOrganization.add(dataSnapshot1.getKey());
                        }
                    }
                } else {
                    Toast.makeText(NewEmployee.this, "There are nothing in Organization's list", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Colour's Master
        colourRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listColour.clear();
                if (dataSnapshot != null) {
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
//                        Master master = dataSnapshot1.getValue(Master.class);
                        if (dataSnapshot1.getValue().toString().equals("true")) {
                            listColour.add(dataSnapshot1.getKey());
                        }
                    }
                } else {
                    Toast.makeText(NewEmployee.this, "There are nothing in Colour's list", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Title's Master
        titleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listTitle.clear();
                if (dataSnapshot != null) {
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                        if (dataSnapshot1.getValue().toString().equals("true")) {
                            listTitle.add(dataSnapshot1.getKey());
                        }
                    }
                } else {
                    Toast.makeText(NewEmployee.this, "There are nothing in Title's list", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Relationship's Master
        relationshipRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listRelationship.clear();
                if (dataSnapshot != null) {
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                        if (dataSnapshot1.getValue().toString().equals("true")) {
                            listRelationship.add(dataSnapshot1.getKey());
                        }
                    }
                } else {
                    Toast.makeText(NewEmployee.this, "There are nothing in Relationship's list", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
