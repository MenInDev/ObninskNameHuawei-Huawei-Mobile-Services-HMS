package pro.midev.obninsknamehuawei.ui.main.container

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.arellomobile.mvp.presenter.InjectPresenter
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.Screens
import pro.midev.obninsknamehuawei.common.base.BaseFragment
import pro.midev.obninsknamehuawei.extensions.addSystemBottomPadding
import kotlinx.android.synthetic.main.fragment_container.*
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ContainerFragment : BaseFragment(R.layout.fragment_container), ContainerView {

    @InjectPresenter
    lateinit var presenter: ContainerPresenter

    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseFragment

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AHBottomNavigationAdapter(activity, R.menu.menu_bottom_navigation).apply {
            setupWithBottomNavigation(bottomNavigation)
        }

        with(bottomNavigation) {
            addSystemBottomPadding()

            accentColor =
                ContextCompat.getColor(context, R.color.textAccent)
            inactiveColor =
                ContextCompat.getColor(context, R.color.textSecondary)

            titleState =
                com.aurelhubert.ahbottomnavigation.AHBottomNavigation.TitleState.ALWAYS_SHOW

            setOnTabSelectedListener { position, wasSelected ->
                if (!wasSelected) selectTab(
                    when (position) {
                        0 -> newsTab
                        1 -> posterTab
                        2 -> galleryTab
                        3 -> moreTab
                        else -> newsTab
                    }
                )
                true
            }

            selectTab(
                when (currentTabFragment?.tag) {
                    newsTab.screenKey -> newsTab
                    else -> newsTab
                }
            )
        }
    }

    private fun selectTab(tab: SupportAppScreen) {
        val currentFragment = currentTabFragment
        val newFragment = childFragmentManager.findFragmentByTag(tab.screenKey)

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) return

        childFragmentManager.beginTransaction().apply {
            if (newFragment == null) {
                createTabFragment(tab)?.let {
                    replace(
                        R.id.vgFragmentContainer,
                        it,
                        tab.screenKey
                    )
                }
            }
        }.commitNow()
    }

    private fun createTabFragment(tab: SupportAppScreen) = tab.fragment

    override fun changeBottomNavigationVisibility(isShow: Boolean) {
        bottomNavigation.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        currentTabFragment?.onBackPressed() ?: super.onBackPressed()
    }

    companion object {
        private val newsTab = Screens.FlowNews
        private val posterTab = Screens.FlowPoster
        private val galleryTab = Screens.FlowGallery
        private val moreTab = Screens.FlowMore
    }
}