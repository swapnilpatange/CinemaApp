package cinema.app.cinemaapp.view.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearSpacingDecoration(private val mSpaceHeight: Int, val size: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = mSpaceHeight
        outRect.left = mSpaceHeight
        outRect.right = mSpaceHeight
        if (parent.getChildAdapterPosition(view) == size - 1) {
            outRect.bottom = mSpaceHeight
        } else {
            outRect.bottom = 0
        }

    }
}