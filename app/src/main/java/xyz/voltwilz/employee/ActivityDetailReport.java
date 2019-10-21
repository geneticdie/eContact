package xyz.voltwilz.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import xyz.voltwilz.employee.ClassOnly.UserProfile;

import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import static xyz.voltwilz.employee.ActivitySumReport.EXTRA_KEYVALUE;

public class ActivityDetailReport extends AppCompatActivity {

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference usersRef = mRootRef.child("Staffs");
    DatabaseReference budgetRef = mRootRef.child("Budget");
    DatabaseReference transactionRef = mRootRef.child("Transaction");

    TextView tv_staffName, tv_staffTitle, tv_staffOrg;

    LinearLayout layoutBudget1, layoutBudget2;

    String idStaff, currentDateString, currentYearString, currentMonthString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detail Report");

        Intent intentFromSummaryReport = getIntent();
        idStaff = intentFromSummaryReport.getStringExtra(EXTRA_KEYVALUE);

        tv_staffName = findViewById(R.id.detailReport_staffName);
        tv_staffTitle = findViewById(R.id.detailReport_staffTitle);
        tv_staffOrg = findViewById(R.id.detailReport_staffOrg);

        layoutBudget1 = findViewById(R.id.detailReport_layoutBudget1);
        layoutBudget2 = findViewById(R.id.detailReport_layoutBudget2);

        getCurrentDate();
        getStaffInfo();
        getBudgetInfo();

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

    public void getStaffInfo() {
        usersRef.child(idStaff).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile staffInfo = dataSnapshot.getValue(UserProfile.class);
                if (dataSnapshot.getValue() != null) {
                    tv_staffName.setText(staffInfo.getFirstName() + " " + staffInfo.getLastName());
                    tv_staffTitle.setText(staffInfo.getTitle_organization());
                    tv_staffOrg.setText(staffInfo.getOrganization());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getBudgetInfo() {
        budgetRef.child(currentYearString).child(idStaff).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile budgetInfo = dataSnapshot.getValue(UserProfile.class);
                if (dataSnapshot.getValue() != null) {
                    if (!budgetInfo.getTypeBudget1().isEmpty()) {
                        layoutBudget1.setVisibility(View.VISIBLE);
                    } else {
                        layoutBudget1.setVisibility(View.GONE);
                    }

                    if (!budgetInfo.getTypeBudget2().isEmpty()) {
                        layoutBudget2.setVisibility(View.VISIBLE);
                    } else {
                        layoutBudget2.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy 'at' HH:mm");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        Date currentDate = new Date(System.currentTimeMillis());
        currentDateString = dateFormat.format(currentDate);
        currentMonthString = monthFormat.format(currentDate);
        currentYearString = yearFormat.format(currentDate);
    }
}
