package com.kotlin.toolbaritems

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.kotlin.toolbaritems.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setToolbar()
    }

    private fun setToolbar() {
        // this is compulsory in order to get behavior of expand/collapse
        setSupportActionBar(binding.toolbarMain)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        menu?.let {
            with(it) {
                val searchItem = findItem(R.id.menu_item_search)
                val inboxItem = findItem(R.id.menu_item_inbox)
                val musicItem = findItem(R.id.menu_item_music)

                // Define the listener
                val expandListener = object : MenuItem.OnActionExpandListener {
                    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                        inboxItem.isVisible = true
                        musicItem.isVisible = true
                        return true
                    }

                    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                        inboxItem.isVisible = false
                        musicItem.isVisible = false
                        return true
                    }
                }
                searchItem.setOnActionExpandListener(expandListener)

                val editText = searchItem.actionView.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
                editText.setTextColor(Color.WHITE)

                // SearchView
                val searchView = searchItem.actionView as SearchView
                val queryTextListener = object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        return true
                    }
                }
                searchView.setOnQueryTextListener(queryTextListener)
            }
        }
        return true
    }

    companion object {
        private const val TAG: String = "MyTag"
    }
}