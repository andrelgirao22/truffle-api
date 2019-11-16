package br.com.alg.trufflesapi.services.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataProviderBuilder {

    @Autowired
    private ClientesDataProvider clientesDataProvider;

    @Autowired
    private ProdutcDataProvider produtcDataProvider;

    public DataProvider builder(String reportName) {
        if(reportName.equals("clientes")) {
            return this.clientesDataProvider;
        } else if(reportName.equals("produtos")) {
            return this.produtcDataProvider;
        }

        return  null;
    }
}
