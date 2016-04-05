package xyz.milinjoshi.to_do;

import android.app.Activity;
import android.content.ClipData;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "to_do_list";

    ListView lv;
    ArrayList<Item_One> mainList;
    Custom_Adapter adapter;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);

        setSupportActionBar(myToolbar);

        //Initialization
        mainList = new ArrayList<Item_One>();

        //Keyboard hide code
        setupUI(findViewById(R.id.parent));

        // Restore preferences
        read_sharedPreferences();

        update_list();

        //New List View with Array Adapter
        //String[] myArray = {"Milin","Joshi","is","Awesome"};

        //Creating array adapter
        //ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, R.layout.single_list_item, myArray);
        //listView = (ListView) findViewById(R.id.tasks);

        //listView.setAdapter(myAdapter);

        /*
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        */
    }


    public void addItem(View view){

        String item_info = ((EditText)findViewById(R.id.add_item)).getText().toString();

        mainList.add(new Item_One(item_info, 0));

        update_list();

        ((EditText)findViewById(R.id.add_item)).setText("");

    }

    //Hiding Keyboard
    public static void hideSoftKeyboard(Activity activity) {

        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {

            view.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(MainActivity.this);
                    return false;
                }

            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {

            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {

                View innerView = ((ViewGroup) view).getChildAt(i);

                setupUI(innerView);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Handling UI button click
    public void delete_item(View view) {
        //get the row the clicked button is in

        View v = (View) view.getParent();

        LinearLayout ll = (LinearLayout)v;

        TextView child = (TextView)ll.getChildAt(1);

        //calling method
        removeItem(child.getText().toString());

    }
    //Updating mainList after deleting
    public void removeItem(String key){

        ArrayList<Item_One> newList = new ArrayList<Item_One>();

        for(Item_One entry : mainList){
            if(entry.getName().equals(key)){
                mainList.remove(entry);
            }
        }

        update_list();
    }
    public void onCheckboxClicked(View view){
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        LinearLayout ll = (LinearLayout) view.getParent();
        TextView tv = (TextView)ll.getChildAt(1);
        Log.v("Yo","Ho "+tv.getText().toString());

        if(checked){
            for(Item_One io : mainList){
                if(io.getName().equals(tv.getText().toString())){
                    io.setValue(1);
                }
            }
        }

    }
    public void read_sharedPreferences(){

        sharedPreferences = getSharedPreferences(PREFS_NAME, 0);

        Map<String, ?> allEntries = sharedPreferences.getAll();

        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            int value = Integer.parseInt(entry.getValue().toString());
            mainList.add(new Item_One(key, value));
        }
    }
    public void write_sharedPreferences(){

        sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        sharedPreferences.edit().clear().commit();

        sharedPreferences = getSharedPreferences(PREFS_NAME, 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        for(Item_One io : mainList){
            editor.putInt(io.getName(), io.getValue());
            Log.v(io.getName(),""+io.getValue());
        }

        editor.commit();
    }
    public void update_list(){

        lv = (ListView) findViewById(R.id.tasks);

        adapter = new Custom_Adapter(this, mainList);

        lv.setAdapter(adapter);

    }
    @Override
    protected void onStop(){
        super.onStop();

        write_sharedPreferences();

    }
}
