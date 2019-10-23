package xyz.voltwilz.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;

import java.util.ArrayList;
import java.util.List;

import static xyz.voltwilz.employee.ActivitySumReport.EXTRA_BUDGETVALUE;
import static xyz.voltwilz.employee.ActivitySumReport.EXTRA_NAMEVALUE;

public class ActivityChartMonthly extends AppCompatActivity {

    ArrayList<String> listName = new ArrayList<>();
    ArrayList<Integer> listBudget = new ArrayList<>();

    ProgressBar progressBar;
    LinearLayout wholeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_monthly);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Chart View");

        Intent intent = getIntent();
        listName = intent.getStringArrayListExtra(EXTRA_NAMEVALUE);
        listBudget = intent.getIntegerArrayListExtra(EXTRA_BUDGETVALUE);

        wholeLayout = findViewById(R.id.chartMonthly_wholeLayout);

        progressBar = findViewById(R.id.chartMonthly_progressBar);

        Pie monthlyPie = AnyChart.pie();
        monthlyPie.title("Distribution of Monthly Budget");

        monthlyPie.labels().position("outside");

        monthlyPie.legend().title().enabled(true);
        monthlyPie.legend().title()
                .text("Staffs name")
                .padding(0d, 0d, 10d, 0d);

        monthlyPie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        List<DataEntry> data = new ArrayList<>();

        for (int i=0; i< listName.size(); i++) {
            data.add(new ValueDataEntry(listName.get(i), listBudget.get(i)));
            System.out.println(listName.get(i) + " " + listBudget.get(i));
        }

//        data.add(new ValueDataEntry("Apple", 100));
//        data.add(new ValueDataEntry("Orange", 200));
//        data.add(new ValueDataEntry("Lime", 300));
//        data.add(new ValueDataEntry("Mango", 250));
//        data.add(new ValueDataEntry("Grape", 50));
//        data.add(new ValueDataEntry("Yogurt", 70));
//        data.add(new ValueDataEntry("Strawberry", 80));

        monthlyPie.data(data);

        AnyChartView budgetMonthlyChart = findViewById(R.id.chartViewMonthly);
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

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        wholeLayout.setVisibility(View.INVISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        wholeLayout.setVisibility(View.VISIBLE);
    }
}
