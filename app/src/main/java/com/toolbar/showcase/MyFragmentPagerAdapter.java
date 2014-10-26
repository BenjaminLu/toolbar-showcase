package com.toolbar.showcase;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.toolbar.showcase.fragment.RegisterClass;
import com.toolbar.showcase.fragment.TestClass;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter
{
    public MyFragmentPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public android.support.v4.app.Fragment getItem(int i)
    {
        switch (i) {
            case 0:
                return new RegisterClass();
            default:
                return new TestClass();
        }
    }

    @Override
    public int getCount()
    {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        CharSequence[] str = {"Menu", "UserRecord", "SymbolInfo"};
        return str[position];
    }
}
