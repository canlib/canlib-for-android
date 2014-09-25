package example.canlib;

import android.util.Log;

import java.util.ArrayList;


/**
 * 図書館が貯蔵する全ての本を管理するシングルトン
 * @author yoshida
 *
 */
public final class AllBooksSingleton {

    private static AllBooksSingleton sInstance = new AllBooksSingleton();
    private ArrayList<Book> mAllBooks = new ArrayList<Book>();

    private AllBooksSingleton() { }

    /**
     * インスタンスを返す
     * @return instance AllBooksSingletonのインスタンス
     */
    public static AllBooksSingleton getInstance() {
        return sInstance;
    }

    /**
     * Bookを配列にする
     * @param book 本1冊のデータ
     */
    public void addBook(Book book) {
        mAllBooks.add(book);

        // 【確認用】
        Log.d("シングルトン", "OK");
    }

    /**
     * 本のリストを空にする
     */
    public void clearBooks() {
        mAllBooks.clear();
    }

    /**
     * 借りている本のリストを作る
     * @param userName 保存されているuserの名前
     * @return mUserBooks 借りているbookのリスト
     */
    public ArrayList<Book> getUserBook(String userName) {
        ArrayList<Book> mUserBooks = new ArrayList<Book>();

        for (int i = 0; i < mAllBooks.size(); i++) {
            Book book = mAllBooks.get(i);
            if ((book.getLending()) != null) {
                if (userName.equals(book.getUserName())) {
                    mUserBooks.add(book);
                }
            }
        }
        return mUserBooks;
    }

    /**
     * 借りていない本のリストを作る
     * @param userName 保存されているuserの名前
     * @return 借りていないbookのリスト
     */
    public ArrayList<Book> getOtherBook(String userName) {
        ArrayList<Book> mOtherBooks = new ArrayList<Book>();

        for (int i = 0; i < mAllBooks.size(); i++) {
            Book book = mAllBooks.get(i);
            if ((book.getLending()) != null) {
                if (!userName.equals(book.getUserName())) {
                    mOtherBooks.add(book);
                }
            } else {
                mOtherBooks.add(book);
            }
        }
        return mOtherBooks;
    }
}
