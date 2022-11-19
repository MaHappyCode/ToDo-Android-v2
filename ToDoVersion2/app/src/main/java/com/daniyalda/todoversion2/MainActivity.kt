package com.daniyalda.todoversion2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private val itemsList = ArrayList<String>()
    private lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val inputText = findViewById<EditText>(R.id.inputText)

        // adding to the list
        val addTodo = findViewById<Button>(R.id.addTodo)
        addTodo.setOnClickListener{
            itemsList.add(inputText.text.toString())
            customAdapter.notifyDataSetChanged()
        }


        val searchView = findViewById<androidx.appcompat.widget.SearchView>(R.id.sv_searchbar)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        customAdapter = CustomAdapter(itemsList)
        val layoutManager = LinearLayoutManager(applicationContext)

        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(pO: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(pO: String?): Boolean {
                customAdapter.getFilter().filter(pO)
                return false
            }
        })

        // NEW - Interface (on Item Click)
        customAdapter.setOnItemClickListener(object: CustomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {

                // CRASH
                if (position >= 0) {
                    itemsList.removeAt(position)
                    customAdapter.notifyItemRemoved(position)
                }
            }
        })

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = customAdapter

    }



}