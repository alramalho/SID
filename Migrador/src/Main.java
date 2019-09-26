
public class Main {

	static long start;
	static long stop;
	
	public static void main(String[] args) {

		/** INICIA A JANELA LOGIN **/
		Thread t1 = new Thread() {
			@SuppressWarnings("static-access")
			@Override
			public void run() {
				LoginJFX app = new LoginJFX();
				app.main(args);
			}
		};
		t1.start();

		try {
			//ESPERA QUE LOGIN ACABE
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		GUI gui = new GUI();
		gui.setText("Loading Mongo...");
		Mongo m = new Mongo();

		gui.setText("Loading SQL...");
		SQL db = SQL.getInstance();
		Analyser analyser = new Analyser(db,db.getLimiteS_temp(), db.getLimiteI_temp(), db.getLimiteS_lum(),
				db.getLimiteI_lum());

		gui.setText("Migrating...");
		Thread t = new Thread() {

			@Override
			public void run() {
				while (true) {
					try {
						sleep(db.getTTR());
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					m.retrieveData();
					if (!m.getParaEnviar().isEmpty()) {
						start = System.currentTimeMillis();
						Mensagem msg = m.getParaEnviar().remove(0);
						/** ALERTAS **/
						analyser.addToList(msg);
						Thread t1 = new Thread() {
							@Override
							public void run() {
								while (analyser.getList().size() != 0) {
									analyser.trataAlertasTemp(msg);
									analyser.trataAlertasLum(msg);
									analyser.remove(msg);
								}
							}
						};
						t1.start();
						/** ALERTAS **/
						try {
							t1.join();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						db.inserirNoSQL(msg);
						stop=System.currentTimeMillis();
						System.out.println("Recetor time: " + (stop - start));
						m.eliminar(msg);
					}
				}
			}
		};
		t.start();

		System.out.println("all done");
	}

}
