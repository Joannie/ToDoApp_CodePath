package test.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    Button saveBtn;
    EditText editTextfield;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        editTextfield = (EditText)findViewById(R.id.editTextfield);
        saveBtn = (Button)findViewById(R.id.saveBtn);
        saveItems();


        intent = getIntent();
        editTextfield.setText(intent.getStringExtra("items_for_update"));

    }

    public void saveItems(){
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("items_for_update", editTextfield.getText().toString());
                setResult(RESULT_OK, i);
                finish();
            }
        });
    }
}
