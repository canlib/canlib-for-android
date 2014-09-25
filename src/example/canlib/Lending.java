package example.canlib;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 貸出を管理するクラス
 * 貸出していなければ存在しない
 * @author yoshida
 *
 */
public class Lending {

    private Date mDate;
    private int mPeriod;
    private String mUserName;

    /**
     * コンストラクタ
     * @param date 借りた日
     * @param period 貸出可能日数
     * @param userName 借りた人の名前
     */
    public Lending(String date, int period, String userName) {

        // date型に変換
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            mDate = format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        mPeriod = period;
        mUserName = userName;

        // 【確認用】
        Log.d("Lendingクラス", "OK");
    }

    /**
     * 借りている人の名前を返す
     * @return 借りている人の名前
     */
    public String getUserName() {
        return mUserName;
    }

    /**
     * 返却日を"xxxx年xx月xx日"の文字列にする
     * @return 返却日
     */
    public String getPeriodDate() {
        Context context = MyApplication.getAppContext();
        Calendar cal = Calendar.getInstance();
        cal.setTime(mDate);
        // 貸出日に貸出期間を足す
        cal.add(Calendar.DATE, mPeriod);
        // 年月日を取り出して、文字列に直す
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DATE);
        String periodDate = year + context.getString(R.string.year) + month
                + context.getString(R.string.month) + day + context.getString(R.string.day);
        return periodDate;
    }
}

