package util;

import mongo.Mongo;
import mqtt.MqttSubscribe;

public class Main {
	
	static long Start;
	static long Stop;

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		

		/** INICIA A JANELA LOGIN **/
		Thread t1 = new Thread() {
			@Override
			public void run() {
				LoginJFX app = new LoginJFX();
				app.main(args);
			}
		};
		t1.start();

		/**
		 * ESCREVE NO MONGO A CADA 10 SEGUNDOS TEM DE SER ANTES DO LOGIN POIS LOGIN SO
		 * ACABA QUANDO SE FECHAR GUI
		 **/
		Thread t = new Thread() {

			@Override
			public void run() {
				System.out.println("Thread comecada");
				while (true) {
					try {
						try {
			 				while (!MqttSubscribe.getInstance().getMessagesReceived().isEmpty()) {
								Start = System.currentTimeMillis();
								Mongo.getInstance().write(MqttSubscribe.getInstance().getMessagesReceived().remove(0));
								Stop=System.currentTimeMillis();
								System.out.println("Recetor time: " + (Stop - Start));
								sleep(1000);
							}
 
						} catch (NullPointerException e) {
						}
					sleep(10000);
					} catch (InterruptedException e1) {
						System.err.println("interrupted");
					}

				}
			}
		};
		t.start();
	}

}
