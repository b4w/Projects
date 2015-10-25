package com.triangularlake.constantine.triangularlake.pojo;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.triangularlake.constantine.triangularlake.data.common.CommonDao;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.dto.Problem;
import com.triangularlake.constantine.triangularlake.data.helpers.OrmConnect;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс для кеширования избранных линий.
 */
public class FavouriteProblemsCache {

    private Map<Integer, FavouriteProblemDTO> favouriteProblems;
    public static boolean isCached = false;

    public Map<Integer, FavouriteProblemDTO> getFavouriteProblems() {
        return favouriteProblems;
    }

    /**
     * Добавлена проблема в избранное?
     * @param problemId - id проблемы
     * @return true / false
     */
    public boolean isContainProblem(Integer problemId) {
        return favouriteProblems.containsKey(problemId);
    }

    /**
     * Добавление проблемы в избранные.
     * @param problemId - id проблемы.
     * @param name - название.
     * @param nameRu - название на русском.
     * @param grade - категория.
     */
    public void addProblemInFavourite(Integer problemId, String name, String nameRu, String grade) {
        favouriteProblems.put(problemId, new FavouriteProblemDTO(problemId, name, nameRu, grade));
    }

    /**
     * Удаление проблемы из збранных.
     * @param problemId - id проблемы.
     */
    public void removeProblemFromFavourite(Integer problemId) {
        favouriteProblems.remove(problemId);
    }

    // singleton holder
    private static class FavouriteProblemsCacheHolder {
        public static final FavouriteProblemsCache HOLDER_INSTANCE = new FavouriteProblemsCache();
    }

    // singleton
    public static FavouriteProblemsCache getInstance() {
        return FavouriteProblemsCacheHolder.HOLDER_INSTANCE;
    }

    public static class AsyncLoaderFavouriteRoutes extends AsyncTask<Void, Void, Void> {
        private Context context;

        public AsyncLoaderFavouriteRoutes(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            final CommonDao commonDao;
            try {
                commonDao = OrmConnect.INSTANCE.getDBConnect(context).getDaoByClass(Problem.class);
                if (commonDao != null) {
                    final FavouriteProblemsCache favouriteProblemsCache = FavouriteProblemsCache.getInstance();
                    @SuppressWarnings("unchecked")
                    final List<Problem> problems = commonDao.queryForEq(ICommonDtoConstants.FAVOURITE, 1);
                    final Map<Integer, FavouriteProblemDTO> dtoMap = new HashMap<>();
                    // TODO: FavouriteProblemDTO - static!!! Переделать?
                    if (problems != null && !problems.isEmpty()) {
                        for (Problem item : problems) {
                            dtoMap.put(item.getId(), new FavouriteProblemDTO(item.getId(), item.getProblemName(),
                                    item.getProblemNameRu(), item.getProblemGrade()));
                        }
                    }
                    favouriteProblemsCache.favouriteProblems = dtoMap;
                    isCached = true;
                }
            } catch (SQLException e) {
                Log.e("FavouriteProblemsCache!", "FavouriteProblemsCache doInBackground() Error! " + e.getMessage());
            }
            return null;
        }
    }
}
