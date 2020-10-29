package pro.midev.obninsknamehuawei.ui.main.news.categories.news

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
import pro.midev.obninsknamehuawei.extensions.addSystemBottomPadding
import pro.midev.obninsknamehuawei.extensions.addSystemTopPadding
import pro.midev.obninsknamehuawei.extensions.setMiddleDivider
import pro.midev.obninsknamehuawei.extensions.tint
import pro.midev.obninsknamehuawei.models.human.CategoryHuman
import pro.midev.obninsknamehuawei.models.human.NewsHuman
import pro.midev.obninsknamehuawei.ui.main.news.main.NewsAdapter
import kotlinx.android.synthetic.main.fragment_news_simple.*
import kotlinx.android.synthetic.main.view_loading.*

class CategoryNewsFragment : BaseFragment(R.layout.fragment_news_simple), CategoryNewsView {

    @InjectPresenter
    lateinit var presenter: CategoryNewsPresenter

    @ProvidePresenter
    fun provideCategoryNewsPresenter(): CategoryNewsPresenter {
        return CategoryNewsPresenter(arguments?.getParcelable<CategoryHuman>(ARG_CATEGORY)?.id?.toLongOrNull())
    }

    private val adapter by lazy { NewsAdapter(presenter::onItemClick) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(toolbar) {
            addSystemTopPadding()
            title = arguments?.getParcelable<CategoryHuman>(ARG_CATEGORY)?.name.orEmpty()
            navigationIcon = context.getDrawable(R.drawable.ic_round_arrow_back)
                ?.tint(context.getColor(R.color.colorAccentPrimary))
            setNavigationOnClickListener {
                onBackPressed()
            }
        }

        with(rvNews) {
            addSystemBottomPadding()
            adapter = this@CategoryNewsFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setMiddleDivider(
                this@CategoryNewsFragment.adapter,
                R.drawable.divider_horizontal_line_16,
                RecyclerView.VERTICAL
            )
            setHasFixedSize(true)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lm = layoutManager as LinearLayoutManager
                    if (lm.findLastCompletelyVisibleItemPosition() == this@CategoryNewsFragment.adapter.news.size) {
                        presenter.loadNews(this@CategoryNewsFragment.adapter.news.size, true)
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
        private const val ARG_CATEGORY = "arg_category"

        fun create(category: CategoryHuman): CategoryNewsFragment {
            val fragment = CategoryNewsFragment()

            val args = Bundle()
            args.putParcelable(ARG_CATEGORY, category)
            fragment.arguments = args

            return fragment
        }
    }
}