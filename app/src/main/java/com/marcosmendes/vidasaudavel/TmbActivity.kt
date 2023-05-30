package com.marcosmendes.vidasaudavel

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.marcosmendes.vidasaudavel.databinding.ActivityTmbBinding
import com.marcosmendes.vidasaudavel.model.Calc

class TmbActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTmbBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTmbBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val items = resources.getStringArray(R.array.tmb_lifestyle)
        binding.autoLifestyle.setText(items.first())
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        binding.autoLifestyle.setAdapter(adapter)

        binding.btnTmbSend.setOnClickListener {
            if (!validate()) {
                Toast.makeText(this, R.string.fields_message, Toast.LENGTH_LONG).show()
            }

            val weight = binding.editTmbWeight.text.toString().toInt()
            val height = binding.editTmbHeight.text.toString().toInt()
            val age = binding.editTmbAge.text.toString().toInt()

            val result = calculateTmb(weight, height, age)
            val response = tmbRequest(result)

            AlertDialog.Builder(this)
                .setMessage(getString(R.string.tmb_response, response))
                .setPositiveButton(android.R.string.ok) { _, _ ->
                }
                .setNegativeButton(R.string.save) { _, _ ->
                    Thread {
                        val app = application as App
                        val dao = app.db.calcDao()
                        dao.insert(Calc(type = "tmb", res = response))

                        runOnUiThread {
                            openListActivity()
                        }
                    }.start()
                }
                .create()
                .show()

            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_search) {
            finish()
            openListActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openListActivity(){
        val intent = Intent(this, ListCalcActivity::class.java)
        intent.putExtra("type","tmb")
        startActivity(intent)
    }

    private fun tmbRequest(tmb: Double): Double {
        val items = resources.getStringArray(R.array.tmb_lifestyle)
        return when {
            binding.autoLifestyle.text.toString() == items[0] -> tmb * 1.2
            binding.autoLifestyle.text.toString() == items[1] -> tmb * 1.375
            binding.autoLifestyle.text.toString() == items[2] -> tmb * 1.55
            binding.autoLifestyle.text.toString() == items[3] -> tmb * 1.725
            binding.autoLifestyle.text.toString() == items[4] -> tmb * 1.9
            else -> 0.0
        }
    }

    private fun calculateTmb(weight: Int, height: Int, age: Int): Double {
        return 66 + (13.8 * weight) + (5 * height) - (6.8 * age)
    }

    private fun validate(): Boolean {
        return (binding.editTmbWeight.text.toString().isNotEmpty()
                && binding.editTmbHeight.text.toString().isNotEmpty()
                && binding.editTmbAge.text.toString().isNotEmpty()
                && !binding.editTmbWeight.text.toString().startsWith("0")
                && !binding.editTmbHeight.text.toString().startsWith("0")
                && !binding.editTmbAge.text.toString().startsWith("0"))
    }
}