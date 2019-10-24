package xyz.voltwilz.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import xyz.voltwilz.employee.ClassOnly.UserProfile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityChartYearly extends AppCompatActivity {

    DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
    DatabaseReference staffRef = mRoot.child("Staffs");
    DatabaseReference budgetRef = mRoot.child("Budget");
    DatabaseReference transactionRef = mRoot.child("Transaction");
    DatabaseReference availableYearRef = budgetRef.child("Available_Year");

    Pie monthlyPie;

    ArrayList<String> listName = new ArrayList<>();
    ArrayList<Integer> listBudget = new ArrayList<>();

    ProgressBar progressBar;
    LinearLayout wholeLayout;

    TextView tv_warnNoStaff;

    String currentFullDateString, currentDateString, currentYearString, currentMonthString;

    Integer currentDateInt, currentYearInt, currentMonthInt;

    Boolean doneGetBudgetDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_yearly);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Yearly Chart");

        wholeLayout = findViewById(R.id.chartYearly_wholeLayout);

        progressBar = findViewById(R.id.chartYearly_progressBar);

        tv_warnNoStaff = findViewById(R.id.chartYearly_warnNoStaff);

        monthlyPie = AnyChart.pie();

        AnyChartView budgetMonthlyChart = findViewById(R.id.chartViewYearly);
        budgetMonthlyChart.setChart(monthlyPie);
        budgetMonthlyChart.setOnRenderedListener(new AnyChartView.OnRenderedListener() {
            @Override
            public void onRendered() {
                hideProgressBar();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        showProgressBar();
        getCurrentDate();
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

    public void getBudgetInfo() {
        doneGetBudgetDetail = false;
        budgetRef.child(currentYearString).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listName.clear();
                listBudget.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    UserProfile budgetInfo = dataSnapshot1.getValue(UserProfile.class);
                    if (dataSnapshot.getValue() != null) {
                        if (budgetInfo.getBudget2()!=null) {
                            listBudget.add(budgetInfo.getBudget2());
                            staffRef.child(dataSnapshot1.getKey()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    UserProfile staffInfo = dataSnapshot.getValue(UserProfile.class);
                                    listName.add(staffInfo.getFirstName()+ " " +staffInfo.getLastName());
                                    System.out.println("Size listName " + listName.size());
                                    showChart();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public void showChart() {
        System.out.println("Panggil showChart");
        System.out.println("Show Chart listName " + listName.size());

        System.out.println("Masuk chart oi");
        monthlyPie.title("Distribution of Yearly Budget");

        monthlyPie.labels().position("outside");

        monthlyPie.legend().title().enabled(true);
        monthlyPie.legend().title()
                .text("Staffs Name")
                .padding(0d, 0d, 10d, 0d);

        monthlyPie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        List<DataEntry> data = new ArrayList<>();
        data.clear();

//        data.add(new ValueDataEntry("Apple", 100));
//        data.add(new ValueDataEntry("Orange", 200));
//        data.add(new ValueDataEntry("Lime", 300));
//        data.add(new ValueDataEntry("Mango", 250));
//        data.add(new ValueDataEntry("Grape", 50));
//        data.add(new ValueDataEntry("Yogurt", 70));
//        data.add(new ValueDataEntry("Strawberry", 80));

        if (listName!=null) {
            for (int i=0; i< listName.size(); i++) {
                data.add(new ValueDataEntry(listName.get(i), listBudget.get(i)));
                System.out.println(listName.get(i) + " " + listBudget.get(i));
            }
        } else {
            noStaffMessage();
        }

        monthlyPie.data(data);

    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        wholeLayout.setVisibility(View.INVISIBLE);
        tv_warnNoStaff.setVisibility(View.INVISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        wholeLayout.setVisibility(View.VISIBLE);
    }

    private void noStaffMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("There are no staff with Yearly Budget yet.")
                .setNeutralButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        onBackPressed();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void getCurrentDate() {
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        Date currentDate = new Date(System.currentTimeMillis());

        currentFullDateString = fullDateFormat.format(currentDate);

        currentDateString = dateFormat.format(currentDate);
        currentMonthString = monthFormat.format(currentDate);
        currentYearString = yearFormat.format(currentDate);
        currentDateInt = Integer.valueOf(currentDateString);
        currentMonthInt = Integer.valueOf(currentMonthString);
        currentYearInt = Integer.valueOf(currentYearString);
    }

    public void checkGetBudgetDetail() {
        if (doneGetBudgetDetail) {
            showChart();
        }
    }
}
