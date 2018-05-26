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
	
	public Group findByIdOrName(Long id, String name) {
		return repository.findByIdOrName(id, name).orElseThrow(new GroupNotFoundException("Grupo não encontrado."));
	}

	public Group findById(Long id) {
		return this.find(id);
	}

	public Group findByName(String string) {
		return this.repository.findByName(string)
				.orElseThrow(new GroupNotFoundException("Grupo não encontrado"));
	}

	public void saveAll(List<Group> groups) {
		this.repository.saveAll(groups);
	}	
}
