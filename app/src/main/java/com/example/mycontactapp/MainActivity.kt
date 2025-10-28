package com.example.mycontactapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycontactapp.data.AppDatabase
import com.example.mycontactapp.databinding.ActivityMainBinding
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
        }

        //fungsinya untuk merefresh data / mengambil data dari database
        private fun refreshList(){
            lifecycleScope.launch {
                var items = withContext(Dispatchers.IO){ contactDao.getAll() }


                // update data ke adapter
            }
        }
    }
}