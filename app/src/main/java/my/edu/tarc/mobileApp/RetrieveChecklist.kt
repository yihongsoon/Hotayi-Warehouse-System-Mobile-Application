package my.edu.tarc.mobileApp

import android.R.attr.key
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RetrieveChecklist : AppCompatActivity() {


    private lateinit var todoAdapter: TodoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrieve_checklist)

        val actionbar = supportActionBar
        actionbar!!.title = "Checklist for Materials"
        actionbar.setDisplayHomeAsUpEnabled(true)
        val rvTodoItems = findViewById<RecyclerView>(R.id.rvTodoItems)
        val btnAddTodo= findViewById<Button>(R.id.btnAddTodo)
        val etTodoTitle = findViewById<EditText>(R.id.etTodoTitle)
        val btnDeleteDoneTodos = findViewById<Button>(R.id.btnDeleteDoneTodos)

//        val sharedPreferences = getSharedPreferences("Checker", Context.MODE_PRIVATE)



        todoAdapter = TodoAdapter(mutableListOf())

        rvTodoItems.adapter = todoAdapter
        rvTodoItems.layoutManager = LinearLayoutManager(this)



        btnAddTodo.setOnClickListener {
            val todoTitle = etTodoTitle.text.toString()
            if(todoTitle.isNotEmpty()) {
                val todo = ToDo(todoTitle)
                todoAdapter.addTodo(todo)
                etTodoTitle.text.clear()
            }
        }
        btnDeleteDoneTodos.setOnClickListener {
            todoAdapter.deleteDoneTodos()
        }

    }



    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }




    }



    




