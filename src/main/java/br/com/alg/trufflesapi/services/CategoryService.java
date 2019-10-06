package br.com.alg.trufflesapi.services;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dropbox.core.v2.files.DbxUserFilesRequests;

import br.com.alg.trufflesapi.exceptions.CategoryNotFoundException;
import br.com.alg.trufflesapi.model.Category;
import br.com.alg.trufflesapi.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Autowired
	private DropboxSdkService dbService;
	
	@Value("${img.prefix.category}")
	private String prefix;
	
	@Value("${img.picture.size}")
	private Integer size;
	
	@Autowired
	private ImageService imageService;

	public List<Category> listAll() {
		return repository.findAll();
	}
	
	public Page<Category> findPage(Integer page, Integer linesPerPage, String orderby, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderby);
		return this.repository.findAll(pageRequest);
	}
	
	public Page<Category> findPageByName(Integer page, Integer linesPerPage, String orderby, String direction, String name) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderby);
		return this.repository.findByName(name, pageRequest);
	}

	public Category save(Category category) {
		category.setDate(new Date());
		return repository.save(category);
	}

	public void update(Category category) {
		Category oldCategory = find(category.getId());
		category.setDate(oldCategory.getDate());
		repository.save(category);
	}

	public void delete(Long id) {
		find(id);
		repository.deleteById(id);
	}
	
	
	public Category find(Long id) {
		return repository.findById(id).orElseThrow(new CategoryNotFoundException("Categoria não encontrada"));
	}

	
	public URI uploadPicture(MultipartFile file, Long id) {
		
		Category category = this.find(id);
		if(category == null) {
			throw new CategoryNotFoundException("Objeto não encontrado " + id);
		}
		BufferedImage jpgImage = imageService.getJpgImageFromFile(file);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		String filename = prefix + "-" + category.getId() + "-" + file.getOriginalFilename();
		
		InputStream is = this.imageService.getInputStream(jpgImage, "jpg");
		
		URI uri = this.dbService.uploadFile(is, filename, "image");
		
		
		return uri;
	}
	

	public InputStream getImageFromId(Long id, Integer index) {

		String filename = prefix + "-" + id + "-" + index + ".png";
		DbxUserFilesRequests file = this.dbService.getFile(this.dbService.getDropClient(), filename);
		try {
			return  file.download("/" + filename).getInputStream();
		} catch (Exception e) {
			throw  new RuntimeException("Arquivo " + filename + " não encontrado");
		}
	}
	
	public void deletePicture(Long id, Integer index) {
		this.dbService.deleteFile(this.dbService.getDropClient(),prefix + "-" + id + "-" + index +  ".png");
	}
	
	
	
}
