package com.triangularlake.constantine.triangularlake.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OrmLiteCursorAdapter;
import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.data.dto.Sector;
import com.triangularlake.constantine.triangularlake.utils.StringUtils;

import java.util.Locale;

public class SectorBouldersAdapter extends OrmLiteCursorAdapter<Sector, View> {

    private LayoutInflater layoutInflater;

    public SectorBouldersAdapter(Context context) {
        super(context);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void bindView(View view, Context context, Sector sector) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder.sectorName != null) {
            viewHolder.sectorName.setText("<<" + sector.getSectorName() + ">>");
        }
        if (viewHolder.sectorNameRu != null) {
            viewHolder.sectorNameRu.setText("<<" + sector.getSectorNameRu() + ">>");
        }
        // подумать насчет picasso для кеширования изображений
        if (sector.getSectorPhoto() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(sector.getSectorPhoto(), 0, sector.getSectorPhoto().length);
            viewHolder.sectorPhoto.setImageBitmap(bitmap);
        }
        viewHolder.maxGrade.setText(sector.getMaxGrade());
        viewHolder.minGrade.setText(sector.getMinGrade());
        viewHolder.problemCount.setText(sector.getProblemCount() + "");
        viewHolder.problemLabel.setText(StringUtils.getSectorLabelOnLanguage());

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        final View view = layoutInflater.inflate(R.layout.sectors_list_layout, parent, false);
        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
            viewHolder.sectorName = (TextView) view.findViewById(R.id.sectors_list_sector_name);
        } else if (Locale.getDefault().getLanguage().equals("ru")) {
            viewHolder.sectorNameRu = (TextView) view.findViewById(R.id.sectors_list_sector_name);
        } else {
            viewHolder.sectorName = (TextView) view.findViewById(R.id.sectors_list_sector_name);
        }
        viewHolder.sectorPhoto = (ImageView) view.findViewById(R.id.sectors_list_sector_photo);
        viewHolder.maxGrade = (TextView) view.findViewById(R.id.sectors_list_max_grade);
        viewHolder.minGrade = (TextView) view.findViewById(R.id.sectors_list_min_grade);
        viewHolder.problemCount = (TextView) view.findViewById(R.id.sectors_list_problem_count);
        viewHolder.problemLabel = (TextView) view.findViewById(R.id.sectors_list_problem_label);
        view.setTag(viewHolder);
        return view;
    }

    public static class ViewHolder {
        public TextView sectorName;         // название сектора на английском
        public TextView sectorNameRu;       // название сектора на русском
        public ImageView sectorPhoto;       // фотография сектора
        public TextView maxGrade;           // максимальная категория в секторе
        public TextView minGrade;           // минимальная категория в секторе
        public TextView problemCount;       // количество проблем в секторе
        public TextView problemLabel;       // текст проблема/проблемы
    }
}
