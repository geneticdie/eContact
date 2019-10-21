package xyz.voltwilz.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import xyz.voltwilz.employee.ClassOnly.Transaction;
import xyz.voltwilz.employee.ClassOnly.UserProfile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ActivityTransaction extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
    DatabaseReference transactionRef = mRoot.child("Transaction");
    DatabaseReference staffRef = mRoot.child("Staffs");
    DatabaseReference budgetRef = mRoot.child("Budget");

    ArrayList<String> staffsName = new ArrayList<>();
    ArrayList<String> idStaff = new ArrayList<>();
    ArrayList<String> listTypeBudget = new ArrayList<>();

    ArrayAdapter<String> arrayAdapterTypeBudget;

    LinearLayout wholeLayout, layoutTransaction, layoutReceiver, layoutTransferDate;

    Spinner spinnerTypeBudget;

    SpinnerDialog spinnerDialogStaff;

    TextView tv_receiverName, tv_transferDate, tv_budgetAmount, tv_budgetUsed;
    EditText mFee, mNote;
    TextInputLayout trans_TIL_fee, trans_TIL_notes;

    String newTransKey, selectedStaffId, currentYear;

    Integer valBudgetAmount, valBudgetUsed = 0;

    ProgressBar progressBar;

    Button btnSubmit;

    Boolean filledName = false, filledTransferDate = false, doneReadStaff = false, doneReadAvaiTypeBudget = false;

    String valReceivername, valNote, valFee, valTypeBudget;

    Locale localeID = new Locale("in", "ID");

    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Transaction");

        wholeLayout = findViewById(R.id.transaction_wholeLayout);
        layoutTransaction = findViewById(R.id.transaction_layoutTransaction);
        layoutReceiver = findViewById(R.id.transaction_layoutReceiver);
        layoutTransferDate = findViewById(R.id.transaction_layoutTransferDate);

        progressBar = findViewById(R.id.transaction_progressBar);

        spinnerTypeBudget = findViewById(R.id.transaction_typeBudget);

        tv_receiverName = findViewById(R.id.transaction_receiverName);
        tv_transferDate = findViewById(R.id.transaction_transferDate);
        tv_budgetAmount = findViewById(R.id.transaction_tvBudgetAmount);
        tv_budgetUsed = findViewById(R.id.transaction_tvBudgetUsed);

        trans_TIL_fee = findViewById(R.id.transaction_TIL_fee);
        trans_TIL_notes = findViewById(R.id.transaction_TIL_notes);

        mFee = findViewById(R.id.transaction_fee);
        mNote = findViewById(R.id.transaction_notes);

        btnSubmit = findViewById(R.id.transaction_btnSubmit);

        showProgressBar();
        hideTransactionLayout();

        listTypeBudget.add("Hellow");

        arrayAdapterTypeBudget = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, listTypeBudget);

        spinnerTypeBudget.setAdapter(arrayAdapterTypeBudget);

        spinnerTypeBudget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                valTypeBudget = adapterView.getItemAtPosition(i).toString();
                System.out.println(valBudgetUsed);
                getBudgetAmount();
                getBudgetUsed();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerDialogStaff = new SpinnerDialog(ActivityTransaction.this, staffsName, "Search staff", R.style.DialogAnimations_SmileWindow , "Close");
        spinnerDialogStaff.setCancellable(true);
        spinnerDialogStaff.setShowKeyboard(false);

        spinnerDialogStaff.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                valReceivername = item;
                selectedStaffId = idStaff.get(position);
                System.out.println(selectedStaffId);
                tv_receiverName.setText(valReceivername);
                filledName = true;
                checkingFilledInitialInput();
            }
        });

        layoutReceiver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialogStaff.showSpinerDialog();
            }
        });

        layoutTransferDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new FragmentDatePicker();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateInput();
                System.out.println("hai");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTransKey(); // Generate Transaction unique key
        readAllStaffs();
        getCurrentYear();
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

    public void getTransKey() {
        SimpleDateFormat transKeyFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date currentDate = new Date(System.currentTimeMillis());

        newTransKey = transKeyFormat.format(currentDate);
        System.out.println(newTransKey);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String transDateString = DateFormat.getDateInstance().format(c.getTime());
        tv_transferDate.setText(transDateString);
        filledTransferDate = true;
        checkingFilledInitialInput();

    }

    public void readAllStaffs() {
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

    public void validateInput() {
        final Animation shake = AnimationUtils.loadAnimation(ActivityTransaction.this, R.anim.shake);

        if (mFee.getText().toString().equals("")) {
            mFee.startAnimation(shake);
            trans_TIL_fee.startAnimation(shake);
            mFee.requestFocus();
            System.out.println(mNote.getText().toString().isEmpty());
        } else {
            createConfirmationMessage();
        }
    }

    private void writeTransaction() {
        if (mNote.getText().toString().isEmpty()) {
            valNote = "";
        } else {
            valNote = mNote.getText().toString();
        }

        Map<String, Object> transactionInfo = new HashMap<>();

        transactionInfo.put("idStaff", selectedStaffId);
        transactionInfo.put("receiver_name", tv_receiverName.getText().toString());
        transactionInfo.put("transfer_date", tv_transferDate.getText().toString());
        transactionInfo.put("note", mNote.getText().toString());
        transactionInfo.put("fee", Integer.valueOf(mFee.getText().toString()));
        transactionInfo.put("from_budget", valTypeBudget);

        transactionRef.child(newTransKey).updateChildren(transactionInfo);

        Toast.makeText(ActivityTransaction.this, "Transaction submitted", Toast.LENGTH_LONG).show();
    }

    public void readBudget() {
        budgetRef.child(currentYear).child(selectedStaffId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile budgets = dataSnapshot.getValue(UserProfile.class);
                if (dataSnapshot.getValue() != null) {
                    listTypeBudget.clear();
                    if (!budgets.getTypeBudget1().isEmpty()) {
                        listTypeBudget.add("Budget 1");
                    }
                    if (!budgets.getTypeBudget2().isEmpty()) {
                        listTypeBudget.add("Budget 2");
                    }
                    System.out.println(budgets.getTypeBudget1().isEmpty());
                    System.out.println(budgets.getTypeBudget2().isEmpty());
                    doneReadAvaiTypeBudget = true;
                    showTransactionLayout();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                listTypeBudget.clear();
            }
        });
    }

    public void getBudgetAmount() {
        budgetRef.child(currentYear).child(selectedStaffId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile infoBudget = dataSnapshot.getValue(UserProfile.class);
                if (valTypeBudget.equals("Budget 1")) {
                    valBudgetAmount = infoBudget.getBudget1();
                } else if (valTypeBudget.equals("Budget 2")) {
                    valBudgetAmount = infoBudget.getBudget2();
                } else {
                    valBudgetAmount = 0;
                }
                tv_budgetAmount.setText(formatRupiah.format((double) valBudgetAmount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getBudgetUsed() {
        transactionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                valBudgetUsed = 0;
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Transaction infoTransaction = dataSnapshot1.getValue(Transaction.class);
                    if ((infoTransaction.getFrom_budget().equals(valTypeBudget))
                        && infoTransaction.getIdStaff().equals(selectedStaffId)) {
                        valBudgetUsed += infoTransaction.getFee();
                    }
                }
                tv_budgetUsed.setText(formatRupiah.format((double) valBudgetUsed));
                System.out.println(valBudgetUsed);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getCurrentYear() {
        currentYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        wholeLayout.setVisibility(View.INVISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        wholeLayout.setVisibility(View.VISIBLE);
    }

    public void showTransactionLayout() {
        layoutTransaction.setVisibility(View.VISIBLE);
        btnSubmit.setVisibility(View.VISIBLE);
    }

    public void hideTransactionLayout() {
        layoutTransaction.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
    }

    public void checkingStatus() {
        //System.out.println(doneReadStaff + " " + doneReadYear);
        if (doneReadStaff) {
            hideProgressBar();
        }
    }

    public void checkingFilledInitialInput() {
        if ((filledName) && (filledTransferDate)) {
            readBudget();
            if (valTypeBudget!=null){
                getBudgetAmount();
                getBudgetUsed();
            }
        }
    }

    private void createConfirmationMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("Submit this transaction?")
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        writeTransaction();
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
}
