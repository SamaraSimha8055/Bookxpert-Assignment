package com.example.bookxperttest.activities

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookxperttest.R
import com.example.bookxperttest.adapters.ApiObjectAdapter
import com.example.bookxperttest.models.ApiObject
import com.example.bookxperttest.models.ApiViewModel
import com.example.bookxperttest.utils.NotificationPreferenceManager
import kotlinx.coroutines.launch

class ApiListActivity : AppCompatActivity() {

    private val viewModel: ApiViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: ApiObjectAdapter
    private lateinit var notificationSwitch: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api_list)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 100)
        }

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        notificationSwitch = findViewById(R.id.notificationSwitch)

        adapter = ApiObjectAdapter(
            onEditClick = { apiObject -> showEditDialog(apiObject) },
            onDeleteClick = { apiObject -> viewModel.deleteItem(apiObject) }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        viewModel.apiObjects.observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        viewModel.fetchDataIfNeeded()

        lifecycleScope.launch {
            NotificationPreferenceManager.getNotificationsEnabled(this@ApiListActivity).collect { enabled ->
                notificationSwitch.isChecked = enabled
            }
        }

        notificationSwitch.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                NotificationPreferenceManager.setNotificationsEnabled(this@ApiListActivity, isChecked)
            }
        }
    }

    private fun showEditDialog(apiObject: ApiObject) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_api_object, null)
        val editName = dialogView.findViewById<EditText>(R.id.editName)
        val editData = dialogView.findViewById<EditText>(R.id.editData)

        val gson = com.google.gson.Gson()
        editName.setText(apiObject.name)
        editData.setText(gson.toJson(apiObject.data)) // pretty-print current map

        AlertDialog.Builder(this)
            .setTitle("Edit API Object")
            .setView(dialogView)
            .setPositiveButton("Update") { _, _ ->
                try {
                    val updatedMap: Map<String, Any> =
                        gson.fromJson(editData.text.toString(), object : com.google.gson.reflect.TypeToken<Map<String, Any>>() {}.type)

                    val updatedObject = apiObject.copy(
                        name = editName.text.toString(),
                        data = updatedMap
                    )
                    viewModel.updateItem(updatedObject)

                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Invalid JSON format, Use {} to update data", Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}