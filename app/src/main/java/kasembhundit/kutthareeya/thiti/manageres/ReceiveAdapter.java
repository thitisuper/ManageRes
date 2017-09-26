package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
    private String[] titleStrings, amountStrings, unitPriceStrings,
            promotionStrings;
    private TextView titleTextView, amountTextView, unitPriceTextView,
            totalTextView, promotionTextView;

    public ReceiveAdapter(Context context,
                          String[] titleStrings,
                          String[] amountStrings,
                          String[] unitPriceStrings,
                          String[] promotionStrings) {
        this.context = context;
        this.titleStrings = titleStrings;
        this.amountStrings = amountStrings;
        this.unitPriceStrings = unitPriceStrings;
        this.promotionStrings = promotionStrings;
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
        promotionTextView = (TextView) view.findViewById(R.id.txtPromotion);

        titleTextView.setText(titleStrings[position]);
        amountTextView.setText(amountStrings[position]);
        unitPriceTextView.setText(unitPriceStrings[position]);
        float number = Integer.parseInt(promotionStrings[position]);
        number /= 100;
        float number2 = (Integer.parseInt(amountStrings[position])) * (Integer.parseInt(unitPriceStrings[position]));
        float number3 = number2*number;
        totalTextView.setText(Integer.toString((int) (number2 - number3)));
        if (promotionStrings[position].equals("0")) {
            promotionTextView.setText("");
        } else {
            promotionTextView.setText("(" + "ลด " + promotionStrings[position] + " %" + ")");
        }

        return view;
    }
}   // Main Class
