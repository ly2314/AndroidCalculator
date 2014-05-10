package com.ly2314.calculator;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
	
	private double _memory = 0;
	private double _operated = 0;
	private boolean _first_operator = true;
	private boolean _new_number = true;
	private String _last_operation = "";
	private boolean _eq_from_eq = false;
	private boolean _was_eq = false;
	private boolean _op_from_eq = false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }
    
    public void plusminusSwitch(View view)
    {
    	String shownum = PlaceholderFragment._textView.getText().toString();
    	if (shownum.equals("0"))
    		return;
    	StringBuilder sb = new StringBuilder(shownum);
    	if (shownum.contains("-"))
    	{
    		sb.deleteCharAt(shownum.indexOf("-"));
    		shownum = sb.toString();
    	}
    	else
    	{
    		shownum = "-" + shownum;
    	}
    	PlaceholderFragment._textView.setText(shownum);
    }

    public void numberPressed(View view)
    {
    	Button b = (Button) view;
    	String str = b.getText().toString();
    	String shownum = PlaceholderFragment._textView.getText().toString();
    	if (_new_number)
    	{
    		_new_number = false;
    		shownum = "";
    	}
    	if (str.equals(".") && shownum.contains("."))
    	{    		
    		return;
    	}
    	if (str.equals("0") && shownum.equals("0"))
    	{
    		return;
    	}
    	if (shownum.equals("0") && !str.equals("."))
    	{
    		shownum = "";
    	}
    	if (shownum.equals("") && str.equals("."))
    	{
    		shownum = "0";
    	}
    	PlaceholderFragment._textView.setText(shownum + str);
    }
    
    public void operationClick(View view)
    {
    	_new_number = true;
    	Button b = (Button) view;
    	String str = b.getText().toString();
    	if (_first_operator && !_eq_from_eq && !_was_eq && !_op_from_eq)
    	{
    		_first_operator = false;
    		_memory = Double.parseDouble(PlaceholderFragment._textView.getText().toString());
        	_last_operation = str;
    		return;
    	}
    	if (str.equals("="))
    	{
    		if (_was_eq)
        		_eq_from_eq = true;
    		_first_operator = true;
    		_was_eq = true;
    		_op_from_eq = false;
    	}
    	else
    	{
    		if (_was_eq)
    			_op_from_eq = true;
    		_eq_from_eq = false;
    		_was_eq = false;
        	_last_operation = str;
    	}
    	if (!_eq_from_eq)
    		_operated = Double.parseDouble(PlaceholderFragment._textView.getText().toString());
    	double result = 0;
    	if (!_op_from_eq)
    	{
			if (_last_operation.equals("+"))
			{
	    		result = _memory + _operated;
			}
			else if (_last_operation.equals("-"))
			{
				result = _memory - _operated;
			}
			else if (_last_operation.equals("*"))
			{
				result = _memory * _operated;
			}
			else if (_last_operation.equals("/"))
			{
				result = _memory / _operated;
			}
			_memory = result;
    	}
		PlaceholderFragment._textView.setText(String.valueOf(_memory));
    }
    
    public void clearClick(View view)
    {
    	_memory = 0;
    	_operated = 0;
    	_first_operator = true;
    	_new_number = true;
    	_last_operation = "";
    	_eq_from_eq = false;
    	_was_eq = false;
    	_op_from_eq = false;
    	PlaceholderFragment._textView.setText("0");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

    	public static EditText _textView;
    	
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            _textView = (EditText) rootView.findViewById(R.id.textView1);
            return rootView;
        }
    }

}
