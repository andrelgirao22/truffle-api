package br.com.alg.trufflesapi.services;

import br.com.alg.trufflesapi.model.ReportType;
import br.com.alg.trufflesapi.services.business.DataProvider;
import br.com.alg.trufflesapi.services.business.DataProviderBuilder;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private DataProviderBuilder dataProviderBuilder;

    public byte[] getReport(ReportType type, String template, String reportName, String dtIni, String dtFinal) {

        String path = this.getReportPath(type, template);

        try {

            Map<String, Object> report = getReportMapped(reportName, dtIni, dtFinal);

            Collection<?> collection = report.get("wdetails") != null ?
                    (Collection<?>) report.get("wdetails")
                    : Arrays.asList("");

            InputStream is = ReportService.class.getResourceAsStream(path);

            JRBeanCollectionDataSource rel =
                    new JRBeanCollectionDataSource(collection, false);

            JasperPrint print = JasperFillManager.fillReport(is, report, rel);

            return JasperExportManager.exportReportToPdf(print);

        } catch (Exception e) {
            System.out.println(e);
        }

        return null;

    }

    private Map<String, Object> getReportMapped(String reportName, String dtIni, String dtFinal) throws ParseException {

        DataProvider dataProvider = dataProviderBuilder.builder(reportName);
        Map queryParameters = new HashMap();
        queryParameters.put("dt_ini", dtIni);
        queryParameters.put("dt_final", dtFinal);
        dataProvider.setQueryParams(queryParameters);
        Map<String, Object> report = dataProvider.getDataProvider();
        return  report;
    }

    private String getReportPath(ReportType type, String template) {
        return  "/templates/" + type.toString().toLowerCase() + "/" + template + ".jasper";
    }

    public void upload(MultipartFile arquivo, String type) {

        try {
            String nomeArquivo = arquivo.getOriginalFilename();

            File file = new File("src/main/resources/templates/" + type);
            File fileTarget = new File(file.getAbsolutePath() + "/" + nomeArquivo);

			/*Path path = Paths.get(fileTarget.getAbsolutePath());

			Files.write(path, arquivo.getBytes());*/

            FileOutputStream out = new FileOutputStream(fileTarget.getAbsolutePath());
            out.write(arquivo.getBytes());
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
