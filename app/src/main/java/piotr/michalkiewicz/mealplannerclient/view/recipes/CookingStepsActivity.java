package piotr.michalkiewicz.mealplannerclient.view.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import piotr.michalkiewicz.mealplannerclient.R;
import piotr.michalkiewicz.mealplannerclient.recipes.model.InstructionStep;

import static piotr.michalkiewicz.mealplannerclient.utils.ConstantValues.COOKING_STEPS_DATA;

public class CookingStepsActivity extends AppCompatActivity {

    List<InstructionStep> data;
    ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooking_steps);

        init();
    }

    private void init() {
        data = (ArrayList) getIntent().getSerializableExtra(COOKING_STEPS_DATA);

        if (data == null) throw new RuntimeException();

        assignUIElements();
        setOnClickListeners();
        initView();
    }

    private void setOnClickListeners() {
        findViewById(R.id.cookingModeBtn).setOnClickListener(v -> {
            Intent intent = new Intent(this, CookingModeActivity.class);
            intent.putExtra(COOKING_STEPS_DATA, (ArrayList) data);
            startActivity(intent);
        });
    }

    private void assignUIElements() {
        container = findViewById(R.id.cookingStepsContainerLayout);
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this);

        int counter = 1;

        for(InstructionStep step : data){
            View view = inflater.inflate(R.layout.cooking_step, null, false);
            ((TextView) view.findViewById(R.id.stepNrTV)).setText(String.valueOf(counter));
            counter+=1;
            ((TextView) view.findViewById(R.id.stepDescriptionTV)).setText(step.getStepInstruction());
            container.addView(view);
        }
    }
}
