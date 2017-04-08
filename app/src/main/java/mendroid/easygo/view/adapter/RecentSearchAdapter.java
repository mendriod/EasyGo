package mendroid.easygo.view.adapter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mendroid.easygo.BaseActivity;
import mendroid.easygo.R;
import mendroid.easygo.model.SearchEntity;
import mendroid.easygo.utils.FuncUtils;

/**
 * Created by avengat on 3/7/2017.
 */

public class RecentSearchAdapter extends BaseAdapter {
    BaseActivity baseActivity;
    SearchEntity[] searchEntities;

    public RecentSearchAdapter(BaseActivity baseActivity, SearchEntity[] searchEntities) {
        this.baseActivity = baseActivity;
        this.searchEntities = searchEntities;
    }

    @Override
    public int getCount() {
        return searchEntities.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        RelativeLayout relativeLayout;
        TextView txtFromTo, txtDateTime;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(baseActivity).inflate(R.layout.recent_search_item, null);
            holder = new ViewHolder();
            holder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.relativeLayout_recentCustom);
            holder.txtFromTo = (TextView) convertView.findViewById(R.id.txt_recentCustom_FromTo);
            holder.txtDateTime = (TextView) convertView.findViewById(R.id.txt_recentCustom_DateTime);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String fromTo = baseActivity.getString(R.string.strRecent_FromTo);
        String dateTime = baseActivity.getString(R.string.strRecent_DateTime);
        if (searchEntities[position].getTxt_origin().length() > 0) {
            fromTo = searchEntities[position].getTxt_origin() + " - " + searchEntities[position].getTxt_dest();
        }
        if (searchEntities[position].getDownloaded_ts() > 0) {
            dateTime = FuncUtils.getDateStringFromMS(searchEntities[position].getDownloaded_ts(), "dd MMM yyyy HH:mm");
        }

        holder.txtFromTo.setText(fromTo);
        holder.txtDateTime.setText(dateTime);

        return convertView;
    }
}
