package pro.midev.obninsknamehuawei.ui.main.more

import android.os.Bundle
import pro.midev.obninsknamehuawei.Screens
import pro.midev.obninsknamehuawei.Screens.MORE_ROUTER
import pro.midev.obninsknamehuawei.common.base.FlowFragment
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

class FlowMoreFragment : FlowFragment(MORE_ROUTER) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.applyCommands(
                arrayOf(
                    BackTo(null),
                    Replace(Screens.Info)
                )
            )
        }
    }
}