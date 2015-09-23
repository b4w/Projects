package com.triangularlake.constantine.triangularlake.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.data.dto.Sector;
import com.triangularlake.constantine.triangularlake.utils.StringUtils;

import java.util.List;
import java.util.Locale;

public class SectorsListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Sector> sectors;

    public SectorsListAdapter(Context context, List<Sector> sectors) {
        this.sectors = sectors;
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Возвращает количество элементов.
     *
     * @return
     */
    @Override
    public int getCount() {
        return sectors.size();
    }

    /**
     * Возвращает элемент списка по его позиции.
     *
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return sectors.get(position);
    }

    /**
     * Возвращает номер позиции элемента списка.
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.sectors_list_layout, parent, false);
            viewHolder = new ViewHolder();
            if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
                viewHolder.sectorName = (TextView) convertView.findViewById(R.id.sectors_list_sector_name);
            } else if (Locale.getDefault().getLanguage().equals("ru")) {
                viewHolder.sectorNameRu = (TextView) convertView.findViewById(R.id.sectors_list_sector_name);
            } else {
                viewHolder.sectorName = (TextView) convertView.findViewById(R.id.sectors_list_sector_name);
            }
            viewHolder.sectorPhoto = (ImageView) convertView.findViewById(R.id.sectors_list_sector_photo);
            viewHolder.maxGrade = (TextView) convertView.findViewById(R.id.sectors_list_max_grade);
            viewHolder.minGrade = (TextView) convertView.findViewById(R.id.sectors_list_min_grade);
            viewHolder.problemCount = (TextView) convertView.findViewById(R.id.sectors_list_problem_count);
            viewHolder.problemLabel = (TextView) convertView.findViewById(R.id.sectors_list_problem_label);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Sector sector = (Sector) getItem(position);
        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
            viewHolder.sectorName.setText("<<" + sector.getSectorName() + ">>");
        } else if (Locale.getDefault().getLanguage().equals("ru")) {
            viewHolder.sectorNameRu.setText("<<" + sector.getSectorNameRu() + ">>");
        } else {
            viewHolder.sectorName.setText("<<" + sector.getSectorName() + ">>");
        }
        if (sector.getSectorPhoto() != null) {
            // TODO: иногда в этом месте падает с OutOfMemory
            Glide.with(context)
                    .load(sector.getSectorPhoto())
                    .asBitmap()
                    .animate(android.R.anim.fade_in)
                    .into(viewHolder.sectorPhoto);
//            final Bitmap bitmap = BitmapFactory.decodeByteArray(sector.getSectorPhoto(), 0, sector.getSectorPhoto().length);
//            viewHolder.sectorPhoto.setImageBitmap(bitmap);
        }
        viewHolder.maxGrade.setText(sector.getMaxGrade());
        viewHolder.minGrade.setText(sector.getMinGrade());
        viewHolder.problemCount.setText(sector.getProblemCount() + "");
        viewHolder.problemLabel.setText(StringUtils.getSectorLabelOnLanguage());

        return convertView;
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
