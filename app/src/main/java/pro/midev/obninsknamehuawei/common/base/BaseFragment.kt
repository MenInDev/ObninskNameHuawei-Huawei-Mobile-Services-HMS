package pro.midev.obninsknamehuawei.common.base

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseFragment(private val layoutRes: Int) : MvpAppCompatFragment() {
    private var instanceStateSaved: Boolean = false

    private val viewHandler = Handler()

    private val compositeDisposable = CompositeDisposable()

    protected fun Disposable.connect() {
        compositeDisposable.add(this)
    }

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutRes, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
        viewHandler.removeCallbacksAndMessages(null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        instanceStateSaved = true
    }

    open fun onBackPressed() {}
}