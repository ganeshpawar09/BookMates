package com.example.bookmates;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class _13_View_Pager_Adapter extends FragmentPagerAdapter {
    public _13_View_Pager_Adapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public _13_View_Pager_Adapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0)
        {
            return new _11_Add_Books();
        }
        else
        {
            return new _12_Add_Flats();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0)
        {
            return "Add Book";
        }
        else
        {
            return "Add Flat";
        }
    }
}
