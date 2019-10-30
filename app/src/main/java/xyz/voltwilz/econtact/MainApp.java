package xyz.voltwilz.econtact;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainApp extends AppCompatActivity {
    Integer selectedID = 0;
    Fragment selectedFragment = new FragmentDashboard();

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new FragmentDashboard()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    if (menuItem.getItemId() == R.id.nav_home && selectedID != R.id.nav_home) {
                        selectedFragment = new FragmentDashboard();
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
                            selectedFragment = new FragmentDashboard();
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

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

}
