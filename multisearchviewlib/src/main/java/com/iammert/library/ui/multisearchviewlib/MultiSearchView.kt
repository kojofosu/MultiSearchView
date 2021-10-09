package com.iammert.library.ui.multisearchviewlib

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
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
        val selectedTabColor = typedArray.getColor(R.styleable.MultiSearchView_selectedTabColor, Color.BLACK)
        val clearIconColor = typedArray.getColor(R.styleable.MultiSearchView_clearIconColor, Color.BLACK)
        val searchTextColor = typedArray.getColor(R.styleable.MultiSearchView_searchTextColor, Color.BLACK)

        binding.searchViewContainer.apply {
            this.searchTextStyle = searchTextStyle
            this.selectedTabStyle = selectedTabStyle
            this.selectedTabSColor = selectedTabColor
            this.clearIconColor = clearIconColor
            this.searchTextColor = searchTextColor
        }

        setSearchIconDrawable(imageSource)
        setSelectedTabColor(selectedTabColor)
        setClearIconColor(clearIconColor)
        setSearchTextColor(searchTextColor)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setSearchIconColor(resources.getColor(searchIconColorResID, context.theme))

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

    fun setSearchTextColor(colorInt: Int) {
        binding.searchViewContainer.searchTextColor = colorInt
    }
    fun setSelectedTabColor(colorInt: Int){
        binding.searchViewContainer.selectedTabSColor = colorInt
    }

    fun setSearchIconDrawable(drawable: Int) {
        binding.imageViewSearch.setImageResource(drawable)
    }

    fun setHintTextColor(colorString: String) {
        binding.searchViewContainer.hintColorStateList = ColorStateList.valueOf(Color.parseColor(colorString))
    }

    fun setClearIconColor(colorInt: Int) {
        binding.searchViewContainer.clearIconColor = colorInt
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun setHintTextAppearance(@ColorRes resId: Int) {
        binding.searchViewContainer.hintColorStateList = resources.getColorStateList(resId, context.theme)
    }

    fun setHintTextColor(colors: ColorStateList) {
        binding.searchViewContainer.hintColorStateList = colors
    }

    fun setHintTextColor(@ColorInt colorInt: Int) {
        binding.searchViewContainer.hintColorStateList = ColorStateList.valueOf(colorInt)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setSearchIconColor(colorString: String){
        val drawable: Drawable = binding.imageViewSearch.drawable
        drawable.setTint(Color.parseColor(colorString))
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setSearchIconColor(@ColorInt colorInt: Int){
        val drawable: Drawable = binding.imageViewSearch.drawable
        drawable.setTint(colorInt)
    }
}