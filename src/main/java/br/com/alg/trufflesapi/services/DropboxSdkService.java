package br.com.alg.trufflesapi.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.DbxUserFilesRequests;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.sharing.LinkAudience;
import com.dropbox.core.v2.sharing.RequestedLinkAccessLevel;
import com.dropbox.core.v2.sharing.RequestedVisibility;
import com.dropbox.core.v2.sharing.SharedLinkMetadata;
import com.dropbox.core.v2.sharing.SharedLinkSettings;

import br.com.alg.trufflesapi.exceptions.FileException;

@Service
public class DropboxSdkService {

	private Logger LOG = LoggerFactory.getLogger(AmazonS3Service.class);

	@Value(("${drop.box.access-token}"))
	private String accessToken;
	
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			LOG.info("Iniciando Upload");
			String fileName = multipartFile.getOriginalFilename();
			File file = new File("");
			
			FileInputStream input = new FileInputStream(file);
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
			
			client.sharing().getFileMetadata("");
			
			
			SharedLinkSettings sharedLinkSettings = new SharedLinkSettings(RequestedVisibility.PUBLIC, null, null, LinkAudience.PUBLIC, RequestedLinkAccessLevel.VIEWER);
			SharedLinkMetadata sharedLink = client.sharing().createSharedLinkWithSettings("/" + fileName, sharedLinkSettings);
			sharedLink.getUrl();
			
			LOG.info("Upload finalizado");

			URI uri = URI.create(fileName);

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
