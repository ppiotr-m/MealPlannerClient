package piotr.michalkiewicz.mealplannerclient.view.settings

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.mealplannerclient.R
import kotlinx.android.synthetic.main.activity_edit_allergies.*

class EditAllergiesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_allergies)

        setAutoCompleteTextView()
    }

    private fun setAutoCompleteTextView(){
        allergySearchET.setAdapter(ArrayAdapter<String>(
                this, android.R.layout.simple_dropdown_item_1line, getProductsMock()))
    }

    private fun getProductsMock(): List<String>{
        return listOf("Sunflower", "Beer", "Bread", "Rice", "Pasta", "Orange", "Kiwi", "Banana",
        "Avocado", "Watermelon", "Citron", "Lemon", "Fish", "Grenadine", "Homars", "Imbir",
                "Jalapeno", "Mango", "Strawberry", "Turkey", "Tuna")
    }
}