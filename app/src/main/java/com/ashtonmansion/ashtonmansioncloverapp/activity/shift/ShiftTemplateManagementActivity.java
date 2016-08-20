package com.ashtonmansion.ashtonmansioncloverapp.activity.shift;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ashtonmansion.ashtonmansioncloverapp.R;
import com.ashtonmansion.ashtonmansioncloverapp.dao.ShiftDAO;
import com.ashtonmansion.ashtonmansioncloverapp.dbo.ShiftTemplate;
import com.ashtonmansion.ashtonmansioncloverapp.webservices.shiftws.ShiftWebServices;

import java.util.List;

public class ShiftTemplateManagementActivity extends AppCompatActivity {
    //ACTIVITY VARS
    private Context shiftTemplateManagementContext;
    //DATA VARS
    private List<ShiftTemplate> shiftTemplateList;
    private ShiftDAO shiftDAO;
    private ShiftTemplate newShiftTemplate;
    private boolean shiftTemplateWSSuccess;
    //UI FIELD ELEMENTS
    private TableLayout shiftTemplateTable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_shift_template);
        //SET ACTIVITY CONTEXT
        shiftTemplateManagementContext = this;
        //Set up table and header row
        setUpTableAndHeader();
        //Retrieve Shift Template information and populate
        fetchShiftTemplates();
        populateShiftTemplateTable();
    }

    private void setUpTableAndHeader() {
        shiftTemplateTable = (TableLayout) findViewById(R.id.shift_template_table);
        TableRow shiftTemplateHeaderRow = new TableRow(this);
        TextView shiftTemplateIDHeaderTV = new TextView(this);
        TextView shiftTemplateCodeHeaderTV = new TextView(this);
        TextView shiftTemplateHeaderNameTV = new TextView(this);
        shiftTemplateIDHeaderTV.setText("Shift Template ID");
        shiftTemplateIDHeaderTV.setAllCaps(true);
        shiftTemplateCodeHeaderTV.setText("Shift Code");
        shiftTemplateCodeHeaderTV.setAllCaps(true);
        shiftTemplateHeaderNameTV.setText("Shift Name");
        shiftTemplateHeaderNameTV.setAllCaps(true);
        shiftTemplateHeaderRow.addView(shiftTemplateIDHeaderTV);
        shiftTemplateHeaderRow.addView(shiftTemplateCodeHeaderTV);
        shiftTemplateHeaderRow.addView(shiftTemplateHeaderNameTV);
        shiftTemplateTable.addView(shiftTemplateHeaderRow);
    }

    private void fetchShiftTemplates() {
        shiftDAO = new ShiftDAO(this);
        shiftTemplateList = shiftDAO.getAllShiftTemplates();
    }

    private void populateShiftTemplateTable() {
        for (final ShiftTemplate shiftTemplate : shiftTemplateList) {
            TableRow newShiftTemplateRow = new TableRow(this);
            TextView newShiftTemplateIDTV = new TextView(this);
            TextView newShiftTemplateCodeTV = new TextView(this);
            TextView newShiftTemplateNameTV = new TextView(this);

            //SHIFT ID AND CODE
            String shiftID = shiftTemplate.getShiftID();
            newShiftTemplateIDTV.setText(shiftID);
            String shiftCode = shiftTemplate.getShiftCode();
            newShiftTemplateCodeTV.setText(shiftCode);
            ShiftTemplate editableShiftTemplate = shiftTemplate;
            //CLICKABLE SHIFT NAME TO GO TO EDIT DETAIL
            newShiftTemplateNameTV.setText(shiftTemplate.getShiftName());
            newShiftTemplateNameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent editShiftTemplateIntent = new Intent(shiftTemplateManagementContext, EditShiftTemplateActivity.class);
                    editShiftTemplateIntent.putExtra("shiftTemplateID", shiftTemplate.getShiftID());
                    startActivity(editShiftTemplateIntent);
                }
            });


            Button deleteShiftTemplateButton = new Button(this);
            deleteShiftTemplateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmShiftTemplateDelete(shiftTemplate);
                }
            });
            deleteShiftTemplateButton.setText("DELETE");

            newShiftTemplateRow.addView(newShiftTemplateIDTV);
            newShiftTemplateRow.addView(newShiftTemplateCodeTV);
            newShiftTemplateRow.addView(newShiftTemplateNameTV);
            newShiftTemplateRow.addView(deleteShiftTemplateButton);

            shiftTemplateTable.addView(newShiftTemplateRow);
        }
    }

    private void reloadShiftTemplatePage() {
        shiftTemplateTable.removeAllViews();
        setUpTableAndHeader();
        fetchShiftTemplates();
        populateShiftTemplateTable();
    }

    public void addShiftTemplateRecord(View view) {
        //TODO CONFIRMATION POPUP HERE
        EditText newShiftCodeTV = (EditText) findViewById(R.id.shift_template_add_code);
        EditText newShiftNameTV = (EditText) findViewById(R.id.shift_template_add_name);
        String newShiftTemplateID = "";
        String newShiftTemplateCode = newShiftCodeTV.getText().toString();
        String newShiftTemplateName = newShiftNameTV.getText().toString();
        newShiftTemplate = new ShiftTemplate();
        newShiftTemplate.setShiftCode(newShiftTemplateCode);
        newShiftTemplate.setShiftName(newShiftTemplateName);
        long newShiftTemplateIDLong = shiftDAO.addShiftTemplate(newShiftTemplate);
        newShiftTemplateID = Long.toString(newShiftTemplateIDLong);
        newShiftTemplate.setShiftID(newShiftTemplateID);
        /////////TESTING DYNAMICS CALL
        callShiftTemplateWSInBackground();
        //RELOAD PAGE
        newShiftCodeTV.setText("");
        newShiftNameTV.setText("");
        reloadShiftTemplatePage();
        Toast.makeText(ShiftTemplateManagementActivity.this, "Shift Template Successfully Added", Toast.LENGTH_LONG).show();
    }

    private void callShiftTemplateWSInBackground() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                ShiftWebServices shiftWebService = new ShiftWebServices();
                shiftTemplateWSSuccess = shiftWebService.createShiftTemplateViaWS(newShiftTemplate);
                Log.i("Result STWS:", "" + shiftTemplateWSSuccess);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                //todo closeoutactivity?
                //todo progress bar?

                //closeOutActivity();
            }
        }.execute();
    }

    private void deleteShiftTemplate(String shiftTemplateID) {
        shiftDAO = new ShiftDAO(this);
        shiftDAO.deleteShiftTemplate(shiftTemplateID);
        shiftDAO.close();
        reloadShiftTemplatePage();
    }

    private void confirmShiftTemplateDelete(final ShiftTemplate shiftTemplate) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Shift Template?")
                .setMessage("Delete Shift Template with Code: " + shiftTemplate.getShiftCode() + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String confirmationToastString = "Template " + shiftTemplate.getShiftCode() + " Deleted!";
                        deleteShiftTemplate(shiftTemplate.getShiftID());
                        Toast.makeText(ShiftTemplateManagementActivity.this, confirmationToastString, Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }


}
