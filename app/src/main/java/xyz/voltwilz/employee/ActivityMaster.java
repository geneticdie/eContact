package xyz.voltwilz.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import xyz.voltwilz.employee.ClassOnly.AdapterMasterCategories;
import xyz.voltwilz.employee.ClassOnly.UserProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityMaster extends AppCompatActivity {

    DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
    DatabaseReference masterRef = mRoot.child("Master");

    ArrayList<String> listCategories = new ArrayList<>();
    List<String> listInsideCategory = new ArrayList<>();

    private AdapterMasterCategories adapterMasterCategories;

    ProgressBar progressBar;

    LinearLayout wholeLayout, layoutCategory, layoutListInCategory;

    RecyclerView recyclerView;

    SpinnerDialog spinnerDialogCategory;

    TextView tv_category, tv_listIn;

    String strSelectedCategory;

    Button btnAddList;

    Boolean filledCategory;
    Boolean doneReadCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Master");

        wholeLayout = findViewById(R.id.master_wholeLayout);
        layoutCategory = findViewById(R.id.master_layoutCategory);
        layoutListInCategory = findViewById(R.id.master_layoutListInCategory);

        adapterMasterCategories = new AdapterMasterCategories(listInsideCategory);

        tv_category = findViewById(R.id.master_selectedCategory);
        tv_listIn = findViewById(R.id.master_tvListIn);

        recyclerView = findViewById(R.id.master_recViewListInCategory);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapterMasterCategories);

        progressBar = findViewById(R.id.master_progressBar);

        btnAddList = findViewById(R.id.master_btnAddList);

        spinnerDialogCategory = new SpinnerDialog(ActivityMaster.this, listCategories, "Select Category", R.style.DialogAnimations_SmileWindow , "Close");
        spinnerDialogCategory.setCancellable(true);
        spinnerDialogCategory.setShowKeyboard(false);
        spinnerDialogCategory.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                strSelectedCategory = item;
                tv_category.setText(strSelectedCategory);
                tv_listIn.setText("List in " + strSelectedCategory);
                btnAddList.setText("Add " + strSelectedCategory);
                filledCategory = true;
                checkingFilledInitialInput();
            }
        });

        layoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialogCategory.showSpinerDialog();
            }
        });

        btnAddList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        hideListInCategory();
        showProgressBar();
        readAllCategories();
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

    public void checkingReadingCategories() {
        if (doneReadCategories) {
            hideProgressBar();
        }
    }

    public void checkingFilledInitialInput() {
        if (filledCategory) {
            showListInCategory();
        }
    }

    public void showAddDialog() {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_list_category, null);

        TextView textView = dialogView.findViewById(R.id.textView);
        final EditText editText = dialogView.findViewById(R.id.edt_comment);
        Button btn1 = dialogView.findViewById(R.id.buttonSubmit);
        Button btn2 = dialogView.findViewById(R.id.buttonCancel);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> addListCategory = new HashMap<>();
                addListCategory.put(editText.getText().toString(), true);
                masterRef.child(strSelectedCategory).updateChildren(addListCategory);
                dialogBuilder.dismiss();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });

        textView.setText("Add new " + strSelectedCategory);

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    public void readAllCategories() {
        doneReadCategories = false;
        masterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCategories.clear();
                if (dataSnapshot != null) {
                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                        listCategories.add(dataSnapshot1.getKey());
                    }
                }

                doneReadCategories = true;
                checkingReadingCategories();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void readListInCategory() {
        masterRef.child(strSelectedCategory).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listInsideCategory.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    if (dataSnapshot1.getValue().toString().equals("true")) {
                        listInsideCategory.add(dataSnapshot1.getKey());
                    }
                }
                adapterMasterCategories = new AdapterMasterCategories(listInsideCategory);
                recyclerView.setAdapter(adapterMasterCategories);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        wholeLayout.setVisibility(View.INVISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        wholeLayout.setVisibility(View.VISIBLE);
    }

    public void showListInCategory() {
        layoutListInCategory.setVisibility(View.VISIBLE);
        readListInCategory();
    }

    public void hideListInCategory() {
        layoutListInCategory.setVisibility(View.GONE);
    }

}
