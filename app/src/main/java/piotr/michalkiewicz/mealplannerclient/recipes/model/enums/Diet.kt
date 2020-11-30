package piotr.michalkiewicz.mealplannerclient.recipes.model.enums

import com.google.gson.annotations.SerializedName

enum class Diet(val value: String) {
    @SerializedName("Standard")
    STANDARD("Standard"),

    @SerializedName("Vegetarian")
    VEGETARIAN("Vegetarian"),

    @SerializedName("Paleo")
    PALEO("Paleo"),

    @SerializedName("Flexitarian")
    FLEXITARIAN("Flexitarian"),

    @SerializedName("Pescatarian")
    PESCATARIAN("Pescatarian"),

    @SerializedName("Vegan")
    VEGAN("Vegan"),

    @SerializedName("LowCarb")
    LOWCARB("LowCarb"),

    @SerializedName("Ketogenic")
    KETOGENIC("Ketogenic");

    companion object {
        fun getValueByName(name: String): String? {
            val lowerCaseName = name.toLowerCase()

            values().forEach {
                if (lowerCaseName == it.value.toLowerCase()) {
                    return it.value
                }
            }
            return null
        }
    }

}