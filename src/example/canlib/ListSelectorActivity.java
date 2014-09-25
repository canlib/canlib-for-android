package example.canlib;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

/**
 * 図書一覧のフラグメントを表示する
 * @author yoshida
 *
 */
public class ListSelectorActivity extends Activity
implements OnFinishGetDataListener {

    private ProgressDialog mProgressDialog;
    private MainTabListener<UserBooksListFragment> mUserBooksContent;
    private MainTabListener<OtherBooksListFragment> mOtherBooksContent;
    private Tab mUserBooksTab;
    private Tab mOtherBooksTab;
    private String mSelectedFragment = "example.canlib.UserBooksListFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_list_selector);

        updateView();
    }

    /**
     * プログレスダイアログを表示して、通信する
     */
     private void updateView() {
         // プログレスダイアログ作成
         mProgressDialog = new ProgressDialog(this);
         mProgressDialog.setMessage(getString(R.string.OK_data));
         mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
         // ダイアログ外タップ、backキーで消えないようにする
         mProgressDialog.setCancelable(false);
         mProgressDialog.show();

         // 通信してデータを取得する
         new Server().getData(this);
     }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         // メニューの要素を追加して取得
         MenuItem actionItem = menu.add("Action Button");

         // SHOW_AS_ACTION_IF_ROOM:常に表示
         actionItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

         // アイコンを設定
         actionItem.setIcon(R.drawable.ic_action_refresh);

         return true;
     }

     // 更新ボタンが押されたときの処理
     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         //すべてのタブを削除
         //mActionBar.removeAllTabs();
         updateView();

         return true;
     }

     // OnFinishGetDataListenerの実装
     @Override
     public void onFinishGetData(boolean result) {
         mProgressDialog.dismiss();
         if (result) {
             Log.d("リスナー", "通信成功");

             // tab表示
             if (mUserBooksContent == null) {
                 // tabが作られていなかったらtabから作る
                 createTab();
             } else {
                 // tabが作られていたらFragmentだけ差し替える
                 mUserBooksContent = new MainTabListener<UserBooksListFragment>(
                         this, "f1", UserBooksListFragment.class);
                 mUserBooksTab.setTabListener(mUserBooksContent);

                 mOtherBooksContent = new MainTabListener<OtherBooksListFragment>(
                         this, "f2", OtherBooksListFragment.class);
                 mOtherBooksTab.setTabListener(mOtherBooksContent);

                 // 選択されているtabのFragmentを表示する
                 if (mSelectedFragment.equals("example.canlib.UserBooksListFragment")) {
                     mUserBooksContent.detach();
                     mUserBooksContent.attach();
                 } else if (mSelectedFragment.equals("example.canlib.OtherBooksListFragment")) {
                     mOtherBooksContent.detach();
                     mOtherBooksContent.attach();
                 }
             }

         } else {
             // エラー表示
             AlertDialog.Builder alert = new AlertDialog.Builder(this);
             alert.setMessage(R.string.NG_data);
             alert.setPositiveButton(R.string.OK, null);
             alert.show();

             // tab表示
             if (mUserBooksContent == null) {
                 createTab();
             }

             Log.d("リスナー", "通信に失敗しました");
         }
     }

     /**
      * ActionBarでタブを作成する
      */
     private void createTab() {

         // タブモードのActionBarを作る
         ActionBar mActionBar = getActionBar();
         mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

         // tab1の作成
         mUserBooksContent = new MainTabListener<UserBooksListFragment>(
                 this, "f1", UserBooksListFragment.class);
         // 新しいタブを作成
         mUserBooksTab = mActionBar.newTab();
         // タブに表示するテキスト
         mUserBooksTab.setText(R.string.user_books);
         // 引数：TabListenerが実装されたクラス
         mUserBooksTab.setTabListener(mUserBooksContent);
         mActionBar.addTab(mUserBooksTab);

         // tab2の作成
         mOtherBooksContent = new MainTabListener<OtherBooksListFragment>(
                 this, "f2", OtherBooksListFragment.class);
         mOtherBooksTab = mActionBar.newTab();
         mOtherBooksTab.setText(R.string.other_books);
         mOtherBooksTab.setTabListener(mOtherBooksContent);
         mActionBar.addTab(mOtherBooksTab);
     }

     /**
      * タブリスナーでフラグメントをタブ表示する
      * @author yoshida
      *
      * @param <T>
      */
     private final class MainTabListener<T extends Fragment>
     implements TabListener {

         private Fragment mFragment;
         private final Activity mActivity;
         private final String mTag;
         private final Class<T> mCls;

         /**
          * コンストラクタ
          * @param activity
          * @param tag
          * @param cls
          */
         private MainTabListener(Activity activity, String tag, Class<T> cls) {
             mActivity = activity;
             mTag = tag;
             mCls = cls;
             //FragmentManagerからFragmentを探す。
             mFragment = mActivity.getFragmentManager().findFragmentByTag(mTag);
             Log.d("タブリスナー", "OK");
         }

         // 同じタブが再度選択されたときに呼ばれる
         @Override
         public void onTabReselected(Tab tab, FragmentTransaction ft) {
         }

         // タブが選択されたときに呼ばれる
         @Override
         public void onTabSelected(Tab tab, FragmentTransaction ft) {
             mSelectedFragment = mCls.getName();
             if (mFragment == null) {
                 mFragment = Fragment.instantiate(mActivity, mCls.getName());
                 FragmentManager manager = mActivity.getFragmentManager();
                 manager.beginTransaction().add(R.id.container, mFragment, mTag).commit();

                 Log.d("名前", mSelectedFragment);

                 Log.d("タブ選択", "OK");
             } else {
                 // detachされていないときだけattachする
                 if (mFragment.isDetached()) {
                     FragmentManager manager = mActivity.getFragmentManager();
                     manager.beginTransaction().attach(mFragment).commit();
                 }
             }
         }

         // タブが非選択になったときに呼ばれる
         @Override
         public void onTabUnselected(Tab tab, FragmentTransaction ft) {
             if (mFragment != null) {
                 FragmentManager manager = mActivity.getFragmentManager();
                 manager.beginTransaction().detach(mFragment).commit();
             }
         }

         public void detach() {
             FragmentManager manager = mActivity.getFragmentManager();
             manager.beginTransaction().detach(mFragment).commit();
         }

         public void attach() {
             FragmentManager manager = mActivity.getFragmentManager();
             manager.beginTransaction().attach(mFragment).commit();
         }
     }
}

