package pro.midev.obninsknamehuawei.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.midev.obninsknamehuawei.R
import pro.midev.obninsknamehuawei.common.enums.NewsType
import java.text.SimpleDateFormat
import java.util.*

fun MenuItem.tint(color: Int) {
    val drawable = icon
    val wrapDrawable = DrawableCompat.wrap(drawable)
    DrawableCompat.setTint(wrapDrawable, color)

    icon = wrapDrawable
}

fun Drawable.tint(color: Int): Drawable {
    val wrapDrawable = DrawableCompat.wrap(this)
    DrawableCompat.setTint(wrapDrawable, color)
    return wrapDrawable
}

fun RecyclerView.setMiddleDivider(
    adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>,
    divider: Int,
    orientation: Int
) {
    val decorator = object : DividerItemDecoration(context, orientation) {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            if (position == adapter.itemCount - 1) {
                outRect.setEmpty()
            } else {
                super.getItemOffsets(outRect, view, parent, state)
            }
        }
    }

    ResourcesCompat.getDrawable(resources, divider, null)?.let {
        decorator.setDrawable(it)
    }

    addItemDecoration(decorator)
}

fun Long.convertToNewsDate(addMls: Boolean = true): String {
    val currentCalendar = Calendar.getInstance()
    val newsCalendar = Calendar.getInstance()

    newsCalendar.timeInMillis = this

    if (newsCalendar.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR) &&
        newsCalendar.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH) &&
        newsCalendar.get(Calendar.DAY_OF_MONTH) == currentCalendar.get(Calendar.DAY_OF_MONTH)
    ) {
        if (addMls) {
            return SimpleDateFormat("HH:mm", Locale.getDefault()).format(this * 1000)
        } else {
            return SimpleDateFormat("HH:mm", Locale.getDefault()).format(this)
        }
    } else {
        if (addMls) {
            return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(this * 1000)
        } else {
            return SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(this)
        }
    }
}

fun Long.toServerDate(): String {
    return SimpleDateFormat("yyyy/MM/dd", Locale.getDefault()).format(this)
}

fun NewsType.convertToServer(): String {
    return when (this) {
        NewsType.ALL -> "all"
        NewsType.HOT -> "hot"
        NewsType.ARTICLE -> "article"
    }
}

fun Context.convertToName(newsType: NewsType): String {
    return when (newsType) {
        NewsType.ALL -> getString(R.string.news_all)
        NewsType.HOT -> getString(R.string.news_hot)
        NewsType.ARTICLE -> getString(R.string.news_article)
    }
}

fun setTextViewHTML(
    text: TextView,
    html: String,
    context: Context,
    onAppLinkClick: (String) -> Unit
) {
    val sequence = Html.fromHtml(html)
    val strBuilder = SpannableStringBuilder(sequence)
    val urls = strBuilder.getSpans(0, sequence.length, URLSpan::class.java)
    for (span in urls) {
        makeLinkClickable(strBuilder, span, context, onAppLinkClick)
    }
    text.text = strBuilder
    text.movementMethod = LinkMovementMethod.getInstance()
}

fun makeLinkClickable(
    strBuilder: SpannableStringBuilder,
    span: URLSpan,
    context: Context,
    onAppLinkClick: (String) -> Unit
) {
    val start = strBuilder.getSpanStart(span)
    val end = strBuilder.getSpanEnd(span)
    val flags = strBuilder.getSpanFlags(span)
    val clickable = object : ClickableSpan() {
        override fun onClick(view: View) {
            if (span.url.startsWith("http://") || span.url.startsWith("https://")) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(span.url))
                context.startActivity(browserIntent)
            } else {
                val newsId: String = span.url.replace("/news", "").replace(".htm", "")
                Toast.makeText(context, newsId, Toast.LENGTH_SHORT).show()
                onAppLinkClick(newsId)
            }
        }
    }
    strBuilder.setSpan(clickable, start, end, flags)
    strBuilder.removeSpan(span)
}

fun convertTimeToDuration(start: String, end: String): String {
    var startTime = start

    if (startTime.isNotEmpty()) startTime = startTime.removeSuffix(":00")
    startTime = if (startTime == "00:00") "" else "с $startTime"

    var endTime = end

    if (endTime.isNotEmpty()) endTime = endTime.removeSuffix(":00")
    endTime = if (endTime == "00:00") "" else "до $endTime"

    return "$startTime $endTime"
}