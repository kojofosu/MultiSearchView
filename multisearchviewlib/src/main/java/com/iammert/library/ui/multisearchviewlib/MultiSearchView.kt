package com.iammert.library.ui.multisearchviewlib

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import com.iammert.library.ui.multisearchviewlib.databinding.ViewMultiSearchBinding
import com.iammert.library.ui.multisearchviewlib.extensions.inflate

class MultiSearchView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    RelativeLayout(context, attrs, defStyleAttr) {

    interface MultiSearchViewListener {

        fun onTextChanged(index: Int, s: CharSequence)

        fun onSearchComplete(index: Int, s: CharSequence)

        fun onSearchItemRemoved(index: Int)

        fun onItemSelected(index: Int, s: CharSequence)
    }

    private val binding = inflate<ViewMultiSearchBinding>(R.layout.view_multi_search)

    var hint : String
        get() = binding.searchViewContainer.hint
        set(value) {
            binding.searchViewContainer.hint = value
        }

    init {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.MultiSearchView, defStyleAttr, defStyleAttr)
        val searchTextStyle = typedArray.getResourceId(R.styleable.MultiSearchView_searchTextStyle, 0)
        val imageSource = typedArray.getResourceId(R.styleable.MultiSearchView_searchIcon, R.drawable.ic_round_search_24px)
        val searchIconColorResID = typedArray.getResourceId(R.styleable.MultiSearchView_searchIconColor, android.R.color.black)
        val searchIconColorColorString = typedArray.getString(R.styleable.MultiSearchView_searchIconColor)
        val selectedTabStyle = typedArray.getInteger(R.styleable.MultiSearchView_selectedTabStyle, 0)

        binding.searchViewContainer.apply {
            this.searchTextStyle = searchTextStyle
            this.selectedTabStyle = selectedTabStyle
        }

        setSearchIconDrawable(imageSource)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if(searchIconColorResID != null)
                setSearchIconColor(searchIconColorResID)

            if (searchIconColorColorString != null)
                setSearchIconColor(searchIconColorColorString)

        }

        binding.imageViewSearch.setOnClickListener {
            if (binding.searchViewContainer.isInSearchMode().not()) {
                binding.searchViewContainer.search()
            } else {
                binding.searchViewContainer.completeSearch()
            }
        }
    }

    fun setSearchViewListener(multiSearchViewListener: MultiSearchViewListener) {
        binding.searchViewContainer.setSearchViewListener(multiSearchViewListener)
    }


    fun setSearchIconDrawable(drawable: Int) {
        binding.imageViewSearch.setImageResource(drawable)
    }

    fun setHintColor(colorString: String) {
        val colorInt = Color.parseColor(colorString)
        binding.searchViewContainer.hintColor = colorInt
    }

    fun setHintColor(color: Int) {
        val colorStateList = AppCompatResources.getColorStateList(context, color)
        binding.searchViewContainer.hintColorStateList = colorStateList
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setSearchIconColor(color : Int) {
        binding.imageViewSearch.imageTintList = AppCompatResources.getColorStateList(context, color)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setSearchIconColor(colorString: String){
        val drawable: Drawable = binding.imageViewSearch.drawable
        drawable.setTint(Color.parseColor(colorString))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setSearchIconColor(color: Color){
        val drawable: Drawable = binding.imageViewSearch.drawable
        drawable.setTint(color.toArgb())
    }
}