package br.com.alg.trufflesapi.resources;


import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import br.com.alg.trufflesapi.model.ReportType;
import br.com.alg.trufflesapi.services.ReportService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value="/report")
public class ReportResource {

    @Autowired
    private ReportService service;

    @GetMapping
    public HttpEntity<byte[]> getReport(
            @RequestParam(value="type", defaultValue="portrait") String type,
            @RequestParam(value="template", defaultValue="generic") String template,
            @RequestParam(value = "reportName") String reportName,
            @RequestParam(value = "dt_ini") String dtIni,
            @RequestParam(value = "dt_final") String dtFinal) throws IOException{

        ReportType reporttype = ReportType.valueOf(type.toUpperCase());

        byte[] documentBody = this.service.getReport(reporttype, template, reportName, dtIni, dtFinal);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.setContentLength(documentBody.length);
        String filename = reportName + ".pdf";
        header.setContentDispositionFormData(filename, filename);
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(documentBody, header, HttpStatus.OK);
        CacheControl cacheControl = CacheControl.maxAge(3600, TimeUnit.SECONDS);
        return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(documentBody);
        //return response; //new HttpEntity<byte[]>(documentBody, header);
    }

    @PostMapping(value="/upload")
    public ResponseEntity<Void> upload(@Valid @RequestParam(name="report", required=true) MultipartFile arquivo,
                                       @RequestParam(value="type", defaultValue="portrait") String type){

        this.service.upload(arquivo, type);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/")
                .buildAndExpand(arquivo.getName()).toUri();

        return ResponseEntity.created(uri).build();

    }

}
