package com.triangularlake.constantine.triangularlake.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Region;
import com.triangularlake.constantine.triangularlake.utils.StringUtils;

import java.util.List;
import java.util.Locale;

public class RegionsAdapter extends RecyclerView.Adapter<RegionsAdapter.ViewHolder> {

    private final static String RU = "ru";
    private List<Region> regions;
    private IRegionsAdapterCallback callback;

    public RegionsAdapter(List<Region> regions, IRegionsAdapterCallback callback) {
        this.regions = regions;
        this.callback = callback;
    }

    @Override
    public RegionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.regions_list_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final long regionId = regions.get(viewHolder.getAdapterPosition()).getId();
                callback.openRegionOnId(regionId);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Region region = regions.get(position);
        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
            holder.regionName.setText("<<" + region.getRegionName() + ">>");
        } else if (Locale.getDefault().getLanguage().equals(RU)) {
            holder.regionName.setText("<<" + region.getRegionNameRu() + ">>");
        } else {
            holder.regionName.setText("<<" + region.getRegionName() + ">>");
        }
        holder.countSectors.setText(region.getCountSectors() + "");
        holder.sectorsLabel.setText(StringUtils.getSectorLabelOnLanguage());
        final Bitmap bitmap = BitmapFactory.decodeByteArray(region.getRegionPhoto(), 0, region.getRegionPhoto().length);
        holder.regionPhoto.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return regions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView regionName;                // название региона на английском
        private TextView countSectors;              // количество секторов в регионе
        private TextView sectorsLabel;              // сектор в едином и множественном числе
        private ImageView regionPhoto;              // фотография региона

        public ViewHolder(View itemView) {
            super(itemView);
            regionName = (TextView) itemView.findViewById(R.id.regions_list_name);
            countSectors = (TextView) itemView.findViewById(R.id.regions_list_count_sectors);
            sectorsLabel = (TextView) itemView.findViewById(R.id.regions_list_sectors_label);
            regionPhoto = (ImageView) itemView.findViewById(R.id.regions_list_region_photo);
        }
    }

    // интерфейс для открытия выбранного региона
    public interface IRegionsAdapterCallback {
        void openRegionOnId(long regionId);
    }
}
