package com.triangularlake.constantine.triangularlake.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.stmt.UpdateBuilder;
import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Problem;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmConnect;
import com.triangularlake.constantine.triangularlake.pojo.FavouriteProblemsCache;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class BoulderProblemsAdapter extends RecyclerView.Adapter<BoulderProblemsAdapter.ViewHolder> {
    private final static int PROBLEM_IS_FAVOURITE = 1;

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
        final Problem problem = problems.get(position);
        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
            viewHolder.name.setText(problem.getProblemName());
        } else if (Locale.getDefault().getLanguage().equals(ICommonDtoConstants.RU)) {
            viewHolder.name.setText(problem.getProblemNameRu());
        } else {
            viewHolder.name.setText(problem.getProblemName());
        }
        viewHolder.grade.setText(problem.getProblemGrade());

        // добавление проблемы в избранное
        if (problem.getFavourite() == PROBLEM_IS_FAVOURITE) {
            viewHolder.favourite.setImageResource(R.drawable.filter_route_square_blue_transparent);
        } else {
            viewHolder.favourite.setImageResource(R.drawable.filter_route_square_grey_transparent);
        }
        viewHolder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (problem.getFavourite() == PROBLEM_IS_FAVOURITE) {
                    // удаление проблемы из избранного
                    viewHolder.favourite.setImageResource(R.drawable.filter_route_square_grey_transparent);
                    addRemoveFavouriteProblem(view.getContext(), problem.getId(), 0);
                } else {
                    // добавление проблемы в избранное
                    viewHolder.favourite.setImageResource(R.drawable.filter_route_square_blue_transparent);
                    addRemoveFavouriteProblem(view.getContext(), problem.getId(), 1);
                }
            }
        });

        // если нажали на название - открываем выбранную пробему
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

        private Context context;             // контекст активности
        private TextView name;               // название проблемы
        private ImageView favourite;         // проблема добавлена в избранное
        private TextView grade;              // категория проблемы

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.problem_name_layout_name);
            favourite = (ImageView) itemView.findViewById(R.id.problem_name_layout_favorite);
            grade = (TextView) itemView.findViewById(R.id.problem_name_layout_grade);
            context = itemView.getContext();
        }
    }

    private void addRemoveFavouriteProblem(final Context context, Integer problemId, int isAdded) {
        final CommonDao commonDao;
        try {
            commonDao = OrmConnect.INSTANCE.getDBConnect(context).getDaoByClass(Problem.class);
            if (commonDao != null) {
                @SuppressWarnings("unchecked")
                final UpdateBuilder<Problem, Integer> updateBuilder = commonDao.updateBuilder();
                updateBuilder.where().eq(ICommonDtoConstants.ID, problemId);
                updateBuilder.updateColumnValue(ICommonDtoConstants.FAVOURITE, isAdded);
                updateBuilder.update();
            }
        } catch (SQLException e) {
            Log.e("BoulderProblemsAdapter", "AddRemoveFavouriteProblem() Error! " + e.getMessage());
        }
    }
}
