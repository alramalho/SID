import java.util.ArrayList;

import org.apache.commons.collections4.queue.CircularFifoQueue;

public class Analyser {

	private static int LIMITE_MENSAGENS_DUBIAS = 3;

	private SQL db;

	private int Limite_Superior_Temp;
	private int Limite_Inferior_Temp;
	private int Limite_Superior_Lum;
	private int Limite_Inferior_Lum;

	private int margem_temp;
	private int margem_lum;

	private static final int BAIXOU_LIMITE_TEMP = -2;
	private static final int APROXIMOU_LIMITE_INFERIOR_TEMP = -1;
	private static final int NORMAL_TEMP = 0;
	private static final int APROXIMOU_LIMITE_SUPERIOR_TEMP = 1;
	private static final int EXCEDEU_LIMITE_TEMP = 2;

	private static final int BAIXOU_LIMITE_LUM = -2;
	private static final int APROXIMOU_LIMITE_INFERIOR_LUM = -1;
	private static final int NORMAL_LUM = 0;
	private static final int APROXIMOU_LIMITE_SUPERIOR_LUM = 1;
	private static final int EXCEDEU_LIMITE_LUM = 2;

	private boolean perigoTemperatura;
	private boolean perigoLuminosidade;


	private boolean lockBaixouTemp;
	private boolean lockAproximouTempInf;
	private boolean lockExcedeuTemp;
	private boolean lockAproximouTempSup;

	private boolean lockBaixouLum;
	private boolean lockAproximouLumInf;
	private boolean lockExcedeuLum;
	private boolean lockAproximouLumSup;


	private int flag = 0;

	private ArrayList<Mensagem> list = new ArrayList<Mensagem>();
	private String ErrorMessage = "No Error";

	private CircularFifoQueue<Boolean> aux1 = new CircularFifoQueue<>(LIMITE_MENSAGENS_DUBIAS);
	private CircularFifoQueue<Boolean> aux2 = new CircularFifoQueue<>(LIMITE_MENSAGENS_DUBIAS);
	private CircularFifoQueue<Boolean> aux3 = new CircularFifoQueue<>(LIMITE_MENSAGENS_DUBIAS);
	private CircularFifoQueue<Boolean> aux4 = new CircularFifoQueue<>(LIMITE_MENSAGENS_DUBIAS);


	private double Valor_medicao = 0;

	private String Nome_variavel;

	public Analyser(SQL db, int limite_Superior_Temp, int limite_Inferior_Temp, int limite_Superior_Lum,
			int limite_Inferior_Lum) {
		margem_temp=SQL.getInstance().getMargTemp();
		margem_lum=SQL.getInstance().getMargLum();
		LIMITE_MENSAGENS_DUBIAS=SQL.getInstance().getLimM();
		this.db = db;
		Limite_Superior_Temp = limite_Superior_Temp;
		Limite_Inferior_Temp = limite_Inferior_Temp;
		Limite_Superior_Lum = limite_Superior_Lum;
		Limite_Inferior_Lum = limite_Inferior_Lum;

		aux1.add(false);
		aux2.add(false);
		aux3.add(false);
		aux4.add(false);

		adicionarCadeados();
	}

	private void  adicionarCadeados() {
		lockBaixouTemp = false;
		lockAproximouTempInf = false;
		lockExcedeuTemp = false;
		lockAproximouTempSup = false;

		lockBaixouLum = false;
		lockAproximouLumInf = false;
		lockExcedeuLum = false;
		lockAproximouLumSup = false;

		perigoLuminosidade = true;
		perigoTemperatura = true;

	}

	public void addToList(Mensagem msg) {
		this.list.add(msg);
	}

	public ArrayList<Mensagem> getList() {
		return list;
	}

	public void remove(Mensagem msg) {
		list.remove(msg);
	}

	public Mensagem remove() {
		return list.remove(0);
	}

	/** VERIFICA SE A LISTA TEM 3 ERROS (TRUE,TRUE,TRUE) **/
	public boolean checkFullCircularQ(CircularFifoQueue<Boolean> q) {
		boolean result = true;
		for (Boolean a : q) {
			if (!a)
				result = false;
		}
		return result;
	}

	/** VERIFICA SE A LISTA TEM 3 SITUAÇOES NORMAIS (FALSE,FALSE,FALSE) **/
	public boolean checkEmptyCircularQ(CircularFifoQueue<Boolean> q) {
		boolean result = false;
		for (Boolean a : q) {
			if (!a)
				result = true;
		}
		return result;
	}

	public double getValor_medicao() {
		return Valor_medicao;
	}

	public String getNome_Variavel() {
		return Nome_variavel;
	}

	public void flag() {
		flag++;
	}

	public int getFlag() {
		return flag;
	}

	public String getErrorMessage() {
		return ErrorMessage;
	}

	public void setErrorMessage(String msg) {
		ErrorMessage = msg;
	}

	public int getLIMITE_MENSAGENS_DUBIAS() {
		return LIMITE_MENSAGENS_DUBIAS;
	}

	public void trataAlertasTemp(Mensagem msg) {
		this.Valor_medicao = msg.getTmp();
		this.Nome_variavel = "Temperatura";
		
		if(msg.getTmp() > Limite_Superior_Temp - margem_temp) {
			excedeuTemperatura(msg.getTmp());
		}else if(msg.getTmp() < Limite_Inferior_Temp + margem_temp) {
			baixouTemperatura(msg.getTmp());
		}else {
			normalTemperatura(msg.getTmp());
		}
	}

	public void trataAlertasLum(Mensagem msg) {
		this.Valor_medicao = msg.getCell();
		this.Nome_variavel = "Luminosidade";
		
		if(msg.getCell() > Limite_Superior_Lum - margem_lum) {
			excedeuLuminosidade(msg.getCell());
		}else if(msg.getCell() < Limite_Inferior_Lum + margem_lum) {
			baixouLuminosidade(msg.getCell());
		}else {
			normalLuminosidade(msg.getCell());
		}
	}


	private  void excedeuTemperatura(double tmp) {

		aux1.add(true);

		if(checkFullCircularQ(aux1)) {
			if(tmp > Limite_Superior_Temp) {
				ErrorMessage = "Temperatura passou o limite superior";
				alertaTemperaturaBD(EXCEDEU_LIMITE_TEMP);

			} else {
				ErrorMessage = "Temperatura aproxima-se do limite superior";
				alertaTemperaturaBD(APROXIMOU_LIMITE_SUPERIOR_TEMP);

			}
		}

	}

	private  void baixouTemperatura(double tmp) {

		aux2.add(true);
		if(checkFullCircularQ(aux2)) {
			if(tmp < Limite_Inferior_Temp) {
				ErrorMessage = "Temperatura passou o limite inferior";
				alertaTemperaturaBD(BAIXOU_LIMITE_TEMP);

			} else {


				ErrorMessage = "Temperatura aproxima-se do limite inferior";
				alertaTemperaturaBD(APROXIMOU_LIMITE_INFERIOR_TEMP);

			}
		}

	}

	private void normalTemperatura(double cell) {
		aux1.add(false);
		aux2.add(false);
		alertaTemperaturaBD(NORMAL_TEMP);
	}

	private  void excedeuLuminosidade(double cell) {

		aux3.add(true);

		if(checkFullCircularQ(aux3)) {
			if(cell > Limite_Superior_Lum) {
				ErrorMessage = "Luminosidade passou o limite superior";
				alertaLuminosidadeBD(EXCEDEU_LIMITE_LUM);

			} else {

				ErrorMessage = "Luminosidade aproxima-se do limite superior";
				alertaLuminosidadeBD(APROXIMOU_LIMITE_SUPERIOR_LUM);
			}
		} 
	}

	private  void baixouLuminosidade(double cell) {

		aux4.add(true);

		if(checkFullCircularQ(aux4)) {
			if(cell < Limite_Inferior_Lum) {
				ErrorMessage = "Luminosidade passou o limite inferior";
				alertaLuminosidadeBD(BAIXOU_LIMITE_LUM);

			} else {

				ErrorMessage = "Luminosidade aproxima-se do limite inferior";
				alertaLuminosidadeBD(APROXIMOU_LIMITE_INFERIOR_LUM);
			}
		}

	}

	private void normalLuminosidade(double cell) {
		aux3.add(false);
		aux4.add(false);
		alertaLuminosidadeBD(NORMAL_LUM);
	}

	private  void alertaTemperaturaBD(int i) {
		if(i == EXCEDEU_LIMITE_TEMP) {
			if(!lockExcedeuTemp) {
				destrancaCadeadosTemperatura();
				lockExcedeuTemp=true;
				db.alert(ErrorMessage, db.generateTime(), 0, Nome_variavel, Valor_medicao);
			}
		}

		if(i == BAIXOU_LIMITE_TEMP) {
			if(!lockBaixouTemp) {
				destrancaCadeadosTemperatura();
				lockBaixouTemp=true;
				db.alert(ErrorMessage, db.generateTime(), 0, Nome_variavel, Valor_medicao);
			}
		}

		if(i == APROXIMOU_LIMITE_SUPERIOR_TEMP) {
			if(!lockAproximouTempSup) {
				destrancaCadeadosTemperatura();
				lockAproximouTempSup=true;
				db.alert(ErrorMessage, db.generateTime(), 0, Nome_variavel, Valor_medicao);
			}
		}

		if(i == APROXIMOU_LIMITE_INFERIOR_TEMP) {
			if(!lockAproximouTempInf) {
				destrancaCadeadosTemperatura();
				lockAproximouTempInf=true;
				db.alert(ErrorMessage, db.generateTime(), 0, Nome_variavel, Valor_medicao);
			}
		}

		if(i == NORMAL_TEMP) {
			if(!perigoTemperatura) {
				ErrorMessage = "Temperatura regularizada";
				destrancaCadeadosTemperatura();
				perigoTemperatura=true;
				db.alert(ErrorMessage, db.generateTime(), 0, Nome_variavel, Valor_medicao);
			}
		}

	}

	private  void alertaLuminosidadeBD(int i) {
		if(i == EXCEDEU_LIMITE_LUM) {
			if(!lockExcedeuLum) {
				destrancaCadeadosLuminosidade();
				lockExcedeuLum=true;
				db.alert(ErrorMessage, db.generateTime(), 0, Nome_variavel, Valor_medicao);
			}
		}

		if(i == BAIXOU_LIMITE_LUM) {
			if(!lockBaixouLum) {
				destrancaCadeadosLuminosidade();
				lockBaixouLum=true;
				db.alert(ErrorMessage, db.generateTime(), 0, Nome_variavel, Valor_medicao);
			}
		}

		if(i == APROXIMOU_LIMITE_SUPERIOR_LUM) {
			if(!lockAproximouLumSup) {
				destrancaCadeadosLuminosidade();
				lockAproximouLumSup=true;
				db.alert(ErrorMessage, db.generateTime(), 0, Nome_variavel, Valor_medicao);
			}
		}

		if(i == APROXIMOU_LIMITE_INFERIOR_LUM) {
			if(!lockAproximouLumInf) {
				destrancaCadeadosLuminosidade();
				lockAproximouLumInf=true;
				db.alert(ErrorMessage, db.generateTime(), 0, Nome_variavel, Valor_medicao);
			}
		}

		if(i == NORMAL_LUM) {
			if(!perigoLuminosidade) {
				ErrorMessage = "Luminosidade regularizada";
				destrancaCadeadosLuminosidade();
				perigoLuminosidade=true;
				db.alert(ErrorMessage, db.generateTime(), 0, Nome_variavel, Valor_medicao);
			}
		}

	}

	private void destrancaCadeadosLuminosidade() {
		lockBaixouLum = false;
		lockAproximouLumInf = false;
		lockExcedeuLum = false;
		lockAproximouLumSup = false;

		perigoLuminosidade = false;

	}

	private  void destrancaCadeadosTemperatura() {
		lockBaixouTemp = false;
		lockAproximouTempInf = false;
		lockExcedeuTemp = false;
		lockAproximouTempSup = false;

		perigoTemperatura = false;
	}

}
