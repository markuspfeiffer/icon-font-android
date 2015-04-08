
package de.codewild.graphics;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;


/**
 * A cache that holds strong references to a limited number of {@link Typeface} instances. Each
 * time a value is accessed, it is moved to the head of a queue. When a value is added to a full
 * cache, the value at the end of that queue is evicted and may become eligible for garbage
 * collection.
 * <p>
 * If a cache miss occurs, {@link AssetManager} is used to create a new instance of
 * {@link Typeface} for the specified {@code key}. The key is expected to be the file name of the
 * font data in the assets directory.
 */
public class TypefaceCache extends LruCache<String, Typeface> {

    private final AssetManager mAssetManager;

    /**
     * Creates a new instance of the {@link TypefaceCache} class.
     * @param assetManager The {@link AssetManager} used to create {@link Typeface} instances.
     * @param maxSize The maximum number of {@link Typeface} instances to cache.
     */
    public TypefaceCache(final AssetManager assetManager, final int maxSize) {
        super(maxSize);
        this.mAssetManager = assetManager;
    }

    /**
     * Creates a new {@link Typeface} instance for the specified key.
     * @param key The file name of the font data in the assets directory
     * @return The new typeface.
     */
    @Override
    protected Typeface create(final String key) {
        return Typeface.createFromAsset(this.mAssetManager, key);
    }
}
