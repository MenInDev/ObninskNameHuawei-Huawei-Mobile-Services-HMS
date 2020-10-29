package pro.midev.obninsknamehuawei.ui.main.more.main

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.common.base.BaseFragment
import pro.midev.obninsknamehuawei.extensions.addSystemTopPadding
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.view_loading.*

class InfoFragment : BaseFragment(R.layout.fragment_info), InfoView {

    @InjectPresenter
    lateinit var presenter: InfoPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(toolbar) {
            addSystemTopPadding()
        }
    }

    override fun showInfo(text: String) {
        tvInfo.text = Html.fromHtml(text)
        tvInfo.movementMethod = LinkMovementMethod.getInstance()
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