package xyz.voltwilz.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import xyz.voltwilz.employee.ClassOnly.UserProfile;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ActivityBudgeting extends AppCompatActivity {

    DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
    DatabaseReference staffRef = mRoot.child("Staffs");
    DatabaseReference budgetRef = mRoot.child("Budget");
    DatabaseReference availableYearRef = budgetRef.child("Available_Year");

    ArrayList<String> staffsName = new ArrayList<>();
    ArrayList<String> idStaff = new ArrayList<>();
    ArrayList<String> listYear = new ArrayList<>();

    TextView tv_name, tv_year;

    SpinnerDialog spinnerDialogName, spinnerDialogYear;

    LinearLayout layoutName, layoutYear;

    String selectedStaffId, selectedYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgeting);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Budgeting");

        layoutName = findViewById(R.id.budgeting_layoutName);
        layoutYear = findViewById(R.id.budgeting_layoutYear);

        tv_name = findViewById(R.id.budgeting_staffName);
        tv_year = findViewById(R.id.budgeting_whichYear);

        spinnerDialogName = new SpinnerDialog(ActivityBudgeting.this, staffsName, "Search staff", R.style.DialogAnimations_SmileWindow , "Close");
        spinnerDialogName.setCancellable(true);
        spinnerDialogName.setShowKeyboard(false);
        spinnerDialogName.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                tv_name.setText(item);
                selectedStaffId = idStaff.get(position);
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
            }
        });

        declareOnClick();
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
        readAllStaffs();
        readAllYears();
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

    public void readAllYears() {
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
