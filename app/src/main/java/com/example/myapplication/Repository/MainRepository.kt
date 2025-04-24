package com.example.myapplication.Repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.CHLog
import com.example.myapplication.Model.ItemsModel
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.StorageException
import java.lang.Exception


class MainRepository {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    fun loadFiltered(id: String): LiveData<List<ItemsModel>> {
        val listData = MutableLiveData<List<ItemsModel>>()
        val ref = firebaseDatabase.getReference("Items")

        Log.d("mainRepo","ref : ${ref}") //https://fcmtestgb-default-rtdb.firebaseio.com/Items
        val query: Query = ref.orderByChild("categoryId").equalTo(id)

        Log.d("mainRepo","query : ${query}") //com.google.firebase.database.Query@58c6606

        //단일 정보를 가져올때 addListenerForSingleValueEvent 사용
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //check flow
                Log.d("mainRepo", "@@@Snapshot children count: ${snapshot.childrenCount}")
//                if (!snapshot.exists()) {
//                    Log.d("mainRepo", "@@@No data found for query")
//                    return
//                }

                val lists = mutableListOf<ItemsModel>()
                Log.d("mainRepo","@@@for Start")

                for (childSnapshot in snapshot.children) {
                    // 수정된 부분: snapshot.getValue 대신 childSnapshot.getValue로 항목을 가져옴
                    val item = childSnapshot.getValue(ItemsModel::class.java)
                    item?.let { lists.add(it) }
                }
                listData.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Log.e( "Failed Call DB", error.toException().toString())
            }
        })


        ref.child("Items").child(id).get()
            .addOnSuccessListener {
                CHLog.i("","data loaded")
            }
            .addOnFailureListener {
                CHLog.w("","empty")
            }

        return listData
    }

    fun loadTitle(title: String): LiveData<List<ItemsModel>> {
        val titleData = MutableLiveData<List<ItemsModel>>()
        val ref = firebaseDatabase.getReference("Items")
        val query: Query = ref.orderByChild("title").equalTo(title)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val products = mutableListOf<ItemsModel>()
                for (childSnapshot in snapshot.children) {
                    val product = childSnapshot.getValue(ItemsModel::class.java)
                    if (product != null) {
                        products.add(product)
                    }
                }
                titleData.value = products
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                Log.e("FirebaseError", error.toException().toString())
            }
        })
        return titleData
    }
}