package it.adbmime;

import it.adbmime.web.WebViewUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

import java.io.IOException;

public class AboutController {

	@FXML
	private Hyperlink hyperlink; 
	
    @FXML
    private void openPrimary() throws IOException {
        App.setRoot("adbmime");
    }
    
    @FXML
	private void openLink() {
    	WebViewUtils.openLink(hyperlink.getText());
	}
}