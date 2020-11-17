package pro.midev.obninsknamehuawei.ui.main.gallery.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.common.base.BaseFragment
import pro.midev.obninsknamehuawei.extensions.addSystemBottomPadding
import pro.midev.obninsknamehuawei.extensions.addSystemTopPadding
import pro.midev.obninsknamehuawei.models.human.GalleryHuman
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.view_loading.*
import timber.log.Timber

class GalleryFragment : BaseFragment(R.layout.fragment_gallery), GalleryView {

    @InjectPresenter
    lateinit var presenter: GalleryPresenter

    private val adapter by lazy { GalleryAdapter(presenter::onImageClick) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(toolbar) {
            addSystemTopPadding()
        }

        with(rvGallery) {
            addSystemBottomPadding()
            adapter = this@GalleryFragment.adapter
            val grid = GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
            grid.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (this@GalleryFragment.adapter.getItemViewType(position)) {
                        this@GalleryFragment.adapter.SIMPLE_VIEW -> 1
                        this@GalleryFragment.adapter.LOADING_VIEW -> 3
                        else -> 0
                    }
                }
            }
            layoutManager = grid
            setHasFixedSize(true)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val lm = layoutManager as LinearLayoutManager
                    if (lm.findLastCompletelyVisibleItemPosition() == this@GalleryFragment.adapter.gallery.size) {
                        presenter.loadGallery(
                            this@GalleryFragment.adapter.gallery.size,
                            true
                        )
                    }
                }
            })
        }
    }

    override fun setGallery(gallery: MutableList<GalleryHuman>, maybeMore: Boolean) {
        adapter.setAll(gallery, maybeMore)
    }

    override fun addGallery(gallery: MutableList<GalleryHuman>, maybeMore: Boolean) {
        adapter.addAll(gallery, maybeMore)
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