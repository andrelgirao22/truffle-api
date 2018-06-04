package br.com.alg.trufflesapi.services;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alg.trufflesapi.exceptions.AccountNotFoundException;
import br.com.alg.trufflesapi.model.Account;
import br.com.alg.trufflesapi.model.dto.AccountDTO;
import br.com.alg.trufflesapi.repositories.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repository;

	public List<AccountDTO> listAll() {
		return repository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
	}

	public Account save(Account account) {
		
		if(account.getId() == null) {			
			String password = account.getPassword();
			password = new BCryptPasswordEncoder().encode(password);
			account.setPassword(password);
			account.setDtStart(new Date());
		}
		return repository.save(account);
	}

	@Transactional
	public void update(Account account) {
		checkExist(account.getId());
		repository.save(account);
	}

	public void delete(Long id) {
		checkExist(id);
		repository.deleteById(id);
	}
	
	private void checkExist(Long id) {
		find(id);
	}
	
	public Account find(Long id) {
		return this.repository.findById(id)
				.orElseThrow(new AccountNotFoundException("Conta não encontrada."));
	}

	public Account findByEmail(String email) {
		return repository.findByEmail(email);
	}

	public void saveAll(List<Account> accounts) {
		this.repository.saveAll(accounts);
		
	}

	public String generateNewPassword() {
		
		char passwd [] = new char[10];
		for(int i=0; i< 10; i++) {
			passwd[i] = random();
		}
		
		return new String (passwd);
	}

	private char random() {
		
		Random random = new Random();
		
		int opt = random.nextInt(3);
		if(opt == 0) { //gerar um digito
			return (char) (random.nextInt(10) + 48);
		} else if(opt == 1){ //gera letra maiuscula 
			return (char) (random.nextInt(26) + 65);
		} else { //gera letra minuscula
			return (char) (random.nextInt(26) + 97);
		}
	}
}
