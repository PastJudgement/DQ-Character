package com.eligustilo.dqcharactersheet

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CharacterSheetPageOne : Fragment() {
    val TAG = "CharacterSheetPageOne"
    var PS = 5
    var MD = 5
    var AG = 5
    var MA = 5
    var WP = 5
    var EN = 5
    var FT = 5
    var PC = 5
    var TMR = 5
    var PB = 5
    var DEF = 5
    var PROT = 5
    var INIT = 5

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.character_sheet_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set character basics
        val characterName = view.findViewById<TextView>(R.id.character_name_textview)
        val imageView = view.findViewById<ImageView>(R.id.character_imageview)
        imageView.setImageResource(R.mipmap.ic_launcher_foreground)
        val psTextView = view.findViewById<TextView>(R.id.ps_textview)
        val mdTextView = view.findViewById<TextView>(R.id.md_textview)
        val agTextView = view.findViewById<TextView>(R.id.ag_textview)
        val maTextView = view.findViewById<TextView>(R.id.ma_textview)
        val wpTextView = view.findViewById<TextView>(R.id.wp_textview)
        val enTextView = view.findViewById<TextView>(R.id.en_textview)
        val ftTextView = view.findViewById<TextView>(R.id.ft_textview)
        val pcTextView = view.findViewById<TextView>(R.id.pc_textview)
        val tmrTextView = view.findViewById<TextView>(R.id.tmr_textview)
        val pbTextView = view.findViewById<TextView>(R.id.pb_textview)
        val defTextView = view.findViewById<TextView>(R.id.def_textview)
        val protTextView = view.findViewById<TextView>(R.id.prot_textview)
        val initTextView = view.findViewById<TextView>(R.id.init_textview)




        //sets saved info if it exists
        Log.d(TAG, "${DataMaster.loadCharacterDetails()}")
        if (DataMaster.dataDetailsCache?.Name != null){
            Log.d(TAG, "${DataMaster.loadCharacterDetails()}")
            val savedData = DataMaster.loadCharacterDetails()
            characterName.text = savedData.Name
            imageView.setImageResource(savedData.Avatar)
        }

        if (DataMaster.dataStatsCache != null){
            val savedData = DataMaster.loadCharacterStats()
            psTextView.text = "PS = ${savedData.PS}"
//            mdTextView.text = "PS = ${savedData.PS}"
//            agTextView.text = "PS = ${savedData.PS}"
//            maTextView.text = "PS = ${savedData.PS}"
//            wpTextView.text = "PS = ${savedData.PS}"
//            enTextView.text = "PS = ${savedData.PS}"
//            ftTextView.text = "PS = ${savedData.PS}"
//            pcTextView.text = "PS = ${savedData.PS}"
//            tmrTextView.text = "PS = ${savedData.PS}"
//            pbTextView.text = "PS = ${savedData.PS}"
//            defTextView.text = "PS = ${savedData.PS}"
//            protTextView.text = "PS = ${savedData.PS}"
        }


        //set onClickListeners
        characterName.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER || event.keyCode == KeyEvent.ACTION_DOWN ) {
                Log.d(TAG, v.text.toString())
                characterName.text = v.text.trim() // remove the return
                // closes the keyboard
                val imm: InputMethodManager? = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if(imm != null) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                // makes it so the nameTextView is not focused object
                v.clearFocus()
                Log.d(TAG, "character name is ${characterName.text}")
                DataMaster.saveCharacterDetails(characterName.text.toString(), R.mipmap.test)
                true //done
            }
            false
        }

        psTextView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER || event.keyCode == KeyEvent.ACTION_DOWN ) {
                Log.d(TAG, v.text.toString())
                psTextView.text = v.text.trim() // remove the return
                // closes the keyboard
                val imm: InputMethodManager? = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if(imm != null) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                // makes it so the nameTextView is not focused object
                v.clearFocus()
                PS = psTextView.text.toString().toInt() //TODO can crash if not proper INT
                Log.d(TAG, "character PS is ${psTextView.text}")
                DataMaster.saveCharacterStats(PS,MD,AG,MA,WP,EN, FT, PC, TMR, PB, DEF, PROT)
                psTextView.text = "PS = ${psTextView.text}"
                true //done
            }
            false
        }

    }
}