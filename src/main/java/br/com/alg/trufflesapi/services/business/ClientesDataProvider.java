package br.com.alg.trufflesapi.services.business;

import br.com.alg.trufflesapi.model.Detail;
import br.com.alg.trufflesapi.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static  br.com.alg.trufflesapi.util.CommonFunctions.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ClientesDataProvider implements  DataProvider {

    @Autowired
    private AccountService accountService;
    private Map queryParams;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void setQueryParams(Map<String, Object> queryParams) {
        this.queryParams = queryParams;
    }

    @Override
    public Map<String, Object> getDataProvider() throws ParseException {


        Date dtInit = dateFormat.parse(queryParams.get("dt_ini").toString());
        Date dtFinal= dateFormat.parse(queryParams.get("dt_final").toString());

        List<ClientBoughtMost> list = this.accountService.findClientsBoughtMost(dtInit, dtFinal);

        Map<String, Object> report = new HashMap<>();

        report.put("wtitle","Clientes que mais compraram");
        report.put("wsubtitle","");
        report.put("wpagheader","Período " + queryParams.get("dt_ini") + " à " + queryParams.get("dt_final"));
        report.put("wimage","");
        report.put( "wcolheader","CODIGO  EMAIL                                  VALOR");
        List<Detail> details= new ArrayList<>();
        list.forEach( c -> {
            String content = padRight(c.getId().toString(), 8)  + padRight(c.getEmail(),40) + formatNumber(c.getValue(), "#,##0.00");
            details.add(new Detail().setLine(content));
        });

        report.put("wdetails", details);
        report.put("wfooter", "Truffleweb " + dateFormat.format(new Date()));

        return report;
    }
}
