package my.edu.tarc.mobileApp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TodoAdapter(



    private val todos: MutableList<ToDo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

        lateinit var sharedPreferences: SharedPreferences
        var context=this


    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    fun addTodo(todo: ToDo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos() {
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if(isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int){

//       val savedString = sharedPreferences.getString("todotitle","")
//        val savedBoolean = sharedPreferences.getBoolean("checked",false)

        val curTodo = todos[position]
        holder.itemView.apply {
            val tvTodoTitle = findViewById<TextView>(R.id.tvTodoTitle)
//           if(savedString!= null){
//           tvTodoTitle.text = savedString
//                }else {
                tvTodoTitle.text = curTodo.title
        /*  }*/
            val cbDone = findViewById<CheckBox>(R.id.cbDone)
            /*if(savedBoolean != null) {
                cbDone.isChecked = savedBoolean
            }else {*/
                cbDone.isChecked = curTodo.isChecked
           /* }*/
                toggleStrikeThrough(tvTodoTitle, curTodo.isChecked)
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(tvTodoTitle, isChecked)
                curTodo.isChecked = !curTodo.isChecked

                sharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.apply{
                    putString("todotitle", curTodo.title)
                    putBoolean("checked", cbDone.isChecked)


                }.apply()

            }
        }


    }

    override fun getItemCount(): Int {
        return todos.size
    }




}










