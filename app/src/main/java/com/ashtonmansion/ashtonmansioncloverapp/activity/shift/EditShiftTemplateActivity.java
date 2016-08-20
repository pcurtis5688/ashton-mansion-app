package com.ashtonmansion.ashtonmansioncloverapp.activity.shift;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.ashtonmansion.ashtonmansioncloverapp.dao.ShiftDAO;
import com.ashtonmansion.ashtonmansioncloverapp.dbo.ShiftTemplate;
import com.ashtonmansion.ashtonmansioncloverapp.R;

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
        shiftTemplateNameTV = (TextView) findViewById(R.id.edit_shift_template_name_TV);
        String idLabel = (getResources().getString(R.string.edit_shift_template_ID_label_str)) + shiftTemplate.getShiftID();
        String codeLabel = (getResources().getString(R.string.edit_shift_template_code_label_str)) + shiftTemplate.getShiftCode();
        String nameLabel = (getResources().getString(R.string.edit_shift_template_name_label_str)) + shiftTemplate.getShiftName();

        shiftTemplateIDTV.setText(idLabel);
        shiftTemplateCodeTV.setText(codeLabel);
        shiftTemplateNameTV.setText(nameLabel);
    }
}
