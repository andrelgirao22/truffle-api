package br.com.alg.trufflesapi.services;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import com.dropbox.core.v2.files.DbxUserFilesRequests;
import com.dropbox.core.v2.files.FileMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.alg.trufflesapi.exceptions.ItemNotFoundException;
import br.com.alg.trufflesapi.exceptions.PriceNotFoudException;
import br.com.alg.trufflesapi.model.Category;
import br.com.alg.trufflesapi.model.Item;
import br.com.alg.trufflesapi.model.Price;
import br.com.alg.trufflesapi.model.PriceType;
import br.com.alg.trufflesapi.model.StatusItem;
import br.com.alg.trufflesapi.model.dto.ItemDTO;
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
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private DropboxSdkService dbService;
	
	@Value("${img.prefix.item}")
	private String prefix;
	
	@Value("${img.picture.size}")
	private Integer size;

	public List<Item> listAll() {
		return repository.findAll();
	}

	public Item save(Item item) {
		item.setDate(new Date());
		item.setStatus(StatusItem.PENDENTE);
		return repository.save(item);
	}

	@Transactional
	public void update(Item item) {
		
		Item newItem = find(item.getId());
		List<Price> oldPrices = newItem.getPrices();
		List<Price> newPrices = item.getPrices();
		
		oldPrices.forEach(price -> {
			this.priceRepository.deleteById(price.getId());
		});
		
		newPrices.forEach(price -> {
			price.setItem(item);
			price.setId(null);
			saveItemPrice(price, item.getId());
		});
		repository.save(item);
		
		
	}

	public void delete(Long id) {
		find(id);
		repository.deleteById(id);
	}
	
	public Item find(Long id) {
		return repository.findById(id).orElseThrow(new ItemNotFoundException("Item não encontrado."));
	}
	
	public ItemDTO findItemDto(Long id) {
		Item item = this.find(id);
		return new ItemDTO(item);
	}
	
	public List<Price> findPrices(Item item) {
		return 
				priceRepository.findByItem(item).orElseThrow((new PriceNotFoudException("Item " + item.getId() + " " + item.getName()  + " sem preço ")));
	}
	
	public Price saveItemPrice(Price price, Long id) {
		Item item = find(id);
		price.setItem(item);
		return priceRepository.save(price);
	}
	
	public List<Price> findPriceByItem(Item item) {
		item = find(item.getId());
		return this.findPrices(item);
	}

	/*public List<ItemDTO> findByCategory(Long id) {
		Category category = this.categoryService.find(id);
		List<ItemDTO> items = this.repository.findByCategory(category)
				.stream()
				.filter(item -> item.getStatus().equals(StatusItem.PUBLICADO))
				.map(i -> new ItemDTO(i)).collect(Collectors.toList());
		return items;
	}*/
	
	public URI uploadPicture(MultipartFile file, Long id) {
		
		Item item = this.find(id);
		if(item == null) {
			throw new ItemNotFoundException("objeto não encontrado " + id);
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(file);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		//String filename = prefix + item.getId() + ".jpg";
		String filename = prefix + "-" + item.getId() + "-" + file.getOriginalFilename();
		
		InputStream is = this.imageService.getInputStream(jpgImage, "jpg");
		
		URI uri = this.dbService.uploadFile(is, filename, "image");
		
		//item.setImageUrl(uri.toString());
		//this.repository.save(item);
		
		return uri;
	}
	
	public List<PriceType> listPriceType() {
		return Arrays.asList(PriceType.values());
	}

	public Page<Item> findPage(Integer page, Integer linesPerPage, String orderby, String direction, String search) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderby);
		if(search != null && !search.equals("")) {
			return this.repository.findByName(search, pageRequest);
		}
		return this.repository.findAll(pageRequest);
	}
	
	public Page<Item> findByCategory(Long id, Integer page, Integer linesPerPage, String orderby, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderby);
		
		Category category = this.categoryService.find(id);
		Page<Item> items = this.repository
				.findByCategory(category, pageRequest);
								/*.stream()
				.filter(item -> item.getStatus().equals(StatusItem.PUBLICADO))
				.map(i -> new ItemDTO(i)).collect(Collectors.toList());*/
		return items;
	}

	public void deletePicture(@Valid Long id, Integer index) {
		this.dbService.deleteFile(this.dbService.getDropClient(),prefix + "-" + id + "-" + index +  ".png");
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
}
