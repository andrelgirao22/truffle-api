package br.com.alg.trufflesapi.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alg.trufflesapi.exceptions.GroupNotFoundException;
import br.com.alg.trufflesapi.model.Account;
import br.com.alg.trufflesapi.model.Group;
import br.com.alg.trufflesapi.repositories.AccountRepository;
import br.com.alg.trufflesapi.repositories.GroupRepository;

@Service
public class DBService {

	@Autowired
	private GroupRepository groupRepositoyry;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Transactional
	public void instantiateDatabase() {
		
		Group groupAdmin = null;
		
		try {
			groupAdmin = this.groupRepositoyry.findByName("ROLE_ADMIN")
					.orElseThrow(new GroupNotFoundException("Grupo nao encontrado"));
		} catch(GroupNotFoundException e) {
			
			groupAdmin = new Group(null, "ROLE_ADMIN");
			Group groupDev = new Group(null, "ROLE_DEV");
			Group groupUser = new Group(null, "ROLE_USER");
			
			
			Account accountAdmin = new Account(null, 
							"Administrador", "admin@truffle.com.br", 
							"{bcrypt}$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra","");
			
			Account accountDev = new Account(null, 
					"Developer", "dev@truffle.com.br", 
					"{bcrypt}$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra","");
			
			
			Account accountUser = new Account(null, 
					"User", "user@truffle.com.br", 
					"{bcrypt}$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", "");
			
			groupAdmin.getAccounts().add(accountAdmin);
			groupUser.getAccounts().add(accountUser);
			groupDev.getAccounts().add(accountDev);
			
			accountAdmin.getGroups().add(groupAdmin);
			accountUser.getGroups().add(groupUser);
			accountDev.getGroups().add(groupDev);
			
			this.groupRepositoyry.saveAll(Arrays.asList(groupAdmin, groupDev, groupUser));
			this.accountRepository.saveAll(Arrays.asList(accountAdmin, accountUser, accountDev));
			
		}

	}
	
}
