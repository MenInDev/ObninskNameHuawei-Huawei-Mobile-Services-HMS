package pro.midev.obninsknamehuawei.ui.main.news.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.common.base.BaseFragment
import pro.midev.obninsknamehuawei.common.enums.NewsViewType
import pro.midev.obninsknamehuawei.extensions.*
import pro.midev.obninsknamehuawei.models.human.NewsHuman
import pro.midev.obninsknamehuawei.ui.main.news.main.NewsAdapter
import kotlinx.android.synthetic.main.fragment_news_search.*
import kotlinx.android.synthetic.main.view_loading.*

class SearchNewsFragment : BaseFragment(R.layout.fragment_news_search), SearchNewsView,
    TextWatcher {

    @InjectPresenter
    lateinit var presenter: SearchNewsPresenter

    private val adapter by lazy { NewsAdapter(presenter::onItemClick) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(toolbar) {
            addSystemTopPadding()
            menu.findItem(R.id.actionClear).tint(context.getColor(R.color.colorAccentPrimary))
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.actionClear -> etSearch.setText("")
                }
                return@setOnMenuItemClickListener true
            }
            navigationIcon = context.getDrawable(R.drawable.ic_round_arrow_back)
                ?.tint(context.getColor(R.color.colorAccentPrimary))
            setNavigationOnClickListener {
                onBackPressed()
            }
        }

        with(rvNews) {
            addSystemBottomPadding()
            adapter = this@SearchNewsFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setMiddleDivider(
                this@SearchNewsFragment.adapter,
                R.drawable.divider_horizontal_line_16,
                RecyclerView.VERTICAL
            )
            setHasFixedSize(true)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lm = layoutManager as LinearLayoutManager
                    if (lm.findLastCompletelyVisibleItemPosition() == this@SearchNewsFragment.adapter.news.size) {
                        presenter.loadNews(
                            etSearch.text.toString(),
                            this@SearchNewsFragment.adapter.news.size,
                            true
                        )
                    }
                }
            })
        }

        etSearch.addTextChangedListener(this)
    }

    override fun setNews(news: MutableList<NewsHuman>, maybeMore: Boolean) {
        adapter.setAll(news, maybeMore, NewsViewType.SIMPLE)
    }

    override fun addNews(news: MutableList<NewsHuman>, maybeMore: Boolean) {
        adapter.addAll(news, maybeMore)
    }

    override fun afterTextChanged(s: Editable?) {}

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        presenter.loadNews(
            etSearch.text.toString(),
            0,
            false
        )
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