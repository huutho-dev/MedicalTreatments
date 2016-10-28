package com.edu.gvn.medicaltreatments.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.edu.gvn.medicaltreatments.R;
import com.edu.gvn.medicaltreatments.common.LogUtils;
import com.edu.gvn.medicaltreatments.common.ReferencesUtils;
import com.edu.gvn.medicaltreatments.db.MedicinesDbManager;
import com.edu.gvn.medicaltreatments.fragment.FindLocationFragment;
import com.edu.gvn.medicaltreatments.service.UpDateLocationService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {

    private MedicinesDbManager dbManager;
    public static UpDateLocationService mUpdateLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbManager = new MedicinesDbManager(this);
        CoppyFile coppyFile = new CoppyFile(this);
        coppyFile.createDataBase();


        Intent intentService = new Intent(this, UpDateLocationService.class);
        bindService(intentService, serviceConn, Context.BIND_AUTO_CREATE);
        startService(new Intent(this, UpDateLocationService.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConn);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
        fm.popBackStack();
    }

    public void replaceFragment(int idLayout, Fragment fragment) {
        String tag = fragment.getClass().getSimpleName();
        String backStackName = tag;
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        boolean popped = fm.popBackStackImmediate(backStackName, 0);
        if (!popped && fm.findFragmentByTag(tag) == null) {
            ft.replace(idLayout, fragment, tag);
            ft.commit();
        }
    }

    public MedicinesDbManager getDbManager() {
        return dbManager;
    }

    private class CoppyFile {
        AsyncTaskLoadData asyncTaskLoadData;
        private final Context mContext;

        private CoppyFile(Context paramContext) {
            this.mContext = paramContext;
        }

        private void createDataBase() {
            boolean dbExist = checkDataMedicalTreatmentDB();
            if (!dbExist) {
                asyncTaskLoadData = new AsyncTaskLoadData(mContext);
                asyncTaskLoadData.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                replaceFragment(R.id.container, new FindLocationFragment());
            } else {
                replaceFragment(R.id.container, new FindLocationFragment());
            }
        }


        private boolean checkDataMedicalTreatmentDB() {
            if ("".equals(ReferencesUtils.getInstance(MainActivity.this).getPartMedical())) {
                return false;
            } else {
                File dbFile = new File(ReferencesUtils.getInstance(MainActivity.this).getPartMedical());
                return dbFile.exists();
            }
        }

        private class AsyncTaskLoadData extends AsyncTask<Void, Integer, Integer> {
            Context context;
            private int count;
            private int error;

            public AsyncTaskLoadData(Context context) {
                this.context = context;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Integer doInBackground(Void... params) {
                copyAssets();
                return count;
            }

            private void copyAssets() {
                InputStream in = null;
                OutputStream out = null;

                //Database 2
                try {
                    in = getAssets().open(MedicinesDbManager.DB_NAME);
                    File outFile = new File(getFilesDir(), MedicinesDbManager.DB_NAME);
                    out = new FileOutputStream(outFile);
                    copyFile(in, out);
                    ReferencesUtils.getInstance(MainActivity.this).savePartMedical(outFile.getPath());
                } catch (IOException e) {
                    LogUtils.e("tag", e.getMessage());
                }
            }

            private void copyFile(InputStream in, OutputStream out) throws IOException {
                byte[] buffer = new byte[1024];
                int read;
                int alreadyCopied = 0;
                count++;
                float size = in.available();
                while ((read = in.read(buffer)) != -1) {
                    alreadyCopied += read;
                    out.write(buffer, 0, read);
                    publishProgress((int) ((alreadyCopied / size) * 100));
                }

                in.close();
                out.flush();
                out.close();
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                if (integer == 2) {
                    LogUtils.d("namIT", "onPostExecute");
                } else {
                    deleteCache(context);
                }
            }
        }

        private void deleteCache(Context context) {
            try {
                File dir = context.getCacheDir();
                deleteDirectory(dir);
            } catch (Exception e) {
            }
        }

        private boolean deleteDirectory(File path) {
            if (path.exists()) {
                File[] files = path.listFiles();
                for (int i = 0; i < files.length; i++) {
                    if (files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    } else {
                        files[i].delete();
                    }
                }
            }
            return (path.delete());
        }
    }

    private ServiceConnection serviceConn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mUpdateLocationService = ((UpDateLocationService.LocalBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


}
