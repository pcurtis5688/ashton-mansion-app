package com.ashtonmansion.ashtonmansioncloverapp.activity.shift;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.dao.ShiftDAO;
import com.ashtonmansion.ashtonmansioncloverapp.dbo.ShiftTemplate;

import java.util.List;

public class ShiftTemplateManagementActivity extends AppCompatActivity {
    //DATA VARS
    private List<ShiftTemplate> shiftTemplateList;
    private ShiftDAO shiftDAO;
    //UI FIELD ELEMENTS
    private TableLayout shiftTemplateTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_shift_template);
        //Set up table and header row
        setUpTableAndHeader();
        //Retrieve Shift Template information and populate
        fetchShiftTemplates();
        populateShiftTemplateTable();
    }


    private void setUpTableAndHeader() {
        shiftTemplateTable = (TableLayout) findViewById(R.id.shift_template_table);
        TableRow shiftTemplateHeaderRow = new TableRow(this);
        TextView shiftTemplateCodeHeaderTV = new TextView(this);
        TextView shiftTemplateHeaderNameTV = new TextView(this);
        shiftTemplateCodeHeaderTV.setText("Shift Code");
        shiftTemplateCodeHeaderTV.setAllCaps(true);
        shiftTemplateHeaderNameTV.setText("Shift Name");
        shiftTemplateHeaderNameTV.setAllCaps(true);
        shiftTemplateHeaderRow.addView(shiftTemplateCodeHeaderTV);
        shiftTemplateHeaderRow.addView(shiftTemplateHeaderNameTV);
        shiftTemplateTable.addView(shiftTemplateHeaderRow);
    }

    private void fetchShiftTemplates() {
        shiftDAO = new ShiftDAO(this);
        //todo this and all similar creation methods need to be handled elsewhere in prog
        shiftDAO.createShiftTemplateTable();

        shiftTemplateList = shiftDAO.getAllShiftTemplates();
    }

    private void populateShiftTemplateTable() {
        for (ShiftTemplate shiftTemplate : shiftTemplateList) {
            TableRow shiftTemplateWorkingRow = new TableRow(this);
            TextView shiftTemplateCodeTV = new TextView(this);
            TextView shiftTemplateNameTV = new TextView(this);
            shiftTemplateCodeTV.setText("" + shiftTemplate.getShiftCode());
            shiftTemplateNameTV.setText(shiftTemplate.getShiftName());
            shiftTemplateWorkingRow.addView(shiftTemplateCodeTV);
            shiftTemplateWorkingRow.addView(shiftTemplateNameTV);
            shiftTemplateTable.addView(shiftTemplateWorkingRow);
        }
    }

    public void addShiftTemplateRecord(View view) {
        //TODO CONFIRMATION POPUP HERE
        EditText newShiftCode = (EditText) findViewById(R.id.shift_template_add_code);
        EditText newShiftName = (EditText) findViewById(R.id.shift_template_add_name);
        int newShiftCodeInt = Integer.parseInt(newShiftCode.getText().toString());
        String newShiftNameString = newShiftName.getText().toString();
        //TODO DAOS IN ASYNC?
        shiftDAO.addShiftTemplate(newShiftCodeInt, newShiftNameString);
        reloadShiftTemplatePage();
    }

    private void reloadShiftTemplatePage() {
        shiftTemplateTable.removeAllViews();
        setUpTableAndHeader();
        fetchShiftTemplates();
        populateShiftTemplateTable();
        //TODO TOAST IF SUCCESS.
    }
}