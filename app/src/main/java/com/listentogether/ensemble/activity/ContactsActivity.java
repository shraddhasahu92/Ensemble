package com.listentogether.ensemble.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Bundle bdl = getIntent().getExtras();
        String s = bdl.getString("users");

        JSONArray users = null;

        try {
            users = new JSONArray(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }




        final ListView listview = (ListView) findViewById(R.id.contacts_list);

        final ArrayList<String> nameList = new ArrayList<String>();
        final ArrayList<String> emailList = new ArrayList<String>();
        for (int i = 0; i < users.length(); ++i) {
            try {
                nameList.add(users.getJSONObject(i).getString("name"));
                emailList.add(users.getJSONObject(i).getString("email"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        final ContactsArrayAdapter adapter = new ContactsArrayAdapter(this.getBaseContext(),nameList,emailList);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                nameList.remove(item);
                                emailList.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
            }
        });
    }

    private class ContactsArrayAdapter extends ArrayAdapter<String> {

        private final ArrayList<String> names;
        private final ArrayList<String> emails;
        Context context;

        public ContactsArrayAdapter(Context context, ArrayList<String> names,ArrayList<String> emails) {
            super(context, -1, names);
            this.context = context;
            this.names = names;
            this.emails = emails;
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.contacts_list_item, parent, false);
            TextView textView1 = (TextView) rowView.findViewById(R.id.firstLine);
            TextView textView2 = (TextView) rowView.findViewById(R.id.secondLine);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.dp);
            textView1.setText(names.get(position));
            textView2.setText(emails.get(position));
            // change the icon for Windows and iPhone
//            String s = values[position];
            imageView.setImageResource(R.drawable.no_dp);

            return rowView;
        }
    }
}


