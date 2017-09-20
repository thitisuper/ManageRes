package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by User on 9/20/2017.
 */

public class StatusUserAdapter extends BaseAdapter {

    private Context context;
    private String[] id_ref, foodNameStrings, priceStrings,
            specialStrings, toppingStrings, itemStrings;
    private TextView refTextView, foodNameTextView, priceTextView,
            specialTextView, toppingTextView, itemTextView;

    public StatusUserAdapter(Context context,
                             String[] id_ref,
                             String[] foodNameStrings,
                             String[] priceStrings,
                             String[] specialStrings,
                             String[] toppingStrings,
                             String[] itemStrings) {
        this.context = context;
        this.id_ref = id_ref;
        this.foodNameStrings = foodNameStrings;
        this.priceStrings = priceStrings;
        this.specialStrings = specialStrings;
        this.toppingStrings = toppingStrings;
        this.itemStrings = itemStrings;
    }

    @Override
    public int getCount() {
        return id_ref.length;
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
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.order_listview_user, parent, false);

        //Initial View
        refTextView = (TextView) view.findViewById(R.id.txtRef);
        foodNameTextView = (TextView) view.findViewById(R.id.txtNameFood);
        priceTextView = (TextView) view.findViewById(R.id.txtPrice);
        specialTextView = (TextView) view.findViewById(R.id.txtSpecial);
        toppingTextView = (TextView) view.findViewById(R.id.txtTopping);
        itemTextView = (TextView) view.findViewById(R.id.txtItem);

        //Show Text
        refTextView.setText("เลขที่ใบสั่งซื้อ: " + id_ref[position]);
        foodNameTextView.setText(foodNameStrings[position]);
        priceTextView.setText("ราคา " + priceStrings[position] + " บาท");
        specialTextView.setText(specialStrings[position]);
        toppingTextView.setText(toppingStrings[position]);
        itemTextView.setText(itemStrings[position] + " จาน");

        return view;
    }
}   //Main Class
