package com.triangularlake.constantine.triangularlake.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OrmLiteCursorAdapter;
import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.data.dto.Problem;

import java.util.Locale;

public class SectorBouldersListAdapter extends OrmLiteCursorAdapter<Problem, View> {

    private LayoutInflater layoutInflater;

    public SectorBouldersListAdapter(Context context) {
        super(context);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void bindView(View view, Context context, Problem problem) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder.problemName != null) {
            viewHolder.problemName.setText(problem.getProblemName());
        }
        if (viewHolder.problemNameRu != null) {
            viewHolder.problemNameRu.setText(problem.getProblemNameRu());
        }

        // TODO: разобраться с добавлением проблемы в избранное
        viewHolder.favorite.setImageResource(R.drawable.region_circle);

        viewHolder.problemGrade.setText(problem.getProblemGrade());
    }

    @Override
    public View newView(final Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        final View view = layoutInflater.inflate(R.layout.sector_boulder_list_layout, parent, false);
        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
            viewHolder.problemName = (TextView) view.findViewById(R.id.sector_boulder_ll_problem_name);
        } else if (Locale.getDefault().getLanguage().equals("ru")) {
            viewHolder.problemNameRu = (TextView) view.findViewById(R.id.sector_boulder_ll_problem_name);
        } else {
            viewHolder.problemName = (TextView) view.findViewById(R.id.sector_boulder_ll_problem_name);
        }

        if (viewHolder.problemName != null) {
            viewHolder.problemName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Open problem name " + ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (viewHolder.problemNameRu != null) {
            viewHolder.problemNameRu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Open problem name " + ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        viewHolder.favorite = (ImageView) view.findViewById(R.id.sector_boulder_ll_problem_favorite);
        viewHolder.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Click Favorite!!", Toast.LENGTH_SHORT).show();
            }
        });

        viewHolder.problemGrade = (TextView) view.findViewById(R.id.sector_boulder_ll_problem_grade);
        viewHolder.problemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Open problem name " + ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        view.setTag(viewHolder);
        return view;
    }

    public static class ViewHolder {
        public TextView problemName;        // название проблемы на английском
        public TextView problemNameRu;      // название проблемы на русском
        public ImageView favorite;          // проблема добавлена в избранное
        public TextView problemGrade;       // категория проблемы
    }
}
