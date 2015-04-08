
package de.codewild.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

import de.codewild.graphics.Typefaces;
import de.codewild.iconfontlib.R;


public class IconFontTextView extends TextView {

    private String mFontPath;

    public IconFontTextView(final Context context) {
        this(context, null);
    }

    public IconFontTextView(final Context context, final AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public IconFontTextView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(attrs);
    }

    private void init(final AttributeSet attrs) {
        final TypedArray attributes = this.getContext().obtainStyledAttributes(attrs, R.styleable.IconFontTextView);
        final String path = attributes.getString(R.styleable.IconFontTextView_font_path);
        this.setFontPath(path);
        attributes.recycle();
    }

    private void setTypeface(final String path) {
        if (path == null || this.isInEditMode()) {
            return;
        }
        final Typeface typeface = Typefaces.fromAsset(this.getContext(), path);
        this.setTypeface(typeface);
    }

    public String getFontPath() {
        return this.mFontPath;
    }

    public void setFontPath(final String path) {
        if (!TextUtils.equals(path, this.mFontPath)) {
            this.mFontPath = path;
            this.setTypeface(path);
        }
    }
}
