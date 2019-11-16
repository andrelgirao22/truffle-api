package br.com.alg.trufflesapi.services.business;

import br.com.alg.trufflesapi.model.Detail;
import br.com.alg.trufflesapi.services.AccountService;
import br.com.alg.trufflesapi.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static br.com.alg.trufflesapi.util.CommonFunctions.formatNumber;
import static br.com.alg.trufflesapi.util.CommonFunctions.padRight;

@Service
public class ProdutcDataProvider implements  DataProvider  {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ItemService itemService;

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

        List<ProductBoughtMost> list = this.itemService.findProductsBoughtMost(dtInit, dtFinal);

        Map<String, Object> report = new HashMap<>();

        report.put("wtitle","PRODUTOS MAIS VENDIDOS");
        report.put("wsubtitle","");
        report.put("wpagheader","Período " + queryParams.get("dt_ini") + " à " + queryParams.get("dt_final"));
        report.put("wimage","");
        report.put( "wcolheader","CODIGO  DESCRICAO                                  TOTAL");
        List<Detail> details= new ArrayList<>();
        if(list.isEmpty()) {
            details.add(new Detail().setLine("Nenhum resultado"));
        }
        list.forEach( c -> {
            String content = padRight(c.getId().toString(), 8)  + padRight(c.getName(),45) + c.getQuantity();
            details.add(new Detail().setLine(content));
        });

        report.put("wdetails", details);
        report.put("wfooter", "Truffleweb " + dateFormat.format(new Date()));

        return report;
    }
}
