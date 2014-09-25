package example.canlib;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 借りていない本を表示するためのアダプター
 * @author yoshida
 *
 */
public class OtherBooksListArrayAdapter extends ArrayAdapter<Book> {

    private int mTextViewResourceId;
    private List<Book> mItems;
    private LayoutInflater mInflater;

    /**
     * コンストラクタ
     * @param appContext アプリケーションのcontent
     * @param textViewResourceId 表示するTextView
     * @param items book
     */
    public OtherBooksListArrayAdapter(Context appContext,
            int textViewResourceId, List<Book> items) {
        super(appContext, textViewResourceId, items);

        Context context = appContext;
        mTextViewResourceId = textViewResourceId;
        mItems = items;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
        private TextView mTitleView;
        private TextView mAuthorNameView;
        private TextView mLendingView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(mTextViewResourceId, null);

            TextView titleView = (TextView) convertView.findViewById(R.id.title);
            TextView authorNameView = (TextView) convertView.findViewById(R.id.author_name);
            TextView lendingView = (TextView) convertView.findViewById(R.id.lending);

            holder = new ViewHolder();
            holder.mTitleView = titleView;
            holder.mAuthorNameView = authorNameView;
            holder.mLendingView = lendingView;

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Book item = mItems.get(position);

        holder.mTitleView.setText(item.getTitle());
        holder.mAuthorNameView.setText(item.getAuthorName());

        if (item.getLending() != null) {
            // "貸出中" 赤で表示
            holder.mLendingView.setText(R.string.lending_book);
            android.util.Log.d("文字色変更", "赤");
            holder.mLendingView.setTextColor(Color.RED);
        } else {
            // "保管中" 黒で表示
            holder.mLendingView.setText(R.string.not_lending_book);
            android.util.Log.d("文字色変更", "黒");
            holder.mLendingView.setTextColor(Color.BLACK);
        }

        // Itemの背景色を交互に変える
        if (position % 2 == 0) {
            convertView.setBackgroundResource(R.drawable.listitem_color1);
        } else {
            convertView.setBackgroundResource(R.drawable.listitem_color2);
        }

        return convertView;
    }

}
