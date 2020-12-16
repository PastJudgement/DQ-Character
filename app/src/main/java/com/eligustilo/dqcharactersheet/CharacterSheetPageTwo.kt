package com.eligustilo.dqcharactersheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CharacterSheetPageTwo : Fragment() {
    val TAG = "CharacterSheetPageTwo"

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.character_sheet_two, container, false)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //test stuff for avatar selection
        val imageViewOne = view.findViewById<ImageView>(R.id.imageViewOneTest)
        val imageViewTwo = view.findViewById<ImageView>(R.id.imageView2Test)

        val characterAvatarArray = this.requireContext()?.resources?.obtainTypedArray(R.array.avatar_ids)
        if (characterAvatarArray != null){
            imageViewOne.setImageResource(characterAvatarArray.getResourceId(1,R.mipmap.test))
            imageViewTwo.setImageResource(characterAvatarArray.getResourceId(2,R.mipmap.test2))
        }

        imageViewOne.setOnClickListener(){
            Log.d(TAG, "imageViewOne test Clicked")
            if (characterAvatarArray != null) {
                DataMaster.saveCharacterDetails(null,1)
                Log.d(TAG, "avatar is now ${characterAvatarArray.getResourceId(1,R.mipmap.test)}")
                this.findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        }

        imageViewTwo.setOnClickListener(){
            Log.d(TAG, "imageViewTwo test Clicked")
            if (characterAvatarArray != null) {
                DataMaster.saveCharacterDetails(null,2)
                Log.d(TAG, "avatar is now ${characterAvatarArray.getResourceId(2,R.mipmap.test2)}")
                this.findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
            }
        }
    }
}