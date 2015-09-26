package com.triangularlake.constantine.triangularlake.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.data.dto.Problem;

import java.util.List;
import java.util.Locale;


public class FavouriteProblemsAdapter extends RecyclerView.Adapter<FavouriteProblemsAdapter.ViewHolder> {

    private final static String TAG = FavouriteProblemsAdapter.class.getSimpleName();

    private List<Problem> problems;

    public FavouriteProblemsAdapter(List<Problem> problems) {
        this.problems = problems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.problems_parent_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Problem problem = problems.get(position);
        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
            holder.name.setText(problem.getProblemName());
        } else if (Locale.getDefault().getLanguage().equals("ru")) {
            holder.name.setText(problem.getProblemNameRu());
        } else {
            holder.name.setText(problem.getProblemName());
        }
        // TODO: проставлять изображение добавленного в избранное, т.к. здесь только такие проблемы
        holder.favourite.setImageResource(R.drawable.region_circle);
        holder.grade.setText(problem.getProblemGrade());
    }

    @Override
    public int getItemCount() {
        return problems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;          // название проблемы
        private ImageView favourite;    // добавленав избранное
        private TextView grade;         // категория

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.problems_parent_ll_problem_name);
            favourite = (ImageView) itemView.findViewById(R.id.problems_parent_ll_problem_favorite);
            grade = (TextView) itemView.findViewById(R.id.problems_parent_ll_problem_grade);
        }
    }
}
