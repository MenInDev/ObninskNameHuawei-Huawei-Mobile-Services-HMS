package pro.midev.obninsknamehuawei.ui.main.gallery.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.models.human.GalleryHuman
import kotlinx.android.synthetic.main.item_gallery.view.*
import java.lang.IllegalArgumentException

class GalleryAdapter(
    private val onImageClick: (GalleryHuman) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val SIMPLE_VIEW = 0
    val LOADING_VIEW = 1

    val gallery: MutableList<GalleryHuman> = mutableListOf()

    private var maybeMore = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            SIMPLE_VIEW -> {
                return GalleryViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_gallery,
                        parent,
                        false
                    )
                )
            }
            LOADING_VIEW -> {
                return LoadingViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_loading,
                        parent,
                        false
                    )
                )
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            SIMPLE_VIEW -> {
                (holder as GalleryViewHolder).bind(gallery[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == gallery.size) {
            return LOADING_VIEW
        } else {
            return SIMPLE_VIEW
        }
    }

    override fun getItemCount(): Int {
        return gallery.size + if (maybeMore) 1 else 0
    }

    fun setAll(
        gallery: MutableList<GalleryHuman>,
        maybeMore: Boolean
    ) {
        this.gallery.clear()
        this.gallery.addAll(gallery)
        this.maybeMore = maybeMore
        notifyDataSetChanged()
    }

    fun addAll(gallery: MutableList<GalleryHuman>, maybeMore: Boolean) {
        this.gallery.addAll(gallery)
        this.maybeMore = maybeMore
        notifyDataSetChanged()
    }

    inner class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(gallery: GalleryHuman) {
            with(itemView) {
                with(gallery) {
                    Glide.with(context).load(image).into(ivImage)
                    setOnClickListener { onImageClick(gallery) }
                }
            }
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}