package br.com.alg.trufflesapi.jwt.service.impl;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.alg.trufflesapi.exceptions.AccountNotFoundException;
import br.com.alg.trufflesapi.model.Account;
import br.com.alg.trufflesapi.model.dto.AccountDTO;
import br.com.alg.trufflesapi.services.AccountService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    protected final Log LOGGER = LogFactory.getLog(getClass());

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountService.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", email));
        } else {
            return account;
        }
    }

    public void changePassword(String oldPassword, String newPassword) {

        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
        String username = currentUser.getName();

        if (authenticationManager != null) {
            LOGGER.debug("Re-authenticating user '"+ username + "' for password change request.");

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
        } else {
            LOGGER.debug("No authentication manager set. can't change Password!");

            return;
        }

        LOGGER.debug("Changing password for user '"+ username + "'");

        Account account = (Account) loadUserByUsername(username);

        account.setPassword(passwordEncoder.encode(newPassword));
        accountService.save(account);

    }

	public String forgotPassword(@Valid AccountDTO accountDto) {
		
		Account account = this.accountService.findByEmail(accountDto.getEmail());
		
		if(account == null) {
			throw new AccountNotFoundException("Email não encontrado");
		}
		
		String newPassword = this.accountService.generateNewPassword();
		
		account.setPassword(this.passwordEncoder.encode(newPassword));
		this.accountService.update(account);
		return account.getPassword();
	}


}
