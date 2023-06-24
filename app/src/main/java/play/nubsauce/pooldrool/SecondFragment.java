package play.nubsauce.pooldrool;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nubsauce.droolpool.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {


   static ArrayList<String> notes = new ArrayList<>();
   static ArrayAdapter arrayAdapter;





    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {



        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public void onPrepareOptionsMenu(@NonNull @NotNull Menu menu) {
        menu.findItem(R.id.exit).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_second, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listView);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("play.nubsauce.pooldrool", Context.MODE_PRIVATE);

        HashSet<String> set =(HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if ( set == null){

            notes.add("This is an example workout. Click on NEW ENTRY then tap screen to add desired routine. Hold down entry to delete.");

        }else{

            notes = new ArrayList(set);
        }



        arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, notes);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getContext(), NoteEditorActivity.class);
                intent.putExtra("noteId", position);

                startActivity(intent);
            }


        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(position);
                                arrayAdapter.notifyDataSetChanged();

                                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("play.nubsauce.pooldrool", Context.MODE_PRIVATE);

                                HashSet<String> set = new HashSet(SecondFragment.notes);

                                sharedPreferences.edit().putStringSet("notes", set).apply();



                            }
                        }
                        )
                        .setNegativeButton("No", null)
                        .show();

                return true;

            }
        });






        return view;
    }







}
