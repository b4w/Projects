package com.triangularlake.constantine.triangularlake.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.triangularlake.constantine.triangularlake.R;
import com.triangularlake.constantine.triangularlake.data.dto.ICommonDtoConstants;
import com.triangularlake.constantine.triangularlake.data.pojo.Problem;

import java.util.List;
import java.util.Locale;

public class ProblemsExpListAdapter extends BaseExpandableListAdapter {

    private final static String TAG = ProblemsExpListAdapter.class.getSimpleName();

    private Context context;
    private LayoutInflater layoutInflater;
    private List<List<Problem>> problems;
    private Resources resources;

    public ProblemsExpListAdapter(Context context, List<List<Problem>> problems) {
        this.context = context;
        this.problems = problems;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        resources = context.getResources();
    }

    /**
     * Возвращает количество элементов (родителей) в списке.
     *
     * @return
     */
    @Override
    public int getGroupCount() {
        return problems.size();
    }

    /**
     * Возвращает количество "детей" у "родителя".
     *
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return problems.get(groupPosition).size();
    }

    /**
     * Возвращает родителя по заданной позиции.
     *
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return problems.get(groupPosition);
    }

    /**
     * Возвращает "ребенка" по заданному номеру "родителя" и "ребенка".
     *
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return problems.get(groupPosition).get(childPosition);
    }

    /**
     * @param groupPosition
     * @return
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * Показываем "родителя" группы.
     *
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.problems_parent_list_layout, parent, false);
        }

        if (isExpanded) {
            // TODO: Показать на изображении линии выбранной проблемы
            //Изменяем что-нибудь, если текущая Group раскрыта
        } else {
            // TODO: Скрыть на изображении линии выбранной проблемы
            //Изменяем что-нибудь, если текущая Group скрыта
        }

        final TextView problemName = (TextView) convertView.findViewById(R.id.problems_parent_ll_problem_name);
        final TextView problemGrade = (TextView) convertView.findViewById(R.id.problems_parent_ll_problem_grade);

        final Problem problem = (Problem) getChild(groupPosition, 0);

        if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
            problemName.setText(problem.getProblemName());
        } else if (Locale.getDefault().getLanguage().equals(ICommonDtoConstants.RU)) {
            problemName.setText(problem.getProblemNameRu());
        } else {
            problemName.setText(problem.getProblemName());
        }
        problemGrade.setText(problem.getProblemGrade());
        return convertView;
    }

    /**
     * Показываем "ребенка" выбранной группы.
     *
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.problems_children_list_layout, null);
        }

        final Problem problem = (Problem) getChild(groupPosition, childPosition);

//        ImageView image = (ImageView) convertView.findViewById(R.id.exercises_list_layout_image);
//
//        if (!exercises.getImagePath().isEmpty()) {
//            Bitmap myBitmap = BitmapFactory.decodeFile(exercises.getImagePath());
//            image.setImageBitmap(myBitmap);
//        }
//
//        TextView name = (TextView) convertView.findViewById(R.id.exercises_list_layout_name);
//        name.setText(exercises.getName());
//
//        TextView nameCategory = (TextView) convertView.findViewById(R.id.exercises_list_layout_name_category);
//        nameCategory.setText(resources.getString(R.string.category) + ": " + exercises.getCategory().getName());
//
//        TextView typeExercise = (TextView) convertView.findViewById(R.id.exercises_list_layout_type_exercise);
//        typeExercise.setText(resources.getString(R.string.type_of_exercise) + ": " + exercises.getTypeExercise().getName());
//
//        TextView equipment = (TextView) convertView.findViewById(R.id.exercises_list_layout_equipment);
//        equipment.setText(resources.getString(R.string.equipment) + ": " + exercises.getEquipment().getName());
//
//        TextView comment = (TextView) convertView.findViewById(R.id.exercises_list_layout_comment);
//        comment.setText(resources.getString(R.string.comment) + ": " + exercises.getComment());

        return convertView;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
