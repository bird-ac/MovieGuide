package com.esoxjem.movieguide.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Debug;

public class DebugDetector {
    public boolean isDebuggable(Context context){
        return ((context.getApplicationContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0);
    }

    public boolean detectDebugger() {
        return Debug.isDebuggerConnected();
    }
}
