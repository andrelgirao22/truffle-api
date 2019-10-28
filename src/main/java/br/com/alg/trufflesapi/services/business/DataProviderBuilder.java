package br.com.alg.trufflesapi.services.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataProviderBuilder {

    @Autowired
    private ClientesDataProvider clientesDataProvider;

    public DataProvider builder(String reportName) {
        if(reportName.equals("clientes")) {
            return this.clientesDataProvider;
        }

        return  null;
    }
}
