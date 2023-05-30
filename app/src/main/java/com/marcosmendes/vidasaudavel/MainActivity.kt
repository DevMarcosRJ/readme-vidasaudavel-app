package com.marcosmendes.vidasaudavel

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marcosmendes.vidasaudavel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val mainItems = mutableListOf<MainItem>()

        mainItems.add(
            MainItem(
                id = 1,
                drawableId = R.drawable.ic_baseline_show_chart_24,
                stringResId = R.string.imc,
                color = Color.GRAY
            )
        )

        mainItems.add(
            MainItem(
                id = 2,
                drawableId = R.drawable.ic_baseline_swap_vert_24,
                stringResId = R.string.tmb,
                color = Color.LTGRAY
            )
        )

        val adapter = MainAdapter(mainItems) { id ->
            when(id){
                1 -> { val intent = Intent(this@MainActivity, ImcActivity::class.java)
                        startActivity(intent)}

                2 -> { val intent = Intent(this@MainActivity, ImcActivity::class.java)
                startActivity(intent)}
            }
        }

        binding.rvMain.adapter = adapter
        binding.rvMain.layoutManager = GridLayoutManager(this, 2)

    }

    inner class MainAdapter(
        private val mainItems: List<MainItem>,
        private val onItemClickListener: (Int) -> Unit,
    ) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            return MainViewHolder(view)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItems[position]
            holder.bind(itemCurrent)
        }

        override fun getItemCount(): Int {
            return mainItems.size
        }

        inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem) {
                val img: ImageView = itemView.findViewById(R.id.item_img_icon)
                val name: TextView = itemView.findViewById(R.id.item_txt_name)
                val container: LinearLayout = itemView.findViewById(R.id.item_container_imc)

                img.setImageResource(item.drawableId)
                name.setText(item.stringResId)
                container.setBackgroundColor(item.color)

                container.setOnClickListener {
                    onItemClickListener.invoke(item.id)
                }
            }
        }
    }
}