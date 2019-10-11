package xyz.voltwilz.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import xyz.voltwilz.employee.ClassOnly.ManageUserAdapter;
import xyz.voltwilz.employee.ClassOnly.UserProfile;

public class FragmentManageUser extends Fragment implements ManageUserAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static final String EXTRA_KEYVALUE = "keyValue";

    DatabaseReference reference;
    //DatabaseReference getRef = FirebaseDatabase.getInstance().getReference("Users");
    RecyclerView recyclerView;
    FloatingActionButton fab_NewEmployee;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar manageUser_progressBar;
    CoordinatorLayout manageuser_wholeLayout;
    ArrayList<UserProfile> listUserProfiles;
    ArrayList keyValue;
    ManageUserAdapter adapter;

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_manage_user, container, false);


        setHasOptionsMenu(true);
        recyclerView = v.findViewById(R.id.manageUser_recView);
        fab_NewEmployee = v.findViewById(R.id.fab_newEmployee);
        //swipeRefreshLayout = v.findViewById(R.id.swipeRecView);
        manageuser_wholeLayout = v.findViewById(R.id.manageUser_wholeLayout);
        manageUser_progressBar = v.findViewById(R.id.manageUser_progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listUserProfiles = new ArrayList<UserProfile>();
        keyValue = new ArrayList<>();

        manageuser_wholeLayout.setVisibility(View.INVISIBLE);
        manageUser_progressBar.setVisibility(View.VISIBLE);

        //swipeRefreshLayout.setOnRefreshListener(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Staffs");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keyValue.clear();
                listUserProfiles.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    keyValue.add(dataSnapshot1.getKey());
                    UserProfile userProfile = dataSnapshot1.getValue(UserProfile.class);
                    listUserProfiles.add(userProfile);
                }
                adapter = new ManageUserAdapter(getActivity(), listUserProfiles);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(FragmentManageUser.this);

                manageuser_wholeLayout.setVisibility(View.VISIBLE);
                manageUser_progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Something went wrong with the database" , Toast.LENGTH_LONG).show();
            }
        });

        fab_NewEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NewEmployee.class));
            }
        });

        return v;
    }

    @Override
    public void onItemClick(int position) {
        UserProfile userProfile = listUserProfiles.get(position);
        String seletedKeyValue = keyValue.get(position).toString();
        Intent detailIntent = new Intent(getActivity(), Profile.class);
        detailIntent.putExtra(EXTRA_KEYVALUE, seletedKeyValue);
        startActivity(detailIntent);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.search_feature, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) searchItem.getActionView();
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void onRefresh() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                keyValue.clear();
                listUserProfiles.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    keyValue.add(dataSnapshot1.getKey());
                    UserProfile userProfile = dataSnapshot1.getValue(UserProfile.class);
                    listUserProfiles.add(userProfile);
                }
                adapter = new ManageUserAdapter(getActivity(), listUserProfiles);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(FragmentManageUser.this);

                manageuser_wholeLayout.setVisibility(View.VISIBLE);
                manageUser_progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Something went wrong with the database" , Toast.LENGTH_LONG).show();
            }
        });
    }
}
