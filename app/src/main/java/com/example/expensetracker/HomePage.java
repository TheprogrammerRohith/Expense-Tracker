package com.example.expensetracker;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;

    private DashboardFragment dashboardFragment;
    private IncomeFragment incomeFragment;
    private ExpenseFragment expenseFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        bottomNavigationView=findViewById(R.id.btmnav);
        frameLayout=findViewById(R.id.main_frame);
        dashboardFragment=new DashboardFragment();
        incomeFragment=new IncomeFragment();
        expenseFragment=new ExpenseFragment();
        //default
        setFragment(dashboardFragment);
        bottomNavigationView.getMenu().findItem(R.id.dashboard).setIcon(R.drawable.baseline_dashboard_24);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                resetIconState(bottomNavigationView);
                int itemId = item.getItemId();
                if(itemId==R.id.dashboard){
                    setFragment(dashboardFragment);
                    item.setIcon(R.drawable.baseline_dashboard_24);
                    return true;
                }
                else if (itemId==R.id.income) {
                    setFragment(incomeFragment);
                    item.setIcon(R.drawable.outline_add_circle_24);
                    return true;
                }
                else if (itemId==R.id.expense) {
                    setFragment(expenseFragment);
                    item.setIcon(R.drawable.baseline_remove_circle_24);
                    return true;
                }
                else{
                    return false;
                }
            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }

    private void resetIconState(BottomNavigationView bottomNavigationView) {
        Menu menu = bottomNavigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            int itemId=item.getItemId();
            if(itemId==R.id.dashboard){
                item.setIcon(R.drawable.outline_dashboard_24);
            }
            else if (itemId==R.id.income) {
                item.setIcon(R.drawable.baseline_add_circle_outline_24);
            }
            else if (itemId==R.id.expense) {
                item.setIcon(R.drawable.baseline_remove_circle_outline_24);
            }
        }
    }
}
