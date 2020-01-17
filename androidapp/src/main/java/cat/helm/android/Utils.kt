package cat.helm.android

import android.widget.ImageView
import com.squareup.picasso.Picasso

fun ImageView.bind(url: String) {
    Picasso.get().load(url).into(this)
}
