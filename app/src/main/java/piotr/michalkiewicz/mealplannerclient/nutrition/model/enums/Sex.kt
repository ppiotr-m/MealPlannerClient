package piotr.michalkiewicz.mealplannerclient.nutrition.model.enums

import com.google.gson.annotations.SerializedName

//  TODO Switch to user.enums.Sex
enum class Sex(val value: String) {
    @SerializedName("Male")
    MALE("Male"),

    @SerializedName("Female")
    FEMALE("Female"),

    @SerializedName("Child")
    CHILD("Child");

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