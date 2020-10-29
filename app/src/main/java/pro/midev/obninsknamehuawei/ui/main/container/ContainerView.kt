package pro.midev.obninsknamehuawei.ui.main.container

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface ContainerView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeBottomNavigationVisibility(isShow: Boolean)
}