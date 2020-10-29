package pro.midev.obninsknamehuawei.ui.main.news.categories.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.models.human.CategoryHuman
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter(
    private val onCategoryClick: (CategoryHuman) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val categories: MutableList<CategoryHuman> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CategoryViewHolder).bind(categories[position])
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    fun addAll(categories: MutableList<CategoryHuman>) {
        this.categories.clear()
        this.categories.addAll(categories)
        notifyDataSetChanged()
    }

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: CategoryHuman) {
            with(itemView) {
                with(category) {
                    tvName.text = name
                    setOnClickListener { onCategoryClick(category) }
                }
            }
        }
    }
}