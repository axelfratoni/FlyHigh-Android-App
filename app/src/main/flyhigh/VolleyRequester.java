package com.app.hci.flyhigh;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by leo on 24/06/17.
 */

public class VolleyRequester {

    private static VolleyRequester instance;
    private static Context context;
    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private VolleyRequester(Context c) {

        context = c;

        requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue,

                new ImageLoader.ImageCache() {

                    private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {

                        return cache.get(url);

                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {

                        cache.put(url, bitmap);

                    }

                });

    }

    public static synchronized VolleyRequester getInstance(Context context) {

        if (instance == null) {

            instance = new VolleyRequester(context);

        }

        return instance;
    }

    public RequestQueue getRequestQueue() {

        if (requestQueue == null) {

            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {

        getRequestQueue().add(req);

    }

    public ImageLoader getImageLoader() {

        return imageLoader;

    }

}
