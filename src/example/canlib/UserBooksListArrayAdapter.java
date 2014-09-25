package example.canlib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 借りている本を表示するためのアダプター
 * @author yoshida
 *
 */
public class UserBooksListArrayAdapter extends ArrayAdapter<Book> {

    private int mTextViewResourceId;
    private List<Book> mItems;
    private LayoutInflater mInflater;

    /**
     * コンストラクタ
     * @param appContext アプリケーションのcontent
     * @param textViewResourceId リストの内容を表示するレイアウト
     * @param items Book
     */
    public UserBooksListArrayAdapter(Context appContext, int textViewResourceId, List<Book> items) {
        super(appContext, textViewResourceId, items);

        Context context = appContext;
        mTextViewResourceId = textViewResourceId;
        mItems = items;

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewHolder {
        private TextView mTitleView;
        private TextView mAuthorNameView;
        private TextView mDate;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(mTextViewResourceId, null);

            TextView titleView = (TextView) convertView.findViewById(R.id.title);
            TextView authorNameView = (TextView) convertView.findViewById(R.id.author_name);
            TextView date = (TextView) convertView.findViewById(R.id.date);

            holder = new ViewHolder();
            holder.mTitleView = titleView;
            holder.mAuthorNameView = authorNameView;
            holder.mDate = date;

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Book item = mItems.get(position);

        holder.mTitleView.setText(item.getTitle());
        holder.mAuthorNameView.setText(item.getAuthorName());
        holder.mDate.setText(item.getDate());

     // Itemの背景色を交互に変える
        if (position % 2 == 0) {
            convertView.setBackgroundResource(R.drawable.listitem_color1);
        } else {
            convertView.setBackgroundResource(R.drawable.listitem_color2);
        }

        return convertView;
    }
}
