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
import com.triangularlake.constantine.triangularlake.data.dto.Sector;
import com.triangularlake.constantine.triangularlake.utils.StringUtils;

import java.util.List;
import java.util.Locale;

public class SectorsAdapter extends RecyclerView.Adapter<SectorsAdapter.ViewHolder> {

    private static final String LEFT_QUOTES = "<<";
    private static final String RIGHT_QUOTES = ">>";

    private List<Sector> sectors;
    private ISectorsAdapterCallback sectorsAdapterCallback;

    public SectorsAdapter(List<Sector> sectors, ISectorsAdapterCallback sectorsAdapterCallback) {
        this.sectors = sectors;
        this.sectorsAdapterCallback = sectorsAdapterCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sectors_list_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Sector sector = sectors.get(viewHolder.getAdapterPosition());
                StringBuilder sbSectorDesc = new StringBuilder();
                StringBuilder sbSectorName = new StringBuilder();
                // берем имя и описание сектора
                if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
                    sbSectorDesc.append(sector.getSectorDesc());
                    sbSectorName.append(sector.getSectorName());
                } else if (Locale.getDefault().getLanguage().equals(ICommonDtoConstants.RU)) {
                    sbSectorDesc.append(sector.getSectorDescRu());
                    sbSectorName.append(sector.getSectorNameRu());
                } else {
                    sbSectorDesc.append(sector.getSectorDesc());
                    sbSectorName.append(sector.getSectorName());
                }
                final long sectorId = sectors.get(viewHolder.getAdapterPosition()).getId();
                sectorsAdapterCallback.openChosenSector(sbSectorName.toString(), sbSectorDesc.toString(), sectorId);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Sector sector = sectors.get(position);
        final StringBuffer sb;
        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
            sb = new StringBuffer(LEFT_QUOTES)
                    .append(sector.getSectorName())
                    .append(RIGHT_QUOTES);
            holder.sectorName.setText(sb.toString());
        } else if (Locale.getDefault().getLanguage().equals(ICommonDtoConstants.RU)) {
            sb = new StringBuffer(LEFT_QUOTES)
                    .append(sector.getSectorNameRu())
                    .append(RIGHT_QUOTES);
            holder.sectorName.setText(sb.toString());
        } else {
            sb = new StringBuffer(LEFT_QUOTES)
                    .append(sector.getSectorName())
                    .append(RIGHT_QUOTES);
            holder.sectorName.setText(sb.toString());
        }
        final Bitmap bitmap = BitmapFactory.decodeByteArray(sector.getSectorPhoto(), 0, sector.getSectorPhoto().length);
        holder.sectorPhoto.setImageBitmap(bitmap);
        holder.maxGrade.setText(sector.getMaxGrade());
        holder.minGrade.setText(sector.getMinGrade());
        holder.problemCount.setText(sector.getProblemCount() + "");
        holder.problemLabel.setText(StringUtils.getSectorLabelOnLanguage());
    }

    @Override
    public int getItemCount() {
        return sectors.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView sectorName;         // название сектора
        private ImageView sectorPhoto;       // фотография сектора
        private TextView maxGrade;           // максимальная категория в секторе
        private TextView minGrade;           // минимальная категория в секторе
        private TextView problemCount;       // количество проблем в секторе
        private TextView problemLabel;       // текст проблема/проблемы

        public ViewHolder(View itemView) {
            super(itemView);

            sectorName = (TextView) itemView.findViewById(R.id.sectors_list_sector_name);
            sectorPhoto = (ImageView) itemView.findViewById(R.id.sectors_list_sector_photo);
            maxGrade = (TextView) itemView.findViewById(R.id.sectors_list_max_grade);
            minGrade = (TextView) itemView.findViewById(R.id.sectors_list_min_grade);
            problemCount = (TextView) itemView.findViewById(R.id.sectors_list_problem_count);
            problemLabel = (TextView) itemView.findViewById(R.id.sectors_list_problem_label);
        }
    }

    // интерфейс для открытия выбранного сектора
    public interface ISectorsAdapterCallback {
        void openChosenSector(String sectorName, String sectorDescription, long sectorId);
    }
}
