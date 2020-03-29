 package com.quocnb.listmaker

import android.os.Bundle
import android.text.InputType
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var listRecyclerView: RecyclerView
    val listDataManager = ListDataManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val lists = listDataManager.readList()
        listRecyclerView = findViewById(R.id.A01_list_recyclerview)
        listRecyclerView.adapter = ListSelectionRecyclerViewAdapter(lists)
        listRecyclerView.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener { _ ->
            showCreateListDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun showCreateListDialog() {
        val dialogTitle = getString(R.string.A01_name_of_list)
        val positiveButtonTitle = getString(R.string.A01_create_list)
        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT
        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)
        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            val list = TaskList(listTitleEditText.text.toString())
            listDataManager.saveList(list)
            val recyclerAdapter = listRecyclerView.adapter as ListSelectionRecyclerViewAdapter
            recyclerAdapter.addList(list)
            dialog.dismiss()
        }
        builder.create().show()
    }
}
