package pro.midev.obninsknamehuawei.ui.main.news.categories.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.common.base.BaseFragment
import pro.midev.obninsknamehuawei.extensions.addSystemBottomPadding
import pro.midev.obninsknamehuawei.extensions.addSystemTopPadding
import pro.midev.obninsknamehuawei.extensions.setMiddleDivider
import pro.midev.obninsknamehuawei.extensions.tint
import pro.midev.obninsknamehuawei.models.human.CategoryHuman
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.view_loading.*

class CategoriesFragment : BaseFragment(R.layout.fragment_categories), CategoriesView {

    @InjectPresenter
    lateinit var presenter: CategoriesPresenter

    private val adapter by lazy { CategoryAdapter(presenter::onCategoryClick) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        with(toolbar) {
            addSystemTopPadding()
            navigationIcon = context.getDrawable(R.drawable.ic_round_close)
                ?.tint(context.getColor(R.color.colorAccentPrimary))
            setNavigationOnClickListener {
                onBackPressed()
            }
        }

        with(rvCategories) {
            addSystemBottomPadding()
            adapter = this@CategoriesFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setMiddleDivider(
                this@CategoriesFragment.adapter,
                R.drawable.divider_horizontal_line_left_16,
                RecyclerView.VERTICAL
            )
            setHasFixedSize(true)
        }
    }

    override fun showCategories(categories: MutableList<CategoryHuman>) {
        adapter.addAll(categories)
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