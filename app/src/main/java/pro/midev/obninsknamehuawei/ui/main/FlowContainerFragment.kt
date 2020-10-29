package pro.midev.obninsknamehuawei.ui.main

import android.os.Bundle
import pro.midev.obninsknamehuawei.Screens
import pro.midev.obninsknamehuawei.Screens.CONTAINER_ROUTER
import pro.midev.obninsknamehuawei.common.base.FlowFragment
import ru.terrakok.cicerone.commands.BackTo
import ru.terrakok.cicerone.commands.Replace

class FlowContainerFragment : FlowFragment(CONTAINER_ROUTER) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (childFragmentManager.fragments.isEmpty()) {
            navigator.applyCommands(
                arrayOf(
                    BackTo(null),
                    Replace(Screens.Container)
                )
            )
        }
    }
}