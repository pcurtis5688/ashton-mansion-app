package com.ashtonmansion.ashtonmansioncloverapp.activity.shift;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.dao.ShiftDAO;
import com.ashtonmansion.ashtonmansioncloverapp.dbo.ShiftTemplate;

public class EditShiftTemplateActivity extends AppCompatActivity {
    //DATA VARS
    private ShiftDAO shiftDAO;
    private ShiftTemplate shiftTemplate;
    // UI ACCESS FIELDS
    private TextView shiftTemplateIDTV;
    private TextView shiftTemplateCodeTV;
    private TextView shiftTemplateNameTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shift_template);
        Bundle extras = getIntent().getExtras();
        String shiftTemplateID = (String) extras.get("shiftTemplateID");

        //POPULATE THE SHIFT TEMPLATE FOR EDITING
        shiftDAO = new ShiftDAO(this);
        shiftTemplate = shiftDAO.getShiftTemplateByID(shiftTemplateID);
        //GET UI HANDLES and POPULATE
        shiftTemplateIDTV = (TextView) findViewById(R.id.edit_shift_template_ID_TV);
        shiftTemplateCodeTV = (TextView) findViewById(R.id.edit_shift_template_code_TV);
        shiftTemplateNameTV = (EditText) findViewById(R.id.edit_shift_template_name_TV);
        shiftTemplateIDTV.setText(shiftTemplate.getShiftID());
        shiftTemplateCodeTV.setText(shiftTemplate.getShiftCode());
        shiftTemplateNameTV.setText(shiftTemplate.getShiftName());

            
    }
}
