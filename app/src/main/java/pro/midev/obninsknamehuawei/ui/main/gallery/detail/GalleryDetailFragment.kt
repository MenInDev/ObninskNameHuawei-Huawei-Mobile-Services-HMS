package pro.midev.obninsknamehuawei.ui.main.gallery.detail

import android.os.Bundle
import android.text.Html
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.common.base.BaseFragment
import pro.midev.obninsknamehuawei.extensions.addSystemBottomPadding
import pro.midev.obninsknamehuawei.extensions.addSystemTopPadding
import pro.midev.obninsknamehuawei.extensions.tint
import pro.midev.obninsknamehuawei.models.human.GalleryHuman
import kotlinx.android.synthetic.main.fragment_gallery_detail.*

class GalleryDetailFragment : BaseFragment(R.layout.fragment_gallery_detail), GalleryDetailView {

    @InjectPresenter
    lateinit var presenter: GalleryDetailPresenter

    @ProvidePresenter
    fun provideGalleryDetailPresenter(): GalleryDetailPresenter {
        return GalleryDetailPresenter(arguments?.getParcelable(ARG_GALLERY))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(toolbar) {
            addSystemTopPadding()
            navigationIcon = context.getDrawable(R.drawable.ic_round_arrow_back)
                ?.tint(context.getColor(R.color.white))
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
        wrapper.addSystemBottomPadding()
    }

    override fun showImage(gallery: GalleryHuman) {
        Glide.with(this).load(gallery.image).into(ivImage)

        tvDesc.text = Html.fromHtml(gallery.desc)
        tvDesc.visibility = if (gallery.desc.isNotEmpty()) View.VISIBLE else View.GONE

        btnNews.visibility = if (gallery.id.isNotEmpty()) View.VISIBLE else View.GONE
        btnNews.setOnClickListener { presenter.news(gallery.id) }
    }

    override fun onBackPressed() {
        presenter.back()
    }

    companion object {
        private const val ARG_GALLERY = "arg_gallery"

        fun create(gallery: GalleryHuman): GalleryDetailFragment {
            val fragment = GalleryDetailFragment()

            val args = Bundle()
            args.putParcelable(ARG_GALLERY, gallery)
            fragment.arguments = args

            return fragment
        }
    }
}