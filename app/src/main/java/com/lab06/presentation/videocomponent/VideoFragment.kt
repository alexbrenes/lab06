package com.lab06.presentation.videocomponent

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import com.lab06.R

class VideoFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onResume() {
        super.onResume()
        val videoView = requireActivity().findViewById<View>(R.id.videoView) as VideoView
        val mediaController = MediaController(requireContext())
        mediaController.setAnchorView(videoView)
        val uri = Uri.parse("android.resource://" + activity?.packageName + "/" + R.raw.house)
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(uri)
        videoView.requestFocus()
        videoView.start()
    }

}