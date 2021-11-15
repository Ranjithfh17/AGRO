package `in`.global.agro.ui.popup

import `in`.global.agro.R
import `in`.global.agro.utils.ViewUtils
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.inline.InlineContentView

open class CustomPopup : PopupWindow(){


    fun init(contentView: View,view:ViewGroup,xOff:Float,yOff:Float){
        setContentView(contentView)
        init()
        showAsDropDown(view,xOff.toInt() - width/2,yOff.toInt()-height,Gravity.START)

    }


    fun init(contentView: View,view:ViewGroup){
        setContentView(contentView)
        init()
        showAsDropDown(view, (width / 1.2F * -1).toInt(), height / 8, Gravity.NO_GRAVITY)
    }




    private fun init(){
        contentView.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED)
        contentView.clipToOutline=false
        width=contentView.measuredWidth
        height=contentView.measuredHeight
        isClippingEnabled=false
        animationStyle= R.anim.image_out
        isFocusable=true
        elevation=50F

        overlapAnchor=false

        ViewUtils.addOutlineShadow(contentView)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            setIsClippedToScreen(false)
            setIsLaidOutInScreen(true)
        }

    }


    override fun showAsDropDown(anchor: View?) {
        super.showAsDropDown(anchor)
        ViewUtils.dimBackground(contentView)
    }

    override fun showAsDropDown(anchor: View?, xoff: Int, yoff: Int, gravity: Int) {
        super.showAsDropDown(anchor, xoff, yoff, gravity)
        ViewUtils.dimBackground(contentView)

    }



}