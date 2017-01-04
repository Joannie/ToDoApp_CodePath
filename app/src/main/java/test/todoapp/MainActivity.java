package test.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView listView;
    Button addBtn;
    TextView addItemTextfield;
    int index;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readItem();

        //Register the UI elements
        listView = (ListView) findViewById(R.id.lvItems);
        itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        addBtn = (Button)findViewById(R.id.btn_AddItem);
        addItemTextfield = (TextView)findViewById(R.id.editText_AddItem);

        //Call to setup the list view listener
        setupListViewListener();
        AddItems();

    }

    private void AddItems(){
        //Register the addBtn behavior
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.add(addItemTextfield.getText().toString());
                addItemTextfield.setText("");
                itemsAdapter.notifyDataSetChanged();
                writeItem();
            }
        });

    }

    //method to setup ListViewListener
    private void setupListViewListener(){
        //set when the item is presssed logner, should delete it
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                writeItem();
                return true;
            }
        });

        //set when the item clicked, should go to the Edit Activity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditItemActivity.class);
                intent.putExtra("items_for_update", items.get(position));
                index = position;
                startActivityForResult(intent, REQUEST_CODE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            String update_item = data.getExtras().getString("items_for_update");
            items.set(index, update_item);
            itemsAdapter.notifyDataSetChanged();
            writeItem();
            //Toast.makeText(this, update_item, Toast.LENGTH_SHORT).show();
        }

    }

    //ReadItem every time
    private void readItem(){
        File fileDir = getFilesDir();
        File toDoFile = new File(fileDir, "todolist.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(toDoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    //write and store the changed items
    private void writeItem(){
        File fileDir = getFilesDir();
        File toDoFile = new File(fileDir, "todolist.txt");
        try {
            FileUtils.writeLines(toDoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
