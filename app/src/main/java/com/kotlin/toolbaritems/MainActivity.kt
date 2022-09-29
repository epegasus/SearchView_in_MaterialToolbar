package com.kotlin.toolbaritems

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.kotlin.toolbaritems.dataProvider.DpUsers
import com.kotlin.toolbaritems.databinding.ActivityMainBinding
import com.kotlin.toolbaritems.models.User
import java.lang.reflect.Field

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val originalList = DpUsers.userList

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
        menu?.let { it ->
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

                val editText = searchItem.actionView?.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
                editText.setTextColor(Color.WHITE)
                // Cursor Color
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    editText.setTextCursorDrawable(R.drawable.bg_cursor)
                } else {
                    try {
                        @SuppressLint("SoonBlockedPrivateApi") val field: Field = TextView::class.java.getDeclaredField("mCursorDrawableRes")
                        field.isAccessible = true
                        field.set(editText, R.drawable.bg_cursor)
                    } catch (throwable: Throwable) {
                        Log.d(TAG, "onCreateOptionsMenu: t")
                    }
                }

                // SearchView
                val searchView = searchItem.actionView as SearchView
                val queryTextListener = object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        val arrayList = ArrayList<User>()
                        newText?.let {
                            if (it.isNotEmpty()) {
                                originalList.forEach { user ->
                                    if (user.username.lowercase().contains(it.lowercase()))
                                        arrayList.add(user)
                                }
                            } else {
                                arrayList.addAll(originalList)
                            }
                        }
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