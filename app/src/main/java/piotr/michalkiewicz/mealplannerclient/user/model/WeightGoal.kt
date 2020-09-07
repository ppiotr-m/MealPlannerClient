package piotr.michalkiewicz.mealplannerclient.user.model

import java.io.Serializable

data class WeightGoal(val value: Float = 0f, val losingWeight: Boolean) : Serializable