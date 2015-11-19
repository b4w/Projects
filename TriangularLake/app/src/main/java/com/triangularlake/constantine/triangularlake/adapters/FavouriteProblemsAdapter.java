package com.triangularlake.constantine.triangularlake.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.helpers.SQLiteHelper;
import com.triangularlake.constantine.triangularlake.data.pojo.Problem;

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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Problem problem = problems.get(position);
        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
            holder.name.setText(problem.getProblemName());
        } else if (Locale.getDefault().getLanguage().equals(ICommonDtoConstants.RU)) {
            holder.name.setText(problem.getProblemNameRu());
        } else {
            holder.name.setText(problem.getProblemName());
        }
        holder.grade.setText(problem.getProblemGrade());

        // добавление проблемы в избранное
        if (problem.getFavourite()) {
            holder.favourite.setImageResource(R.drawable.filter_route_square_blue_transparent);
        } else {
            holder.favourite.setImageResource(R.drawable.filter_route_square_grey_transparent);
        }
        holder.favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (problem.getFavourite()) {
                    // удаление проблемы из избранного
                    holder.favourite.setImageResource(R.drawable.filter_route_square_grey_transparent);
                    addRemoveFavouriteProblem(view.getContext(), problem.getId(), 0);
                } else {
                    // добавление проблемы в избранное
                    holder.favourite.setImageResource(R.drawable.filter_route_square_blue_transparent);
                    addRemoveFavouriteProblem(view.getContext(), problem.getId(), 1);
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
    private void addRemoveFavouriteProblem(final Context context, Integer problemId, int isAdded) {
        final SQLiteDatabase db = new SQLiteHelper(context).getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("favourite", isAdded);
        db.update("PROBLEMS", contentValues, "_id = ?", new String[]{problemId + ""});
        db.close();
    }
}
