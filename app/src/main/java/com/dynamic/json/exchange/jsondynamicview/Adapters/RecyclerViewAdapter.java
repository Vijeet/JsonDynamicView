package com.dynamic.json.exchange.jsondynamicview.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.dynamic.json.exchange.jsondynamicview.R;
import com.dynamic.json.exchange.jsondynamicview.RangeSeekBar;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by Vijeet on 08-06-2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private List<BuildViewObject> mDataset;
    private Context mContext;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTextView;
        public FrameLayout mLayout;
        public ViewHolder(CardView v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.title);
            mLayout = (FrameLayout) v.findViewById(R.id.content);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerViewAdapter(List myDataset, Context applicationContext) {
        mDataset = myDataset;
        mContext = applicationContext;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).getTitle());
        switch (mDataset.get(position).getTitleType()){
            case 0  :   populateLanguage(holder.mLayout);
                        break;
            case 1  :   populateInterests(holder.mLayout);
                        break;
            case 2  :   populateMultiChoiceText("Delhi/NCR", holder.mLayout);
                        break;
            case 3  :   populateGenres(holder.mLayout);
                        break;
            case 4  :   populateHDSelector("HD Only", holder.mLayout);
                        break;
            case 5  :   populateVideoSubscriptions(holder.mLayout);
                        break;
            case 6  :   populateMultiChoiceText("Tata Sky", holder.mLayout);
                        break;
            case 7  :   populateTimeSlots(holder.mLayout);
                        break;
            default :   break;
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void populateTimeSlots(FrameLayout mLayout) {
        RelativeLayout ll =  new RelativeLayout(mContext);
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setMargins(0,0,0, getPxFromDp(22));
        ll.setLayoutParams(layout);

        int startValue = 12;
        int endValue = 24;
        final int factor = 3;
        RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(startValue/factor, endValue/factor, mContext);
        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                minValue = minValue * factor;
                maxValue *= factor;
                //value.setText(minValue + " : " + maxValue);
            }
        });
        seekBar.setNotifyWhileDragging(true);
        ll.addView(seekBar);
        mLayout.addView(ll);
    }

    public void populateGenres(FrameLayout mLayout) {
        ScrollView sv = new ScrollView(mContext);
        ViewGroup.LayoutParams layout = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        sv.setLayoutParams(layout);

        LinearLayout ll = new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.setLayoutParams(params);

        populateCheckbox("All", ll);
        populateCheckbox("Action", ll);
        populateCheckbox("Adventure", ll);
        populateCheckbox("Animation", ll);
        populateCheckbox("Classic", ll);

        sv.addView(ll);
        mLayout.addView(sv);
    }

    public void populateMultiChoiceText(String defaultText, FrameLayout mLayout) {
        RelativeLayout ll =  new RelativeLayout(mContext);
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setMargins(0,0,0, getPxFromDp(22));
        ll.setLayoutParams(layout);

        TextView et = new TextView(mContext);
        et.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        et.setText(defaultText);
        et.setTextColor(mContext.getResources().getColor(R.color.textColor));
        et.setTextSize(16);
        et.setLineSpacing(2,0);

        TextView tv = new TextView(mContext);
        layout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layout.setMargins(0,0,getPxFromDp(20), 0);
        tv.setLayoutParams(layout);
        tv.setText("Change");
        tv.setTextColor(mContext.getResources().getColor(R.color.linkColor));
        tv.setTextSize(12);
        tv.setLineSpacing(2,0);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open Dialog to change EditText
            }
        });

        ll.addView(et);
        ll.addView(tv);
        mLayout.addView(ll);
    }

    public void populateHDSelector(String text, FrameLayout mLayout){
        RelativeLayout ll =  new RelativeLayout(mContext);
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setMargins(0,0,0, getPxFromDp(22));
        ll.setLayoutParams(layout);

        TextView tv = new TextView(mContext);
        tv.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setText(text);
        tv.setTextColor(mContext.getResources().getColor(R.color.textColor));
        tv.setTextSize(16);
        tv.setLineSpacing(2,0);

        Switch sw = new Switch(mContext);
        layout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layout.setMargins(0,0,getPxFromDp(20), 0);
        sw.setLayoutParams(layout);

        ll.addView(tv);
        ll.addView(sw);
        mLayout.addView(ll);
    }

    public void populateVideoSubscriptions(FrameLayout mLayout) {
        LinearLayout ll =  new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setMargins(0,0,0, getPxFromDp(21));
        ll.setLayoutParams(layout);

        populateButton("All", ll);
        populateButton("NetFlix", ll);
        populateButton("Amazon Prime", ll);
        populateButton("HotStar", ll);

        mLayout.addView(ll);
    }

    public void populateInterests(FrameLayout mLayout) {
        LinearLayout ll =  new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setMargins(0,0,0, getPxFromDp(21));
        ll.setLayoutParams(layout);

        populateButton("All", ll);
        populateButton("Movies", ll);
        populateButton("Shows", ll);
        populateButton("Videos", ll);
        populateButton("Events", ll);

        mLayout.addView(ll);
    }
    
    public void populateLanguage(FrameLayout mLayout) {
        LinearLayout ll =  new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setMargins(0,0,0, getPxFromDp(21));
        ll.setLayoutParams(layout);

        populateButton("Hindi", ll);
        populateButton("English", ll);
        populateButton("Both", ll);

        mLayout.addView(ll);
    }

    public void populateButton(String buttonText, LinearLayout mLayout) {
        final AppCompatButton button = new AppCompatButton(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,0,getPxFromDp(10), 0);
        button.setLayoutParams(lp);
        button.setText(buttonText);
        button.setTextSize(12);
        //button.setGravity(Gravity.CENTER);
        button.setPadding(getPxFromDp(10), getPxFromDp(10), getPxFromDp(10), getPxFromDp(8));
        button.setTextColor(mContext.getResources().getColor(R.color.buttonBorder));
        button.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_border));
        boolean check = false;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = true;
                button.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.button_border));
            }
        });
        mLayout.addView(button);
    }

    public void populateCheckbox(String optionText, LinearLayout mLayout) {
        RelativeLayout ll =  new RelativeLayout(mContext);
        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.setMargins(0,0,0, getPxFromDp(22));

        ll.setLayoutParams(layout);

        TextView tv = new TextView(mContext);
        tv.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setText(optionText);
        tv.setTextColor(mContext.getResources().getColor(R.color.textColor));
        tv.setTextSize(16);
        tv.setLineSpacing(2,0);

        CheckBox sw = new CheckBox(mContext);
        layout = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layout.setMargins(0,0,getPxFromDp(20), 0);
        int states[][] = { {android.R.attr.state_checked}, {} };
        int colors[] = { mContext.getResources().getColor(R.color.checkboxSelectedColor), mContext.getResources().getColor(R.color.checkboxNotSelectedColor)};
        CompoundButtonCompat.setButtonTintList(sw, new ColorStateList(states, colors));
        sw.setLayoutParams(layout);

        ll.addView(tv);
        ll.addView(sw);
        mLayout.addView(ll);
    }

    public int getPxFromDp(int dp){
        Resources r = mContext.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
        return px;
    }

}
