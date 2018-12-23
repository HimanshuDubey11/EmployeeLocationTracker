package project.himanshu.com.employeelocationtracker;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;

public class HomeActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout = findViewById(R.id.mylogintablayout);
        tabLayout.setupWithViewPager(viewPager);

        viewPager = findViewById(R.id.myloginviewpager);
        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager(),this));

        tabLayout.addTab(tabLayout.newTab().setText("Student"));
        tabLayout.addTab(tabLayout.newTab().setText("Admin"));

        tabLayout.setupWithViewPager(viewPager);



    }
}
