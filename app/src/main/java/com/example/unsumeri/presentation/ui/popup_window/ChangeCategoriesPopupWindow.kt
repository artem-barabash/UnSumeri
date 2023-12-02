package com.example.unsumeri.presentation.ui.popup_window

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.namespace.R
import com.example.unsumeri.domain.entities.Article
import com.example.unsumeri.domain.entities.Category
import com.example.unsumeri.presentation.adapter.CategoryAdapter


class ChangeCategoriesPopupWindow(val context: Context,
                                  private val linearLayout: LinearLayout
): PopupWindow() {

    private lateinit var popupWindow: PopupWindow

    private lateinit var recyclerView: RecyclerView


    @SuppressLint("WrongConstant")
    fun showPopupWindow(){
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        @SuppressLint("InflateParams")
        val customView: View =
            inflater.inflate(R.layout.popup_change_categories, null)

        popupWindow = PopupWindow(
            customView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        popupWindow.isFocusable = true
        // Settings disappear when you click somewhere else
        // Settings disappear when you click somewhere else
        popupWindow.isOutsideTouchable = true
        popupWindow.softInputMode = INPUT_METHOD_NEEDED
        popupWindow.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE

        popupWindow.showAtLocation(linearLayout, Gravity.BOTTOM, 0, 0)

        recyclerView = customView.findViewById(R.id.categoriesRecyclerView)


        showList()
    }

    /*private fun showList() {
        val mGroupsArray = arrayOf("Winter", "Spring", "Summer", "Autumn")

        val mWinterMonthsArray = arrayOf("December", "January", "February")
        val mSpringMonthsArray = arrayOf("March", "April", "May")
        val mSummerMonthsArray = arrayOf("June", "July", "August")
        val mAutumnMonthsArray = arrayOf("September", "October", "November")


        var map: MutableMap<String?, String?>
        // коллекция для групп
        // коллекция для групп
        val groupDataList: ArrayList<Map<String?, String?>> = ArrayList()
        // заполняем коллекцию групп из массива с названиями групп

        // заполняем коллекцию групп из массива с названиями групп
        for (group in mGroupsArray) {
            // заполняем список атрибутов для каждой группы
            map = HashMap()
            map["groupName"] = group // время года
            groupDataList.add(map)
        }

        // список атрибутов групп для чтения

        // список атрибутов групп для чтения
        val groupFrom = arrayOf("groupName")
        // список ID view-элементов, в которые будет помещены атрибуты групп
        // список ID view-элементов, в которые будет помещены атрибуты групп
        val groupTo = intArrayOf(android.R.id.text1)

        // создаем общую коллекцию для коллекций элементов

        // создаем общую коллекцию для коллекций элементов
        val сhildDataList: ArrayList<ArrayList<Map<String?, String?>?>> = ArrayList()

        // в итоге получится сhildDataList = ArrayList<сhildDataItemList>

        // создаем коллекцию элементов для первой группы

        // в итоге получится сhildDataList = ArrayList<сhildDataItemList>

        // создаем коллекцию элементов для первой группы
        var сhildDataItemList: ArrayList<Map<String?, String?>?> = ArrayList()
        // заполняем список атрибутов для каждого элемента
        // заполняем список атрибутов для каждого элемента
        for (month in mWinterMonthsArray) {
            map = HashMap()
            map["monthName"] = month // название месяца
            сhildDataItemList.add(map)
        }
        // добавляем в коллекцию коллекций
        // добавляем в коллекцию коллекций
        сhildDataList.add(сhildDataItemList)

        // создаем коллекцию элементов для второй группы

        // создаем коллекцию элементов для второй группы
        сhildDataItemList = ArrayList()
        for (month in mSpringMonthsArray) {
            map = HashMap()
            map["monthName"] = month
            сhildDataItemList.add(map)
        }
        сhildDataList.add(сhildDataItemList)

        // создаем коллекцию элементов для третьей группы

        // создаем коллекцию элементов для третьей группы
        сhildDataItemList = ArrayList()
        for (month in mSummerMonthsArray) {
            map = HashMap()
            map["monthName"] = month
            сhildDataItemList.add(map)
        }
        сhildDataList.add(сhildDataItemList)

        // создаем коллекцию элементов для четвертой группы

        // создаем коллекцию элементов для четвертой группы
        сhildDataItemList = ArrayList()
        for (month in mAutumnMonthsArray) {
            map = HashMap()
            map["monthName"] = month
            сhildDataItemList.add(map)
        }
        сhildDataList.add(сhildDataItemList)

        // список атрибутов элементов для чтения

        // список атрибутов элементов для чтения
        val childFrom = arrayOf("monthName")
        // список ID view-элементов, в которые будет помещены атрибуты
        // элементов
        // список ID view-элементов, в которые будет помещены атрибуты
        // элементов
        val childTo = intArrayOf(android.R.id.text1)




        val adapter = SimpleExpandableListAdapter(
            context, groupDataList,
            android.R.layout.simple_expandable_list_item_1, groupFrom,
            groupTo, сhildDataList, android.R.layout.simple_list_item_1,
            childFrom, childTo
        )

        expandableListView.setAdapter(adapter)



        ///val m = HashMap<String?, String?>()
        //val m: MutableMap<String?, String?> = HashMap()
        //m[""] = "Space"


        //groupDataList.add(m)
    }*/

    private fun showList() {


        val mGroupsArray = arrayOf("Winter", "Spring", "Summer", "Autumn")

        val mWinterMonthsArray = arrayOf("December", "January", "February")
        val mSpringMonthsArray = arrayOf("March", "April", "May")
        val mSummerMonthsArray = arrayOf("June", "July", "August")
        val mAutumnMonthsArray = arrayOf("September", "October", "November")

        val articleWinters = ArrayList<Article>()
        articleWinters.add(Article(1, "000", "December", "111", "2023-11-07 19:0", 0))
        articleWinters.add(Article(1, "000", "January", "111", "2023-11-07 19:0", 0))
        articleWinters.add(Article(1, "000", "February", "111", "2023-11-07 19:0", 0))



        val articleSummer = ArrayList<Article>()
        articleWinters.add(Article(1, "000", "June", "111", "2023-11-07 19:0", 0))
        articleWinters.add(Article(1, "000", "July", "111", "2023-11-07 19:0", 0))
        articleWinters.add(Article(1, "000", "August", "111", "2023-11-07 19:0", 0))





        val currentCategoriesList = ArrayList<Category>()

        val church = Category(
            Id = 101,
            userId = "0000",
            name = "Church",
            createdAtTime = "2023-11-07 19:00",
            parent = null,
            parentId = 0,
            subCategories = null,
            articles = articleWinters
        )

        val cleaning = Category(
            Id = 102,
            userId = "0000",
            name = "Cleaning",
            createdAtTime = "2023-11-07 19:00",
            parent = null,
            parentId = 0,
            subCategories = null,
            articles = articleWinters
        )

        currentCategoriesList.add(church)
        currentCategoriesList.add(cleaning)

        //currentCategoriesList.add(church)
        //currentCategoriesList.add(cleaning)


        val category1 = Category(
            Id = 1,
            userId = "0000",
            name = "Winter",
            createdAtTime = "2023-11-07 19:00",
            parent = null,
            parentId = 0,
            subCategories = currentCategoriesList,
            articles = articleWinters
        )

        val category2 = Category(
            Id = 1,
            userId = "0000",
            name = "Summer",
            createdAtTime = "2023-11-07 19:00",
            parent = null,
            parentId = 0,
            subCategories = currentCategoriesList,
            articles = articleSummer
        )


        val categories = ArrayList<Category>()

        categories.add(category1)
        categories.add(category2)

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val categoryAdapter = CategoryAdapter(context, categories)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        recyclerView.adapter = categoryAdapter
    }

}