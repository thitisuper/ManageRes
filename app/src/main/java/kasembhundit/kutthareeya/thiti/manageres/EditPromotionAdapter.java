package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by User on 9/23/2017.
 */

public class EditPromotionAdapter extends BaseAdapter{

    private Context context;
    private ImageView iconImageView;
    private TextView nameFoodTextView;
    private EditText promotionEditText;
    private String[] nameFoodStrings, iconStrings, promotionStrings;

    public EditPromotionAdapter(Context context,
                                String[] nameFoodStrings,
                                String[] iconStrings,
                                String[] promotionStrings) {
        this.context = context;
        this.nameFoodStrings = nameFoodStrings;
        this.iconStrings = iconStrings;
        this.promotionStrings = promotionStrings;
    }

    @Override
    public int getCount() {
        return nameFoodStrings.length;
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
        View view = layoutInflater.inflate(R.layout.edit_promotion_listview_layout, parent, false);

        //Initial View
        iconImageView = (ImageView) view.findViewById(R.id.imvIcon);
        nameFoodTextView = (TextView) view.findViewById(R.id.txtTitle);
        promotionEditText = (EditText) view.findViewById(R.id.edtPromotion);

        //Show Image
        Picasso.with(context).load(iconStrings[position]).into(iconImageView);

        //Show View
        nameFoodTextView.setText(nameFoodStrings[position]);
        promotionEditText.setText(promotionStrings[position]);

        return view;
    }
}
