package piotr.michalkiewicz.mealplannerclient.view.adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import piotr.michalkiewicz.mealplannerclient.view.fragments.RecipeImgFragment;

public class RecipeImgFragmentAdapter extends FragmentStatePagerAdapter {

    private List <Integer> mListOfFragments;

    public RecipeImgFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public RecipeImgFragmentAdapter(FragmentManager fm, List content) {
        super(fm);
        mListOfFragments = content;
    }

    @Override
    public Fragment getItem(int position) {

        RecipeImgFragment fragment = new RecipeImgFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("drawable_id", mListOfFragments.get(position));
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return mListOfFragments.size();
    }
}
