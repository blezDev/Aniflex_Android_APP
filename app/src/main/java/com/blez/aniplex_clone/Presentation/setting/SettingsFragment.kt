package com.blez.aniplex_clone.Presentation.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.databinding.FragmentSettingsBinding
import com.blez.aniplex_clone.utils.SettingManager

class SettingsFragment : Fragment() {
    private lateinit var binding : FragmentSettingsBinding
    private lateinit var settingManager: SettingManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingManager = SettingManager(requireContext())
        var videoType = settingManager.getVideoPrefs()
        binding.videoFormatText.text = "Video Preference"
        if (videoType.isNullOrEmpty()){
            settingManager.saveVideoPreference("InApp")
            videoType = settingManager.getVideoPrefs()
            binding.VideoChangeBTN.text = videoType
        }
        binding.VideoChangeBTN.text = videoType
        binding.VideoChangeBTN.setOnClickListener {
            when(videoType){
                "InApp"->{binding.VideoChangeBTN.text = "VLC"
                settingManager.saveVideoPreference("VLC")
                videoType = "VLC"}
                "VLC"->{
                    binding.VideoChangeBTN.text = "InApp"
                    settingManager.saveVideoPreference("InApp")
                    videoType = "InApp"
                }
            }

        }

    }
}