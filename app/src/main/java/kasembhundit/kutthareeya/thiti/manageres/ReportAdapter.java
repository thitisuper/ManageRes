package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by User on 10/12/2017.
 */

public class ReportAdapter extends BaseAdapter {

    private Context context;
    private TextView nameFoodTextView, dateFoodTextView, priceFoodTextView;
    private String[] nameFoodStrings, dateFoodStrings, priceFoodStrings;

    public ReportAdapter(Context context,
                         String[] nameFoodStrings,
                         String[] dateFoodStrings,
                         String[] priceFoodStrings) {
        this.context = context;
        this.nameFoodStrings = nameFoodStrings;
        this.dateFoodStrings = dateFoodStrings;
        this.priceFoodStrings = priceFoodStrings;
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
        View view = layoutInflater.inflate(R.layout.report_listview_layout, parent, false);

        //Initial View
        nameFoodTextView = (TextView) view.findViewById(R.id.txtNameFood);
        dateFoodTextView = (TextView) view.findViewById(R.id.txtDate);
        priceFoodTextView = (TextView) view.findViewById(R.id.txtPrice);

        //show Texts
        nameFoodTextView.setText(nameFoodStrings[position]);
        dateFoodTextView.setText("วันที่: " + dateFoodStrings[position]);
        priceFoodTextView.setText("ราคา " + priceFoodStrings[position] + " บาท");

        return view;
    }
}
