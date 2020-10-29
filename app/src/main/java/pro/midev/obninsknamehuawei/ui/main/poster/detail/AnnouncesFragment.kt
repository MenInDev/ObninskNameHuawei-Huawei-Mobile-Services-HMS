package pro.midev.obninsknamehuawei.ui.main.poster.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.common.base.BaseFragment
import pro.midev.obninsknamehuawei.extensions.addSystemBottomPadding
import pro.midev.obninsknamehuawei.extensions.addSystemTopPadding
import pro.midev.obninsknamehuawei.extensions.setMiddleDivider
import pro.midev.obninsknamehuawei.extensions.tint
import pro.midev.obninsknamehuawei.models.human.AnnounceHuman
import pro.midev.obninsknamehuawei.models.human.UnitHuman
import kotlinx.android.synthetic.main.fragment_unit_detail.*
import kotlinx.android.synthetic.main.fragment_unit_detail.toolbar
import kotlinx.android.synthetic.main.view_loading.*

class AnnouncesFragment : BaseFragment(R.layout.fragment_unit_detail), AnnouncesView {

    @InjectPresenter
    lateinit var presenter: AnnouncesPresenter

    @ProvidePresenter
    fun provideUnitDetailPresenter(): AnnouncesPresenter {
        return AnnouncesPresenter(arguments?.getParcelable(ARG_UNIT))
    }

    private val adapter by lazy { AnnouncesAdapter() }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(toolbar) {
            addSystemTopPadding()
            navigationIcon = context.getDrawable(R.drawable.ic_round_arrow_back)
                ?.tint(context.getColor(R.color.colorAccentPrimary))
            setNavigationOnClickListener {
                onBackPressed()
            }
        }

        with(rvAnnounces) {
            addSystemBottomPadding()
            adapter = this@AnnouncesFragment.adapter
            layoutManager = LinearLayoutManager(context)
            setMiddleDivider(
                this@AnnouncesFragment.adapter,
                R.drawable.divider_horizontal_line_16,
                RecyclerView.VERTICAL
            )
            setHasFixedSize(true)
        }
    }

    override fun showUnitInfo(unit: UnitHuman) {
        toolbar.title = unit.name
        tvWeb.text = unit.url
        tvWeb.visibility = if (unit.url.isNotEmpty()) View.VISIBLE else View.GONE

        tvPhone.text = unit.phone
        tvPhone.visibility = if (unit.phone.isNotEmpty()) View.VISIBLE else View.GONE

        tvAddress.text = unit.address
        tvAddress.visibility = if (unit.address.isNotEmpty()) View.VISIBLE else View.GONE

        tvEmail.text = unit.email
        tvEmail.visibility = if (unit.email.isNotEmpty()) View.VISIBLE else View.GONE
    }

    override fun showAnnounces(announces: MutableList<AnnounceHuman>) {
        adapter.addAll(announces)
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
        private const val ARG_UNIT = "arg_unit"

        fun create(unit: UnitHuman): AnnouncesFragment {
            val fragment = AnnouncesFragment()

            val args = Bundle()
            args.putParcelable(ARG_UNIT, unit)
            fragment.arguments = args

            return fragment
        }
    }
}