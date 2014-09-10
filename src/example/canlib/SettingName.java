package example.canlib;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreferenesを使って、名前の保存と読み込みをする
 * @author yoshida
 *
 */
public class SettingName {

    private SharedPreferences mSharedPreferences;

    /**
     * コンストラクタ
     * @param context
     */
    public SettingName() {
        Context context = MyApplication.getAppContext();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * 名前を保存する
     * @param userName 保存したい名前
     */
    public void setName(String userName) {
        // 保存
        mSharedPreferences.edit().putString("SaveString", userName).commit();
    }

    /**
     * 名前を読み込んで、Stringで返す
     * @return mUserName 保存してあった名前
     */
    public String getName() {
        // 読み込み
        return mSharedPreferences.getString("SaveString", null);
    }
}
