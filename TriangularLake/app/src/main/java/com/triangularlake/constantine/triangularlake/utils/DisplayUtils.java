package com.triangularlake.constantine.triangularlake.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class DisplayUtils {

    private int displayWidth;
    private int displayHeight;
    private boolean isUpdateDisplaySizes = false;

    // Singleton
    private static class DisplayUtilsHolder {
        public static final DisplayUtils HOLDER_INSTANCE = new DisplayUtils();
    }

    // Singleton
    public static DisplayUtils getInstance() {
        return DisplayUtilsHolder.HOLDER_INSTANCE;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void updateDisplaySizes(Context context) {
        if (!isUpdateDisplaySizes) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            displayWidth = size.x;
            displayHeight = size.y;
            isUpdateDisplaySizes = true;
        }
    }
}
