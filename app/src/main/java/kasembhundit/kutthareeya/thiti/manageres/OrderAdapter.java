package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by masterung on 7/14/2017 AD.
 */

public class OrderAdapter extends BaseAdapter {

    private Context context;
    private String[] nameFoodStrings, specialStrings,
            itemStrings, priceStrings, toppingStrings;
    private TextView nameFoodTextView, specialTextView,
            itemTextView, priceTextView, toppingTextView;

    public OrderAdapter(Context context,
                        String[] nameFoodStrings,
                        String[] specialStrings,
                        String[] itemStrings,
                        String[] priceStrings,
                        String[] toppingStrings) {
        this.context = context;
        this.nameFoodStrings = nameFoodStrings;
        this.specialStrings = specialStrings;
        this.itemStrings = itemStrings;
        this.priceStrings = priceStrings;
        this.toppingStrings = toppingStrings;
    }

    @Override
    public int getCount() {
        return nameFoodStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.order_listview_layout, viewGroup, false);

        //Initial View
        nameFoodTextView = (TextView) view1.findViewById(R.id.txtNameFood);
        specialTextView = (TextView) view1.findViewById(R.id.txtSpecial);
        itemTextView = (TextView) view1.findViewById(R.id.txtItem);
        priceTextView = (TextView) view1.findViewById(R.id.txtPrice);
        toppingTextView = (TextView) view1.findViewById(R.id.txtTopping);

        //Show Text
        nameFoodTextView.setText(nameFoodStrings[i]);
        specialTextView.setText(specialStrings[i]);
        itemTextView.setText(itemStrings[i]);
        priceTextView.setText(priceStrings[i]);
        toppingTextView.setText(toppingStrings[i]);


        return view1;
    }
}   // Main Class