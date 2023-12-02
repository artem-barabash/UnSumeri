package com.example.unsumeri.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.namespace.R
import com.example.namespace.databinding.FragmentCategoriesBinding
import com.example.unsumeri.presentation.view_model.HomeViewModel


class CategoriesFragment : Fragment() {

    private val sharedViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentCategoriesBinding? = null

    private val binding get() = _binding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)

        val root:View = binding!!.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {  }

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {

    }
}