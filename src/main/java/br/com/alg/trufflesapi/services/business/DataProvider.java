package br.com.alg.trufflesapi.services.business;

import java.text.ParseException;
import java.util.Map;

public interface DataProvider {

    void setQueryParams(Map<String, Object> queryParams);

    Map<String, Object> getDataProvider() throws ParseException;
}
