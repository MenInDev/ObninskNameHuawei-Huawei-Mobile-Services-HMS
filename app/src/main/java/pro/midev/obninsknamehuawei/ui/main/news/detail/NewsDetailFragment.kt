package pro.midev.obninsknamehuawei.ui.main.news.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.common.base.BaseFragment
import pro.midev.obninsknamehuawei.extensions.addSystemBottomPadding
import pro.midev.obninsknamehuawei.extensions.setTextViewHTML
import pro.midev.obninsknamehuawei.extensions.tint
import pro.midev.obninsknamehuawei.models.human.NewsHuman
import kotlinx.android.synthetic.main.fragment_news_detail.*
import kotlinx.android.synthetic.main.view_loading.*

class NewsDetailFragment : BaseFragment(R.layout.fragment_news_detail), NewsDetailView {

    @InjectPresenter
    lateinit var presenter: NewsDetailPresenter

    @ProvidePresenter
    fun provideNewsDetailPresenter(): NewsDetailPresenter {
        return NewsDetailPresenter(arguments?.getLong(ARG_ID))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(toolbar) {
            navigationIcon = context.getDrawable(R.drawable.ic_round_arrow_back)
                ?.tint(context.getColor(R.color.white))
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
        coordinator.addSystemBottomPadding()
    }

    override fun showNews(news: NewsHuman) {
        context?.let {
            Glide.with(this).load(news.image).into(ivCollapsing)
            tvTopInfo.text = getString(R.string.news_detail_top_info_template, news.date, news.tag)
            tvTitle.text = news.title
            setTextViewHTML(tvDesc, news.text, it, presenter::onAppLinkClick)
            tvAuthor.text = news.author
            tvReviews.text = news.reviews.toString()
        }

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.actionTranslate -> {
                    presenter.translate(news)
                }
            }
            return@setOnMenuItemClickListener true
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

    companion object {
        private const val ARG_ID = "arg_id"

        fun create(id: Long): NewsDetailFragment {
            val fragment = NewsDetailFragment()

            val args = Bundle()
            args.putLong(ARG_ID, id)
            fragment.arguments = args

            return fragment
        }
    }
}