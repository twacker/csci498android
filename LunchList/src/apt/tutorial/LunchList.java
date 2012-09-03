package apt.tutorial;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.widget.*;
import java.util.*;

public class LunchList extends Activity {
	
	List<Restaurant> model = new ArrayList<Restaurant>();
	ArrayAdapter<Restaurant> adapter=null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_list);
        
        Button save = (Button) findViewById(R.id.save);
        Typeface face = Typeface.createFromAsset(getAssets(), "Chantelli_Antiqua.ttf");
        save.setTypeface(face);
        
		RadioGroup types = (RadioGroup) findViewById(R.id.types);
		setRadioButtons(types);
		
		Spinner list = (Spinner) findViewById(R.id.restaurants);
        
		adapter = new ArrayAdapter<Restaurant> (this, android.R.layout.simple_list_item_1, model);
		list.setAdapter(adapter);
		
        save.setOnClickListener(onSave);
    }

    private View.OnClickListener onSave = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Restaurant r = new Restaurant();
			EditText name = (EditText) findViewById(R.id.name);
			AutoCompleteTextView address = (AutoCompleteTextView) findViewById(R.id.addr);
			RadioGroup types = (RadioGroup) findViewById(R.id.types);
			RadioButton rb = (RadioButton) findViewById(types.getCheckedRadioButtonId());
			
			r.setName(name.getText().toString());
			r.setAddress(address.getText().toString());
			r.setType(rb.getText().toString());
			
			adapter.add(r);
			
			resetDropDownList(address);		
			
		}
	};
	
	public void resetDropDownList(AutoCompleteTextView addr){
		
		String[] addrs = getKnownAddresses();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, addrs);
		addr.setAdapter(adapter);
		
	}
	
	public void setRadioButtons( RadioGroup rg ){
		String[] types = {"Sit Down", "Fast Food", "Food Cart"};//, "Take Out", "Delivery", "Coffee Shop", "Desserts", "Bar", "Grill", "Vending Machine", "Grocery Store"};
		for(int i = 0; i < types.length; i ++){
			RadioButton rb = new RadioButton(this);
        	rb.setText(types[i]);		
        	rg.addView(rb);
		}
	}
	
	public String[] getKnownAddresses(){
		ArrayList<String> addrs = new ArrayList<String>();
		for(Restaurant r : model){
			if(!addrs.contains(r.getAddress())){
				addrs.add(r.getAddress());
			}
		}
		String[] addrs_array = new String[addrs.size()];
		for(int i = 0; i < addrs.size(); i ++){
			addrs_array[i] = addrs.get(i);
		}
		return addrs_array;
	}
}
