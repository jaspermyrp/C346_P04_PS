package sg.edu.rp.c346.reservation;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView tvHeader;
    Button btnClear, btnSubmit;
    DatePicker dp;
    TimePicker tp;
    CheckBox cbSmokingArea;
    EditText etName, etPax, etMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind UI Components //
        tvHeader = findViewById(R.id.header);
        btnClear = findViewById(R.id.reset);
        btnSubmit = findViewById(R.id.submit);
        dp = findViewById(R.id.datePicker);
        tp = findViewById(R.id.timePicker);
        cbSmokingArea = findViewById(R.id.smokingIn);
        etName = findViewById(R.id.nameInput);
        etPax = findViewById(R.id.paxIn);
        etMobile = findViewById(R.id.mobileIn);

        setDefaultDateTime();

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAllFields();
                tvHeader.setText("Enter your Reservation Information");
                setDefaultDateTime();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callReservation();
            }
        });

        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if (hourOfDay < 8) {
                    tp.setCurrentHour(8);
                }

                if (hourOfDay > 20) {
                    tp.setCurrentHour(20);
                }
            }
        });

    }

    public void setDefaultDateTime() {
        dp.updateDate(2019, 5, 1);
        dp.setMinDate(System.currentTimeMillis());
        tp.setCurrentHour(19);
        tp.setCurrentMinute(30);
    }

    public void clearAllFields() {
        tvHeader.setText("");
        etName.setText("");
        etPax.setText("");
        etMobile.setText("");
        cbSmokingArea.setChecked(false);
    }

    public void callReservation() {
        String name = etName.getText().toString();
        String pax = etPax.getText().toString();
        String mobile = etMobile.getText().toString();
        String smoking;

        if (name.isEmpty() || pax.isEmpty() || mobile.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
        } else {
            int paxNo = Integer.parseInt(pax);

            if (paxNo < 1 || mobile.length() != 8) {
                String errorMsg = "Invalid Pax or Mobile number";
                Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            } else {
                // Reserve //

                // Check checkbox selection //
                if (cbSmokingArea.isChecked()) {
                    smoking = "Smoking Area";
                } else {
                    smoking = "Non-smoking Area";
                }

                // Check dates //
                String date = dp.getDayOfMonth() + "/" + (dp.getMonth() + 1) + "/" + dp.getYear();
                String time = tp.getCurrentHour() + ":" + tp.getCurrentMinute();

                String reservationMsg = String.format("Reservation successful\nName:%s, Pax:%s, Mobile:%s, \n%s \non %s %s ", name, pax, mobile, smoking, date, time);
                Toast.makeText(MainActivity.this, reservationMsg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
