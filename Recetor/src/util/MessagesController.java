package util;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class MessagesController implements Initializable{
	
	private static MessagesController instance;
	
	@FXML
	private TextArea messageList;
	
	@FXML
	private TextArea messageList2;
	
	@FXML
	private TextArea messageList3;
	
	@FXML 
	private Label counter;
	@FXML 
	private Label counter2;
	@FXML 
	private Label counter3;
	
	@FXML
	private ResourceBundle resources;

	// Add a public no-args constructor
	public MessagesController()	{
		instance=this;
	}

	@FXML
	private void initialize()	{
	}

	public void show(String message)	{
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				messageList.setText(message + "\r\n" + messageList.getText());
				String aux = String.valueOf(Integer.parseInt(counter.getText())+1);
				counter.setText(aux);				
			}
		});
	}	
	
	public void write(String message)	{
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				messageList2.setText(message + "\r\n" + messageList2.getText());
				String aux = String.valueOf(Integer.parseInt(counter2.getText())+1);
				counter2.setText(aux);				
			}
		});
	}
	public void discard(String message)	{
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				messageList3.setText(message + "\r\n" + messageList3.getText());
				String aux = String.valueOf(Integer.parseInt(counter3.getText())+1);
				counter3.setText(aux);				
			}
		});	
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

	public static MessagesController getInstance() {
		if(instance==null)
			instance = new MessagesController();
		return instance;
	}


}
