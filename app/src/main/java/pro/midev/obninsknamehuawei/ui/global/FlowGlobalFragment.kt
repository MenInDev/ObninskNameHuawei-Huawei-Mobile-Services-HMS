package pro.midev.obninsknamehuawei.ui.global

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import pro.midev.obninsknamehuawei.Screens
import pro.midev.obninsknamehuawei.Screens.APP_ROUTER
import pro.midev.obninsknamehuawei.common.base.FlowFragment
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

class FlowGlobalFragment : FlowFragment(APP_ROUTER), GlobalView {

    @InjectPresenter
    lateinit var presenter: GlobalPresenter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.applyCommands(
                arrayOf(
                    BackTo(null),
                    Replace(Screens.Splash)
                )
            )
        }
    }
}