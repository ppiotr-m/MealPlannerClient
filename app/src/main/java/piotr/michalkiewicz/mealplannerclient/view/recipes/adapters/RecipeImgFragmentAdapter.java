package piotr.michalkiewicz.mealplannerclient.view.recipes.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class RecipeImgFragmentAdapter extends FragmentStateAdapter {

    private List <Integer> mListOfFragments;

    public RecipeImgFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return null;
    }

    @Override
    public int getItemCount() {
        return 0;
    }
/*
    public RecipeImgFragmentAdapter(FragmentManager fm, List content) {
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

 */
}
