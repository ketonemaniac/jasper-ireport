package report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import com.jaspersoft.mongodb.connection.MongoDbConnection;

public class MongoPDF {

	public static void main(String [] args) {
		new MongoPDF().generatePDF2File();
	}


    public void generatePDF2File() {
        JasperPrint jasperPrint = null;
        try {
        	JasperReport jr = JasperCompileManager.compileReport("MongoReport.jrxml");
//        	File f = new File("FirstReport.jasper");
//        	JasperReport jr = (JasperReport) JRLoader.loadObject(f);
        	
            jasperPrint = JasperFillManager.fillReport(jr, new HashMap(), getDataSource());
            
            OutputStream stream = new FileOutputStream(new File("MongoReport.pdf"));
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            stream.flush();
            stream.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public Connection getDataSource() {
    	String mongoURI = "mongodb://127.0.0.1:27017/oms";
    	MongoDbConnection connection = null;
    	Map<String, Object> parameters = new HashMap<String, Object>();
    	try {
    	  connection = new MongoDbConnection(mongoURI, "omsuser", "a12345678");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return connection;
    }
}
