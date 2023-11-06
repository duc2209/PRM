package com.example.quizzz;

import static com.example.quizzz.DbQuery.ANSWERED;
import static com.example.quizzz.DbQuery.NOT_VISITED;
import static com.example.quizzz.DbQuery.REVIEW;
import static com.example.quizzz.DbQuery.UNANSWERED;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.play.core.integrity.e;

public class QuestionGridAdapter extends BaseAdapter {

    private int numOfQues;
    private Context context;
    public QuestionGridAdapter(Context context, int numOfQues){
        this.numOfQues = numOfQues;
        this.context = context;
    }
    @Override
    public int getCount() {
        return numOfQues;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View myview;

        if(convertView == null)
        {
            myview = LayoutInflater.from(parent.getContext()).inflate(R.layout.ques_grid_item, parent, false);
        }
        else
        {
            myview = convertView;
        }

        myview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (context instanceof QuestionActivity)
                    ((QuestionActivity)context).goToQuestion(position);
            }
        });

        TextView quesTV = myview.findViewById(R.id.ques_num);
        quesTV.setText(String.valueOf(position + 1));

        switch (DbQuery.g_quesList.get(position).getStatus())
        {
            case NOT_VISITED :
                quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(),R.color.grey)));
                break;
            case UNANSWERED:
                quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(),R.color.red)));
                break;
            case ANSWERED:
                quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(),R.color.green)));
                break;
            case REVIEW:
                quesTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(myview.getContext(),R.color.pink)));
                break;
            default:
                break;
        }
        return myview;
    }
}
