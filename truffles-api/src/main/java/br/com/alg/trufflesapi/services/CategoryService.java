package br.com.alg.trufflesapi.services;

import java.io.File;
import java.io.FileOutputStream;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
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
	
	public Page<Category> findPage(Integer page, Integer linesPerPage, String orderby, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderby);
		return this.repository.findAll(pageRequest);
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
		
		removeOldImage(id + "");
		
		find(id);
		repository.deleteById(id);
	}
	
	
	public Category find(Long id) {
		return repository.findById(id).orElseThrow(new CategoryNotFoundException("Categoria não encontrada"));
	}

	
	public Map<String, String> getImage(String image) {
		
		try {
			
			Path path = Paths.get("imagens/category/" + image);
			
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
			
			Path path = Paths.get("imagens/category/" + file.getOriginalFilename());
			path.toFile().setExecutable(true, false);
			
			FileOutputStream out = new FileOutputStream(path.toFile());
			out.write(file.getBytes());
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void removeOldImage(String image) {
		
		if(image == null) return ;

		try {
			Category category = find(Long.valueOf(image));
			
			Path path = Paths.get("imagens/category" + category.getImage());
			Files.delete(path);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	
	
}
