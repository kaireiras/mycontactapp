package com.example.mycontactapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycontactapp.data.AppDatabase
import com.example.mycontactapp.data.Contact
import com.example.mycontactapp.databinding.ActivityMainBinding
import com.example.mycontactapp.databinding.FormContactBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    // create variable

    private lateinit var binding: ActivityMainBinding

    /*untuk mengambil data, dan mengelola data contact
    / kita butuh interface / model yangsudah dibuat
     */

    private val contactDao by lazy { AppDatabase.get(this).contactDao() }

    private lateinit var contactViewAdapter: ContactViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)

        //assign contact adp
        contactViewAdapter = ContactViewAdapter()

        setContentView(binding.root)

        with(binding){
            //setting recyclerview
            rvContact.apply {
                adapter = contactViewAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
            btnAdd.setOnClickListener {
                showAddDialog()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        refreshList()
    }


    //fungsinya untuk merefresh data / mengambil data dari database
    private fun refreshList(){
        lifecycleScope.launch {
            var items = withContext(Dispatchers.IO){ contactDao.getAll() }


            // update data ke adapter
            contactViewAdapter.setItems(items)
        }
    }

    private fun showAddDialog() {
        // binding ui form
        val binding = FormContactBinding.inflate(layoutInflater)

        // buat dialog
        var builder = AlertDialog.Builder(this@MainActivity)

        builder.setTitle("add new contact")
        builder.setView(binding.root)
        builder.setPositiveButton("Save") { dialog, which ->
            val name = binding.edtName.text.toString().trim()
            val phone = binding.edtPhone.text.toString().trim()

            if(name.isNotEmpty() && phone.isNotEmpty()){
                lifecycleScope.launch(Dispatchers.IO) {
                    contactDao.insert(Contact(name = name, phone = phone))

                    withContext(Dispatchers.Main){
                        refreshList()
                    }
                }
            } else{
                Toast.makeText(
                    this@MainActivity,
                    "nama dan phone harus diisi",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        builder.setNeutralButton("Cancel"){ dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }
}