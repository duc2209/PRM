package com.example.quizzz;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private List<CategoryModel> cat_list;
    CategoryAdapter(List<CategoryModel> cat_list){
        this.cat_list = cat_list;
    }
    @Override
    public int getCount() {
        return cat_list.size();
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
        View myView = null;
        if(convertView == null){
            myView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_layout,parent,false);
        }else {
            myView = convertView;
        }

        myView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), TestActivity.class);
                intent.putExtra("CAT_INDEX", position);
                view.getContext().startActivity(intent);
            }
        });

        TextView carName = myView.findViewById(R.id.catName);
        TextView noOfTests = myView.findViewById(R.id.no_of_tests);

        carName.setText(cat_list.get(position).getName());
        noOfTests.setText(String.valueOf(cat_list.get(position).getNoOfTests()) + "Tests");
        return myView;
    }
}
