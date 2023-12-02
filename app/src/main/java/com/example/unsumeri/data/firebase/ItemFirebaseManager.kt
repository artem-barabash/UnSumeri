package com.example.unsumeri.data.firebase

import com.example.unsumeri.data.room.UserDao
import com.example.unsumeri.domain.entities.Article
import com.example.unsumeri.domain.use_cases.factory.UserAccountFactory.Companion.ACCOUNT
import com.google.firebase.database.*
import java.util.UUID

class ItemFirebaseManager {

    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun addArticle(article: Article){
        val articleRef = databaseReference.child("Article")

        articleRef.child(UUID.randomUUID().toString()).setValue(article)
    }


    fun retrieveAllArticles(userDao: UserDao){
        val articleRef = databaseReference.child("Article")

        val list = ArrayList<Article>()

        var id = 1

        articleRef
            //articleRef.orderByChild("userId").equalTo(ACCOUNT.number)
            .addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot!!.exists()){

                    for(h in snapshot.children){

                        val item = Article(
                            //h.child("id").toString().toInt(),
                            id,
                            h.child("userId").toString(),
                            h.child("title").toString(),
                            h.child("content").toString(),
                            h.child("createAtTime").toString(),
                            //h.child("categoryId").toString().toInt()
                        0
                        )
                        println("item=$item")
                        list.add(item)
                        id++
                    }
                }

                userDao.insertAllArticle(list)

                println("list=$list")
            }

            override fun onCancelled(error: DatabaseError) {
                println(error.message)
            }
        })


    }
}