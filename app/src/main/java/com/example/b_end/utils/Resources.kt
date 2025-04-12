package com.example.b_end.utils

import android.content.Context
import com.example.b_end.R

object Resources {
    fun getDisasters(context: Context): List<String> {
        return context.resources.getStringArray(R.array.disasters).toList()
    }

    fun getBunkers(context: Context): List<String> {
        return context.resources.getStringArray(R.array.bunkers).toList()
    }

    fun getProfessions(context: Context): List<String> {
        return context.resources.getStringArray(R.array.professions_array).toList()
    }

    fun getHealthConditions(context: Context): List<String> {
        return context.resources.getStringArray(R.array.health_array).toList()
    }

    fun getPersonalityTraits(context: Context): List<String> {
        return context.resources.getStringArray(R.array.character_array).toList()
    }

    fun getBiology(context: Context): List<String> {
        return context.resources.getStringArray(R.array.sex_array).toList()
    }

    fun getLuggage(context: Context): List<String> {
        return context.resources.getStringArray(R.array.baggage_array).toList()
    }

    fun getFacts(context: Context): List<String> {
        return context.resources.getStringArray(R.array.facts_array).toList()
    }
}