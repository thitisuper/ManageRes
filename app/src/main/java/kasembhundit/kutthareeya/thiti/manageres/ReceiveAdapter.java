package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by User on 9/2/2017.
 */

public class ReceiveAdapter extends BaseAdapter {

    private Context context;
    private String[] titleStrings, amountStrings, unitPriceStrings;
    private TextView titleTextView, amountTextView, unitPriceTextView, totalTextView;

    public ReceiveAdapter(Context context,
                          String[] titleStrings,
                          String[] amountStrings,
                          String[] unitPriceStrings) {
        this.context = context;
        this.titleStrings = titleStrings;
        this.amountStrings = amountStrings;
        this.unitPriceStrings = unitPriceStrings;
    }

    @Override
    public int getCount() {
        return titleStrings.length;
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
        View view = layoutInflater.inflate(R.layout.receive_layout, parent, false);

        titleTextView = (TextView) view.findViewById(R.id.txtName);
        amountTextView = (TextView) view.findViewById(R.id.txtAmount);
        unitPriceTextView = (TextView) view.findViewById(R.id.txtUnitPrice);
        totalTextView = (TextView) view.findViewById(R.id.txtTotal);

        titleTextView.setText(titleStrings[position]);
        amountTextView.setText(amountStrings[position]);
        unitPriceTextView.setText(unitPriceStrings[position]);
        totalTextView.setText(Integer.toString((Integer.parseInt(amountStrings[position]))*(Integer.parseInt(unitPriceStrings[position]))));

        return view;
    }
}   // Main Class
