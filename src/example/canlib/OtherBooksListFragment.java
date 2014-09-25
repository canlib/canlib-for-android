package example.canlib;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * 借りていない本を表示するフラグメント
 * @author yoshida
 *
 */
public class OtherBooksListFragment extends Fragment {

    private ListView mOtherBooksListView;

    /**
     * 空のコンストラクタ
     */
    public OtherBooksListFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.other_books_list, container, false);
        setAdapter(view);
        itemClickListener();
        Log.d("onCreateView", "OK");

        return view;
    }

    /**
     * アダプターに自分が借りていない本のリストをセットする
     */
    private void setAdapter(View view) {
        // 自分が借りていない本を取得する
        ArrayList<Book> otherBooksArrayList = new ArrayList<Book>();
        AllBooksSingleton allBooksSingleton = AllBooksSingleton.getInstance();
        otherBooksArrayList = allBooksSingleton.getOtherBook(new SettingName().getName());

        // アダプターに借りていない本のリストをセットする
        Context context = MyApplication.getAppContext();
        OtherBooksListArrayAdapter adapter = new OtherBooksListArrayAdapter(
                context, R.layout.other_book, otherBooksArrayList);
        mOtherBooksListView = (ListView) view.findViewById(android.R.id.list);
        mOtherBooksListView.setAdapter(adapter);
    }

    private AlertDialog mAlertDialog;
    /**
     * アイテムをタップしたときの処理
     */
    private void itemClickListener() {
        // アイテムクリック時のイベントを追加
        mOtherBooksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ダイアログに表示させる情報を取得する
                ListView listView = (ListView) parent;
                Book book = (Book) listView.getItemAtPosition(position);
                String title = book.getTitle();
                String otherBookData = book.getOtherBookData();

                // 2回以上タップされたときは、1回目以降ダイアログを表示しない
                if (mAlertDialog != null && mAlertDialog.isShowing()) {
                    return;
                }
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle(title);
                alert.setMessage(otherBookData);
                alert.setPositiveButton(R.string.OK, null);
                mAlertDialog = alert.create();
                mAlertDialog.show();
                Log.d("詳細ダイアログ表示", "表示");
            }
        });
    }
}
