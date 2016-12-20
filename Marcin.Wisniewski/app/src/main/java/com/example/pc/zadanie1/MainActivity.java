package com.example.pc.zadanie1;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText adultsField;
    private EditText kidsField;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void onStart(){

        super.onStart();

        adultsField = (EditText)findViewById(R.id.adultsQuantityTextField);
        kidsField = (EditText)findViewById(R.id.kidsQuantityTextField);

        EditText flightDateField = (EditText)findViewById(R.id.flightDateTextField);
        EditText arrivalDateField = (EditText)findViewById(R.id.arrivalDateTextField);

        setDatePicker(flightDateField);
        setDatePicker(arrivalDateField);

        EditText searchFlightField = (EditText)findViewById(R.id.flightTextField);
        EditText searchArrivalField = (EditText)findViewById(R.id.arrivalTextField);

        setSearchFieldEmpty(searchFlightField);
        setSearchFieldEmpty(searchArrivalField);

        Button increaseAdults = (Button) findViewById(R.id.increaseAdultsButton);
        Button increaseKids = (Button) findViewById(R.id.increaseKidsButton);
        Button decreaseAdults = (Button) findViewById(R.id.decreaseAdultsButton);
        Button decreaseKids = (Button) findViewById(R.id.decreaseKidsButton);

        changePassengersTextFieldValue(increaseAdults);
        changePassengersTextFieldValue(decreaseAdults);
        changePassengersTextFieldValue(increaseKids);
        changePassengersTextFieldValue(decreaseKids);
    }

    private void setDatePicker(EditText dateField){

        dateField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    DateDialog chosenDate = new DateDialog(v);
                    FragmentTransaction transferDate = getFragmentManager().beginTransaction();

                    chosenDate.show(transferDate, "DatePicker");
                }
            }
        });
    }
    private void changePassengersTextFieldValue(Button addOrDeletePassenger){

        addOrDeletePassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch(v.getId()){

                    case R.id.increaseAdultsButton:

                        updateTextField(adultsField,'I');
                        break;
                    case R.id.increaseKidsButton:

                        updateTextField(kidsField,'I');
                        break;
                    case R.id.decreaseAdultsButton:

                        updateTextField(adultsField,'D');
                        break;

                    case R.id.decreaseKidsButton:

                        updateTextField(kidsField,'D');
                        break;
                }
            }
        });

    }
    private void updateTextField(EditText passengerTextField,char operation){

        if(operation=='I'){

            int passengersValue = Integer.parseInt(passengerTextField.getText().toString()) + 1;
            passengerTextField.setText(String.valueOf(passengersValue));
        }
        else{

            int passengersValue = Integer.parseInt(passengerTextField.getText().toString()) - 1;

            if(passengersValue<0){
            passengerTextField.setText("0");
            }

            else{
                passengerTextField.setText(String.valueOf(passengersValue));
            }
        }
    }
    private void setSearchFieldEmpty(final EditText searchCityField){
        searchCityField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchCityField.setText("");
            }
        }
        );
    }

}