package xyz.voltwilz.econtact;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentDashboard extends Fragment {

    Button btnTransaction, btnBudgeting, btnReport, btnMaster, btnChartMonthly, btnChartYearly;
    RelativeLayout layoutBudget, layoutTransaction, layoutReport, layoutMaster, layoutChartMonthly, layoutChartYearly;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        layoutBudget  = v.findViewById(R.id.dashboard_layoutBudget);
        layoutTransaction = v.findViewById(R.id.dashboard_layoutTransaction);
        layoutReport = v.findViewById(R.id.dashboard_layoutReport);
        layoutMaster = v.findViewById(R.id.dashboard_layoutMaster);
        layoutChartMonthly = v.findViewById(R.id.dashboard_layoutChartMonthly);
        layoutChartYearly = v.findViewById(R.id.dashboard_layoutChartYearly);

        btnBudgeting = v.findViewById(R.id.dashboard_btnBudgeting);
        btnTransaction = v.findViewById(R.id.dashboard_btnTransaction);
        btnReport = v.findViewById(R.id.dashboard_btnReport);
        btnMaster = v.findViewById(R.id.dashboard_btnMaster);
        btnChartMonthly = v.findViewById(R.id.dashboard_btnChartMonthly);
        btnChartYearly = v.findViewById(R.id.dashboard_btnChartYearly);

        layoutBudget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityBudgeting.class));
            }
        });

        layoutTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityTransaction.class));
            }
        });

        layoutReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivitySumReport.class));
            }
        });

        layoutMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityMaster.class));
            }
        });

        layoutChartMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityChartMonthly.class));
            }
        });

        layoutChartYearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityChartYearly.class));
            }
        });

        btnBudgeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityBudgeting.class));
            }
        });

        btnTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityTransaction.class));
            }
        });

        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivitySumReport.class));
            }
        });

        btnChartMonthly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityChartMonthly.class));
            }
        });

        btnChartYearly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityChartYearly.class));
            }
        });

        btnMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ActivityMaster.class));
            }
        });

        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainApp) getActivity()).setActionBarTitle("Dashboard");
    }
}
