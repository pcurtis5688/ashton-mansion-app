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
    private TextView shiftTemplateIDTV1;
    private TextView shiftTemplateCodeTV1;
    private TextView shiftTemplateNameTV1;
    private TextView shiftTemplateIDTV2;
    private TextView shiftTemplateCodeTV2;
    private TextView shiftTemplateNameTV2;

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
        shiftTemplateIDTV1 = (TextView) findViewById(R.id.edit_shift_template_ID_TV_1);
        shiftTemplateCodeTV1 = (TextView) findViewById(R.id.edit_shift_template_code_TV_1);
        shiftTemplateNameTV1 = (TextView) findViewById(R.id.edit_shift_template_name_TV_1);
        shiftTemplateIDTV2 = (TextView) findViewById(R.id.edit_shift_template_ID_TV_2);
        shiftTemplateCodeTV2 = (TextView) findViewById(R.id.edit_shift_template_code_TV_2);
        shiftTemplateNameTV2 = (TextView) findViewById(R.id.edit_shift_template_name_TV_2);


        //SET HEADER FIRST ROW
        shiftTemplateIDTV1.setText(getResources().getString(R.string.edit_shift_template_ID_label_str));
        shiftTemplateCodeTV1.setText(getResources().getString(R.string.edit_shift_template_code_label_str));
        shiftTemplateNameTV1.setText(getResources().getString(R.string.edit_shift_template_name_label_str));
        //SET HEADER SECOND ROW
        shiftTemplateIDTV2.setText(shiftTemplate.getShiftID());
        shiftTemplateCodeTV2.setText(shiftTemplate.getShiftCode());
        shiftTemplateNameTV2.setText(shiftTemplate.getShiftName());
    }
}
