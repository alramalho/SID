import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import javafx.scene.control.TextField;

public class SQL {

	private static SQL instance;
	private Connection connect;

	private String user;
	private String pass;
	private String database = "grupo23_bd";
	private String table_tem = "medicoes_temperaturas";
	private String table_lum = "medicoes_luminosidades";

	private String id_investigador;

	private static long TIME_TO_RETRIEVE;

	private boolean auth = false;
	private int margTemp;
	private int margLum;
	private int limM;

	public SQL() {

	}

	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Unable to load MySQL Driver");
		}

		String jdbcURL = "jdbc:mysql://localhost/" + database;

		try {
			connect = DriverManager.getConnection(jdbcURL, user, pass);
			System.out.println("Connected!");
			auth = true;
		} catch (SQLException e) {
			System.err.println("Unable to connect to database.");
			auth = false;
		}

	}

	public void testQuery(String query) {

		try {
			Statement statement = connect.createStatement();
			ResultSet result = statement.executeQuery(query);

			while (result.next()) {
				System.out.println("Result: " + result.getString(1));
			}
		} catch (SQLException e) {
			System.err.println("Unable to conclude query.");
		}
	}

	public void inserirNoSQL(int tem, int lum, String data) {
		try {
			String query1 = "Insert into " + database + "." + table_tem + "(Valor_Mediï¿½ï¿½o_Temp, Data_Hora) values("
					+ tem + "," + "'" + data + "'" + ");";
			String query2 = "Insert into " + database + "." + table_lum + "(Valor_Mediï¿½ï¿½o_Lum, Data_Hora) values("
					+ lum + "," + "'" + data + "'" + ");";
			CallableStatement stmt1 = connect.prepareCall(query1);
			CallableStatement stmt2 = connect.prepareCall(query2);
			int rs1 = stmt1.executeUpdate();
			int rs2 = stmt2.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Inserted");
	}

	/**
	 * CONVERTE DATA DE JAVA.UTIL.DATE (TIPO USADO NA MENSAGEM) PARA JAVA.SQL.DATE
	 * (TIPO ACEITE PELO SQL)
	 **/
	public java.sql.Date convertUtilToSql(java.util.Date uDate) {
		java.sql.Date sDate = new java.sql.Date(uDate.getTime());
		return sDate;
	}

	public void inserirNoSQL(Mensagem m) {
		if(m.getTmp()==-1000) {
			double lum = m.getCell();
			String data = m.getData();
			// java.sql.Date data = convertUtilToSql(m.getDat());
			try {
				String query2 = "Insert into " + database + "." + table_lum + "(Valor_Medicao_Lum, Data_Hora) values(" + lum
						+ ",'" + data + "');";
				CallableStatement stmt2 = connect.prepareCall(query2);
				System.out.println(data);
				int rs2 = stmt2.executeUpdate();

			} catch (SQLException e) {
				System.err.println("Mensagem duplicada" + m.getData());
			}

			System.out.println("Inserted");
		} else if(m.getCell()==-1000) {
			double tem = m.getTmp();
			String data = m.getData();
			// java.sql.Date data = convertUtilToSql(m.getDat());
			try {
				String query1 = "Insert into " + database + "." + table_tem + "(Valor_Medicao_Temp, Data_Hora) values("
						+ tem + ",'" + data + "');";
				CallableStatement stmt1 = connect.prepareCall(query1);
				System.out.println(data);
				int rs1 = stmt1.executeUpdate();

			} catch (SQLException e) {
				System.err.println("Mensagem duplicada" + m.getData());
			}

			System.out.println("Inserted");
		} else  {
			double tem = m.getTmp();
			double lum = m.getCell();
			String data = m.getData();
			// java.sql.Date data = convertUtilToSql(m.getDat());
			try {
				String query1 = "Insert into " + database + "." + table_tem + "(Valor_Medicao_Temp, Data_Hora) values("
						+ tem + ",'" + data + "');";
				String query2 = "Insert into " + database + "." + table_lum + "(Valor_Medicao_Lum, Data_Hora) values(" + lum
						+ ",'" + data + "');";
				CallableStatement stmt1 = connect.prepareCall(query1);
				CallableStatement stmt2 = connect.prepareCall(query2);
				System.out.println(data);
				int rs1 = stmt1.executeUpdate();
				int rs2 = stmt2.executeUpdate();

			} catch (SQLException e) {
				System.err.println("Mensagem duplicada" + m.getData());
			}

			System.out.println("Inserted");
		}
	}

	public void alert(String descricao, String date, int id_cultura, String nome_variavel, double valor_medicao) {
		double limite_superior = 0;
		double limite_inferior = 0;
		if (nome_variavel.equals("Temperatura")) {
			limite_superior = getLimiteS_temp();
			limite_inferior = getLimiteI_temp();
		} else if (nome_variavel.equals("Luminosidade")) {
			limite_superior = getLimiteS_lum();
			limite_inferior = getLimiteI_lum();
		}
		String query1 = "Insert into " + database
				+ ".alertas_investigador(Data_Hora, ID_Cultura, Nome_Variavel, Descricao, Limite_Inferior, Limite_Superior, Valor_Medicao) values('"
				+ date + "','" + id_cultura + "','" + nome_variavel + "','" + descricao + "','" + limite_inferior
				+ "','" + limite_superior + "','" + valor_medicao + "');";

		CallableStatement stmt1;
		try {
			stmt1 = connect.prepareCall(query1);
			int rs1 = stmt1.executeUpdate();
		} catch (MySQLIntegrityConstraintViolationException e) {
			System.err.println("Alerta já existente com" + nome_variavel + ":"+ valor_medicao);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getId_investigador() {

		return id_investigador;
	}

	public void setId_investigador(String id_investigador) {
		this.id_investigador = id_investigador;
	}

	public ArrayList<String> getId_culturas(int ls_temp, int ls_lum, int li_temp, int li_lum) {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String query = "select id_cultura from cultura, variáveis_medidas, sistema ";
			CallableStatement stmt = connect.prepareCall(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String lineResult = String.format("%s - %s", rs.getString("ID_Variável"),
						rs.getString("Nome_Variável"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int getLimiteS_temp() {
		int result = 0;
		try {
			String query = "select Limite_Superior_Temp from sistema";
			CallableStatement stmt = connect.prepareCall(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				//				result = String.format("%s", rs.getString("Limite_Superior_Temp"));
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int getLimiteI_temp() {
		int result = 0;
		try {
			String query = "select Limite_Inferior_Temp from sistema";
			CallableStatement stmt = connect.prepareCall(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				//				result = String.format("%s", rs.getString("Limite_Superior_Temp"));
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int getLimiteS_lum() {
		int result = 0;
		try {
			String query = "select Limite_Superior_lum from sistema";
			CallableStatement stmt = connect.prepareCall(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				//				result = String.format("%s", rs.getString("Limite_Superior_Temp"));
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public int getLimiteI_lum() {
		int result = 0;
		try {
			String query = "select Limite_Inferior_lum from sistema";
			CallableStatement stmt = connect.prepareCall(query);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				//				result = String.format("%s", rs.getString("Limite_Superior_Temp"));
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void setUsername(String user) {
		this.user = user;
	}

	public void setPassword(String pwd) {
		this.pass = pwd;
	}

	public static SQL getInstance() {
		if (instance == null)
			instance = new SQL();
		return instance;
	}

	public boolean authSuccessfull() {
		return auth;
	}

	public String generateTime() {
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter m = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String result = myDateObj.format(m);
		return result;
	}

	public void setTTR(long l) {
		TIME_TO_RETRIEVE=l;
		String query = "update trabalhador set Time_To_Retrieve="+TIME_TO_RETRIEVE+" where Email='" + this.user + "';";

		try {
			CallableStatement stmt = connect.prepareCall(query);
			stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public long getTTR() {
		String query = "select Time_To_Retrieve from trabalhador where Email='" + this.user + "';";
		long lineResult = -1;
		try {
			CallableStatement stmt = connect.prepareCall(query);
			ResultSet rs;
			rs = stmt.executeQuery();
			if (rs.next()) {
				lineResult = Long.parseLong(String.format("%s", rs.getInt("Time_To_Retrieve")));
				if(lineResult != -1)
					return lineResult;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return TIME_TO_RETRIEVE;
	}


	public int getMargTemp() {
		return margTemp;
	}

	public int getMargLum() {
		return margLum;
	}

	public int getLimM() {
		return limM;
	}


	public void setSpecs(int margTemp, int margLum, int limM) {
		this.margTemp=margTemp;
		this.margLum=margLum;
		this.limM=limM;
	}

}