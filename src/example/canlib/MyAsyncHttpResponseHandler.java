package example.canlib;

import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * 非同期通信でデータを取得し、BookとLendingを作る
 * @author yoshida
 *
 */
public class MyAsyncHttpResponseHandler extends AsyncHttpResponseHandler {

    private static final String BOOK = "book";
    private static final String TITLE = "title";
    private static final String AUTHOR_NAME = "author_name";
    private static final String LENDING = "lending";

    private static final String DATE = "date";
    private static final String PERIOD = "period";
    private static final String USER_NAME = "user_name";

    private OnFinishGetDataListener mListener;

    /**
     * コンストラクタ
     * @param listener 通信終了を知らせるリスナー
     */
    public MyAsyncHttpResponseHandler(OnFinishGetDataListener listener) {
        mListener = listener;
    }

    // 通信が失敗したとき
    @Override
    public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
        Log.d("通信", "NG");
        mListener.onFinishGetData(false);
    }

    // 通信が成功したとき
    @Override
    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
        AllBooksSingleton allBoolsSingleton = AllBooksSingleton.getInstance();
        // 通信を複数回行ったときにデータが重複しないようにするため、シングルトンの本データを消す
        allBoolsSingleton.clearBooks();

        try {
            String jsonString = new String(arg2, "UTF-8");
            JSONArray jsonArray = new JSONArray(jsonString);

            // 取得した配列のオブジェクトを取り出す
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                JSONObject book = jsonObject.getJSONObject(BOOK);
                // bookオブジェクトの中身のデータ取得
                String title = book.getString(TITLE);
                String authorName = book.getString(AUTHOR_NAME);

                JSONObject lending = null;
                Lending theLending = null;
                if (!jsonObject.isNull(LENDING)) {
                    // lendingがあった場合は取得する
                    lending = jsonObject.getJSONObject(LENDING);
                    // lendingオブジェクトの中身のデータ取得
                    String date = lending.getString(DATE);
                    int period = lending.getInt(PERIOD);
                    String userName = lending.getString(USER_NAME);

                    // Lendingクラスに取得したデータをセットする
                    theLending = new Lending(date, period, userName);
                }
                // Booksクラスに取得したデータをセットする
                Book theBook = new Book(title, authorName, theLending);

                // AllBooksSingletonにある全書籍の配列に追加する
                allBoolsSingleton.addBook(theBook);
            }
            Log.d("通信", "OK");
            mListener.onFinishGetData(true);
        } catch (UnsupportedEncodingException e) {
            mListener.onFinishGetData(false);
            Log.d("通信", "OKNG");
            e.printStackTrace();
        } catch (JSONException e) {
            Log.d("通信", "OKNG");
            mListener.onFinishGetData(false);
            e.printStackTrace();
        }
    };
}
