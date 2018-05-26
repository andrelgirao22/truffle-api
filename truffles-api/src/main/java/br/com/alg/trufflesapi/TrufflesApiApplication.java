package br.com.alg.trufflesapi;

import java.util.Arrays;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import br.com.alg.trufflesapi.exceptions.GroupNotFoundException;
import br.com.alg.trufflesapi.model.Account;
import br.com.alg.trufflesapi.model.Group;
import br.com.alg.trufflesapi.repositories.AccountRepository;
import br.com.alg.trufflesapi.repositories.GroupRepository;

@SpringBootApplication
public class TrufflesApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TrufflesApiApplication.class, args);
	}

	@Autowired
	private GroupRepository groupRepositoyry;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		
		Group groupAdmin = null;
		
		try {
			groupAdmin = this.groupRepositoyry.findByName("ROLE_ADMIN")
					.orElseThrow(new GroupNotFoundException("Grupo nao encontrado"));
		} catch(GroupNotFoundException e) {
			
			groupAdmin = new Group(null, "ROLE_ADMIN");
			Group groupDev = new Group(null, "ROLE_DEV");
			Group groupUser = new Group(null, "ROLE_USER");
			
			Date date = new Date();
			
			Account accountAdmin = new Account(null, 
							"Administrador", "admin@truffle.com.br", 
							"{bcrypt}$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", 
							null, date, null, true);
			
			Account accountDev = new Account(null, 
					"Developer", "dev@truffle.com.br", 
					"{bcrypt}$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", 
					null, date, null, true);
			
			
			Account accountUser = new Account(null, 
					"User", "user@truffle.com.br", 
					"{bcrypt}$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra", 
					null, date, null, true);
			
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
