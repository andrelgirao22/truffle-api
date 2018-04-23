package br.com.alg.trufflesapi.services;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		find(id);
		repository.deleteById(id);
	}
	
	
	public Category find(Long id) {
		return repository.findById(id).orElseThrow(new CategoryNotFoundException("Categoria não encontrada"));
	}

	
	public Map<String, String> getImage(String image) {
		
		try {
			
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("img/" + image).getFile());
			
			String encodeImage = Base64.getEncoder().withoutPadding().encodeToString(Files.readAllBytes(file.toPath()));
			
			Map<String, String> jsonMap = new HashMap<>();
			jsonMap.put("image", encodeImage);
			
			return jsonMap;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	
	
}
