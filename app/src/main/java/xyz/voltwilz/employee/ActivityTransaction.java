package xyz.voltwilz.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import xyz.voltwilz.employee.ClassOnly.UserProfile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActivityTransaction extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
    DatabaseReference transactionRef = mRoot.child("Transaction");
    DatabaseReference staffRef = mRoot.child("Staffs");

    String newTransKey, selectedStaffId;

    LinearLayout layoutReceiver, layoutTransferDate;
    ArrayList<String> staffsName = new ArrayList<>();
    ArrayList<String> idStaff = new ArrayList<>();
    SpinnerDialog spinnerDialogStaff;

    TextView tv_receiverName, tv_transferDate;
    EditText mFee, mNote;
    TextInputLayout trans_TIL_fee, trans_TIL_notes;

    Button btnSubmit;

    String valReceivername, valNote, valFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Transaction");

        layoutReceiver = findViewById(R.id.transaction_layoutReceiver);
        layoutTransferDate = findViewById(R.id.transaction_layoutTransferDate);
        tv_receiverName = findViewById(R.id.transaction_receiverName);
        tv_transferDate = findViewById(R.id.transaction_transferDate);
        trans_TIL_fee = findViewById(R.id.transaction_TIL_fee);
        trans_TIL_notes = findViewById(R.id.transaction_TIL_notes);
        mFee = findViewById(R.id.transaction_fee);
        mNote = findViewById(R.id.transaction_notes);
        btnSubmit = findViewById(R.id.transaction_btnSubmit);

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

        transactionInfo.put("keyValue", selectedStaffId);
        transactionInfo.put("receiver_name", tv_receiverName.getText().toString());
        transactionInfo.put("transfer_date", tv_transferDate.getText().toString());
        transactionInfo.put("note", mNote.getText().toString());
        transactionInfo.put("fee", mFee.getText().toString());

        transactionRef.child(newTransKey).updateChildren(transactionInfo);

        Toast.makeText(ActivityTransaction.this, "Transaction submitted", Toast.LENGTH_LONG).show();
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
