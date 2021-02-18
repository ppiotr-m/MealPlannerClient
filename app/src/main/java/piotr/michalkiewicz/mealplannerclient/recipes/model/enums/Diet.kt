package piotr.michalkiewicz.mealplannerclient.recipes.model.enums

import com.google.gson.annotations.SerializedName

enum class Diet(val value: String) {
    @SerializedName("standard")
    STANDARD("standard"),

    @SerializedName("vegetarian")
    VEGETARIAN("vegetarian"),

    @SerializedName("paleo")
    PALEO("paleo"),

    @SerializedName("flexitarian")
    FLEXITARIAN("flexitarian"),

    @SerializedName("pescatarian")
    PESCATARIAN("pescatarian"),

    @SerializedName("vegan")
    VEGAN("vegan"),

    @SerializedName("lowCarb")
    LOWCARB("lowCarb"),

    @SerializedName("ketogenic")
    KETOGENIC("ketogenic");

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