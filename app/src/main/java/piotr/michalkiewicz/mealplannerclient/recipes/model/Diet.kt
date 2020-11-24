package piotr.michalkiewicz.mealplannerclient.recipes.model

enum class Diet(val value: String) {
    STANDARD("Standard"),
    VEGETARIAN("Vegetarian"),
    PALEO("Paleo"),
    FLEXITARIAN("Flexitarian"),
    PESCATARIAN("Pescatarian"),
    VEGAN("Vegan"),
    LOWCARB("LowCarb"),
    KETOGENIC("Ketogenic");

    companion object{
        fun getValueByName(name: String): String?{
            val lowerCaseName = name.toLowerCase()

            values().forEach {
                if(lowerCaseName == it.value.toLowerCase()){
                    return it.value
                }
            }
            return null
        }
    }

}