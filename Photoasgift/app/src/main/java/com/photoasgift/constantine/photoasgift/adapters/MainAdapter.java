package com.photoasgift.constantine.photoasgift.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.photoasgift.constantine.photoasgift.R;
import com.photoasgift.constantine.photoasgift.database.MainMenu;

import java.util.List;
import java.util.Locale;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private List<MainMenu> mainMenuList;
    private IMainAdapterCallback callback;

    public MainAdapter(List<MainMenu> mainMenuList, IMainAdapterCallback callback) {
        this.mainMenuList = mainMenuList;
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item_layout, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final MainMenu mainMenu = mainMenuList.get(viewHolder.getAdapterPosition());
                callback.openChosenActivity(mainMenu.getId());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MainMenu menuItem = mainMenuList.get(position);
        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
            holder.name.setText(menuItem.getName());
        } else if (Locale.getDefault().getLanguage().equals("ru")) {
            holder.name.setText(menuItem.getNameRu());
        } else {
            holder.name.setText(menuItem.getName());
        }
        // TODO: добавлять изображения с SD карты! Временно добавляю из drawable.
        holder.image.setImageResource(R.drawable.forest);
    }

    @Override
    public int getItemCount() {
        return mainMenuList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.main_item_layout_text_view);
            image = (ImageView) itemView.findViewById(R.id.main_item_layout_image);
        }
    }

    public interface IMainAdapterCallback {
        void openChosenActivity(String mainMenuItemId);
    }
}
