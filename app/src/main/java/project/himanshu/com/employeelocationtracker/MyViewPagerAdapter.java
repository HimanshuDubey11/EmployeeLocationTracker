package project.himanshu.com.employeelocationtracker;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public MyViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new Login();

            case 1:
                return  new SignUp();

        }

        return null;

    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {

            case 0 :
                return context.getString(R.string.login);
            case 1:
                return context.getString(R.string.signup);
        }

        return super.getPageTitle(position);
    }

}
