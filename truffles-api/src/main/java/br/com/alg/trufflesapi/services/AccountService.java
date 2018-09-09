package br.com.alg.trufflesapi.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alg.trufflesapi.exceptions.AccountEmailExistException;
import br.com.alg.trufflesapi.exceptions.AccountNotFoundException;
import br.com.alg.trufflesapi.model.Account;
import br.com.alg.trufflesapi.model.Address;
import br.com.alg.trufflesapi.model.City;
import br.com.alg.trufflesapi.model.Group;
import br.com.alg.trufflesapi.model.dto.AccountDTO;
import br.com.alg.trufflesapi.repositories.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private GroupService groupService;

	public List<AccountDTO> listAll() {
		return repository.findAll().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
	}

	public Account save(Account account) {
		
		Optional<Account> optional = this.findByEmail(account.getEmail());
		
		if(optional.isPresent()) {
			throw new AccountEmailExistException("Email já cadastrado");
		}
		
		if(account.getId() == null) {
			Group userGroup = this.groupService.findByName("ROLE_USER");
			String password = account.getPassword();
			account.setPassword("{bcrypt}" + new BCryptPasswordEncoder().encode(password));
			account.setDtStart(new Date());
			account.getGroups().add(userGroup);
		}
		return repository.save(account);
	}
	
	public Account fromDto(AccountDTO accountDTO) {
		Account account = 
				new Account(null, accountDTO.getName(), accountDTO.getEmail(), accountDTO.getPassword(), accountDTO.getRegister());
		
		City city = new City(accountDTO.getCityId(), null, null);
		
		Address address = 
				new Address(null, account, accountDTO.getAddressName(), 
						accountDTO.getAddressNumber(), accountDTO.getNeighborhood(), 
						city, accountDTO.getComplement(), accountDTO.getPostalCode());
		
		account.getAddresses().add(address);
		
		return account;
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

	public Optional<Account> findByEmail(String email) {
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

	public static Account authenticated() {
		try {			
			return (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
