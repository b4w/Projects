package com.triangularlake.constantine.triangularlake.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.data.dto.Problem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class BoulderProblemsAdapter extends RecyclerView.Adapter<BoulderProblemsAdapter.ViewHolder> {

    private List<Problem> problems;

    public BoulderProblemsAdapter(Collection<Problem> problems) {
        this.problems = new ArrayList<>(problems);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.problem_name_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
            viewHolder.name.setText(problems.get(position).getProblemName());
        } else if (Locale.getDefault().getLanguage().equals("ru")) {
            viewHolder.name.setText(problems.get(position).getProblemNameRu());
        } else {
            viewHolder.name.setText(problems.get(position).getProblemName());
        }
        // TODO: разобраться с добавлением проблемы в избранное
        viewHolder.favorite.setImageResource(R.drawable.region_circle);
        viewHolder.grade.setText(problems.get(position).getProblemGrade());

        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(viewHolder.context, viewHolder.name.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return problems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private Context context;
        private TextView name;               // название проблемы
        private ImageView favorite;          // проблема добавлена в избранное
        private TextView grade;              // категория проблемы

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.problem_name_layout_name);
            favorite = (ImageView) itemView.findViewById(R.id.problem_name_layout_favorite);
            grade = (TextView) itemView.findViewById(R.id.problem_name_layout_grade);
            context = itemView.getContext();
        }
    }
}
