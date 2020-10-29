package pro.midev.obninsknamehuawei.common.base

import com.arellomobile.mvp.MvpView

interface BaseView : MvpView {
    fun toggleLoading(show: Boolean)

    fun showError(errorMessage: String)
}