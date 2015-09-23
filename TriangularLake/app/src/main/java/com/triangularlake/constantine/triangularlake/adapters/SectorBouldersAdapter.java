package com.triangularlake.constantine.triangularlake.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.activities.SideActivity;
import com.triangularlake.constantine.triangularlake.data.dto.Boulder;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.pojo.BoulderProblems;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.util.List;
import java.util.Locale;

public class SectorBouldersAdapter extends RecyclerView.Adapter<SectorBouldersAdapter.ViewHolder> {

    private final static String TAG = SectorBouldersAdapter.class.getSimpleName();

    private List<BoulderProblems> boulderProblemsList;

    public SectorBouldersAdapter(List<BoulderProblems> boulderProblemsList) {
        this.boulderProblemsList = boulderProblemsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sector_boulder_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        final BoulderProblems boulderProblems = boulderProblemsList.get(position);
        viewHolder.boulderName.setText(boulderProblems.getName());
        final Bitmap bitmap = BitmapFactory.decodeByteArray(boulderProblems.getPhoto(), 0, boulderProblems.getPhoto().length);
        viewHolder.boulderPhoto.setImageBitmap(bitmap);

        // адаптер для названий трасс
        final BoulderProblemsAdapter namesAdapter = new BoulderProblemsAdapter(boulderProblems.getProblems());
        viewHolder.problemNames.setAdapter(namesAdapter);

        // открываем активность выбранной стороны камня
        viewHolder.boulderPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChosenSide(boulderProblems.getId(),
                        boulderProblems.getName(),
                        boulderProblems.getNameRu(),
                        viewHolder.context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return boulderProblemsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView boulderPhoto;             // фотография камня
        private TextView boulderName;               // название камня
        private RecyclerView problemNames;          // названия проблем
        private Context context;                    // контекст активности

        public ViewHolder(View itemView) {
            super(itemView);
            boulderPhoto = (ImageView) itemView.findViewById(R.id.sector_boulder_layout_photo);
            boulderName = (TextView) itemView.findViewById(R.id.sector_boulder_layout_name);
            // RecyclerView названий трасс
            problemNames = (RecyclerView) itemView.findViewById(R.id.sector_boulder_layout_problem_names);
            problemNames.setHasFixedSize(true);
            final LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            problemNames.setLayoutManager(layoutManager);
            context = itemView.getContext();
        }
    }

    private void openChosenSide(long boulderId, String name, String nameRu, Context context) {
        Log.d(TAG, "openChosenSide() start");
        Intent intent = new Intent(context, SideActivity.class);
        intent.putExtra(ICommonDtoConstants.BOULDER_ID, boulderId);
        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
            intent.putExtra(ICommonDtoConstants.BOULDER_NAME, name);
        } else if (Locale.getDefault().getLanguage().equals("ru")) {
            intent.putExtra(ICommonDtoConstants.BOULDER_NAME, nameRu);
        } else {
            intent.putExtra(ICommonDtoConstants.BOULDER_NAME, name);
        }
        context.startActivity(intent);
        Log.d(TAG, "openChosenSide() done");
    }
}
