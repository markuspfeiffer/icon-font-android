
package de.codewild.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import de.codewild.graphics.Screen;
import de.codewild.graphics.Typefaces;
import de.codewild.iconfontlib.R;


public class IconFontView extends View {

    private static final String EDIT_MODE_GLYPH = "T";

    private static final int    DEFAULT_WIDTH   = Screen.dip2px(48);
    private static final int    DEFAULT_HEIGHT  = Screen.dip2px(48);

    private String              mFontPath;

    private String              mGlyph          = EDIT_MODE_GLYPH;

    private final Paint         mGlyphPaint     = new Paint();
    private final Rect          mGlyphBounds    = new Rect();

    private float               mGlyphX;
    private float               mGlyphY;

    public IconFontView(final Context context) {
        this(context, null);
    }

    public IconFontView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IconFontView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(attrs);
    }

    private void init(final AttributeSet attrs) {
        final TypedArray attributes = this.getContext().obtainStyledAttributes(attrs, R.styleable.IconFontView);

        final String path = attributes.getString(R.styleable.IconFontView_font_path);

        final String glyph = attributes.getString(R.styleable.IconFontView_glyph);
        final int glyphColor = attributes.getColor(R.styleable.IconFontView_glyph_color, Color.BLACK);

        if (!this.isInEditMode()) {
            this.setFontPath(path);
            this.setGlyph(glyph);
        }

        this.mGlyphPaint.setTextAlign(Align.CENTER);
        this.mGlyphPaint.setAntiAlias(true);
        this.mGlyphPaint.setTextSize(100);
        this.mGlyphPaint.setColor(glyphColor);

        attributes.recycle();
    }

    private void setTypeface(final String path) {
        if (path == null || this.isInEditMode()) {
            return;
        }
        final Typeface typeface = Typefaces.fromAsset(this.getContext(), path);
        this.setTypeface(typeface);
    }

    private Rect measureText(final float width, final float height) {
        if (TextUtils.isEmpty(this.mGlyph)) {
            return this.mGlyphBounds;
        }
        this.mGlyphPaint.getTextBounds(this.mGlyph, 0, this.mGlyph.length(), this.mGlyphBounds);

        final float heightFactor = height / this.mGlyphBounds.height();
        final float widthFactor = width / this.mGlyphBounds.width();
        final float factor = Math.min(heightFactor, widthFactor);

        this.scaleTextSize(factor);

        this.mGlyphPaint.getTextBounds(this.mGlyph, 0, this.mGlyph.length(), this.mGlyphBounds);

        return this.mGlyphBounds;
    }

    private float scaleTextSize(final float factor) {
        final float size = this.mGlyphPaint.getTextSize();
        this.mGlyphPaint.setTextSize(size * factor);
        return size;
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = DEFAULT_WIDTH;
        int height = DEFAULT_HEIGHT;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = (heightMode == MeasureSpec.AT_MOST)
                    ? Math.min(widthSize, DEFAULT_WIDTH)
                    : widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = (widthMode == MeasureSpec.AT_MOST)
                    ? Math.min(heightSize, DEFAULT_HEIGHT)
                    : heightSize;
        }

        final Rect rect = this.measureText(width, height);

        if (widthMode != MeasureSpec.EXACTLY) {
            width = rect.width();
        }
        if (heightMode != MeasureSpec.EXACTLY) {
            height = rect.height();
        }

        final float baseline = rect.bottom + ((height - rect.height()) / 2);

        this.mGlyphX = width / 2f;
        this.mGlyphY = height - baseline;

        this.setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        final Drawable background = this.getBackground();

        if (background != null) {
            background.draw(canvas);
        }

        if (!TextUtils.isEmpty(this.mGlyph)) {
            canvas.drawText(this.mGlyph, this.mGlyphX, this.mGlyphY, this.mGlyphPaint);
        }
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

    public Typeface getTypeface() {
        return this.mGlyphPaint.getTypeface();
    }

    public void setTypeface(final Typeface typeface) {
        this.mGlyphPaint.setTypeface(typeface);
        this.invalidate();
    }

    public int getGlyphColor() {
        return this.mGlyphPaint.getColor();
    }

    public void setGlyphColor(final int color) {
        this.mGlyphPaint.setColor(color);
        this.invalidate();
    }

    public String getGlyph() {
        return this.mGlyph;
    }

    public void setGlyph(final String glyph) {
        if (!TextUtils.equals(glyph, this.mGlyph)) {
            this.mGlyph = glyph;
            this.invalidate();
        }
    }
}
