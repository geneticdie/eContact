package xyz.voltwilz.employee;

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

    Button btnTransaction, btnBudgeting;
    RelativeLayout layoutBudget, layoutTransaction;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_dashboard, container, false);

        layoutBudget  = v.findViewById(R.id.dashboard_layoutBudget);
        layoutTransaction = v.findViewById(R.id.dashboard_layoutTransaction);
        btnBudgeting = v.findViewById(R.id.dashboard_btnBudgeting);
        btnTransaction = v.findViewById(R.id.dashboard_btnTransaction);

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

        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainApp) getActivity()).setActionBarTitle("Dashboard");
    }
}
