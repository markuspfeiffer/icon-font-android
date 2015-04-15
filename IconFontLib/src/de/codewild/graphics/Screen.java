
package de.codewild.graphics;

import android.content.res.Resources;
import android.util.DisplayMetrics;


/**
 * Provides constants and static methods to perform unit conversions based on a device's display
 * metrics.
 */
public final class Screen {

    /**
     * Converts the specified dimension, in density-independent pixel (dip), to pixel (px).
     * <p/>
     * A density-independent pixel is equivalent to one physical pixel on a 160 dpi screen, which
     * is the baseline density assumed by the system for a "medium" density screen. On systems with
     * a higher density a density-independent pixel is equivalent to multiple physical pixels and
     * on system with a lower density multiple density-independent pixels are equivalent to one
     * physical pixel.
     * @param dip The dimension to convert, in dip.
     * @return The specified dimension, converted to pixels.
     * @see DisplayMetrics#density
     * @see #px2dip(int)
     * @see #sp2px(float)
     */
    public static int dip2px(final float dip) {
        final float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dip * density);
    }

    /**
     * Converts the specified dimension, in pixel (px), to density-independent pixel (dip).
     * <p/>
     * A density-independent pixel is equivalent to one physical pixel on a 160 dpi screen, which
     * is the baseline density assumed by the system for a "medium" density screen. On systems with
     * a higher density a density-independent pixel is equivalent to multiple physical pixels and
     * on system with a lower density multiple density-independent pixels are equivalent to one
     * physical pixel.
     * @param px The dimension to convert, in pixel.
     * @return The specified dimension, converted to density-independent pixel.
     * @see DisplayMetrics#density
     * @see #dip2px(float)
     * @see #px2sp(int)
     */
    public static float px2dip(final int px) {
        final float density = Resources.getSystem().getDisplayMetrics().density;
        return px / density;
    }

    /**
     * Converts the specified dimension, in scaled density-independent pixel (sp), to pixel (px).
     * <p/>
     * Scaled density-independent pixel is a size for fonts displayed on the screen. This is the
     * same as density-independent pixel (dip), except that it may be adjusted in smaller
     * increments at runtime based on a user preference for the font size.
     * @param sp The dimension to convert, in sp.
     * @return The specified dimension, converted to pixels.
     * @see DisplayMetrics#scaledDensity
     * @see #px2sp(int)
     * @see #dip2px(float)
     */
    public static int sp2px(final float sp) {
        final float density = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (sp * density);
    }

    /**
     * Converts the specified dimension, in pixel (px), to scaled density-independent pixel (sp).
     * <p/>
     * Scaled density-independent pixel is a size for fonts displayed on the screen. This is the
     * same as density-independent pixel (dip), except that it may be adjusted in smaller
     * increments at runtime based on a user preference for the font size.
     * @param px The dimension to convert, in pixel.
     * @return The specified dimension, converted to scaled density-independent pixel.
     * @see #sp2px(float)
     * @see #px2dip(int)
     */
    public static float px2sp(final int px) {
        final float density = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return px / density;
    }

    /**
     * Gets the absolute width of the display, in pixel.
     */
    public static int getWidth() {
        final DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.widthPixels;
    }

    /**
     * Gets the absolute height of the display, in pixel.
     */
    public static int getHeight() {
        final DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        return metrics.heightPixels;
    }

    /**
     * Gets the absolute width of the display, density-independent pixel (dip).
     */
    public static float getWidthDp() {
        final int width = getWidth();
        return px2dip(width);
    }

    /**
     * Gets the absolute height of the display, density-independent pixel (dip).
     */
    public static float getHeightDp() {
        final int height = getHeight();
        return px2dip(height);
    }

    /**
     * Gets the density of the screen, expressed in dots per inch (DPI).
     * @return The screen's density in DPI.
     */
    public static double getDensityDpi() {
        final double density = Resources.getSystem().getDisplayMetrics().density;
        return density * DisplayMetrics.DENSITY_DEFAULT; // DPI
    }
}
