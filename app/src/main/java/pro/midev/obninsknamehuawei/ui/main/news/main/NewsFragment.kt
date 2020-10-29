package pro.midev.obninsknamehuawei.ui.main.news.main

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.huawei.agconnect.crash.AGConnectCrash
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.common.base.BaseFragment
import pro.midev.obninsknamehuawei.common.enums.NewsType
import pro.midev.obninsknamehuawei.extensions.*
import pro.midev.obninsknamehuawei.models.human.NewsHuman
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.view_loading.*
import java.util.*

class NewsFragment : BaseFragment(R.layout.fragment_news), NewsView {

    @InjectPresenter
    lateinit var presenter: NewsPresenter

    private val adapter by lazy { NewsAdapter(presenter::onItemClick) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(toolbar) {
            addSystemTopPadding()
            menu.findItem(R.id.actionCalendar).tint(context.getColor(R.color.colorAccentPrimary))
            menu.findItem(R.id.actionSearch).tint(context.getColor(R.color.colorAccentPrimary))
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.actionCalendar -> presenter.calendar()
                    R.id.actionSearch -> presenter.search()
                }
                return@setOnMenuItemClickListener true
            }
        }

        with(rvNews) {
            addSystemBottomPadding()
            adapter = this@NewsFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setMiddleDivider(
                this@NewsFragment.adapter,
                R.drawable.divider_horizontal_line_16,
                RecyclerView.VERTICAL
            )
            setHasFixedSize(true)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lm = layoutManager as LinearLayoutManager
                    if (lm.findLastCompletelyVisibleItemPosition() == this@NewsFragment.adapter.news.size) {
                        presenter.loadNews(this@NewsFragment.adapter.news.size, true)
                    }
                }
            })
        }

        tvCategories.setOnClickListener { presenter.categories() }
    }

    override fun initCategories(currentNewsType: NewsType) {
        vgNewsType.removeAllViews()

        NewsType.values().forEach {
            val view = layoutInflater.inflate(R.layout.item_news_type, vgNewsType, false)
            val btnNewsType = view.findViewById<Button>(R.id.btnNewsType)

            btnNewsType.text = context?.convertToName(it)
            btnNewsType.isEnabled = it != currentNewsType
            btnNewsType.setOnClickListener { _ ->
                presenter.changeType(it)
            }

            vgNewsType.addView(view)
        }
    }

    override fun setNews(news: MutableList<NewsHuman>, maybeMore: Boolean) {
        adapter.setAll(news, maybeMore)
        rvNews.scrollToPosition(0)
    }

    override fun addNews(news: MutableList<NewsHuman>, maybeMore: Boolean) {
        adapter.addAll(news, maybeMore)
    }

    override fun changeCategory(type: NewsType) {
        vgNewsType.children.forEachIndexed { childIndex, view ->
            val button = view as Button
            button.isEnabled = NewsType.values().indexOf(type) != childIndex
        }
    }

    override fun showCalendar() {
        val calendar = Calendar.getInstance()
        context?.let {
            val dpd = DatePickerDialog(
                it,
                { _, year, month, dayOfMonth ->
                    presenter.dateNews(year, month, dayOfMonth)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            dpd.datePicker.maxDate = calendar.timeInMillis
            dpd.show()
        }
    }

    override fun toggleLoading(show: Boolean) {
        loading.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        presenter.back()
    }
}