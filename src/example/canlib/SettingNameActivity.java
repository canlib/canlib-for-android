package example.canlib;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 起動時に表示される画面
 * 名前の入力を行う
 * @author yoshida
 *
 */
public class SettingNameActivity extends Activity
implements OnClickListener {

    private Button mOkButton;
    private EditText mNameEditText;
    private SettingName mSettingName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_name);

        mOkButton = (Button) findViewById(R.id.OkButton);
        mOkButton.setOnClickListener(this);

        mNameEditText = (EditText) findViewById(R.id.editText1);

        mSettingName = new SettingName();

        // 保存していた名前をEditTextに表示する
        mNameEditText.setText(mSettingName.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting_name, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        // EditTextに入力された名前を保存する
        String userName = mNameEditText.getText().toString();
        mSettingName.setName(userName);
    }
}
