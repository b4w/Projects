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
import com.triangularlake.constantine.triangularlake.pojo.FavouriteProblemDTO;
import com.triangularlake.constantine.triangularlake.pojo.FavouriteProblemsCache;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;


public class FavouriteProblemsAdapter extends RecyclerView.Adapter<FavouriteProblemsAdapter.ViewHolder> {

    private final static String TAG = FavouriteProblemsAdapter.class.getSimpleName();

    private List<FavouriteProblemDTO> problems;

    public FavouriteProblemsAdapter(List<FavouriteProblemDTO> problems) {
        this.problems = problems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.problems_parent_list_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final FavouriteProblemDTO problemDTO = problems.get(position);
        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
            holder.name.setText(problemDTO.name);
        } else if (Locale.getDefault().getLanguage().equals(ICommonDtoConstants.RU)) {
            holder.name.setText(problemDTO.nameRu);
        } else {
            holder.name.setText(problemDTO.name);
        }
        holder.grade.setText(problemDTO.grade);

        // добавление проблемы в избранное
        final boolean isContainProblem = FavouriteProblemsCache.getInstance().isContainProblem(problemDTO.id);
        if (isContainProblem) {
            holder.favourite.setImageResource(R.drawable.filter_route_square_blue_transparent);
        } else {
            holder.favourite.setImageResource(R.drawable.filter_route_square_grey_transparent);
        }
        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean isContainProblem = FavouriteProblemsCache.getInstance().isContainProblem(problemDTO.id);
                if (isContainProblem) {
                    // удаление проблемы из избранного
                    holder.favourite.setImageResource(R.drawable.filter_route_square_grey_transparent);
                    addRemoveFavouriteProblem(view.getContext(), problemDTO.id, 0);
                    FavouriteProblemsCache.getInstance().removeProblemFromFavourite(problemDTO.id);
                } else {
                    // добавление проблемы в избранное
                    holder.favourite.setImageResource(R.drawable.filter_route_square_blue_transparent);
                    addRemoveFavouriteProblem(view.getContext(), problemDTO.id, 1);
                    FavouriteProblemsCache.getInstance().addProblemInFavourite(problemDTO.id,
                            problemDTO.name, problemDTO.nameRu, problemDTO.grade);
                }
            }
        });

        // если нажали на название - открываем выбранную пробему
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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

    // TODO: т.к. есть 2 одинаковых метода, то вынести в Abstract или static???
    private void addRemoveFavouriteProblem(final Context context, Long problemId, int isAdded) {
        final CommonDao commonDao;
        try {
            commonDao = OrmConnect.INSTANCE.getDBConnect(context).getDaoByClass(Problem.class);
            if (commonDao != null) {
                @SuppressWarnings("unchecked")
                final UpdateBuilder<Problem, Long> updateBuilder = commonDao.updateBuilder();
                updateBuilder.where().eq(ICommonDtoConstants.ID, problemId);
                updateBuilder.updateColumnValue(ICommonDtoConstants.FAVOURITE, isAdded);
                updateBuilder.update();
            }
        } catch (SQLException e) {
            Log.e("BoulderProblemsAdapter", "AddRemoveFavouriteProblem() Error! " + e.getMessage());
        }
    }
}
