package xyz.voltwilz.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentAccount extends Fragment {
    private FirebaseAuth mAuth;

    ListView listView;
    TextView tv_listView;
    String[] listItem;

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_account, container, false);

        mAuth = FirebaseAuth.getInstance();
        listView = v.findViewById(R.id.account_listView);
        tv_listView = v.findViewById(R.id.tv_AccountList);
        listItem = getResources().getStringArray(R.array.array_accountList);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, listItem);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String value = adapter.getItem(i);

                switch (i) {
                    /*case 0:
                        startActivity(new Intent(getActivity(), Profile.class));
                        break;
                    case 1:
                        mAuth.signOut();
                        startActivity(new Intent(getActivity(), LoginPage.class));
                        getActivity().finish();
                        break;*/
                    case 0:
                        mAuth.signOut();
                        startActivity(new Intent(getActivity(), LoginPage.class));
                        getActivity().finish();
                        break;

                }
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainApp) getActivity()).setActionBarTitle("Account");
    }
}
