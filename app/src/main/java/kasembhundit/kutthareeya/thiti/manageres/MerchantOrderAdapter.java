package kasembhundit.kutthareeya.thiti.manageres;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by User on 9/14/2017.
 */

public class MerchantOrderAdapter extends BaseAdapter{
    private Context context;
    private String[] id_ref, nameUserStrings, foodNameStrings,
            priceStrings, specialStrings, toppingStrings, itemStrings,
            timeStrings, deliveryStrings;
    private TextView refTextView, nameUserTextView, foodNameTextView,
            priceTextView, specialTextView, toppingTextView, itemTextView,
            timeTextView, deliveryTextView;

    public MerchantOrderAdapter(Context context,
                                String[] id_ref,
                                String[] nameUserStrings,
                                String[] foodNameStrings,
                                String[] priceStrings,
                                String[] specialStrings,
                                String[] toppingStrings,
                                String[] itemStrings,
                                String[] timeStrings,
                                String[] deliveryStrings) {
        this.context = context;
        this.id_ref = id_ref;
        this.nameUserStrings = nameUserStrings;
        this.foodNameStrings = foodNameStrings;
        this.priceStrings = priceStrings;
        this.specialStrings = specialStrings;
        this.toppingStrings = toppingStrings;
        this.itemStrings = itemStrings;
        this.timeStrings = timeStrings;
        this.deliveryStrings = deliveryStrings;
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
        View view = layoutInflater.inflate(R.layout.order_listviewmer_layout, parent, false);

        //Initial View
        refTextView = (TextView) view.findViewById(R.id.txtRef);
        nameUserTextView = (TextView) view.findViewById(R.id.txtNameSur);
        foodNameTextView = (TextView) view.findViewById(R.id.txtNameFood);
        priceTextView = (TextView) view.findViewById(R.id.txtPrice);
        specialTextView = (TextView) view.findViewById(R.id.txtSpecial);
        toppingTextView = (TextView) view.findViewById(R.id.txtTopping);
        itemTextView = (TextView) view.findViewById(R.id.txtItem);
        timeTextView = (TextView) view.findViewById(R.id.txtTime);
        deliveryTextView = (TextView) view.findViewById(R.id.txtDelivery);

        //Show Text
        refTextView.setText(id_ref[position]);
        nameUserTextView.setText("ชื่อผู้รับ : " + nameUserStrings[position]);
        foodNameTextView.setText(foodNameStrings[position]);
        priceTextView.setText("ราคา " + priceStrings[position] + " บาท");
        specialTextView.setText(specialStrings[position]);
        toppingTextView.setText(toppingStrings[position]);
        itemTextView.setText(itemStrings[position] + " จาน");
        timeTextView.setText("เวลารับ = " + timeStrings[position]);
        deliveryTextView.setText("จัดส่ง : " + deliveryStrings[position]);

        return view;
    }
}   //Main Class
