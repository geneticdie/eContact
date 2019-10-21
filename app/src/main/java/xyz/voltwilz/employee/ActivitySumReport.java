package xyz.voltwilz.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import xyz.voltwilz.employee.ClassOnly.UserProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivitySumReport extends AppCompatActivity {

    public static final String EXTRA_KEYVALUE = "idStaff";

    DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
    DatabaseReference staffRef = mRoot.child("Staffs");
    DatabaseReference budgetRef = mRoot.child("Budget");
    DatabaseReference availableYearRef = budgetRef.child("Available_Year");

    ArrayList<String> staffsName = new ArrayList<>();
    ArrayList<String> idStaff = new ArrayList<>();

    ProgressBar progressBar;

    SpinnerDialog spinnerDialogName;

    TextView tv_staffName, tv_Title, tv_Organization;

    Button btnViewReport;

    LinearLayout layoutStaffName, layoutStaffDetail, wholeLayout;

    String selectedStaffId;

    Boolean doneReadStaff = false, filledName = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Summary Report");

        progressBar = findViewById(R.id.sumReport_progressBar);

        btnViewReport  = findViewById(R.id.sumReport_btnViewReport);

        wholeLayout = findViewById(R.id.sumReport_wholeLayout);
        layoutStaffName = findViewById(R.id.sumReport_layoutStaffName);
        layoutStaffDetail = findViewById(R.id.sumReport_layoutStaffDetail);

        tv_staffName = findViewById(R.id.sumReport_staffName);
        tv_Title = findViewById(R.id.sumReport_tvTitle);
        tv_Organization = findViewById(R.id.sumReport_tvOrganization);

        spinnerDialogName = new SpinnerDialog(ActivitySumReport.this, staffsName, "Search staff", R.style.DialogAnimations_SmileWindow, "Close");
        spinnerDialogName.setCancellable(true);
        spinnerDialogName.setShowKeyboard(false);
        spinnerDialogName.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                tv_staffName.setText(item);
                selectedStaffId = idStaff.get(position);
                filledName = true;
                checkingNameInput();
            }
        });

        layoutStaffName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialogName.showSpinerDialog();
            }
        });

        btnViewReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(ActivitySumReport.this, ActivityDetailReport.class));

                Intent goToDetail = new Intent(ActivitySumReport.this, ActivityDetailReport.class);
                goToDetail.putExtra(EXTRA_KEYVALUE, selectedStaffId);
                startActivity(goToDetail);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onStart() {
        super.onStart();

        showProgressBar();

        readAllStaffs();

        hideStaffDetail();
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

    public void getStaffDetail() {
        staffRef.child(selectedStaffId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile staffDetail = dataSnapshot.getValue(UserProfile.class);
                if (dataSnapshot.getValue() != null) {
                    tv_Title.setText(staffDetail.getTitle_organization());
                    tv_Organization.setText(staffDetail.getOrganization());
                    showStaffDetail();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void checkingNameInput() {
        if (filledName) {
            getStaffDetail();
        }
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        wholeLayout.setVisibility(View.INVISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        wholeLayout.setVisibility(View.VISIBLE);
    }

    public void showStaffDetail() {
        layoutStaffDetail.setVisibility(View.VISIBLE);
    }

    public void hideStaffDetail() {
        layoutStaffDetail.setVisibility(View.GONE);
    }

    public void checkingStatus() {
        if (doneReadStaff) {
            hideProgressBar();
        }
    }
}
