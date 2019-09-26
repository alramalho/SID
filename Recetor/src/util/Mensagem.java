package util;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Mensagem {

	private String id;
	private double tmp, hum;
	private int cell;
	private String dat;
	private String tim;

	private String sens;
	private String data;

	private String dados;

	public Mensagem(String id, double tmp, double hum, int cell, String dat, String tim, String sens) {
		super();
		this.id = id;
		this.tmp = tmp;
		this.hum = hum;
		this.cell = cell;
		this.dat = getCurrentData();
		this.tim = getCurrentTime();
		this.data = invert(dat + " " + tim);
		this.sens = sens;
	}

	public static void main(String[] args) {
		String dados = "{\"tmp\":\"22.40\",\"hum\":\"61.30\",\"dat\":\"9/4/2019\",\"tim\":\"14:59:32\",\"cell\":\"3138\"\"sens\":\"wifi\"}";
		new Mensagem(dados);
	}

	public Mensagem(String dados) {
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			if (dados.contains("tmp") || dados.contains("cell")) {
				
				if(!dados.contains("tmp")) {
					dados = dados.replace("{", "");
					dados = "{\"tmp\":\"-1000\"," + dados;				
				}
				String[] aux;
				aux = dados.split(",");

				String data = "\"" + getCurrentData() + "\"";

				String dat = aux[2].split(":")[0];
				aux[2] = dat + ":" + data;
				
				String time = "\"" + getCurrentTime() + "\"";
				String tim = aux[3].split(":")[0];
				aux[3] = tim + ":" + time;

				String finalString = "";
				if(!dados.contains("cell")){
					aux[4] = "\"cell\":\"-1000\"" + aux[4];
				}
				for (int i = 0; i < aux.length; i++) {
					if (i < aux.length - 1) {
						finalString = finalString + aux[i] + ",";
					} else {
						finalString = finalString + aux[i];
					}
				}
				this.dados = finalString;
				System.out.println(finalString);
			}
			// ss
			/*
			 * tmp = Double.parseDouble(aux[1].split("=")[1]); hum =
			 * Double.parseDouble(aux[2].split("=")[1]);
			 * 
			 * String[] time = aux[4].split("=")[1].split(":"); data = aux[3].split("=")[1]
			 * + " " + time[0] + ":" + time[1] + ":" + time[2];
			 * 
			 * cell = Integer.parseInt(aux[5].split("=")[1]); sens = aux[6].split("=")[1];
			 */
		} catch (NumberFormatException e) {
			System.out.println("MENSAGEM INVALIDA");
		}
	}

	public String invert(String x) {
		String[] aux = x.split("/");
		String s = aux[2] + "-" + aux[1] + "-" + aux[0];
		return s;
	}

	public double getTmp() {
		return tmp;
	}

	public void setTmp(double tmp) {
		this.tmp = tmp;
	}

	public int getCell() {
		return cell;
	}

	public void setCell(int cell) {
		this.cell = cell;
	}

	public String getData() {
		return this.data.replace('/', '-');
	}

	public String getDat() {
		return dat;
	}

	public String getCurrentTime() {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		String formattedDate = dateFormat.format(date);
		return formattedDate;
	}

	public String getCurrentData() {
		String pattern = "dd/MM/yyyy";
		String data = new SimpleDateFormat(pattern).format(new Date());
		return data;
	}

	public String getTim() {
		return tim;
	}

	public String getID() {
		return id;
	}

	public String getDados() {
		return dados;
	}
}
