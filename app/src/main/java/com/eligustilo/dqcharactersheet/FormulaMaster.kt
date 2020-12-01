package com.eligustilo.dqcharactersheet

import android.content.Context
import android.util.Log

object FormulaMaster {
    private val TAG = "FormulaMaster"
    private lateinit var context: Context

    fun loadFormulaMaster(newContext: Context) {
        context = newContext
    }

    fun meleeAttack(baseChance: Int, modifiedAgility: Int, rankWithWeapon: Int): Int{
        val chanceToHit = baseChance + modifiedAgility + (rankWithWeapon * 4)
        Log.d(TAG, "The chance to hit for the melee attack is: $chanceToHit")
        return chanceToHit
    }

}