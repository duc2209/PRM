package com.example.quizzz.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizzz.Models.QuestionModel;
import com.example.quizzz.R;

import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolders> {
    private List<QuestionModel> questList;

    public AnswersAdapter(List<QuestionModel> questList) {
        this.questList = questList;
    }

    @NonNull
    @Override
    public AnswersAdapter.ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item_layout, parent, false);
        return new AnswersAdapter.ViewHolders(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersAdapter.ViewHolders holder, int position) {
        String ques = questList.get(position).getQuestion();
        String a = questList.get(position).getOptionA();
        String b = questList.get(position).getOptionB();
        String c = questList.get(position).getOptionC();
        String d = questList.get(position).getOptionD();
        int selected = questList.get(position).getSelectedAns();
        int ans = questList.get(position).getCorrectAns();

        holder.setData(position,ques,a,b,c,d,selected,ans);
    }

    @Override
    public int getItemCount() {
        return questList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {
        private TextView questNo ,question,optionA,optionB,optionC,optionD,result;
        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            questNo = itemView.findViewById(R.id.quesNo);
            question = itemView.findViewById(R.id.question);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);
            result = itemView.findViewById(R.id.result);

        }

        private void  setData(int pos,String ques,String a,String b,String c,String d,int selected,int correctAns){
            questNo.setText("Question No. "+String.valueOf(pos+1));
            question.setText(ques);
            optionA.setText("A. "+a);
            optionB.setText("B. "+b);
            optionC.setText("C. "+c);
            optionD.setText("D. "+d);

            if (selected == -1){
                result.setText("Un-Answered");
                result.setTextColor(itemView.getContext().getResources().getColor(R.color.black));
            }else{
                if (selected == correctAns){
                    result.setText("CORRECT");
                    result.setTextColor(itemView.getContext().getResources().getColor(R.color.green));
                    setOptionColor(selected,R.color.green);
                }else{
                    result.setText("WRONG");
                    result.setTextColor(itemView.getContext().getResources().getColor(R.color.red));
                    setOptionColor(selected,R.color.red);
                }
            }
        }

        private void setOptionColor(int selected,int color){
            switch (selected){
                case 1:
                    optionA.setTextColor(itemView.getContext().getResources().getColor(color));
                    break;
                case 2:
                    optionB.setTextColor(itemView.getContext().getResources().getColor(color));
                    break;
                case 3:
                    optionC.setTextColor(itemView.getContext().getResources().getColor(color));
                    break;
                case 4:
                    optionD.setTextColor(itemView.getContext().getResources().getColor(color));
                    break;

                default:
            }
        }
    }
}
