package com.triangularlake.constantine.triangularlake.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.fragments.SideFragment;

import java.util.List;
import java.util.Map;

public class ProblemsPagerAdapter extends FragmentPagerAdapter {

    private int numberOfPages;
    private Map<Long, List<Long>> sideProblemsMap;

    public ProblemsPagerAdapter(FragmentManager fm, Map<Long, List<Long>> sideProblemsMap) {
        super(fm);
        this.sideProblemsMap = sideProblemsMap;
        // TODO: посмотреть ошибку, иногда sideProblemsMap == null
        if (sideProblemsMap != null) {
            this.numberOfPages = sideProblemsMap.size();
        }
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = SideFragment.newInstance();
        List<Long> problems = null;
        final Bundle args = new Bundle();

        int index = 0;
        // т.к. map -> linkedHashMap - берем значение по индексу.
        for (Long item : sideProblemsMap.keySet()) {
            if (position == index) {
                problems = sideProblemsMap.get(item);
                args.putLong(ICommonDtoConstants.SIDE_ID, item);
                break;
            }
            index++;
        }

        long[] array = new long[problems.size()];
        for (int i = 0; i < problems.size(); i++) {
            array[i] = problems.get(i);
        }

        args.putLongArray(ICommonDtoConstants.PROBLEM_NUMBERS, array);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return numberOfPages;
    }
}
