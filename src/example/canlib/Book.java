package example.canlib;

import android.content.Context;
import android.util.Log;
/**
 * 本の情報を保持する
 * @author yoshida
 *
 */
public class Book {

    private String mTitle;
    private String mAuthorName;
    private Lending mLending;

    /**
     * コンストラクタ
     * 本1冊の情報をセットする
     * @param title 本のタイトル
     * @param authorName 著者
     * @param lending 貸出
     */
    public Book(String title, String authorName, Lending lending) {
        mTitle = title;
        mAuthorName = authorName;
        mLending = lending;

        // 【確認用】
        Log.d("Booksクラス", "OK");
    }

    /**
     * 借りている人の名前を返す
     * @return mLending.getUserName()
     */
    public String getUserName() {
        return mLending.getUserName();
    }

    /**
     * タイトルを返す
     * @return mTitle
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * 著者の名前を返す
     * @return mAuthor_name
     */
    public String getAuthorName() {
        return mAuthorName;
    }

    /**
     *  貸出を返す
     * @return mLending
     */
    public Lending getLending() {
        return mLending;
    }

    /**
     * Lendingにある返却日を返す
     * @return mLending.getPeriodDate()
     */
    public String getDate() {
        return mLending.getPeriodDate();
    }

    /**
     * 借りている本詳細情報を返す
     * @return otherBookData 著者名、返却期限
     */
    public String getUserBookData() {
    	Context context = MyApplication.getAppContext();
        String userBookData = context.getText(R.string.author_name) + "　　：" + mAuthorName + "\n"
                + context.getText(R.string.period_date) + ":" + getDate();
        return userBookData;
    }

    /**
     * 借りていない本詳細情報を返す
     * @return otherBookData 著者名、貸出状況、（貸出中：貸出期限、借りている人）
     */
    public String getOtherBookData() {
        String otherBookData;
        Context context = MyApplication.getAppContext();
        if (getLending() == null) {
            otherBookData =
                    context.getText(R.string.author_name) + "　　：" + mAuthorName
                    + "\n" + context.getText(R.string.is_lending) + "："
                    + context.getText(R.string.not_lending_book);
        } else {
            otherBookData =
                    context.getText(R.string.author_name) + "　　："  + mAuthorName
                    + "\n" + context.getText(R.string.is_lending) + "："
                    + context.getText(R.string.lending_book)
                    + "\n" + context.getText(R.string.period_date_other) + "：" + getDate()
                    + "\n" + context.getText(R.string.user_name) + "：" + getUserName();
        }
        return otherBookData;
    }
}
