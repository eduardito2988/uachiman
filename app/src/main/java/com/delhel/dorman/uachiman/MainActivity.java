package com.delhel.dorman.uachiman;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.delhel.dorman.uachiman.Fragmentos.Fragment_principal;
import com.delhel.dorman.uachiman.Fragmentos.Fragment_principal_encargo;
import com.delhel.dorman.uachiman.Fragmentos.Salida_Invitado;
import com.delhel.dorman.uachiman.Fragmentos.Salida_encargo;
import com.delhel.dorman.uachiman.Fragmentos.Salida_operario;
import com.delhel.dorman.uachiman.Interface.Comunicacion;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , Comunicacion{


    private static final String TAG = "MainActivity";
    Class Framentclass = null;
    Fragment fragment = null;
    FragmentManager fragmentManager;
    public static int con = 0;

    private Salida_encargo bTabFragment;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /**carga fragment principal**/

        try {
            fragment = (Fragment) Fragment_principal.class.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.layou_cont, fragment).commit();

        /************************/

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        if (id == R.id.principal_penel) {
            Framentclass = Fragment_principal.class;
        } else if (id == R.id.nav_encargo) {
            Framentclass = Salida_encargo.class;
        } else if (id == R.id.nav_invitado) {
            Framentclass = Salida_Invitado.class;
        } else if (id == R.id.nav_profesion) {
            Framentclass = Salida_operario.class;
        } else if (id == R.id.nav_delivery) {
            //  Framentclass =Fragment_delivery.class;
        }

        try {
            fragment = (Fragment) Framentclass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.layou_cont, fragment).commit();
        item.setTitle(item.getTitle());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        drawer.closeDrawers();

        return true;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {

            con++;

            if (con == 1) {

                try {

                    fragment = (Fragment) Fragment_principal.class.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.right_in, R.anim.right_out)
                        .replace(R.id.layou_cont, fragment).commit();

            } else {

             /*   Mensaje mensaje = new Mensaje();
                mensaje.msga = "Desea salir de la aplicación?";
                mensaje.titulo = "Aviso Doorman";
                mensaje.show(getSupportFragmentManager(), "custom");*/

            }
        }

        return true;
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        //   Framentclass=Fragment_encargo.class;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //   savedInstanceState.putInt(STATE_SCORE, mCurrentScore);
        //  savedInstanceState.putString(STATE_NAME, mCurrentName);
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restore variables on screen orientation change. Restore state members from saved instance
        // mCurrentScore = savedInstanceState.getInt(STATE_SCORE);
        // mCurrentName = savedInstanceState.getString(STATE_NAME);
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.delhel.dorman.uachiman/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.delhel.dorman.uachiman/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    @Override
    public void refrescar() {

        //bTabFragment= Salida_encargo.newInstance(null,null);
        //bTabFragment.refrescarq();
        Fragment fragment= getEncargoMainFragment();
        if(fragment!=null){
            Fragment_principal_encargo fragment_principal_encargo =(Fragment_principal_encargo)(fragment);
            Log.v(TAG, " fragment_principal_encargo "+fragment_principal_encargo);
            fragment_principal_encargo.updateSalida();
        }

    }

    private Fragment getEncargoMainFragment()
    {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                {
                    if(fragment instanceof Fragment_principal_encargo){
                        return  fragment;
                    }
                }

            }
        }

        return null;
    }
}
