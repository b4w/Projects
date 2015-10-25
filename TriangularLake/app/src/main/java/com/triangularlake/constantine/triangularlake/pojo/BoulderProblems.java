package com.triangularlake.constantine.triangularlake.pojo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.CloseableWrappedIterable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.Boulder;
import com.triangularlake.constantine.triangularlake.data.dto.Problem;
import com.triangularlake.constantine.triangularlake.data.dto.Sector;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmConnect;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class BoulderProblems {
    private static final String TAG = BoulderProblems.class.getSimpleName();
    private static final String ID = "_id";

    private long id;
    private byte[] photo;
    private String name;
    private String nameRu;
    private Collection<Problem> problems;

    public BoulderProblems(long id, byte[] photo, String name, String nameRu, Collection<Problem> problems) {
        this.id = id;
        this.photo = photo;
        this.name = name;
        this.nameRu = nameRu;
        this.problems = problems;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public Collection<Problem> getProblems() {
        return problems;
    }

    public void setProblems(Collection<Problem> problems) {
        this.problems = problems;
    }

    public static class BoulderProblemsAsyncLoader extends AsyncTask<Void, Void, Void> {

        private long sectorId;
        private IBoulderProblemsLoaderCallBack callback;
        private List<BoulderProblems> boulderProblemsList;
        private Context context;
        private byte[] sectorPhoto;

        public BoulderProblemsAsyncLoader(long sectorId, Context context, IBoulderProblemsLoaderCallBack callback) {
            this.sectorId = sectorId;
            this.callback = callback;
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            boulderProblemsList = new ArrayList<>();
            try {
                final CommonDao commonDao = OrmConnect.INSTANCE.getDBConnect(context).getDaoByClass(Sector.class);
                final Sector sector = (Sector) commonDao.queryForEq(ID, sectorId).get(0);
                if (sector != null) {
//                    final Iterator iterator = sector.getBoulders().iterator();
//                    while (iterator.hasNext()) {
//                        Boulder boulder = (Boulder) iterator.next();
//                    }
                    sectorPhoto = sector.getSectorPhoto();
                    for (Boulder boulder : sector.getBoulders()) {
                        boulderProblemsList.add(new BoulderProblems(
                                boulder.getId(),
                                boulder.getPhoto().getPhotoData(),
                                boulder.getBoulderName(),
                                boulder.getBoulderNameRu(),
                                boulder.getProblems()));
                    }
                }
            } catch (SQLException e) {
                Log.e(TAG, "BoulderProblemsAsyncLoader Error! " + e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            callback.callback(boulderProblemsList, sectorPhoto);
        }
    }

    public interface IBoulderProblemsLoaderCallBack {
        void callback(List<BoulderProblems> boulderProblemsList, byte[] sectorPhoto);
    }
}
