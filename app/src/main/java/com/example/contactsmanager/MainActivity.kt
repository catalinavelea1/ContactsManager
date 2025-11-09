package com.example.contactsmanager

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Objects.toString
import androidx.core.view.isGone


class MainActivity : AppCompatActivity() {
    private lateinit var saveButton : Button
    private lateinit var cancelButton : Button
    private lateinit var showAditionalButton : Button

    fun onSaveIntentTrigger() {
        val intent = Intent(ContactsContract.Intents.Insert.ACTION).apply {
            setType(ContactsContract.RawContacts.CONTENT_TYPE)

            findViewById<EditText>(R.id.name_edit_text).text.toString().let {
                putExtra(ContactsContract.Intents.Insert.NAME, it)
            }

            findViewById<EditText>(R.id.phone_number_edit_text).text.toString().let {
                putExtra(ContactsContract.Intents.Insert.PHONE, it)
            }

            findViewById<EditText>(R.id.email_edit_text).text.toString().let {
                putExtra(ContactsContract.Intents.Insert.EMAIL, it)
            }

            findViewById<EditText>(R.id.address_edit_text).text.toString().let {
                putExtra(ContactsContract.Intents.Insert.POSTAL, it)
            }

            findViewById<EditText>(R.id.job_edit_text).text.toString().let {
                putExtra(ContactsContract.Intents.Insert.JOB_TITLE, it)
            }

            findViewById<EditText>(R.id.company_edit_text).text.toString().let {
                putExtra(ContactsContract.Intents.Insert.COMPANY, it)
            }
        }

        val contactData = ArrayList<ContentValues>().apply {
            findViewById<EditText>(R.id.website_edit_text).text.toString().let {
                add(ContentValues().apply {
                    put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE)
                    put(ContactsContract.CommonDataKinds.Website.URL, it)
                })
            }

            findViewById<EditText>(R.id.im_edit_text).text.toString().let {
                add(ContentValues().apply {
                    put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE)
                    put(ContactsContract.CommonDataKinds.Im.DATA, it)
                })
            }
        }

        intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData)
        startActivity(intent)
    }

    // instanta a clasei ascultator
    private val buttonClickListener = ButtonClickListener()


    // clasa ascultator pentru butoane
    private inner class ButtonClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            when(view.id) {
                R.id.aditional_fields_button -> {
                    val hiddenLayout = findViewById<LinearLayout>(R.id.hidden)
                    if (hiddenLayout.isGone) {
                        hiddenLayout.visibility = View.VISIBLE
                    } else {
                        hiddenLayout.visibility = View.GONE
                    }
                }

                R.id.cancel_button -> {
                    finish()
                }

                R.id.save_button -> {
                    onSaveIntentTrigger()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        saveButton = findViewById(R.id.save_button)
        saveButton.setOnClickListener(buttonClickListener)

        cancelButton = findViewById(R.id.cancel_button)
        cancelButton.setOnClickListener(buttonClickListener)

        showAditionalButton = findViewById(R.id.aditional_fields_button)
        showAditionalButton.setOnClickListener(buttonClickListener)

    }
}