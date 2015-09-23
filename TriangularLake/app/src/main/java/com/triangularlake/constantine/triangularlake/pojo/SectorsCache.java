package com.triangularlake.constantine.triangularlake.pojo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.Sector;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmConnect;
import com.triangularlake.constantine.triangularlake.utils.IStringConstants;

import java.sql.SQLException;
import java.util.List;

// Singleton
public class SectorsCache {

    private List<Sector> lietlahtiSectors;
    private List<Sector> triangularSectors;
    public static boolean isCached = false;

    // Singleton
    private static class SectorCacheHolder {
        public static final SectorsCache HOLDER_INSTANCE = new SectorsCache();
    }

    // Singleton
    public static SectorsCache getInstance() {
        return SectorCacheHolder.HOLDER_INSTANCE;
    }

    public List<Sector> getLietlahtiSectors() {
        return lietlahtiSectors;
    }

    public void setLietlahtiSectors(List<Sector> lietlahtiSectors) {
        this.lietlahtiSectors = lietlahtiSectors;
    }

    public List<Sector> getTriangularSectors() {
        return triangularSectors;
    }

    public void setTriangularSectors(List<Sector> triangularSectors) {
        this.triangularSectors = triangularSectors;
    }

    // кеширование секторов
    public static class AsyncLoadSectors extends AsyncTask<Void, Void, Void> {
        private Context context;

        public AsyncLoadSectors(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            final CommonDao commonDao;
            try {
                commonDao = OrmConnect.INSTANCE.getDBConnect(context).getDaoByClass(Sector.class);
                if (commonDao != null) {
                    final SectorsCache sectorsCache = SectorsCache.getInstance();
                    sectorsCache.lietlahtiSectors = commonDao.queryForEq(IStringConstants.REGION_ID, 1);
                    sectorsCache.triangularSectors = commonDao.queryForEq(IStringConstants.REGION_ID, 2);
                    isCached = true;
                }
            } catch (SQLException e) {
                Log.e("SectorsCache!", "SectorCache doInBackground() Error! " +e.getMessage());
            }
            return null;
        }
    }
}
