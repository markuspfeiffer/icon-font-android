
package de.codewild.graphics;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;


/**
 * A utility class that simplifies access to {@link Typeface} instances created from files in the
 * assets directory. For improved performance {@link Typeface} instances created by this class are
 * cached using an instance of the {@link TypefaceCache} class. The cache is initialized to hold the
 * {@code 2} last recently used typeface instances.
 */
public class Typefaces {

    private static final int    CACHE_SIZE = 2;

    private static Typefaces    sInstance;

    private final TypefaceCache mCache;

    /**
     * Returns the single instance of the {@link Typefaces} class.
     * @param context The {@link Context} to use.
     * @return The single instance of the {@link Typefaces} class.
     */
    public static Typefaces getInstance(final Context context) {
        if (sInstance == null) {
            sInstance = new Typefaces(context);
        }
        return sInstance;
    }

    /**
     * Returns a typeface from the specified font data.
     * <p>
     * If a typeface instance for the specified path is in the cache the cached instance is
     * returned; otherwise a new instance is created and placed in the cache. If the cache's size
     * exceeds {@link #getMaxCacheSize()} after this operation, the least recently used item is
     * evicted from the cache.
     * @param context The {@link Context} to use.
     * @param path The file name of the font data in the assets directory.
     * @return The {@link Typeface} for the specified path.
     * @see #fromAsset(String)
     * @see #getMaxCacheSize()
     */
    public static Typeface fromAsset(final Context context, final String path) {
        final Typefaces typefaces = getInstance(context);
        return typefaces.fromAsset(path);
    }

    /**
     * Initializes a new instance of the {@link Typefaces} class.
     * @param context The {@link Context} to use.
     */
    private Typefaces(final Context context) {
        final Context applicationContext = context.getApplicationContext();
        final AssetManager assetManager = applicationContext.getAssets();
        this.mCache = new TypefaceCache(assetManager, CACHE_SIZE);
    }

    /**
     * Returns a typeface from the specified font data.
     * <p>
     * If a typeface instance for the specified path is in the cache the cached instance is
     * returned; otherwise a new instance is created and placed in the cache. If the cache's size
     * exceeds {@link #getMaxCacheSize()} after this operation, the least recently used item is
     * evicted from the cache.
     * @param path The file name of the font data in the assets directory.
     * @return The {@link Typeface} for the specified path.
     * @see #fromAsset(Context, String)
     * @see #getMaxCacheSize()
     */
    public Typeface fromAsset(final String path) {
        return this.mCache.get(path);
    }

    /**
     * Returns the actual number of items in the cache.
     * @return The actual number of items in the cache.
     * @see #getMaxCacheSize()
     */
    public int getCacheSize() {
        return this.mCache.size();
    }

    /**
     * Returns the maximum number of items in the cache.
     * @return The maximum number of items in the cache.
     * @see #setMaxCacheSize(int)
     * @see #getCacheSize()
     */
    public int getMaxCacheSize() {
        return this.mCache.maxSize();
    }

    /**
     * Resizes the cache to hold at most <i>maxSize</i> items.
     * <p>
     * If the current size of the cache is larger than <i>maxSize</i>, the cache continues to
     * evict the least recently used item until the cache's size is equal to <i>maxSize</i>.
     * @param maxSize The maximum size of the cache to set.
     * @see #getMaxCacheSize()
     * @see #getCacheSize()
     */
    public void setMaxCacheSize(final int maxSize) {
        if (maxSize != this.mCache.maxSize()) {
            this.mCache.resize(maxSize);
        }
    }
}
