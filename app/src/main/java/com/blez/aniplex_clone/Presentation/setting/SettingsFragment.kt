package com.blez.aniplex_clone.Presentation.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.blez.aniplex_clone.R
import com.blez.aniplex_clone.databinding.FragmentSettingsBinding
import com.blez.aniplex_clone.utils.Constants.IN_APP
import com.blez.aniplex_clone.utils.Constants.VLC
import com.blez.aniplex_clone.utils.SettingManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var binding : FragmentSettingsBinding
    private lateinit var settingManager: SettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.recentReleaseFragment)
        }
        callback
    }


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
        binding.videoFormatText.text = "Video Player Preference"
        if (videoType.isNullOrEmpty()){
            settingManager.saveVideoPreference(IN_APP)
            videoType = settingManager.getVideoPrefs()
            binding.VideoChangeBTN.text = videoType
        }
        binding.VideoChangeBTN.text = videoType
        binding.VideoChangeBTN.setOnClickListener {
            when(videoType){
                IN_APP->{binding.VideoChangeBTN.text = VLC
                settingManager.saveVideoPreference(VLC)
                videoType = VLC}
                VLC->{
                    binding.VideoChangeBTN.text = IN_APP
                    settingManager.saveVideoPreference(IN_APP)
                    videoType = IN_APP
                }
            }

        }
        binding.clearBTN.setOnClickListener {
            settingManager.deteleCredit()
        }

    }
}