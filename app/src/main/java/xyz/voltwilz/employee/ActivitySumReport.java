package xyz.voltwilz.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import xyz.voltwilz.employee.ClassOnly.Transaction;
import xyz.voltwilz.employee.ClassOnly.UserProfile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.lang.StrictMath.abs;

public class ActivitySumReport extends AppCompatActivity {

    public static final String EXTRA_KEYVALUE = "idStaff";
    public static final String EXTRA_NAMEVALUE = "namaStaff";
    public static final String EXTRA_BUDGETVALUE = "budgetStaff";


    Locale localeID = new Locale("in", "ID");
    NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");

    DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
    DatabaseReference staffRef = mRoot.child("Staffs");
    DatabaseReference budgetRef = mRoot.child("Budget");
    DatabaseReference transactionRef = mRoot.child("Transaction");
    DatabaseReference availableYearRef = budgetRef.child("Available_Year");

    ArrayList<String> staffsName = new ArrayList<>();
    ArrayList<String> idStaff = new ArrayList<>();
    ArrayList<String> valChartMonthlyName = new ArrayList<>();
    ArrayList<Integer> valChartMonthlyBudget = new ArrayList<>();
    ArrayList<String> valChartYearlyName = new ArrayList<>();
    ArrayList<Integer> valChartYearlyBudget = new ArrayList<>();

    ProgressBar progressBar;

    SpinnerDialog spinnerDialogName;

    TextView tv_staffName, tv_Title, tv_Organization, tv_currentDate;
    TextView tv_valActual1, tv_valActual2;
    TextView tv_valBudget1WithType, tv_valBudget2WithType;
    TextView tv_valVariance1, tv_valVariance2;

    Button btnViewReport, btnViewReportMonthly, btnViewReportYearly;

    LinearLayout layoutStaffName, layoutStaffDetail, wholeLayout, layoutBudget1, layoutBudget2;

    ImageView img_arrowVariance1, img_arrowVariance2;

    String selectedStaffId, strValTypeBudget1, strValTypeBudget2;
    String currentFullDateString, currentDateString, currentYearString, currentMonthString;

    Integer currentDateInt, currentYearInt, currentMonthInt;
    Integer countMonthly, countYearly;

    Double dblTotalValueBudget1, dblTotalValueBudget2;
    Double dblTotalValueActual1, dblTotalValueActual2;
    Double dblValVariance1, dblValVariance2;

    Boolean doneReadStaff = false, filledName = false;
    Boolean doneGetBudgetDetail = false, doneGetActualDetail = false;
    Boolean budget1OverBudget, budget2OverBudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_report);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Summary Report");

        progressBar = findViewById(R.id.sumReport_progressBar);

        btnViewReport  = findViewById(R.id.sumReport_btnViewReport);
        btnViewReportMonthly = findViewById(R.id.sumReport_btnViewReportMonthly);
        btnViewReportYearly = findViewById(R.id.sumReport_btnViewReportYearly);

        img_arrowVariance1 = findViewById(R.id.sumReport_arrowVariance1);
        img_arrowVariance2 = findViewById(R.id.sumReport_arrowVariance2);

        wholeLayout = findViewById(R.id.sumReport_wholeLayout);
        layoutStaffName = findViewById(R.id.sumReport_layoutStaffName);
        layoutStaffDetail = findViewById(R.id.sumReport_layoutStaffDetail);
        layoutBudget1 = findViewById(R.id.sumReport_layoutBudget1);
        layoutBudget2 = findViewById(R.id.sumReport_layoutBudget2);

        tv_currentDate = findViewById(R.id.sumReport_tvCurrentDate);
        tv_staffName = findViewById(R.id.sumReport_staffName);
        tv_Title = findViewById(R.id.sumReport_tvTitle);
        tv_Organization = findViewById(R.id.sumReport_tvOrganization);
        tv_valBudget1WithType = findViewById(R.id.sumReport_valueBudget1WithType);
        tv_valBudget2WithType = findViewById(R.id.sumReport_valueBudget2WithType);
        tv_valActual1 = findViewById(R.id.sumReport_valueActual1);
        tv_valActual2 = findViewById(R.id.sumReport_valueActual2);
        tv_valVariance1 = findViewById(R.id.sumReport_valVariance1);
        tv_valVariance2 = findViewById(R.id.sumReport_valVariance2);

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
                tv_staffName.requestFocus();
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

        btnViewReportMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToChartMonthly = new Intent(ActivitySumReport.this, ActivityChartMonthly.class);
                System.out.println("Ukuran listName: " + valChartMonthlyName.size());
                goToChartMonthly.putExtra(EXTRA_NAMEVALUE, valChartMonthlyName);
                goToChartMonthly.putExtra(EXTRA_BUDGETVALUE, valChartMonthlyBudget);
                startActivity(goToChartMonthly);
            }
        });

        // Currently disable feature
        btnViewReportMonthly.setVisibility(View.GONE);
        btnViewReportYearly.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();

        tv_staffName.setText("- CHOOSE -");

        showProgressBar();

        getCurrentDate();
        getBudgetInfo();
        getActualInfo();

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

    public void getBudgetInfo() {
        doneGetBudgetDetail = false;
        strValTypeBudget1 = "";
        strValTypeBudget2 = "";
        valChartMonthlyName.clear();
        valChartMonthlyBudget.clear();
        dblTotalValueBudget1 = (double) 0;
        dblTotalValueBudget2 = (double) 0;
        budgetRef.child(currentYearString).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    UserProfile budgetInfo = dataSnapshot1.getValue(UserProfile.class);
                    if (dataSnapshot.getValue() != null) {
                    /*if (!budgetInfo.getTypeBudget1().isEmpty()) {
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
                    }*/
                        if (budgetInfo.getBudget1()!=null) {
                            dblTotalValueBudget1 += (budgetInfo.getBudget1() * currentMonthInt);
                            valChartMonthlyBudget.add(budgetInfo.getBudget1() * currentMonthInt);
                            staffRef.child(dataSnapshot1.getKey()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    UserProfile staffInfo = dataSnapshot.getValue(UserProfile.class);
                                    valChartMonthlyName.add(staffInfo.getFirstName()+ " " +staffInfo.getLastName());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        if (budgetInfo.getBudget2()!=null) {
                            dblTotalValueBudget2 += budgetInfo.getBudget2();
                        }
                        System.out.println(dblTotalValueBudget1);
                        System.out.println(dblTotalValueBudget2);
                    }
                }
                tv_valBudget1WithType.setText(formatRupiah.format(dblTotalValueBudget1));
                tv_valBudget2WithType.setText(formatRupiah.format(dblTotalValueBudget2));
                doneGetBudgetDetail = true;
                checkingRetrievedData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getActualInfo() {
        doneGetActualDetail = false;
        dblTotalValueActual1 = 0.0;
        dblTotalValueActual2 = 0.0;
        countMonthly = 0;
        countYearly =0;
        transactionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Transaction infoTransaction = dataSnapshot1.getValue(Transaction.class);
                    if (dataSnapshot.getValue() != null) {
                        try {
                            String tempFullDate = infoTransaction.getTransfer_date();
                            SimpleDateFormat tempSDF = new SimpleDateFormat("MMM dd, yyyy");
                            Date convertedFullDate = tempSDF.parse(tempFullDate);

                            SimpleDateFormat SDFYear = new SimpleDateFormat("yyyy");
                            SimpleDateFormat SDFMonth = new SimpleDateFormat("MM");
                            SimpleDateFormat SDFDate = new SimpleDateFormat("dd");

                            Integer convertedYear = Integer.valueOf(SDFYear.format(convertedFullDate));
                            Integer convertedMonth = Integer.valueOf(SDFMonth.format(convertedFullDate));
                            Integer convertedDate = Integer.valueOf(SDFDate.format(convertedFullDate));

                            if (convertedYear.equals(currentYearInt)) {
                                if ((convertedMonth.equals(currentMonthInt) && (convertedDate <= currentDateInt))
                                    || (convertedMonth < currentMonthInt)) {
                                    if (infoTransaction.getFrom_budget()!=null) {
                                        if (infoTransaction.getFrom_budget().equals("Budget Monthly")) {
                                            dblTotalValueActual1 += infoTransaction.getFee();
                                            countMonthly += 1;
                                        } else if (infoTransaction.getFrom_budget().equals("Budget Yearly")) {
                                            dblTotalValueActual2 += infoTransaction.getFee();
                                            countYearly += 1;
                                        }

                                        System.out.println(convertedYear + " " + convertedMonth + " " + convertedDate);
                                    }
                                }
                            }
                        } catch (ParseException e) {
                            System.out.println("Failed to parse");
                        }
                    }
                }

                System.out.println(countMonthly + " " + countYearly);
                tv_valActual1.setText(formatRupiah.format(dblTotalValueActual1));
                tv_valActual2.setText(formatRupiah.format(dblTotalValueActual2));
                doneGetActualDetail = true;
                checkingRetrievedData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getVarianceValue() {
        dblValVariance1 = 0.0;
        dblValVariance2 = 0.0;
        budget1OverBudget = false;
        budget2OverBudget = false;

        dblValVariance1 = (abs(dblTotalValueActual1 - dblTotalValueBudget1) / dblTotalValueBudget1) * 100;
        dblValVariance2 = (abs(dblTotalValueActual2 - dblTotalValueBudget2) / dblTotalValueBudget2) * 100;

        if (dblTotalValueActual1 > dblTotalValueBudget1) budget1OverBudget = true;
        if (dblTotalValueActual2 > dblTotalValueBudget2) budget2OverBudget = true;

        if (budget1OverBudget) {
            img_arrowVariance1.setImageResource(R.drawable.ic_arrow_down);
            tv_valVariance1.setTextColor(ContextCompat.getColor(ActivitySumReport.this, R.color.colorAccent));
        }

        if (budget2OverBudget) {
            img_arrowVariance1.setImageResource(R.drawable.ic_arrow_down);
            tv_valVariance2.setTextColor(ContextCompat.getColor(ActivitySumReport.this, R.color.colorAccent));
        }

        tv_valVariance1.setText(decimalFormat.format(dblValVariance1) + "%");
        tv_valVariance2.setText(decimalFormat.format(dblValVariance2) + "%");
    }

    public void checkingRetrievedData() {
        if ((doneGetBudgetDetail) && (doneGetActualDetail)) {
            getVarianceValue();
        }
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
}
