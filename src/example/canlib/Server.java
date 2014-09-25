package example.canlib;

import com.loopj.android.http.AsyncHttpClient;

/**
 * サーバーからjson形式のデータを取得し、Stringかintに変換する
 * @author yoshida
 *
 */
public class Server {

    /**
     * データ取得を始める
     * @param listener 通信終了を知らせるリスナー
     */
    public void getData(OnFinishGetDataListener listener) {

        // サーバーのURLを取得する
        String url = new GetUrl().getUrl();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new MyAsyncHttpResponseHandler(listener));
    }
}
