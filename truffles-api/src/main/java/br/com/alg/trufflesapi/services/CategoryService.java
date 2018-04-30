package br.com.alg.trufflesapi.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.alg.trufflesapi.exceptions.CategoryNotFoundException;
import br.com.alg.trufflesapi.model.Category;
import br.com.alg.trufflesapi.repositories.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	public List<Category> listAll() {
		return repository.findAll();
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
		
		try {
			removeOldImage(id + "");
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
		
		find(id);
		repository.deleteById(id);
	}
	
	
	public Category find(Long id) {
		return repository.findById(id).orElseThrow(new CategoryNotFoundException("Categoria não encontrada"));
	}

	
	public Map<String, String> getImage(String image) {
		
		try {
			
			Path path = Paths.get("imagens/" + image);
			
			File file = path.toFile();
			
			String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(file.toPath()));
			
			Map<String, String> jsonMap = new HashMap<>();
			jsonMap.put("image", encodeImage);
			
			return jsonMap;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

	public void saveImage(String id, @Valid MultipartFile file) {
		
		try {
			
			removeOldImage(id);
			
			Path path = Paths.get("imagens/" + file.getOriginalFilename());
			path.toFile().setExecutable(true, false);
			
			FileOutputStream out = new FileOutputStream(path.toFile());
			out.write(file.getBytes());
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void removeOldImage(String image) throws IOException {
		
		if(image == null) return ;

		Category category = find(Long.valueOf(image));
		
		Path path = Paths.get("imagens/" + category.getImage());
		Files.delete(path);
		
	}
	
	
	
}
