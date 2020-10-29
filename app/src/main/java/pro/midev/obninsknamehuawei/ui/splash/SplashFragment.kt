package pro.midev.obninsknamehuawei.ui.splash

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.common.base.BaseFragment

class SplashFragment : BaseFragment(R.layout.fragment_splash), MvpView {

    @InjectPresenter
    lateinit var presenter: SplashPresenter

    override fun onBackPressed() {
        presenter.back()
    }
}