package br.com.alg.trufflesapi.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alg.trufflesapi.exceptions.AccountNotFoundException;
import br.com.alg.trufflesapi.model.Account;
import br.com.alg.trufflesapi.model.User;
import br.com.alg.trufflesapi.repositories.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repository;
	
	@Autowired
	private UserService userService;

	public List<Account> listAll() {
		return repository.findAll();
	}

	public Account save(Account account) {
		
		User user = userService.find(account.getUser().getId());
		account.setUser(user);
		account.setDtStart(new Date());
		
		return repository.save(account);
	}

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
}