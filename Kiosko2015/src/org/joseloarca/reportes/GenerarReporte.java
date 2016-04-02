package org.joseloarca.reportes;
import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.joseloarca.db.Conexion;
public class GenerarReporte {
    private static GenerarReporte instancia;
    public GenerarReporte(){     
    }
    public static GenerarReporte getInstancia(){
        if(instancia ==null){
            instancia = new GenerarReporte();
        }
        return instancia;
    }
    public void generarReporte(Map parametros, String nombre, String titulo){
        InputStream reporte = GenerarReporte.class.getResourceAsStream(nombre);
        try{
            JasperReport reporteMaestro = null;
            reporteMaestro = (JasperReport) JRLoader.loadObject(reporte);
            JasperPrint vistaReporte = JasperFillManager.fillReport(reporteMaestro,
                    parametros,Conexion.getInstancia().getConexion());
            JasperViewer visor = new JasperViewer(vistaReporte, false);
            visor.setTitle(titulo);
            visor.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}