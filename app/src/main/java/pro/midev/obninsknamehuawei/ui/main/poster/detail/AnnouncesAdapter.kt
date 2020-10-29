package pro.midev.obninsknamehuawei.ui.main.poster.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.models.human.AnnounceHuman
import kotlinx.android.synthetic.main.item_announce.view.*

class AnnouncesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val announces: MutableList<AnnounceHuman> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return AnnounceViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_announce, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as AnnounceViewHolder).bind(announces[position])
    }

    override fun getItemCount(): Int {
        return announces.size
    }

    fun addAll(announces: MutableList<AnnounceHuman>) {
        this.announces.clear()
        this.announces.addAll(announces)
        notifyDataSetChanged()
    }

    inner class AnnounceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(announce: AnnounceHuman) {
            with(itemView) {
                with(announce) {
                    Glide.with(context).load(image).into(ivImage)
                    tvType.text = eventType
                    tvTitle.text = eventName
                    tvSchedule.text = "$date $time"
                    tvDesc.text = desc
                }
            }
        }
    }
}