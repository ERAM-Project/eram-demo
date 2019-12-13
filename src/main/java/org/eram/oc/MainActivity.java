package org.eram.oc;
import org.eram.R;
import org.eram.apps.NQueens;
import org.eram.common.Clone;
import org.eram.common.Utils;
import org.eram.common.settings.ConnectionSettings;
import org.eram.oc.communication.ERAMNetworkService;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private ERAM eram;
    private String EDGE_SERVER_IP_ADRESS ="3.133.135.242";
    private Handler handler;
    private String PACKAGE_NAME="";
    private TextView timeZone;


    private Context iContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.timeZone = findViewById(R.id.timeZone);


        this.PACKAGE_NAME = getPackageName();
        this.iContext = this;
        initOffloadingFramework();

    }
    public void startDemo(View view)
    {

        new NQueensTask().execute();
    }

    private void initOffloadingFramework()
    {
        ConnectionSettings.ConnectionType commType = ConnectionSettings.ConnectionType.CLEAR;
        // The handler for
        handler = new Handler();
        ERAMNetworkService.usePrevVm = false;
        Utils.validateIpAddress(EDGE_SERVER_IP_ADRESS);

        Clone clone = new Clone(EDGE_SERVER_IP_ADRESS);

        eram = ERAM.getInstance(getPackageName(), this, getPackageManager(), this, clone);

    }

    public class NQueensTask extends AsyncTask<Void, Void, Integer> {
        int nrQueens;
        String lastTask ="";

        // Show a spinning dialog while solving the puzzle
        ProgressDialog pd = ProgressDialog.show(MainActivity.this, "Working...", "Solving N Queens...", true, false);

        NQueensTask() {
            this.nrQueens = 8;
        }

        public NQueensTask(int nrQueens) {
            this.nrQueens = nrQueens;
            // Show a spinning dialog while solving the puzzle
            pd = ProgressDialog.show(MainActivity.this, "Working33...", "Solving N Queens...", true, false);
        }

        @Override
        protected Integer doInBackground(Void... params) {

            NQueens puzzle = new NQueens(nrQueens);

            Object result = eram.execute(puzzle.getApp());
            lastTask = puzzle.lastTask();
            Log.e("JAJOJI", result.getClass().toString());

            if(result instanceof Integer)
                return (Integer)result;
            return 1771389;
        }

        @Override
        protected void onPostExecute(Integer result) {

            if (pd != null) {
                pd.dismiss();
            }

            Toast.makeText(iContext, nrQueens + "-Queens solved, solutions: " + result,
                    Toast.LENGTH_SHORT).show();
            timeZone.setText("FINISHED: "+eram.getTime(getPackageName())+" ms");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
