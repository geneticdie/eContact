package xyz.voltwilz.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import xyz.voltwilz.employee.ClassOnly.UserProfile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ActivityBudgeting extends AppCompatActivity {

    DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
    DatabaseReference staffRef = mRoot.child("Staffs");
    DatabaseReference budgetRef = mRoot.child("Budget");
    DatabaseReference availableYearRef = budgetRef.child("Available_Year");

    ArrayList<String> staffsName = new ArrayList<>();
    ArrayList<String> idStaff = new ArrayList<>();
    ArrayList<String> listYear = new ArrayList<>();

    ArrayAdapter<String> arrayAdapterTypeBudget;

    String selectedStaffId, selectedYear, currentYear;
    String val_typeBudget1 = "";
    String val_typeBudget2 = "";

    Integer val_budget1, val_budget2;

    Boolean doneReadStaff, doneReadYear, filledName = false, filledYear = false;

    TextView tv_name, tv_year, tv_typeBudget2;

    EditText mBudget1, mBudget2;

    Button btnSave;

    Spinner spinnerTypeBudget1, spinnerTypeBudget2;

    SpinnerDialog spinnerDialogName, spinnerDialogYear;

    ProgressBar progressBar;

    LinearLayout wholeLayout, layoutBudgeting, layoutName, layoutYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgeting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Budgeting");

        String[] typeBudget = {"Monthly", "Yearly", "Periodic"};

        layoutName = findViewById(R.id.budgeting_layoutName);
        layoutYear = findViewById(R.id.budgeting_layoutYear);
        wholeLayout = findViewById(R.id.budgeting_wholeLayout);
        layoutBudgeting = findViewById(R.id.budgeting_layoutBudgeting);
        progressBar = findViewById(R.id.budgeting_progressBar);
        btnSave = findViewById(R.id.budgeting_btnSave);
        mBudget1 = findViewById(R.id.budgeting_budget1);
        mBudget2 = findViewById(R.id.budgeting_budget2);

        showProgressBar();
        hideBudgetLayout();

        tv_name = findViewById(R.id.budgeting_staffName);
        tv_year = findViewById(R.id.budgeting_whichYear);
        tv_typeBudget2 = findViewById(R.id.budgeting_tvTypeBudget2);

        arrayAdapterTypeBudget = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, typeBudget);

        spinnerTypeBudget1 = findViewById(R.id.budgeting_SpinnerTypeBudget1);
        spinnerTypeBudget2 = findViewById(R.id.budgeting_SpinnerTypeBudget2);

        spinnerTypeBudget1.setAdapter(arrayAdapterTypeBudget);
        spinnerTypeBudget2.setAdapter(arrayAdapterTypeBudget);

        spinnerDialogName = new SpinnerDialog(ActivityBudgeting.this, staffsName, "Search staff", R.style.DialogAnimations_SmileWindow , "Close");
        spinnerDialogName.setCancellable(true);
        spinnerDialogName.setShowKeyboard(false);
        spinnerDialogName.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                tv_name.setText(item);
                selectedStaffId = idStaff.get(position);
                filledName = true;
                checkingFilledInitialInput();
            }
        });

        spinnerDialogYear = new SpinnerDialog(ActivityBudgeting.this, listYear, "Search year", R.style.DialogAnimations_SmileWindow , "Close");
        spinnerDialogYear.setCancellable(true);
        spinnerDialogYear.setShowKeyboard(false);
        spinnerDialogYear.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                selectedYear = item;
                tv_year.setText(item);
                filledYear = true;
                checkingFilledInitialInput();
            }
        });

        spinnerTypeBudget1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                val_typeBudget1 = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerTypeBudget2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                val_typeBudget2 = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mBudget1.addTextChangedListener(new TextWatcher() {
            boolean maySetText_mBudget1 = true;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mBudget1.getText().toString().isEmpty()) {
                    mBudget1.setText("0");
                    mBudget1.setSelection(mBudget1.getText().length());
                } else {
                    if (maySetText_mBudget1) {
                        maySetText_mBudget1 = false;
                        mBudget1.setText((Integer.valueOf(mBudget1.getText().toString())).toString());
                        mBudget1.setSelection(mBudget1.getText().length());
                    } else {
                        maySetText_mBudget1 = true;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mBudget2.addTextChangedListener(new TextWatcher() {
            boolean maySetText_mBudget2 = true;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mBudget2.getText().toString().isEmpty()) {
                    mBudget2.setText("0");
                    mBudget2.setSelection(mBudget2.getText().length());
                    val_typeBudget2 = "";
                    System.out.println("kosong");
                } else if (mBudget2.getText().toString().equals("0")) {
                    tv_typeBudget2.setVisibility(View.GONE);
                    spinnerTypeBudget2.setVisibility(View.GONE);
                    val_typeBudget2 = "";
                } else  {
                    tv_typeBudget2.setVisibility(View.VISIBLE);
                    spinnerTypeBudget2.setVisibility(View.VISIBLE);
                    if (maySetText_mBudget2) {
                        maySetText_mBudget2 = false;
                        mBudget2.setText((Integer.valueOf(mBudget2.getText().toString())).toString());
                        mBudget2.setSelection(mBudget2.getText().length());
                    } else {
                        maySetText_mBudget2 = true;
                    }
                    //mBudget2.setText(String.valueOf(Integer.valueOf(mBudget2.getText().toString())));
                    System.out.println(Integer.valueOf(mBudget2.getText().toString()));
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveConfirmationMessage();
            }
        });

    }

    public void declareOnClick() {
        layoutName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialogName.showSpinerDialog();
            }
        });

        layoutYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialogYear.showSpinerDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        val_typeBudget2 = "";

        getCurrentYear();
        checkIfNewYear();
        readAllStaffs();
        readAllYears();
        declareOnClick();
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

    public void readAllStaffs() {
        doneReadStaff = false;
        staffRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                staffsName.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    idStaff.add(dataSnapshot1.getKey());
                    UserProfile userProfile = dataSnapshot1.getValue(UserProfile.class);
                    staffsName.add(userProfile.getFirstName() + " " + userProfile.getLastName());
                }
                doneReadStaff = true;
                checkingStatus();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readAllYears() {
        doneReadYear = false;
        availableYearRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listYear.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    String boolStatus = dataSnapshot1.getValue().toString();
                    if (boolStatus.equals("true")) {
                        listYear.add(dataSnapshot1.getKey());
                    }
                }
                doneReadYear = true;
                checkingStatus();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        tv_year.setText(currentYear);
        selectedYear = currentYear;
        filledYear = true;
    }

    public void readBudget() {
         budgetRef.child(selectedYear).child(selectedStaffId).addValueEventListener(new ValueEventListener() {
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
                         tv_typeBudget2.setVisibility(View.GONE);
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
                 } else {
                     mBudget1.setText("");
                     mBudget2.setText("");
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
    }

    public void writeBudget() {
        /*if (mBudget1.getText().toString().equals("0")) {

        } else {
            val_budget1 = Integer.valueOf(mBudget1.getText().toString());
        }
        if (Integer.valueOf(mBudget2.getText().toString()) == 0) {
            val_budget2 = 0;
        } else {
            val_budget2 = Integer.valueOf(mBudget2.getText().toString());
        }*/

        val_budget1 = Integer.valueOf(mBudget1.getText().toString());
        val_budget2 = Integer.valueOf(mBudget2.getText().toString());

        Map<String, Object> budgetInfo = new HashMap<>();

        budgetInfo.put("budget1", val_budget1);
        budgetInfo.put("budget2", val_budget2);
        budgetInfo.put("typeBudget1", val_typeBudget1);
        budgetInfo.put("typeBudget2", val_typeBudget2);

        budgetRef.child(selectedYear).child(selectedStaffId).updateChildren(budgetInfo)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ActivityBudgeting.this, "Budget plan succesfully updated", Toast.LENGTH_LONG).show();
                        onBackPressed();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ActivityBudgeting.this, "Failed to save budget plan. Check your internet connection", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void getCurrentYear() {
        currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

    public void checkIfNewYear() {
        availableYearRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!listYear.contains(currentYear)) {
                    Map<String, Object> addYear = new HashMap<>();
                    addYear.put(currentYear, true);
                    availableYearRef.updateChildren(addYear);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        wholeLayout.setVisibility(View.INVISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        wholeLayout.setVisibility(View.VISIBLE);
    }

    public void showBudgetLayout() {
        layoutBudgeting.setVisibility(View.VISIBLE);
        btnSave.setVisibility(View.VISIBLE);
    }

    public void hideBudgetLayout() {
        layoutBudgeting.setVisibility(View.GONE);
        btnSave.setVisibility(View.GONE);
    }

    public void checkingStatus() {
        System.out.println(doneReadStaff + " " + doneReadYear);
        if ((doneReadYear) && (doneReadStaff)) {
            hideProgressBar();
        }
    }

    public void checkingFilledInitialInput() {
        if ((filledName) && (filledYear)) {
            showBudgetLayout();
            readBudget();
        }
    }

    private void SaveConfirmationMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("Save this budget plan?")
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        writeBudget();
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

}
