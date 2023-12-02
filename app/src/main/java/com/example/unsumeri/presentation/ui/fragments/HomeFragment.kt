package com.example.unsumeri.presentation.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.namespace.R
import com.example.namespace.databinding.FragmentHomeBinding
import com.example.unsumeri.domain.use_cases.UserDataApplication
import com.example.unsumeri.domain.use_cases.factory.UserAccountFactory.Companion.ACCOUNT
import com.example.unsumeri.presentation.view_model.HomeViewModel
import com.example.unsumeri.presentation.view_model.factory.HomeViewModelFactory


class HomeFragment : Fragment() {

    private val sharedViewModel: HomeViewModel by activityViewModels{
        HomeViewModelFactory(
            ACCOUNT,
            (activity?.application as UserDataApplication).database.userDao()
        )
    }

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding

    private lateinit var buttonAdd:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    /////TODO write addArtcileFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val root:View = binding!!.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {  }

        buttonAdd = binding!!.buttonAdd

        buttonAdd.setOnClickListener {
            enterToAddArticleFragment()
        }
    }


    private fun enterToAddArticleFragment() {
        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
        val transactionFragment = fragmentManager.beginTransaction()

        transactionFragment.setReorderingAllowed(true)
        transactionFragment.addToBackStack(null)

        transactionFragment.replace(R.id.fl_layout, AddArticleFragment())

        transactionFragment.commit()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
    }
}