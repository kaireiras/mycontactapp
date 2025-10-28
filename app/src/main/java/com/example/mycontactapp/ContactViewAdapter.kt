package com.example.mycontactapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mycontactapp.data.Contact
import com.example.mycontactapp.databinding.ItemContactBinding

class ContactViewAdapter(private val onEdit: (Contact) -> Unit, private val onDelete: (Contact) -> Unit): RecyclerView.Adapter<ContactViewAdapter.ItemContactViewHolder>() {

    // variabel yang menyimpan data list contact
    private val contacts = mutableListOf<Contact>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemContactViewHolder {
        // assign binding
        val binding = ItemContactBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        // semacam assign binding di activity
        return ItemContactViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ItemContactViewHolder,
        position: Int
    ) {
        // binding tiap contact data
        holder.binding(contacts[position])
    }

    override fun getItemCount(): Int {
        return contacts.count()
    }


    // viewhilder
    // setiap item view akan di atur disini
    inner class ItemContactViewHolder(private val binding: ItemContactBinding): RecyclerView.ViewHolder(binding.root){


        fun binding(contact: Contact){
            with(binding){
                txtName.setText(contact.name)
                txtPhone.setText(contact.phone)

                btnEdit.setOnClickListener { onEdit(contact) }
                btnDelete.setOnClickListener { onDelete(contact) }
            }
        }
    }

    fun setItems(newData: List<Contact>){
        // hapus data sebelumnya
        contacts.clear()
        // tambahkan data baru
        contacts.addAll(newData)

        //notify ke ui agar ada perubahan data
        notifyDataSetChanged()
    }
}