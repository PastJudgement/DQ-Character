package com.eligustilo.dqcharactersheet

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class CharacterSheetPageOne : Fragment() {
    private lateinit var viewModel: CharSheetOneViewModel
    var characterName = "" //comment on usage
    var characterAvatarIndex = 0
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
    var DEF = 0
    var PROT = 0
    var INIT = 5

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //set up viewModel
        viewModel = ViewModelProvider(this).get(CharSheetOneViewModel::class.java)
        viewModel.mutableCharacterName.observe(viewLifecycleOwner, Observer {
            var mutableCharName = it
            Log.d(TAG, "Incoming Name is $mutableCharName")
        })
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.character_sheet_one, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set character basics
        val characterName = view.findViewById<TextView>(R.id.character_name_textview)
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
        val imageView = view.findViewById<ImageView>(R.id.character_imageview)

        //avatar stuff and context stuff
        val characterAvatarArray = this.requireContext()?.resources?.obtainTypedArray(R.array.avatar_ids)
        if (characterAvatarArray != null){
            val charDetails = DataMaster.loadCharacterDetails()
            if (charDetails.Avatar != null) {
                characterAvatarIndex = charDetails.Avatar
            } else {
                characterAvatarIndex = 0
            }
            val characterAvatarID = characterAvatarArray.getResourceId(characterAvatarIndex,R.mipmap.test)
            imageView.setImageResource(characterAvatarID)
        }

        //sets saved info if it exists

        if (DataMaster.dataDetailsCache?.Name != null){
            Log.d(TAG, "${DataMaster.loadCharacterDetails()}")
            val savedData = DataMaster.loadCharacterDetails()
            characterName.text = savedData.Name
            if (savedData.Avatar != null && characterAvatarArray != null){
                if (savedData.Avatar != null) {
                    characterAvatarIndex = savedData.Avatar
                } else {
                    characterAvatarIndex = 0
                }
                val characterAvatarID = characterAvatarArray.getResourceId(characterAvatarIndex,R.mipmap.test)
                imageView.setImageResource(characterAvatarID)
            }
        }

        if (DataMaster.dataStatsCache != null){
            val savedData = DataMaster.loadCharacterStats()
            psTextView.text = "PS: ${savedData.PS}"
            mdTextView.text = "MD: ${savedData.MD}"
            agTextView.text = "AG: ${savedData.AG}"
            maTextView.text = "MA: ${savedData.MA}"
            wpTextView.text = "WP: ${savedData.WP}"
            enTextView.text = "EN: ${savedData.EN}"
            ftTextView.text = "FT: ${savedData.FT}"
            pcTextView.text = "PC: ${savedData.PC}"
            tmrTextView.text = "TMR: ${savedData.TMR}"
            pbTextView.text = "PB: ${savedData.PB}"
            defTextView.text = "DEF: ${savedData.DEF}"
            protTextView.text = "PROT: ${savedData.PROT}"
        }


        //set onClickListeners
        characterName.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                Log.d(TAG, "Clicked")
                characterName.text = ""
                showKeyboard()
            } // Instead of your Toast
        })
        characterName.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER || event.keyCode == KeyEvent.ACTION_DOWN ) {
                Log.d(TAG, v.text.toString())
                characterName.text = v.text.trim() // remove the return
                Log.d(TAG, "character name is ${v.text.toString()}")
                DataMaster.saveCharacterDetails(v.text.toString(), characterAvatarIndex)

                // closes the keyboard
                val imm: InputMethodManager? = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if(imm != null) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                // makes it so the nameTextView is not focused object
                v.clearFocus()
                true //done
            }
            false
        }

        imageView.setOnClickListener(){
            Log.d(TAG, "ImageViewAvatar Clicked")
        }

        psTextView.setOnTouchListener(OnTouchListener { v, event ->
            Log.d(TAG, "Clicked")
            psTextView.text = ""
            false
        })
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
                try {
                    PS = psTextView.text.toString().toInt() //TODO can crash if not proper INT
                }catch (nfe: NumberFormatException){
                    Snackbar.make(view, "Stats must be a number. Ex: 15", Snackbar.LENGTH_LONG)
                        .setAction("", null).show()
                }
                Log.d(TAG, "character PS is ${psTextView.text}")
                DataMaster.saveCharacterStats(PS,MD,AG,MA,WP,EN, FT, PC, TMR, PB, DEF, PROT)
                psTextView.text = "PS: ${psTextView.text}"
                true //done
            }
            false
        }

        mdTextView.setOnTouchListener(OnTouchListener { v, event ->
            Log.d(TAG, "Clicked")
            mdTextView.text = ""
            false
        })
        mdTextView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER || event.keyCode == KeyEvent.ACTION_DOWN ) {
                Log.d(TAG, v.text.toString())
                mdTextView.text = v.text.trim() // remove the return
                // closes the keyboard
                val imm: InputMethodManager? = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if(imm != null) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                // makes it so the nameTextView is not focused object
                v.clearFocus()
                try {
                    MD = mdTextView.text.toString().toInt() //TODO can crash if not proper INT
                }catch (nfe: NumberFormatException){
                    Snackbar.make(view, "Stats must be a number. Ex: 15", Snackbar.LENGTH_LONG)
                        .setAction("", null).show()
                }
                Log.d(TAG, "character MD is ${mdTextView.text}")
                DataMaster.saveCharacterStats(PS,MD,AG,MA,WP,EN, FT, PC, TMR, PB, DEF, PROT)
                mdTextView.text = "MD: ${mdTextView.text}"
                true //done
            }
            false
        }

        agTextView.setOnTouchListener(OnTouchListener { v, event ->
            Log.d(TAG, "Clicked")
            agTextView.text = ""
            false
        })
        agTextView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER || event.keyCode == KeyEvent.ACTION_DOWN ) {
                Log.d(TAG, v.text.toString())
                agTextView.text = v.text.trim() // remove the return
                // closes the keyboard
                val imm: InputMethodManager? = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if(imm != null) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                // makes it so the nameTextView is not focused object
                v.clearFocus()
                try {
                    AG = agTextView.text.toString().toInt() //TODO can crash if not proper INT
                }catch (nfe: NumberFormatException){
                    Snackbar.make(view, "Stats must be a number. Ex: 15", Snackbar.LENGTH_LONG)
                        .setAction("", null).show()
                }
                Log.d(TAG, "character AG is ${agTextView.text}")
                DataMaster.saveCharacterStats(PS,MD,AG,MA,WP,EN, FT, PC, TMR, PB, DEF, PROT)
                agTextView.text = "AG: ${agTextView.text}"
                true //done
            }
            false
        }

        maTextView.setOnTouchListener(OnTouchListener { v, event ->
            Log.d(TAG, "Clicked")
            maTextView.text = ""
            false
        })
        maTextView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER || event.keyCode == KeyEvent.ACTION_DOWN ) {
                Log.d(TAG, v.text.toString())
                maTextView.text = v.text.trim() // remove the return
                // closes the keyboard
                val imm: InputMethodManager? = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if(imm != null) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                // makes it so the nameTextView is not focused object
                v.clearFocus()
                try {
                    MA = maTextView.text.toString().toInt() //TODO can crash if not proper INT
                }catch (nfe: NumberFormatException){
                    Snackbar.make(view, "Stats must be a number. Ex: 15", Snackbar.LENGTH_LONG)
                        .setAction("", null).show()
                }
                Log.d(TAG, "character MA is ${maTextView.text}")
                DataMaster.saveCharacterStats(PS,MD,AG,MA,WP,EN, FT, PC, TMR, PB, DEF, PROT)
                maTextView.text = "MA: ${maTextView.text}"
                true //done
            }
            false
        }

        wpTextView.setOnTouchListener(OnTouchListener { v, event ->
            Log.d(TAG, "Clicked")
            wpTextView.text = ""
            false
        })
        wpTextView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER || event.keyCode == KeyEvent.ACTION_DOWN ) {
                Log.d(TAG, v.text.toString())
                wpTextView.text = v.text.trim() // remove the return
                // closes the keyboard
                val imm: InputMethodManager? = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if(imm != null) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                // makes it so the nameTextView is not focused object
                v.clearFocus()
                try {
                    WP = wpTextView.text.toString().toInt() //TODO can crash if not proper INT
                }catch (nfe: NumberFormatException){
                    Snackbar.make(view, "Stats must be a number. Ex: 15", Snackbar.LENGTH_LONG)
                        .setAction("", null).show()
                }
                Log.d(TAG, "character MA is ${wpTextView.text}")
                DataMaster.saveCharacterStats(PS,MD,AG,MA,WP,EN, FT, PC, TMR, PB, DEF, PROT)
                wpTextView.text = "WP: ${wpTextView.text}"
                true //done
            }
            false
        }

        enTextView.setOnTouchListener(OnTouchListener { v, event ->
            Log.d(TAG, "Clicked")
            enTextView.text = ""
            false
        })
        enTextView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER || event.keyCode == KeyEvent.ACTION_DOWN ) {
                Log.d(TAG, v.text.toString())
                enTextView.text = v.text.trim() // remove the return
                // closes the keyboard
                val imm: InputMethodManager? = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if(imm != null) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                // makes it so the nameTextView is not focused object
                v.clearFocus()
                try {
                    EN = enTextView.text.toString().toInt() //TODO can crash if not proper INT
                }catch (nfe: NumberFormatException){
                    Snackbar.make(view, "Stats must be a number. Ex: 15", Snackbar.LENGTH_LONG)
                        .setAction("", null).show()
                }
                Log.d(TAG, "character EN is ${enTextView.text}")
                DataMaster.saveCharacterStats(PS,MD,AG,MA,WP,EN, FT, PC, TMR, PB, DEF, PROT)
                enTextView.text = "EN: ${enTextView.text}"
                true //done
            }
            false
        }

        ftTextView.setOnTouchListener(OnTouchListener { v, event ->
            Log.d(TAG, "Clicked")
            ftTextView.text = ""
            false
        })
        ftTextView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER || event.keyCode == KeyEvent.ACTION_DOWN ) {
                Log.d(TAG, v.text.toString())
                ftTextView.text = v.text.trim() // remove the return
                // closes the keyboard
                val imm: InputMethodManager? = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if(imm != null) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                // makes it so the nameTextView is not focused object
                v.clearFocus()
                try {
                    FT = ftTextView.text.toString().toInt() //TODO can crash if not proper INT
                }catch (nfe: NumberFormatException){
                    Snackbar.make(view, "Stats must be a number. Ex: 15", Snackbar.LENGTH_LONG)
                        .setAction("", null).show()
                }
                Log.d(TAG, "character FT is ${enTextView.text}")
                DataMaster.saveCharacterStats(PS,MD,AG,MA,WP,EN, FT, PC, TMR, PB, DEF, PROT)
                ftTextView.text = "FT: ${ftTextView.text}"
                true //done
            }
            false
        }

        pcTextView.setOnTouchListener(OnTouchListener { v, event ->
            Log.d(TAG, "Clicked")
            pcTextView.text = ""
            false
        })
        pcTextView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER || event.keyCode == KeyEvent.ACTION_DOWN ) {
                Log.d(TAG, v.text.toString())
                pcTextView.text = v.text.trim() // remove the return
                // closes the keyboard
                val imm: InputMethodManager? = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if(imm != null) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                // makes it so the nameTextView is not focused object
                v.clearFocus()
                try {
                    PC = pcTextView.text.toString().toInt() //TODO can crash if not proper INT
                }catch (nfe: NumberFormatException){
                    Snackbar.make(view, "Stats must be a number. Ex: 15", Snackbar.LENGTH_LONG)
                        .setAction("", null).show()
                }
                Log.d(TAG, "character PC is ${enTextView.text}")
                DataMaster.saveCharacterStats(PS,MD,AG,MA,WP,EN, FT, PC, TMR, PB, DEF, PROT)
                pcTextView.text = "PC: ${pcTextView.text}"
                true //done
            }
            false
        }

        tmrTextView.setOnTouchListener(OnTouchListener { v, event ->
            Log.d(TAG, "Clicked")
            tmrTextView.text = ""
            false
        })
        tmrTextView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER || event.keyCode == KeyEvent.ACTION_DOWN ) {
                Log.d(TAG, v.text.toString())
                tmrTextView.text = v.text.trim() // remove the return
                // closes the keyboard
                val imm: InputMethodManager? = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if(imm != null) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                // makes it so the nameTextView is not focused object
                v.clearFocus()
                try {
                    TMR = tmrTextView.text.toString().toInt() //TODO can crash if not proper INT
                }catch (nfe: NumberFormatException){
                    Snackbar.make(view, "Stats must be a number. Ex: 15", Snackbar.LENGTH_LONG)
                        .setAction("", null).show()
                }
                Log.d(TAG, "character TMR is ${enTextView.text}")
                DataMaster.saveCharacterStats(PS,MD,AG,MA,WP,EN, FT, PC, TMR, PB, DEF, PROT)
                tmrTextView.text = "TMR: ${tmrTextView.text}"
                true //done
            }
            false
        }

        pbTextView.setOnTouchListener(OnTouchListener { v, event ->
            Log.d(TAG, "Clicked")
            pbTextView.text = ""
            false
        })
        pbTextView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER || event.keyCode == KeyEvent.ACTION_DOWN ) {
                Log.d(TAG, v.text.toString())
                pbTextView.text = v.text.trim() // remove the return
                // closes the keyboard
                val imm: InputMethodManager? = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if(imm != null) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                // makes it so the nameTextView is not focused object
                v.clearFocus()
                try {
                    PB = pbTextView.text.toString().toInt() //TODO can crash if not proper INT
                }catch (nfe: NumberFormatException){
                    Snackbar.make(view, "Stats must be a number. Ex: 15", Snackbar.LENGTH_LONG)
                        .setAction("", null).show()
                }
                Log.d(TAG, "character PB is ${enTextView.text}")
                DataMaster.saveCharacterStats(PS,MD,AG,MA,WP,EN, FT, PC, TMR, PB, DEF, PROT)
                pbTextView.text = "PB: ${pbTextView.text}"
                true //done
            }
            false
        }

        defTextView.setOnTouchListener(OnTouchListener { v, event ->
            Log.d(TAG, "Clicked")
            defTextView.text = ""
            false
        })
        defTextView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER || event.keyCode == KeyEvent.ACTION_DOWN ) {
                Log.d(TAG, v.text.toString())
                defTextView.text = v.text.trim() // remove the return
                // closes the keyboard
                val imm: InputMethodManager? = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if(imm != null) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                // makes it so the nameTextView is not focused object
                v.clearFocus()
                try {
                    DEF = defTextView.text.toString().toInt() //TODO can crash if not proper INT
                }catch (nfe: NumberFormatException){
                    Snackbar.make(view, "Stats must be a number. Ex: 15", Snackbar.LENGTH_LONG)
                        .setAction("", null).show()
                }
                Log.d(TAG, "character DEF is ${defTextView.text}")
                DataMaster.saveCharacterStats(PS,MD,AG,MA,WP,EN, FT, PC, TMR, PB, DEF, PROT)
                defTextView.text = "DEF: ${defTextView.text}"
                true //done
            }
            false
        }

        protTextView.setOnTouchListener(OnTouchListener { v, event ->
            Log.d(TAG, "Clicked")
            protTextView.text = ""
            false
        })
        protTextView.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event.keyCode == KeyEvent.KEYCODE_ENTER || event.keyCode == KeyEvent.ACTION_DOWN ) {
                Log.d(TAG, v.text.toString())
                protTextView.text = v.text.trim() // remove the return
                // closes the keyboard
                val imm: InputMethodManager? = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                if(imm != null) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
                // makes it so the nameTextView is not focused object
                v.clearFocus()
                try {
                    PROT = protTextView.text.toString().toInt() //TODO can crash if not proper INT
                }catch (nfe: NumberFormatException){
                    Snackbar.make(view, "Stats must be a number. Ex: 15", Snackbar.LENGTH_LONG)
                        .setAction("", null).show()
                }
                Log.d(TAG, "character PROT is ${enTextView.text}")
                DataMaster.saveCharacterStats(PS,MD,AG,MA,WP,EN, FT, PC, TMR, PB, DEF, PROT)
                protTextView.text = "PROT: ${protTextView.text}"
                true //done
            }
            false
        }
    }


    private fun showKeyboard() {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}