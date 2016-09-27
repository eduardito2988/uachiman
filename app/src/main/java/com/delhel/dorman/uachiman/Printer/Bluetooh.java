package com.delhel.dorman.uachiman.Printer;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.delhel.dorman.uachiman.R;
import com.zj.btsdk.BluetoothService;

/**
 * Created by Usuario on 15/09/2016.
 */


public class Bluetooh extends Activity {


    Context context;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    public BluetoothService mService = null;
    BluetoothAdapter mBluetoothAdapter;



    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BluetoothService.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:
                            Toast.makeText(context,
                                    R.string.bluetooth_state_connected,
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case BluetoothService.STATE_CONNECTING:
                            Toast.makeText(context,
                                    R.string.bluetooth_state_connecting,
                                    Toast.LENGTH_SHORT).show();
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:
                    /*
                     * Toast.makeText(getApplicationContext(),
					 * R.string.bluetooth_listen, Toast.LENGTH_SHORT) .show();
					 */
                            break;
                    } // --end.switch
                    break;
                case BluetoothService.MESSAGE_CONNECTION_LOST:
                    Toast.makeText(context,
                            R.string.bluetooth_state_disconnected,
                            Toast.LENGTH_SHORT).show();
                    break;
                case BluetoothService.MESSAGE_UNABLE_CONNECT:
                    Toast.makeText(context,
                            R.string.bluetooth_connect_unable, Toast.LENGTH_SHORT)
                            .show();
                    break;
            } // --end.switch
        }

    };


    public Bluetooh(Context context) {
        this.context = context;
    }


    public boolean conectarBluetooth() {

        // Comprobar si el bluetooth esta disponible en el equipo
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(context, context.getString(R.string.bluetooth_available_no),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        // Si no esta activo, encendemos bluetooth
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
        // Bluetooth-lib iniciar

        mService = new BluetoothService(context, mHandler);

        if (mService.isAvailable()) {
            if (mService.isBTopen()) {
                return true;
            }
        }

        return false;
    }

    public BluetoothService rmService(){
        return mService;
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode you started it with,
     * the resultCode it returned, and any additional data from it.
     *
     * @param requestCode The request code originally supplied to startActivityForResult(),
     *                    allowing you to identify who this result came from.
     * @param resultCode  The integer result code returned by the child activity through its setResult().
     */
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(
                            context,
                            ConstantesPrinter.TAG + context.getString(R.string.bluetooth_open_successful)
                                    + ConstantesPrinter.printer_name, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context,
                            ConstantesPrinter.TAG + context.getString(R.string.bluetooth_available_no),
                            Toast.LENGTH_LONG).show();
                }
                break;
            case REQUEST_CONNECT_DEVICE:
                Toast.makeText(context,
                        ConstantesPrinter.TAG + context.getString(R.string.bluetooth_available_no),
                        Toast.LENGTH_LONG).show();
                break;
        }
    }
}
