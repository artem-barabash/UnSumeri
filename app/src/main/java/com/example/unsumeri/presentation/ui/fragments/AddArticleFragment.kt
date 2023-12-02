package com.example.unsumeri.presentation.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.namespace.R
import com.example.namespace.databinding.FragmentAddArticleBinding
import com.example.unsumeri.data.firebase.ItemFirebaseManager
import com.example.unsumeri.domain.entities.Article
import com.example.unsumeri.domain.use_cases.factory.UserAccountFactory.Companion.ACCOUNT
import com.example.unsumeri.presentation.ui.popup_window.ChangeCategoriesPopupWindow
import com.example.unsumeri.presentation.view_model.HomeViewModel
import java.time.LocalDateTime


class AddArticleFragment : Fragment() {

    private val sharedViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentAddArticleBinding? = null

    private val binding get() = _binding

    lateinit var buttonChange:Button
    lateinit var mainBlock:LinearLayout

    lateinit var buttonAddItem:Button

    lateinit var title:EditText
    private lateinit var content:EditText

    private val itemFirebaseManager = ItemFirebaseManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddArticleBinding.inflate(inflater, container, false)

        val root:View = binding!!.root

        return root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonChange = binding!!.buttonChange
        buttonAddItem = binding!!.buttonAddItem

        mainBlock = binding!!.mainBlock

        title = binding!!.editTextTitle
        content = binding!!.editTextMultiLine

        buttonChange.setOnClickListener {
            val paymentPopupWindow = ChangeCategoriesPopupWindow(
                requireContext(),
                mainBlock
            )

            //paymentPopupWindow.setFragmentActivity(activity)
            paymentPopupWindow.showPopupWindow()
        }

        buttonAddItem.setOnClickListener {

            writeArticle()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun writeArticle() {

        if(!title.text.toString().isNullOrEmpty() && !content.text.toString().isNullOrEmpty()){
            val localDateTime = LocalDateTime.now()

            val article = Article(
                id = 0,
                userId = ACCOUNT.number,
                title = title.text.toString(),
                content= content.text.toString(),
                createAtTime = localDateTime.toString(),
                categoryId = 0,
                //category = null
            )

            itemFirebaseManager.addArticle(article)
            enterToHomeFragment()
        }else{
            Toast.makeText(requireContext(), "Title and content can't be empty!", Toast.LENGTH_SHORT).show()
        }


    }

    private fun enterToHomeFragment() {
        val fragmentManager = (context as AppCompatActivity).supportFragmentManager
        val transactionFragment = fragmentManager.beginTransaction()

        transactionFragment.setReorderingAllowed(true)
        transactionFragment.addToBackStack(null)

        transactionFragment.replace(R.id.fl_layout, HomeFragment())

        transactionFragment.commit()

    }


    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
    }
}