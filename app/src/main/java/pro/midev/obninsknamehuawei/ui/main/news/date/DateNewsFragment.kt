package pro.midev.obninsknamehuawei.ui.main.news.date

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.common.base.BaseFragment
import pro.midev.obninsknamehuawei.common.enums.NewsViewType
import pro.midev.obninsknamehuawei.extensions.*
import pro.midev.obninsknamehuawei.models.human.NewsHuman
import pro.midev.obninsknamehuawei.ui.main.news.main.NewsAdapter
import kotlinx.android.synthetic.main.fragment_news_simple.*
import kotlinx.android.synthetic.main.view_loading.*

class DateNewsFragment : BaseFragment(R.layout.fragment_news_simple), DateNewsView {

    @InjectPresenter
    lateinit var presenter: DateNewsPresenter

    @ProvidePresenter
    fun provideDateNewsPresenter(): DateNewsPresenter {
        return DateNewsPresenter(arguments?.getLong(ARG_DATE))
    }

    private val adapter by lazy { NewsAdapter(presenter::onItemClick) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(toolbar) {
            addSystemTopPadding()
            title = arguments?.getLong(ARG_DATE)?.convertToNewsDate(false)
            navigationIcon = context.getDrawable(R.drawable.ic_round_arrow_back)
                ?.tint(context.getColor(R.color.colorAccentPrimary))
            setNavigationOnClickListener {
                onBackPressed()
            }
        }

        with(rvNews) {
            addSystemBottomPadding()
            adapter = this@DateNewsFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setMiddleDivider(
                this@DateNewsFragment.adapter,
                R.drawable.divider_horizontal_line_16,
                RecyclerView.VERTICAL
            )
            setHasFixedSize(true)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lm = layoutManager as LinearLayoutManager
                    if (lm.findLastCompletelyVisibleItemPosition() == this@DateNewsFragment.adapter.news.size) {
                        presenter.loadNews(this@DateNewsFragment.adapter.news.size, true)
                    }
                }
            })
        }
    }

    override fun setNews(news: MutableList<NewsHuman>, maybeMore: Boolean) {
        adapter.setAll(news, maybeMore, NewsViewType.SIMPLE)
    }

    override fun addNews(news: MutableList<NewsHuman>, maybeMore: Boolean) {
        adapter.addAll(news, maybeMore)
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

    companion object {
        private const val ARG_DATE = "arg_date"

        fun create(date: Long): DateNewsFragment {
            val fragment = DateNewsFragment()

            val args = Bundle()
            args.putLong(ARG_DATE, date)
            fragment.arguments = args

            return fragment
        }
    }
}