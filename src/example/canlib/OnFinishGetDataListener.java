package example.canlib;

/**
 * データ通信の終了を知らせるリスナー
 * @author yoshida
 *
 */
public interface OnFinishGetDataListener {

    /**
     * インターフェース
     * @param result 正常終了:true
     */
    void onFinishGetData(boolean result);
}
