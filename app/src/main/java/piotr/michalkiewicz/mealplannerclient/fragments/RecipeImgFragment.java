package piotr.michalkiewicz.mealplannerclient.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import piotr.michalkiewicz.mealplannerclient.support.Constants;
import piotr.michalkiewicz.mealplannerclient.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeImgFragment extends Fragment {

    ImageView singleImageView;
    int mDrawableId;

    public RecipeImgFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDrawableId = getArguments() != null ? getArguments().getInt("drawable_id") : 1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_recipe_img, container, false);

        singleImageView = (ImageView) v.findViewById(R.id.singleRecipeImgView);
        Drawable d = ResourcesCompat.getDrawable(getActivity().getResources(),
                mDrawableId, null);
        Log.d(Constants.TAG, d.toString());
        singleImageView.setImageDrawable(d);
        return  v;

    }

}
