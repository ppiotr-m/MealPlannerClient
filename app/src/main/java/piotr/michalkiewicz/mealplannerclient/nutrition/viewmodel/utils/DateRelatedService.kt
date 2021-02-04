package piotr.michalkiewicz.mealplannerclient.nutrition.viewmodel.utils

import java.time.LocalDate

interface DateRelatedService {

    fun updateAccordingToDate(date: LocalDate)
}