package xyz.voltwilz.employee;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import xyz.voltwilz.employee.ClassOnly.UserProfile;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainApp extends AppCompatActivity {
    Integer selectedID = 0;
    Fragment selectedFragment = new FragmentHome();

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new FragmentHome()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    if (menuItem.getItemId() == R.id.nav_home && selectedID != R.id.nav_home) {
                        selectedFragment = new FragmentHome();
                        selectedID = R.id.nav_home;
                    } else if (menuItem.getItemId() == R.id.nav_manageUser && selectedID != R.id.nav_manageUser) {
                        selectedFragment = new FragmentManageUser();
                        selectedID = R.id.nav_manageUser;
                    } else if (menuItem.getItemId() == R.id.nav_account && selectedID != R.id.nav_account) {
                        selectedFragment = new FragmentAccount();
                        selectedID = R.id.nav_account;
                    }

                    /*switch (menuItem.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new FragmentHome();
                            break;
                        case R.id.nav_manageUser:
                            selectedFragment = new FragmentManageUser();
                            break;
                        case R.id.nav_account:
                            selectedFragment = new FragmentAccount();
                            break;
                    }*/

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

}
