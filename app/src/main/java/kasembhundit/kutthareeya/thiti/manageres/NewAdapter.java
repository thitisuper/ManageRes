package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by User on 29/5/2560.
 */

public class NewAdapter extends BaseAdapter{

    private Context context;
    private String[] titleString, detailString, iconString;
    private TextView titleTextView, detailTextView;
    private ImageView imageView;
    private String shortDetailString;

    public NewAdapter(Context context,
                      String[] titleString,
                      String[] detailString,
                      String[] iconString) {
        this.context = context;
        this.titleString = titleString;
        this.detailString = detailString;
        this.iconString = iconString;
    }

    @Override
    public int getCount() {
        return titleString.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.new_listview, parent, false);

        //Initial View
        titleTextView = (TextView) view.findViewById(R.id.txtTitle);
        detailTextView = (TextView) view.findViewById(R.id.txtDetail);
        imageView = (ImageView) view.findViewById(R.id.imvIcon);

        //Show Text
        titleTextView.setText(titleString[position]);

        //Separate String
        if (detailString[position].length() >= 30) {
            shortDetailString = detailString[position].substring(0, 30) + "...";
        } else {
            shortDetailString = detailString[position];
        }

        detailTextView.setText(shortDetailString);

        //Show Image
        Picasso.with(context).load(iconString[position]).into(imageView);

        return view;
    }
}   // Main Class
