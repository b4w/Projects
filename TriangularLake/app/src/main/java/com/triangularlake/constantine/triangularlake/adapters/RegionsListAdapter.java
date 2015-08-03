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
import com.triangularlake.constantine.triangularlake.data.dto.Region;
import com.triangularlake.constantine.triangularlake.utils.StringUtils;

import java.util.Locale;

public class RegionsListAdapter extends OrmLiteCursorAdapter<Region, View> {

    private LayoutInflater layoutInflater;

    public RegionsListAdapter(Context context) {
        super(context);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void bindView(View view, Context context, Region region) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        if (viewHolder.regionName != null) {
            viewHolder.regionName.setText(region.getRegionName());
        }
        if (viewHolder.regionNameRu != null) {
            viewHolder.regionNameRu.setText(region.getRegionNameRu());
        }
        viewHolder.countSectors.setText(region.getCountSectors() + "");
        viewHolder.sectorsLabel.setText(StringUtils.getSectorLabelOnLanguage());
        // подумать насчет picasso для кеширования изображений
        if (region.getRegionPhoto() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(region.getRegionPhoto(), 0, region.getRegionPhoto().length);
            viewHolder.regionPhoto.setImageBitmap(bitmap);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        final View view = layoutInflater.inflate(R.layout.regions_list_layout, parent, false);
        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
            viewHolder.regionName = (TextView) view.findViewById(R.id.regions_list_name);
        } else if (Locale.getDefault().getLanguage().equals("ru")) {
            viewHolder.regionNameRu = (TextView) view.findViewById(R.id.regions_list_name);
        } else {
            viewHolder.regionName = (TextView) view.findViewById(R.id.regions_list_name);
        }
        viewHolder.countSectors = (TextView) view.findViewById(R.id.regions_list_count_sectors);
        viewHolder.sectorsLabel = (TextView) view.findViewById(R.id.regions_list_sectors_label);
        viewHolder.regionPhoto = (ImageView) view.findViewById(R.id.regions_list_region_photo);
        view.setTag(viewHolder);
        return view;
    }

    public static class ViewHolder {
        public TextView regionName;     // название региона на английском
        public TextView regionNameRu;   // название региона на русском
        public TextView countSectors;   // количество секторов в регионе
        public TextView sectorsLabel;   // сектор в едином и множественном числе
        public ImageView regionPhoto;   // фотография региона
    }
}
