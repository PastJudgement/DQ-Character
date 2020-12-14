package com.eligustilo.dqcharactersheet

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


data class characterStatsDataClass (
    val PS: Int,
    val MD: Int,
    val AG: Int,
    val MA: Int,
    val WP: Int,
    val EN: Int,
    val FT: Int,
    val PC: Int,
    val TMR: Int,
    val PB: Int,
    val DEF: Int,
    val PROT: Int
)

data class characterDetailsDataClass(
    val Name: String?,
    val Avatar: Int?
)

object DataMaster {
    val TAG = "DataMaster"
    private lateinit var context: Context
    private val dataMasterSaveStatsKey = "keyStats"
    private val dataMasterSaveDetailsKey = "keyDetails"
    private val dataMasterSaveRanksKey = "keyRanks"

    var dataStatsCache: characterStatsDataClass? = null
    var dataDetailsCache: characterDetailsDataClass? = null


    init {
    }

    fun loadDataMaster(newContext: Context) {
        context = newContext
        loadCharacterDetails()
        loadCharacterStats()
        loadCharacterRanks()
    }

    fun saveCharacterDetails(name: String?, avatarPicture: Int){
        val userDefaults: SharedPreferences = context.getSharedPreferences(dataMasterSaveDetailsKey, 0)
        val editor = userDefaults.edit()
        val characterDetailsString = "{\n" +
                "  \"Name\": \"$name\",\n" +
                "  \"avatarNumber\": \"$avatarPicture\"\n" +
                "}"
        Log.d(TAG, "Data to be saved is $characterDetailsString")
        editor.putString(dataMasterSaveDetailsKey, characterDetailsString)  //CAN I save a class and then send it to another file and use it? Do I need an interface or just make it an open?
        editor.apply()
        dataDetailsCache = characterDetailsDataClass(name, avatarPicture)
    }

    fun saveCharacterStats(PS: Int, MD: Int, AG: Int, MA: Int, WP: Int, EN: Int, FT: Int, PC: Int, TMR: Int, PB: Int, DEF: Int, PROT: Int){
        val userDefaults: SharedPreferences = context.getSharedPreferences(dataMasterSaveStatsKey, 0)
        val editor = userDefaults.edit()
        val characterStatsString = "{\n" +
                "  \"PS\": $PS,\n" +
                "  \"MD\": $MD,\n" +
                "  \"AG\": $AG,\n" +
                "  \"MA\": $MA,\n" +
                "  \"WP\": $WP,\n" +
                "  \"EN\": $EN,\n" +
                "  \"FT\": $FT,\n" +
                "  \"PC\": $PC,\n" +
                "  \"TMR\": $TMR,\n" +
                "  \"PB\": $PB,\n" +
                "  \"DEF\": $DEF,\n" +
                "  \"PROT\": $PROT\n" +
                "}"
        Log.d(TAG, "Character stats to be saved is $characterStatsString")
        editor.putString(dataMasterSaveStatsKey, characterStatsString)
        editor.apply()
        dataStatsCache = characterStatsDataClass(PS,MD,AG,MA,WP,EN,FT,PC,TMR,PB,DEF,PROT)
    }

    fun saveCharacterRanks(){

    }

    fun loadCharacterDetails(): characterDetailsDataClass{
        val userDefaults: SharedPreferences = context.getSharedPreferences(dataMasterSaveDetailsKey, 0)
        val savedCharacterDetails = userDefaults.getString(dataMasterSaveDetailsKey, null)
        Log.d(TAG, "The loaded details raw are $savedCharacterDetails")
        if (savedCharacterDetails != null){
            val gsonParser = Gson()
            val gsonDataType: Type = object: TypeToken<characterDetailsDataClass>() {}.type //So i can make a data class object but can I use it else where? Can I return it?
            val gsonResults: characterDetailsDataClass = gsonParser.fromJson(savedCharacterDetails, gsonDataType)
            if (gsonResults != null) {
                dataDetailsCache = gsonResults
            }
            Log.d(TAG, "gson results are $dataDetailsCache")
            if (dataDetailsCache != null){
                return dataDetailsCache as characterDetailsDataClass
            }
        }
        return characterDetailsDataClass("should never happen", 0)
    }

    fun loadCharacterStats(): characterStatsDataClass {
        val userDefaults: SharedPreferences = context.getSharedPreferences(dataMasterSaveStatsKey, 0)
        val savedCharacterStats = userDefaults.getString(dataMasterSaveStatsKey, null)
        Log.d(TAG, "The loaded stats raw are $savedCharacterStats")
        if (savedCharacterStats != null){
            val gsonParser = Gson()
            val gsonDataType: Type = object: TypeToken<characterStatsDataClass>() {}.type
            val gsonResults: characterStatsDataClass = gsonParser.fromJson(savedCharacterStats, gsonDataType)
            if (gsonResults != null) {
                dataStatsCache = gsonResults
            }
            Log.d(TAG, "gson results are $dataStatsCache")
            if (dataStatsCache != null){
                return dataStatsCache as characterStatsDataClass
            }
        }

        return characterStatsDataClass(0,0,0,0,0,0,0,0,0,0,0,0)
    }

    fun loadCharacterRanks(){

    }
}