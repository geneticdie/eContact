package xyz.voltwilz.employee;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentHome extends Fragment {

    Button btnTransaction, btnBudgeting;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);

        btnBudgeting = v.findViewById(R.id.home_btnBudgeting);
        btnTransaction = v.findViewById(R.id.home_btnTransaction);

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
