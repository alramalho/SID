import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Mensagem {

	private String id;
	private double tmp, hum;
	private double cell;
	private String dat;
	private String tim;

	private String sens;
	private String data;

	private String dados;

	public Mensagem(String id, double tmp, double hum, double d, String dat, String tim, String sens) {
		super();
		this.id = id;
		this.tmp = tmp;
		this.hum = hum;
		this.cell = d;
		this.dat = dat;
		this.tim = tim;
		this.data = (invert(dat) + " " + tim);
		this.sens = sens;
	}

	public Mensagem(String dados) {
		this.dados = dados;
		try {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

			dados = dados.replace("{", "");
			dados = dados.replace("}", "");

			String[] aux;
			aux = dados.split(",");

			//ss
			
			tmp = Double.parseDouble(aux[1]);
			hum = Double.parseDouble(aux[2]);
			
			String[] time = aux[4].split(":");
			data = aux[3] + " " + time[0] + ":" + time[1] + ":" + time[2];
			
			cell = Integer.parseInt(aux[5]);
			sens = aux[6];
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

	public double getCell() {
		return cell;
	}

	public void setCell(int cell) {
		this.cell = cell;
	}

	public String getData() {
		return this.data;
	}

	public String getDat() {
		return dat;
	}

	public String getTim() {
		return tim;
	}

	public String getID() {
		return id;
	}
}
