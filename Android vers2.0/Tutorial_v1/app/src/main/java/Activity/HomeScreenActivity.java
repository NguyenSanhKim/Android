package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tutorial_v1.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;

import Model.UserAccount;
import dmax.dialog.SpotsDialog;
import retrofit2.Retrofit;
import Retrofit.IMyService;
import Retrofit.RetrofitClient;
import Activity.Account_Fragment;

public class HomeScreenActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    IMyService iMyService;
    AlertDialog alertDialog;
    SharedPreferences sharedPreferences;
    public UserAccount userAccount=new UserAccount();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        bottomNav = findViewById(R.id.navBottom);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        alertDialog= new SpotsDialog.Builder().setContext(this).build();
        Retrofit retrofitClient= RetrofitClient.getInstance();
        iMyService=retrofitClient.create(IMyService.class);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }
    private  BottomNavigationView.OnNavigationItemSelectedListener
        navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.navHome:
                    selectedFragment = new Home_Fragment();
                    break;
                case R.id.navSearch:
                    selectedFragment = new Home_Fragment();
                    break;
                case R.id.navCourse:
                    selectedFragment = new Home_Fragment();
                    break;
                case R.id.navCart:
                    selectedFragment = new Home_Fragment();
                    break;
                case R.id.navAccount:
                    selectedFragment = new Account_Fragment(userAccount);

                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.flagmentComtaint,selectedFragment).commit();
            return true;
        }
    };
}