package apt.tutorial;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioButton;

public class LunchList extends Activity {
	
	Restaurant r = new Restaurant();
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_list);
        
        Button save = (Button) findViewById(R.id.save);
        Typeface face = Typeface.createFromAsset(getAssets(), "Chantelli_Antiqua.ttf");
        save.setTypeface(face);
        
		RadioGroup types = (RadioGroup) findViewById(R.id.types);
		setRadioButtons(types);
        
        save.setOnClickListener(onSave);
    }

    private View.OnClickListener onSave = new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			EditText name = (EditText) findViewById(R.id.name);
			EditText address = (EditText) findViewById(R.id.addr);
			RadioGroup types = (RadioGroup) findViewById(R.id.types);
			RadioButton rb = (RadioButton) findViewById(types.getCheckedRadioButtonId());
			
			r.setName(name.getText().toString());
			r.setAddress(address.getText().toString());
			r.setType(rb.getText().toString());
			
		}
	};
	
	public void setRadioButtons( RadioGroup rg ){
		String[] types = {"Sit Down", "Fast Food", "Food Cart", "Take Out", "Delivery", "Coffie Shop", "Desserts", "Bar", "Grill", "Vending Machine", "Grocery Store"};
		for(int i = 0; i < types.length; i ++){
			RadioButton rb = new RadioButton(this);
        	rb.setText(types[i]);		
        	rg.addView(rb);
		}
	}
}
