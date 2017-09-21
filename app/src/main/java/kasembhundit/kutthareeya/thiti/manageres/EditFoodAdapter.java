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
 * Created by User on 9/21/2017.
 */

public class EditFoodAdapter extends BaseAdapter{

    private Context context;
    private String[] iconStrings, nameFoodStrings;
    private ImageView iconImageView;
    private TextView nameTextView;
    private String nameFoodString;

    public EditFoodAdapter(Context context,
                           String[] iconStrings,
                           String[] nameFoodStrings) {
        this.context = context;
        this.iconStrings = iconStrings;
        this.nameFoodStrings = nameFoodStrings;
    }

    @Override
    public int getCount() {
        return nameFoodString.length();
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
        View view = layoutInflater.inflate(R.layout.edit_food_listview_layout, parent, false);

        //Initial View
        nameTextView = (TextView) view.findViewById(R.id.txtTitle);
        iconImageView = (ImageView) view.findViewById(R.id.imvIcon);

        //show Texts
        nameTextView.setText(nameFoodStrings[position]);

        //Show Image
        Picasso.with(context).load(iconStrings[position]).into(iconImageView);

        return view;
    }
}   //Main Class
