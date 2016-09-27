package com.delhel.dorman.uachiman.Printer;

import android.annotation.SuppressLint;
import android.content.Context;

import com.delhel.dorman.uachiman.R;

import java.util.HashMap;
import java.util.Map;


public enum TypeTicket {
	DELIVERY(4, (R.string.delivery_voucher_title)), ENCARGO(8,
			(R.string.encargo_voucher_title)), SERVICIOS_PROFESIONALES(
			2, 
			(R.string.profesional_voucher_title)), PROVEEDOR(
			3, 
			(R.string.proveedor_voucher_title)), CORRETAJE(5,
					(R.string.corretaje_voucher_title)), REMODELACION(7,
							(R.string.remodelacion_voucher_title)), INVITADO(1,
									(R.string.invitado_voucher_title));

	private String title;
	private int code;
	private Context mContext = null;
	private static Map<Integer, TypeTicket> codeToTypeTicketMapping;

	public void setConText(Context context){
		this.mContext = context;
	}
	
	@SuppressLint("UseSparseArrays")
	private static void initMapping() {
		codeToTypeTicketMapping = new HashMap<Integer, TypeTicket>();
		for (TypeTicket s : values()) {
			codeToTypeTicketMapping.put(s.code, s);
		}
	}

	TypeTicket(int code, String title) {
		this.code = code;
		this.title = title;
	}

	TypeTicket(int code, int title) {
		this.code = code;
		this.title = String.valueOf(title);
	}
	
	TypeTicket(String title) {
		this.title = title;
	}

	public String getTitle() {
		return mContext== null ? title : mContext.getString( Integer.parseInt(title) );
	}


	public String getTitle(Context context) {
		return context.getString( Integer.parseInt(title) );
	}
	
	public int getCode() {
		return code;
	}

	/**
	 * Identificar el tipo de ticket a imprimir
	 * 
	 * @param type
	 * @return
	 */
	public static TypeTicket getTypeNumber(int type) {
		TypeTicket tt;
		switch (type) {
		case 1:
			tt = TypeTicket.INVITADO;
			break;
		case 2:
			tt = TypeTicket.SERVICIOS_PROFESIONALES;
			break;
		case 3:
			tt = TypeTicket.PROVEEDOR; // TECNICOS
			break;
		case 4:
			tt = TypeTicket.DELIVERY;
			break;
		case 5:
			tt = TypeTicket.CORRETAJE;
			break;
		case 6:
		case 7:
			tt = TypeTicket.REMODELACION;
			break;
		case 8:
			tt = TypeTicket.ENCARGO;
			break;
		case 9:
		case 10:
		default:
			tt = TypeTicket.INVITADO;
			break;
		}
		return tt;
	}
}
