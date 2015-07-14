package report;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import report.bean.CustomBeanFactory;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.jaspersoft.mongodb.connection.MongoDbConnection;

/**
 * For JavaBean report, you need to
 * 1. make the CustomBean and CustomBeanFactory
 * 2. Make the report, setting the target/classes in the classpath of the iReport first
 * 3. In Datasources use Javabeans set datasource
 * 4. There will be NO columns loaded by default, you need to add it by Report Query -> JavaBean Datasource 
 *    -> Type the class name and Read Attributes.
 * @author keithtung
 * 
 *
 */
public class JavaBeanPDF {

	public static void main(String [] args) {
		new JavaBeanPDF().generatePDF2File();
	}


    public void generatePDF2File() {
        JasperPrint jasperPrint = null;
        try {
        	JasperReport jr = JasperCompileManager.compileReport("JavaBeanReport.jrxml");
//        	File f = new File("FirstReport.jasper");
//        	JasperReport jr = (JasperReport) JRLoader.loadObject(f);
        	
            //Preparing parameters
            Map parameters = new HashMap();
            parameters.put("ReportTitle", "Address Report");
            parameters.put("DataFile", "CustomBeanFactory.java - Bean Collection");

        	
            jasperPrint = JasperFillManager.fillReport(jr, parameters, getDataSource());
            
            OutputStream stream = new FileOutputStream(new File("JavaBeanReport.pdf"));
            JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
            stream.flush();
            stream.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    
    public JRBeanCollectionDataSource getDataSource() {
     return new JRBeanCollectionDataSource(CustomBeanFactory.getBeanCollection());
    }
}
