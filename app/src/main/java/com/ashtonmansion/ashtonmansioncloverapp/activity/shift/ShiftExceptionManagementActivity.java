package com.ashtonmansion.ashtonmansioncloverapp.activity.shift;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.dao.ShiftDAO;
import com.ashtonmansion.ashtonmansioncloverapp.dbo.ShiftException;

import java.util.List;

/**
 * Created by paul on 8/5/2016.
 */
public class ShiftExceptionManagementActivity extends AppCompatActivity {
    //DATA VARS
    private ShiftDAO shiftDAO;
    private List<ShiftException> shiftExceptions;

    //UI FIELD ELEMENTS
    private TableLayout shiftExceptionTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_shift_exception);
    }


    private void setUpTableAndHeader() {
        shiftExceptionTable = (TableLayout) findViewById(R.id.shift_exception_table);
        TableRow shiftExceptionTableHeaderRow = new TableRow(this);

        TextView shiftExceptionTableHeaderDateTV = new TextView(this);
        TextView shiftExceptionTableHeaderTimeTV = new TextView(this);
        TextView shiftExceptionTableHeaderEmployeeTV = new TextView(this);
        TextView shiftExceptionTableHeaderDurationTV = new TextView(this);

        shiftExceptionTableHeaderDateTV.setText("Exception Date");
        shiftExceptionTableHeaderTimeTV.setText("Time");
        shiftExceptionTableHeaderEmployeeTV.setText("Relevant Employee");
        shiftExceptionTableHeaderDurationTV.setText("Duration of Exception");

        shiftExceptionTableHeaderDateTV.setAllCaps(true);
        shiftExceptionTableHeaderTimeTV.setAllCaps(true);
        shiftExceptionTableHeaderEmployeeTV.setAllCaps(true);
        shiftExceptionTableHeaderDurationTV.setAllCaps(true);

        shiftExceptionTableHeaderRow.addView(shiftExceptionTableHeaderDateTV);
        shiftExceptionTableHeaderRow.addView(shiftExceptionTableHeaderTimeTV);
        shiftExceptionTableHeaderRow.addView(shiftExceptionTableHeaderEmployeeTV);
        shiftExceptionTableHeaderRow.addView(shiftExceptionTableHeaderDurationTV);

        shiftExceptionTable.addView(shiftExceptionTableHeaderRow);
    }

    private void fetchShiftExceptions() {
        shiftDAO = new ShiftDAO(this);
        //todo this and all similar creation methods need to be handled elsewhere in prog
        shiftDAO.createShiftExceptionTable();
        shiftExceptions = shiftDAO.getAllShiftExceptions();
    }

    private void populateShiftExceptionTable() {
        for (ShiftException shiftException : shiftExceptions) {
            TableRow workingShiftExceptionTableRow = new TableRow(this);

            TextView workingShiftExceptionIDTV = new TextView(this);
            TextView workingShiftExceptionEmployeeTV = new TextView(this);
            TextView workingShiftExceptionDateTV = new TextView(this);
            TextView workingShiftExceptionTimeTV = new TextView(this);
            TextView workingShiftExceptionDurationTV = new TextView(this);

            workingShiftExceptionIDTV.setText("" + shiftException.getShiftExceptionID());
            workingShiftExceptionEmployeeTV.setText(shiftException.getEmployeeID());
            workingShiftExceptionDateTV.setText(shiftException.getShiftExceptionDate());
            workingShiftExceptionTimeTV.setText(shiftException.getShiftExceptionTime());
            workingShiftExceptionDurationTV.setText("" + shiftException.getDuration());

            workingShiftExceptionTableRow.addView(workingShiftExceptionIDTV);
            workingShiftExceptionTableRow.addView(workingShiftExceptionEmployeeTV);
            workingShiftExceptionTableRow.addView(workingShiftExceptionDateTV);
            workingShiftExceptionTableRow.addView(workingShiftExceptionTimeTV);
            workingShiftExceptionTableRow.addView(workingShiftExceptionDurationTV);

            shiftExceptionTable.addView(workingShiftExceptionTableRow);
        }
    }

    public void addShiftExceptionRecord(View view) {
        //TODO CONFIRMATION POPUP HERE
        //TODO DAOS IN ASYNC?


        ShiftException newShiftException = new ShiftException();

        // shiftDAO.addShiftException();
        //  reloadShiftTemplatePage();
    }

    private void reloadShiftTemplatePage() {
//        shiftTemplateTable.removeAllViews();
//        setUpTableAndHeader();
//        fetchShiftTemplates();
//        populateShiftTemplateTable();
//        //TODO TOAST IF SUCCESS.
    }
}
