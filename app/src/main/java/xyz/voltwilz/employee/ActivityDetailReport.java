package xyz.voltwilz.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import xyz.voltwilz.employee.ClassOnly.Transaction;
import xyz.voltwilz.employee.ClassOnly.UserProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static java.lang.StrictMath.abs;
import static xyz.voltwilz.employee.ActivitySumReport.EXTRA_KEYVALUE;

public class ActivityDetailReport extends AppCompatActivity {

    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");

    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference usersRef = mRootRef.child("Staffs");
    DatabaseReference budgetRef = mRootRef.child("Budget");
    DatabaseReference transactionRef = mRootRef.child("Transaction");

    TextView tv_staffName, tv_staffTitle, tv_staffOrg, tv_currentDate;
    TextView tv_budget1WithTypeBudget, tv_budget1Total;
    TextView tv_budget2WithTypeBudget, tv_budget2Total;
    TextView tvVal_budget1WithTypeBudget, tvVal_budget1Total, tvVal_actual1, tvVal_variance1;
    TextView tvVal_budget2WithTypeBudget, tvVal_budget2Total, tvVal_actual2, tvVal_variance2;

    ImageView img_arrowVariance1, img_arrowVariance2;

    ProgressBar progressBar;

    LinearLayout layoutBudget1, layoutBudget2, wholeLayout;
    LinearLayout layoutBudget1Total, layoutBudget2Total;

    Boolean doneGetStaffDetail, doneGetBudgetDetail, doneGetActualDetail, doneGetVarianceValue;
    Boolean budget1Overbudget, budget2Overbudget;

    String idStaff, currentFullDateString, currentDateString, currentYearString, currentMonthString;
    String strValTypeBudget1, strValTypeBudget2;

    Integer currentDateInt, currentYearInt, currentMonthInt;

    Double val_budget1WithTypeBudget, val_budget1Total, val_actual1, val_variance1;
    Double val_budget2WithTypeBudget, val_budget2Total, val_actual2, val_variance2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detail Report");

        Intent intentFromSummaryReport = getIntent();
        idStaff = intentFromSummaryReport.getStringExtra(EXTRA_KEYVALUE);

        progressBar = findViewById(R.id.detailReport_progressBar);

        img_arrowVariance1 = findViewById(R.id.detailReport_arrowVariance1);
        img_arrowVariance2 = findViewById(R.id.detailReport_arrowVariance2);

        tv_staffName = findViewById(R.id.detailReport_staffName);
        tv_staffTitle = findViewById(R.id.detailReport_staffTitle);
        tv_staffOrg = findViewById(R.id.detailReport_staffOrg);
        tv_currentDate = findViewById(R.id.detailReport_tvCurrentDate);
        tv_budget1WithTypeBudget = findViewById(R.id.detailReport_tvBudget1WithType);
        tv_budget1Total = findViewById(R.id.detailReport_tvBudget1Total);
        tv_budget2WithTypeBudget = findViewById(R.id.detailReport_tvBudget2WithType);
        tv_budget2Total = findViewById(R.id.detailReport_tvBudget2Total);

        tvVal_budget1WithTypeBudget = findViewById(R.id.detailReport_valueBudget1WithType);
        tvVal_budget1Total = findViewById(R.id.detailReport_valueBudget1Total);
        tvVal_actual1 = findViewById(R.id.detailReport_valueActual1);
        tvVal_variance1 = findViewById(R.id.detailReport_valVariance1);
        tvVal_budget2WithTypeBudget = findViewById(R.id.detailReport_valueBudget2WithType);
        tvVal_budget2Total = findViewById(R.id.detailReport_valueBudget2Total);
        tvVal_actual2 = findViewById(R.id.detailReport_valueActual2);
        tvVal_variance2 = findViewById(R.id.detailReport_valVariance2);

        layoutBudget1 = findViewById(R.id.detailReport_layoutBudget1);
        layoutBudget2 = findViewById(R.id.detailReport_layoutBudget2);
        wholeLayout = findViewById(R.id.detailReport_wholeLayout);
        layoutBudget1Total = findViewById(R.id.detailReport_layoutBudget1Total);
        layoutBudget2Total = findViewById(R.id.detailReport_layoutBudget2Total);

    }

    @Override
    protected void onResume() {
        super.onResume();

        showProgressBar();

        getCurrentDate();
        getStaffInfo();
        getBudgetInfo();
        getActualInfo();
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
        doneGetStaffDetail = false;
        usersRef.child(idStaff).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile staffInfo = dataSnapshot.getValue(UserProfile.class);
                if (dataSnapshot.getValue() != null) {
                    tv_staffName.setText(staffInfo.getFirstName() + " " + staffInfo.getLastName());
                    tv_staffTitle.setText(staffInfo.getTitle_organization());
                    tv_staffOrg.setText(staffInfo.getOrganization());
                }
                doneGetStaffDetail = true;
                checkingRetrievedData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getBudgetInfo() {
        doneGetBudgetDetail = false;
        strValTypeBudget1 = "";
        strValTypeBudget2 = "";
        budgetRef.child(currentYearString).child(idStaff).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile budgetInfo = dataSnapshot.getValue(UserProfile.class);
                if (dataSnapshot.getValue() != null) {
                    if (!budgetInfo.getTypeBudget1().isEmpty()) {
                        layoutBudget1.setVisibility(View.VISIBLE);

                        strValTypeBudget1 = budgetInfo.getTypeBudget1();
                        tv_budget1WithTypeBudget.setText("Budget (" + strValTypeBudget1 + ")");
                        val_budget1WithTypeBudget = (double) budgetInfo.getBudget1();
                        tvVal_budget1WithTypeBudget.setText(formatRupiah.format(val_budget1WithTypeBudget));

                        if (budgetInfo.getTypeBudget1().equals("Monthly")) {
                            Integer selectedMonthInt = Integer.valueOf(currentMonthString);

                            layoutBudget1Total.setVisibility(View.VISIBLE);
                            tv_budget1Total.setText("Budget (" + selectedMonthInt + " months)");
                            val_budget1Total = (double) budgetInfo.getBudget1() * selectedMonthInt;
                            tvVal_budget1Total.setText(formatRupiah.format(val_budget1Total));

                        } else {
                            layoutBudget1Total.setVisibility(View.GONE);
                        }
                    } else {
                        layoutBudget1.setVisibility(View.GONE);

                    }

                    if (!budgetInfo.getTypeBudget2().isEmpty()) {
                        layoutBudget2.setVisibility(View.VISIBLE);

                        strValTypeBudget2 = budgetInfo.getTypeBudget2();
                        tv_budget2WithTypeBudget.setText("Budget (" + strValTypeBudget2 + ")");
                        val_budget2WithTypeBudget = (double) budgetInfo.getBudget2();
                        tvVal_budget2WithTypeBudget.setText(formatRupiah.format(val_budget2WithTypeBudget));

                        if (budgetInfo.getTypeBudget2().equals("Monthly")) {
                            Integer selectedMonthInt = Integer.valueOf(currentMonthString);

                            layoutBudget2Total.setVisibility(View.VISIBLE);
                            tv_budget2Total.setText("Budget (" + selectedMonthInt + " months)");
                            val_budget2Total = (double) budgetInfo.getBudget2() * selectedMonthInt;
                            tvVal_budget2Total.setText(formatRupiah.format(val_budget2Total));
                        } else {
                            layoutBudget2Total.setVisibility(View.GONE);
                        }

                    } else {
                        layoutBudget2.setVisibility(View.GONE);
                    }
                }
                doneGetBudgetDetail = true;
                checkingRetrievedData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getActualInfo() {
        val_actual1 = (double) 0;
        val_actual2 = (double) 0;
        doneGetActualDetail = false;
        transactionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Transaction infoTransaction = dataSnapshot1.getValue(Transaction.class);
                    Integer transactionYear = Integer.valueOf(dataSnapshot1.getKey().substring(0,4));
                    Integer transactionMonth = Integer.valueOf(dataSnapshot1.getKey().substring(4,6));
                    Integer transactionDate = Integer.valueOf(dataSnapshot1.getKey().substring(6,8));

                    System.out.println(transactionYear + " " + currentYearInt);

                    if (infoTransaction.getIdStaff().equals(idStaff)) {
                        if (transactionYear.equals(currentYearInt)) {
                            System.out.println(dataSnapshot1.getKey() + "1 masuk");
                            if (((transactionMonth.equals(currentMonthInt)) && (transactionDate <= currentDateInt))
                                || (transactionMonth < currentMonthInt)) {
                                System.out.println(dataSnapshot1.getKey() + "2 masuk");
                                if (transactionDate <= currentDateInt) {
                                    System.out.println(dataSnapshot1.getKey() + "3 masuk");
                                    if (infoTransaction.getFrom_budget().equals("Budget 1")) {
                                        val_actual1 += infoTransaction.getFee();
                                        tvVal_actual1.setText(formatRupiah.format(val_actual1));
                                        System.out.println("actual 1: " + val_actual1);
                                    } else if (infoTransaction.getFrom_budget().equals("Budget 2")) {
                                        val_actual2 += infoTransaction.getFee();
                                        tvVal_actual2.setText(formatRupiah.format(val_actual2));
                                        System.out.println("actual 1: " + val_actual2);
                                    }

                                }
                            }
                        }
                    }
                }
                doneGetActualDetail = true;
                checkingRetrievedData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getVarianceValue() {
        val_variance1 = (double) 0;
        val_variance2 = (double) 0;
        budget1Overbudget = false;
        budget2Overbudget = false;

        if (!strValTypeBudget1.isEmpty()) {
            if (strValTypeBudget1.equals("Monthly")) {
                val_variance1 = (abs(val_actual1 - val_budget1Total) / val_budget1Total ) * 100;
                if (val_actual1 > val_budget1Total) {
                    budget1Overbudget = true;
                }
                System.out.println(budget1Overbudget);
            } else {
                val_variance1 = (abs(val_actual1 - val_budget1WithTypeBudget) / val_budget1WithTypeBudget ) * 100;
                if (val_actual1 > val_budget1WithTypeBudget) {
                    budget1Overbudget = true;
                }
            }

            if (budget1Overbudget) {
                img_arrowVariance1.setImageResource(R.drawable.ic_arrow_down);
                tvVal_variance1.setTextColor(ContextCompat.getColor(ActivityDetailReport.this, R.color.colorAccent));
            }

            tvVal_variance1.setText(decimalFormat.format(val_variance1) + "%");
        }

        if (!strValTypeBudget2.isEmpty()) {
            if (strValTypeBudget2.equals("Monthly")) {
                val_variance2 = (abs(val_actual2 - val_budget2Total) / val_budget2Total ) * 100;
                if (val_actual2 > val_budget2Total) {
                    budget2Overbudget = true;
                }
                System.out.println(budget2Overbudget);
            } else {
                val_variance2 = (abs(val_actual2 - val_budget2WithTypeBudget) / val_budget2WithTypeBudget ) * 100;
                if (val_actual2 > val_budget2WithTypeBudget) {
                    budget2Overbudget = true;
                }
            }

            if (budget2Overbudget) {
                img_arrowVariance2.setImageResource(R.drawable.ic_arrow_down);
                tvVal_variance2.setTextColor(ContextCompat.getColor(ActivityDetailReport.this, R.color.colorAccent));
            }

            tvVal_variance2.setText(decimalFormat.format(val_variance2) + "%");
        }

        doneGetVarianceValue = true;
        checkingVarianceValue();
    }

    public void getCurrentDate() {
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        Date currentDate = new Date(System.currentTimeMillis());

        currentFullDateString = fullDateFormat.format(currentDate);
        tv_currentDate.setText(currentFullDateString);

        currentDateString = dateFormat.format(currentDate);
        currentMonthString = monthFormat.format(currentDate);
        currentYearString = yearFormat.format(currentDate);
        currentDateInt = Integer.valueOf(currentDateString);
        currentMonthInt = Integer.valueOf(currentMonthString);
        currentYearInt = Integer.valueOf(currentYearString);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        wholeLayout.setVisibility(View.INVISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        wholeLayout.setVisibility(View.VISIBLE);
    }

    public void checkingRetrievedData() {
        if ((doneGetBudgetDetail) && (doneGetStaffDetail) && (doneGetActualDetail)) {
            getVarianceValue();
        }
    }

    public void checkingVarianceValue() {
        if (doneGetVarianceValue) {
            hideProgressBar();
        }
    }
}
