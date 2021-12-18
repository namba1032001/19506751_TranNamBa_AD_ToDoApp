package com.example.a19506751_trannamba_ad_todoapp

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.a19506751_trannamba_ad_todoapp.databinding.ActivityMainBinding
55
class MainActivity : AppCompatActivity(),NewTaskDialogFragment.NewTaskDialogListener {
    private var todoListItems = ArrayList<String>()
    private var listView: ListView? = null
    private var listAdapter: ArrayAdapter<String>? = null

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    fun showNewTaskUI() {
        val newFragment = NewTaskDialogFragment.newInstance(R.string.add_new_task_dialog_title)
        newFragment.show(fragmentManager, "newtask")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        listView = findViewById(R.id.list_view)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener {
            showNewTaskUI()
        }
        populateListView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
    override fun onDialogPositiveClick(dialog: DialogFragment, task:String) {
        todoListItems.add(task)
        listAdapter?.notifyDataSetChanged()
        Snackbar.make(binding.fab, "Task Added Successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }
    override fun onDialogNegativeClick(dialog: DialogFragment) {

    }
    private fun populateListView() {
        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, todoListItems)
        listView?.adapter = listAdapter
    }
}