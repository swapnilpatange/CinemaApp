package cinema.app.cinemaapp.view.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingDecoration(private val mSpaceHeight: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.bottom = mSpaceHeight
        outRect.top = mSpaceHeight
        outRect.left = mSpaceHeight
        outRect.right = mSpaceHeight
    }
}