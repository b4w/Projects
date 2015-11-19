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
    private Map<Integer, List<Integer>> sideProblemsMap;

    public ProblemsPagerAdapter(FragmentManager fm, Map<Integer, List<Integer>> sideProblemsMap) {
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
        List<Integer> problems = null;
        final Bundle args = new Bundle();

        int index = 0;
        // т.к. map -> linkedHashMap - берем значение по индексу.
        for (Integer item : sideProblemsMap.keySet()) {
            if (position == index) {
                problems = sideProblemsMap.get(item);
                args.putInt(ICommonDtoConstants.SIDE_ID, item);
                break;
            }
            index++;
        }

        if (problems != null) {
            int[] array = new int[problems.size()];
            for (int i = 0; i < problems.size(); i++) {
                array[i] = problems.get(i);
            }
            args.putIntArray(ICommonDtoConstants.PROBLEM_NUMBERS, array);
        }

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return numberOfPages;
    }
}
