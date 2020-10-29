package pro.midev.obninsknamehuawei.ui.main.poster.map

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.models.human.UnitHuman
import kotlinx.android.synthetic.main.item_unit.view.*

class UnitsAdapter(
    private val onPosterClick: (UnitHuman) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val units: MutableList<UnitHuman> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return UnitViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_unit, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UnitViewHolder).bind(units[position])
    }

    override fun getItemCount(): Int {
        return units.size
    }

    fun addAll(units: MutableList<UnitHuman>) {
        this.units.clear()
        this.units.addAll(units)
        notifyDataSetChanged()
    }

    fun getCurrentItem() {

    }

    fun getCurrentItemPosition(unit: UnitHuman): Int {
        units.forEachIndexed { index, shopHuman ->
            if (unit.id == shopHuman.id) return index
        }
        return 0
    }

    fun getCurrentItem(position: Int): UnitHuman {
        return units[position]
    }

    inner class UnitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(unit: UnitHuman) {
            with(itemView) {
                with(unit) {
                    tvTitle.text = name
                    tvAddress.text = address
                    btnWatch.setOnClickListener { onPosterClick(unit) }
                }
            }
        }
    }
}