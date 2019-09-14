package br.com.alg.trufflesapi.services;

import br.com.alg.trufflesapi.exceptions.FileException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DbxUserFilesRequests;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

@Service
public class DropboxSdkService {

	private Logger LOG = LoggerFactory.getLogger(AmazonS3Service.class);

	@Value(("${drop.box.access-token}"))
	private String accessToken;
	
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			LOG.info("Iniciando Upload");
			String fileName = multipartFile.getOriginalFilename();
			InputStream is = multipartFile.getInputStream();
			String contentType = multipartFile.getContentType();
			return uploadFile(is, fileName, contentType);
		} catch (IOException e) {
			throw new FileException("Erro de IO " + e.getMessage());
		}	
	}
	
	public URI uploadFile(InputStream is, String fileName, String contentType) {

		DbxClientV2 client = getDropClient();

		LOG.info("Upload iniciado");

		try {

			DbxUserFilesRequests file = getFile(client, fileName);
			if(file != null) {
				this.deleteFile(client, fileName);
			}

			FileMetadata metadata = client.files().uploadBuilder("/" + fileName).uploadAndFinish(is);
			LOG.info("Upload finalizado");

			URI uri = URI.create(metadata.getMediaInfo().toString());

			return uri;
		} catch (Exception e) {
			throw new FileException("Erro ao converte URL para URI");
		}
	}

	public DbxUserFilesRequests getFile(DbxClientV2 client, String filename) {

		try {

			ListFolderResult result = client.files().listFolderBuilder("")
					.withIncludeDeleted(false)
					.withRecursive(true)
					.withIncludeMediaInfo(true)
					.start();

			for (Metadata metadata : result.getEntries()) {
				if(metadata.getPathLower().contains(filename)) {
					FileMetadata fileMetadata = (FileMetadata) metadata;
					return client.files(); //.download(metadata.getPathLower(), ((FileMetadata) metadata).getRev());

				}
			}

			return null;
		} catch (Exception e) {
			throw new FileException(e.getMessage());
		}

	}

	public DbxClientV2 getDropClient() {
		DbxRequestConfig config = new DbxRequestConfig("dropbox/truffle-app");
		DbxClientV2 client = new DbxClientV2(config, accessToken);
		return client;
	}
	
	public void deleteFile(DbxClientV2 client, String filename) {
		try {
			LOG.info("Deleting file");
			client.files().deleteV2("/" + filename);
			LOG.info("Delete finalizado");
		} catch(Exception e) {
			throw new FileException(e.getMessage());
		}
	}
	
}
