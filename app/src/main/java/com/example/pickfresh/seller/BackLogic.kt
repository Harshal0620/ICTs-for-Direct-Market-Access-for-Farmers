package com.example.pickfresh.seller

import android.icu.util.Calendar
import android.os.Build
import androidx.annotation.RequiresApi

class BackLogic {
    enum class Month(val monthNumber: Int) {
        JANUARY(1), FEBRUARY(2), MARCH(3), APRIL(4), MAY(5),
        JUNE(6), JULY(7), AUGUST(8), SEPTEMBER(9), OCTOBER(10),
        NOVEMBER(11), DECEMBER(12)
    }

    data class ClimaticCondition(val temperature: String, val humidity: String)

    class Crop(val name: String, val preferredConditions: ClimaticCondition)

    val cropConditions = listOf(
        Crop("Wheat", ClimaticCondition("Cool", "Medium")),
        Crop("Rice", ClimaticCondition("Hot", "High")),
        Crop("Maize", ClimaticCondition("Warm", "Medium")),
        Crop("Barley", ClimaticCondition("Cool", "Low")),
        Crop("Cotton", ClimaticCondition("Hot", "Medium")),
        Crop("Soybean", ClimaticCondition("Warm", "Medium")),
        Crop("Oats", ClimaticCondition("Cool", "Medium")),
        Crop("Sugarcane", ClimaticCondition("Hot", "High"))
    )

    val monthConditions = mapOf(
        Month.JANUARY to ClimaticCondition("Cold", "Low"),
        Month.FEBRUARY to ClimaticCondition("Cold", "Low"),
        Month.MARCH to ClimaticCondition("Warm", "Medium"),
        Month.APRIL to ClimaticCondition("Hot", "Medium"),
        Month.MAY to ClimaticCondition("Hot", "High"),
        Month.JUNE to ClimaticCondition("Hot", "High"),
        Month.JULY to ClimaticCondition("Hot", "High"),
        Month.AUGUST to ClimaticCondition("Warm", "Medium"),
        Month.SEPTEMBER to ClimaticCondition("Warm", "Medium"),
        Month.OCTOBER to ClimaticCondition("Cool", "Low"),
        Month.NOVEMBER to ClimaticCondition("Cool", "Low"),
        Month.DECEMBER to ClimaticCondition("Cold", "Low")
    )

    fun recommendCropsForMonth(month: Month): List<String> {
        val monthClimaticCondition = monthConditions[month] ?: return emptyList()
        val recommendedCrops = cropConditions.filter { crop ->
            crop.preferredConditions.temperature == monthClimaticCondition.temperature &&
                    crop.preferredConditions.humidity == monthClimaticCondition.humidity
        }
        return recommendedCrops.map { it.name }
    }

    fun displayRecommendationsForMonth(month: Month): String {
        val recommendations = recommendCropsForMonth(month)
        return if (recommendations.isEmpty()) {
            "No crops recommended for ${month.name}."
        } else {
            ("${month.name} Best Crop: ${recommendations.joinToString(", ")}")
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun pickTheMonth(): String {
        val calender = java.util.Calendar.getInstance()
        calender.timeInMillis = System.currentTimeMillis()
        val month = calender.get(Calendar.MONTH + 1)
        when (month) {
            1 -> {
                Month.JANUARY
            }

            2 -> {
                Month.FEBRUARY
            }

            3 -> {
                Month.MARCH
            }

            4 -> {
                Month.APRIL
            }

            5 -> {
                Month.MAY
            }

            6 -> {
                Month.JUNE
            }

            7 -> {
                Month.JULY
            }

            8 -> {
                Month.AUGUST
            }

            9 -> {
                Month.SEPTEMBER
            }

            10 -> {
                Month.OCTOBER
            }

            11 -> {
                Month.NOVEMBER
            }

            12 -> {
                Month.DECEMBER
            }

            else -> {
                Month.DECEMBER
            }
        }.let {
           return displayRecommendationsForMonth(it)
        }
    }

}