package br.com.alg.trufflesapi.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.alg.trufflesapi.exceptions.UserException;
import br.com.alg.trufflesapi.model.Group;
import br.com.alg.trufflesapi.model.User;
import br.com.alg.trufflesapi.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private GroupService groupService;
	
	public List<User> listAll() {
		return repository.findAll();
	}

	public User save(User user, MultipartFile image) {
		
		checkUserExist(user);
		
		if(image != null) {
			try {
				File file = new File("src/main/resources/images/" + image.getOriginalFilename());
				FileOutputStream outputStream = new FileOutputStream(file);
				outputStream.write(image.getBytes());
				outputStream.flush();
				outputStream.close();
				user.setUserImage(image.getOriginalFilename());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		user.setStatus(true);
		findGroup(user);
		return repository.save(user);
	}

	private void checkUserExist(User user) {
		
		if(repository.findByEmail(user.getEmail()).isPresent()) {
			throw new UserException("Usuário já cadastrado com este email.");
		}
		
	}

	public void update(User user) {
		//String password = user.getPassword();
		//user.setPassword(generatePassword(password));
		findGroup(user);
		find(user.getId());
		repository.save(user);
	}

	private void findGroup(User user) {
		List<Group> groupsExistents = new ArrayList<>();
		if(user.getGroups() != null && !user.getGroups().isEmpty()) {
			user.getGroups().forEach(group -> {
				Group findGrop = groupService.findByIdOrName(group.getId(), group.getName());
				findGrop.addUsers(user);
				groupsExistents.add(findGrop);
			});
		}
		user.setGroups(groupsExistents);
	}

	public void delete(Long id) {
		find(id);
		repository.deleteById(id);
	}
	
	public User find(Long id) {
		return repository.findById(id).orElseThrow(new UserException("Usuario não encontrado."));
	}
	
	
	public User findByEmail(String email) {
		Optional<User> userOptional = repository.findByEmail(email); 
		User user =userOptional.orElseThrow(UserException::new);
		return user;
	}

	/*public void validateUser(User user, String password) {
		
		if(password == null || !user.getPassword().equals(generatePassword(password))) {
			throw new CredentiaisInvalidException("User or Password invalid");
		}
		
		if(!user.isStatus()) {
			throw new CredentiaisInvalidException("User is not active");
		}
	}*/

	public void addGroup(Group group, Long id) {
		group = this.groupService.find(id);
		
	}

	/*@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = this.repository.findByEmail(username)
				.orElseThrow(new CredentiaisInvalidException("Login inválido"));
		
		return user;
	}*/
	
}
