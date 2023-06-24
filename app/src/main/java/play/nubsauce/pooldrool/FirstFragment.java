package play.nubsauce.pooldrool;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nubsauce.droolpool.R;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {



    Button btnStart, btnPause, btnLap, btnReset;
    TextView txtTimer;
    TextView lapTimer;
    Handler customHandler = new Handler();
    LinearLayout container;

    Animation scaleUp, scaleDown;



    long lapTime = 0L, startTime =0L, timeInMilliseconds=0L, timeSwapBuff = 0L, updateTime=0L;


    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis()-startTime;
            updateTime = timeSwapBuff +timeInMilliseconds;
             int secs = (int) (updateTime/1000);
             int mins = secs/60;
            secs%=60;
             int milliseconds = (int) (updateTime%1000);
            txtTimer.setText(""+ mins +":"+String.format("%02d",secs)+":"
                    +String.format("%03d", milliseconds) );
            customHandler.postDelayed(this,0);
        }
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FirstFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FirstFragment newInstance(String param1, String param2) {
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);




        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public void onPrepareOptionsMenu(@NonNull @NotNull Menu menu) {
        menu.findItem(R.id.add_note).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        final Button btnStart = (Button) view.findViewById(R.id.bthStart);
        btnPause = (Button) view.findViewById(R.id.bthPause);
        btnLap = (Button) view.findViewById(R.id.btnLap);
        btnReset = (Button) view.findViewById(R.id.btnReset);
        txtTimer = (TextView) view.findViewById(R.id.timerValue);

        container = (LinearLayout) view.findViewById(R.id.container);

        scaleUp = AnimationUtils.loadAnimation(getActivity(),R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(getActivity(),R.anim.scale_down);

        btnStart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    btnStart.startAnimation(scaleUp);
                }else if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    btnStart.startAnimation(scaleDown);
                }
                return false;
            }
        });

        btnPause.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    btnPause.startAnimation(scaleUp);
                }else if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    btnPause.startAnimation(scaleDown);
                }
                return false;
            }
        });

        btnLap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    btnLap.startAnimation(scaleUp);
                }else if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    btnLap.startAnimation(scaleDown);
                }
                return false;
            }
        });

        btnReset.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    btnReset.startAnimation(scaleUp);
                }else if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    btnReset.startAnimation(scaleDown);
                }
                return false;
            }
        });



        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread,0);
                btnStart.setEnabled(false);
                btnPause.setEnabled(true);

            }
        });
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeSwapBuff+=timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
                btnPause.setEnabled(false);
                btnStart.setEnabled(true);
            }
        });
        ViewGroup finalContainer = container;
        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView = inflater.inflate(R.layout.row,null);
                TextView txtValue = (TextView)addView.findViewById(R.id.txtContent);
                txtValue.setText(txtTimer.getText());
                finalContainer.addView(addView);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customHandler.postDelayed(updateTimerThread,0);
                startTime =0L;
                timeInMilliseconds=0L;
                timeSwapBuff = 0L;
                updateTime=0L;
                customHandler.removeCallbacks(updateTimerThread);
                int mins =0;
                int secs =0;
                int milliseconds =0;
                txtTimer.setText(""+ mins +":"+String.format("%02d",secs)+":"
                        +String.format("%03d", milliseconds) );
                finalContainer.removeAllViews();
                btnStart.setEnabled(true);


            }
        });



        return  view;
    }





    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    };

}

