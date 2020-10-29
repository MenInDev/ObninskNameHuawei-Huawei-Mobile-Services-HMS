package pro.midev.obninsknamehuawei.ui.main.news.main

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.common.enums.NewsViewType
import pro.midev.obninsknamehuawei.models.human.NewsHuman
import kotlinx.android.synthetic.main.item_news_big.view.*
import kotlinx.android.synthetic.main.item_news_small.view.*
import java.lang.IllegalArgumentException

class NewsAdapter(
    private val onItemClick: (NewsHuman) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var BIG_VIEW = 0
    private var SMALL_VIEW = 1
    private var LOADING_VIEW = 2

    val news: MutableList<NewsHuman> = mutableListOf()

    private var maybeMore = false
    private var currentViewType: NewsViewType = NewsViewType.BIG

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            BIG_VIEW -> {
                return BigNewsViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_news_big,
                        parent,
                        false
                    )
                )
            }
            SMALL_VIEW -> {
                return SmallNewsViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_news_small,
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
            BIG_VIEW -> {
                (holder as BigNewsViewHolder).bind(news[position])
            }
            SMALL_VIEW -> {
                (holder as SmallNewsViewHolder).bind(news[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 && currentViewType != NewsViewType.SIMPLE) {
            return BIG_VIEW
        } else if (position == news.size) {
            return LOADING_VIEW
        } else {
            return SMALL_VIEW
        }
    }

    override fun getItemCount(): Int {
        return news.size + if (maybeMore) 1 else 0
    }

    fun setAll(
        news: MutableList<NewsHuman>,
        maybeMore: Boolean,
        currentViewType: NewsViewType = NewsViewType.BIG
    ) {
        this.currentViewType = currentViewType
        this.news.clear()
        this.news.addAll(news)
        this.maybeMore = maybeMore
        notifyDataSetChanged()
    }

    fun addAll(news: MutableList<NewsHuman>, maybeMore: Boolean) {
        this.news.addAll(news)
        this.maybeMore = maybeMore
        notifyDataSetChanged()
    }

    inner class BigNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(news: NewsHuman) {
            with(itemView) {
                with(news) {
                    Glide.with(context).load(image).into(ivImageBig)
                    tvTitleBig.text = title
                    if (text.isNotEmpty()) {
                        tvDescBig.visibility = View.VISIBLE
                        tvDescBig.text = Html.fromHtml(text)
                    } else {
                        tvDescBig.visibility = View.GONE
                    }
                    tvDateBig.text = date

                    setOnClickListener { onItemClick(news) }
                }
            }
        }
    }

    inner class SmallNewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(news: NewsHuman) {
            with(itemView) {
                with(news) {
                    Glide.with(context).load(image).into(ivImageSmall)
                    tvTitleSmall.text = title
                    tvDateSmall.text = date

                    setOnClickListener { onItemClick(news) }
                }
            }
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}