package com.example.unsumeri.data.firebase

import com.example.unsumeri.domain.entities.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FirebaseManager {

    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference


    fun addUser(user: User) {

        val usersRef: DatabaseReference = databaseReference.child("User")

        //val query: Query = usersRef.orderByKey().limitToLast(1)
        //query.addListenerForSingleValueEvent(object : ValueEventListener {})


        usersRef.child("1111").setValue(user)

    }

    private fun generateCode(lastNumber: String): String {
        val num = 4
        val arrNum = IntArray(num)

        for (i in arrNum.indices) arrNum[i] = lastNumber.substring(i * num, (i + 1) * num).toInt()

        //TODO доработать функцию для коректной выдачи номера

        //TODO доработать функцию для коректной выдачи номера
        for (i in arrNum.indices.reversed()) {
            if ((arrNum[i] + 1) % 10000 != 0) {
                arrNum[i] += 1
                break
            }
        }

        val sb = StringBuilder()

        for (i in arrNum) sb.append(i)

        return sb.toString()
    }

}