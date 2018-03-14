package br.com.alg.trufflesapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alg.trufflesapi.exceptions.GroupNotFoundException;
import br.com.alg.trufflesapi.model.Group;
import br.com.alg.trufflesapi.repositories.GroupRepository;

@Service
public class GroupService {

	@Autowired
	private GroupRepository repository;
	
	public List<Group> listAll() {
		return repository.findAll();
	}

	public Group save(Group group) {		
		return repository.save(group);
	}

	public void update(Group group) {
		find(group.getId());
		repository.save(group);
	}

	public void delete(Long id) {
		find(id);
		repository.deleteById(id);
	}
	
	public Group find(Long id) {
		return repository.findById(id).orElseThrow(new GroupNotFoundException("Grupo não encontrado."));
	}

	public Group findById(Long id) {
		return this.find(id);
	}	
}
