package com.dhruv.attendancesheetgenerater;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    EditText et1, et2, et3;
    ScrollView sv1;
    Uri uri;
    Workbook workbook;
    DatePicker dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request the WRITE_EXTERNAL_STORAGE and READ_EXTERNAL_STORAGE permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        sv1 = findViewById(R.id.scrollView);
        // initialize the workbook
        workbook = new XSSFWorkbook();
    }

    public void submit(View view) {
        EditText et1 = findViewById(R.id.numOfStudentsEditText);
        int numOfStudents = 0;
        try {
            numOfStudents = Integer.parseInt(et1.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        // create and add radio buttons to the LinearLayout
        for (int i = 1; i <= numOfStudents; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText("RollNo " + i);
            linearLayout.addView(radioButton);
        }

        // clear any existing views in the ScrollView
        sv1.removeAllViews();

        // add the LinearLayout to the ScrollView
        sv1.addView(linearLayout);
    }

    public void save(View view) {
        saveAttendanceToExcel();
    }

    public void saveAttendanceToExcel() {
        dp = findViewById(R.id.date);
        et2 = findViewById(R.id.class_et);
        String class_name = et2.getText().toString();
        int day = dp.getDayOfMonth();
        int month = dp.getMonth() + 1;
        int year = dp.getYear();
        String dateString = String.format("%02d-%02d-%04d", day, month, year);
        // create a new sheet in the workbook
        Sheet sheet = workbook.createSheet("Attendance");

        // create a header row in the sheet
        Row headerRow = sheet.createRow(0);
        Cell headerCell = headerRow.createCell(0);
        headerCell.setCellValue("Student");
        headerCell = headerRow.createCell(1);
        headerCell.setCellValue("Attendance");
        headerCell = headerRow.createCell(2);
        headerCell.setCellValue(dateString);
        headerCell = headerRow.createCell(3);
        headerCell.setCellValue(class_name);

        // initialize the row number
        int rowNumber = 1;

        // recursively iterate over the LinearLayout and its child views
        traverseLinearLayout(sv1, sheet, rowNumber);

        // write the workbook to a file
        try {
            // create a file chooser dialog to allow the user to choose the save location
            Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
            intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            // intent.putExtra(Intent.EXTRA_TITLE, "attendance.xlsx"); //default file name
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: Excel app not found", Toast.LENGTH_SHORT).show();
        }
    }

    private String fileName;

    private void traverseLinearLayout(ViewGroup viewGroup, Sheet sheet, int rowNumber) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child instanceof RadioButton) {
                Log.d("Child view", "View " + i + " is a RadioButton");
                RadioButton radioButton = (RadioButton) child;
                Row attendanceRow = sheet.createRow(rowNumber++);
                Cell cell = attendanceRow.createCell(0);
                cell.setCellValue(radioButton.getText().toString());
                cell = attendanceRow.createCell(1);
                cell.setCellValue(radioButton.isChecked() ? "Present" : "Absent"); // convert "Present" to 1 and "Absent" to 0
            } else if (child instanceof ViewGroup) {
                traverseLinearLayout((ViewGroup) child, sheet, rowNumber);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                try {
                    // create a file output stream to write the workbook to a file
                    OutputStream outputStream = getContentResolver().openOutputStream(uri);

                    // write the workbook to the output stream
                    workbook.write(outputStream);

                    // close the output stream
                    outputStream.close();

                    // display a success message
                    Toast.makeText(this, "File saved successfully", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error: Failed to save file", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted
            } else {
                Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
