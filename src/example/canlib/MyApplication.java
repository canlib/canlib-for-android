package example.canlib;

import android.app.Application;
import android.content.Context;

/**
 * アプリケーションのContextを取得して返す
 * @author yoshida
 *
 */
public class MyApplication extends Application {

    // アプリケーションのContext
    private static Context sAppContext;

    @Override
    public void onCreate() {
        sAppContext = getApplicationContext();
    }

    /**
     * アプリケーションのContextを取得
     * @return アプリケーションのContext
     */
    public static Context getAppContext() {
        return sAppContext;
    }
}

