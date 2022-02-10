package it.adbmime.web;

import java.awt.Desktop;
import java.io.StringWriter;
import java.net.URI;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class WebViewUtils {
	
	public static void openLink(String link) {
		try {
            Desktop.getDesktop().browse(new URI(link));
        } catch (Exception e) {
        }
	}

	/*
	public static String getWebViewHtml(WebViewUtils webView) {
		org.w3c.dom.Document doc = webView.getEngine().getDocument();
		return getStringFromDocument(doc);
	}
	*/

	private static String getStringFromDocument(org.w3c.dom.Document doc) {
		try {
			DOMSource domSource = new DOMSource(doc);
			StringWriter writer = new StringWriter();
			StreamResult result = new StreamResult(writer);
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			transformer.transform(domSource, result);
			return writer.toString();
		} catch (TransformerException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
}
