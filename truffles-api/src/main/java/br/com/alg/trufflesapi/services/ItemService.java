package br.com.alg.trufflesapi.services;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.alg.trufflesapi.exceptions.ItemNotFoundException;
import br.com.alg.trufflesapi.exceptions.PriceNotFoudException;
import br.com.alg.trufflesapi.model.Category;
import br.com.alg.trufflesapi.model.Item;
import br.com.alg.trufflesapi.model.Price;
import br.com.alg.trufflesapi.model.PriceType;
import br.com.alg.trufflesapi.repositories.ItemRepository;
import br.com.alg.trufflesapi.repositories.PriceRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository repository;
	
	@Autowired
	private PriceRepository priceRepository;
	
	@Autowired
	private CategoryService categoryService;

	public List<Item> listAll() {
		return repository.findAll();
	}

	public Item save(Item item) {
		item.setDate(new Date());
		return repository.save(item);
	}

	public void update(Item item) {
		find(item.getId());
		repository.save(item);
	}

	public void delete(Long id) {
		find(id);
		repository.deleteById(id);
	}
	
	public Item find(Long id) {
		return repository.findById(id).orElseThrow(new ItemNotFoundException("Item não encontrado."));
	}
	
	public List<Price> findPrices(Item item) {
		return 
				priceRepository.findByItem(item).orElseThrow((new PriceNotFoudException("Item " + item.getId() + " " + item.getName()  + " sem preço ")));
	}
	
	public Price saveItemPrice(Price price, Long id) {
		Item item = find(id);
		price.setItem(item);
		price.setDtStart(new Date());
		return priceRepository.save(price);
	}
	
	public List<Price> findPriceByItem(Item item) {
		item = find(item.getId());
		return this.findPrices(item);
	}

	public List<Item> findByCategory(@Valid Long id) {
		Category category = this.categoryService.find(id);  
		return this.repository.findByCategory(category);
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
	

	public void saveImage(String id, @Valid MultipartFile file) {
		
		try {
			
			removeOldImage(id);
			
			Path path = Paths.get("imagens/item/" + file.getOriginalFilename());
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
			Item item = find(Long.valueOf(image));
			
			Path path = Paths.get("imagens/item" + item.getImage());
			Files.delete(path);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}

	public List<PriceType> listPriceType() {
		return Arrays.asList(PriceType.values());
	}
	
}
