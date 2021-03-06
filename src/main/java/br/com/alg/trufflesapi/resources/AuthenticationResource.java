package br.com.alg.trufflesapi.resources;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alg.trufflesapi.jwt.security.TokenHelper;
import br.com.alg.trufflesapi.jwt.service.impl.CustomUserDetailsService;
import br.com.alg.trufflesapi.model.Account;
import br.com.alg.trufflesapi.model.JwtAuthenticationRequest;
import br.com.alg.trufflesapi.model.UserTokenState;
import br.com.alg.trufflesapi.model.dto.AccountDTO;

@RestController
@CrossOrigin("${origin-permited}")
@RequestMapping(value="/auth")
public class AuthenticationResource {

	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenHelper tokenHelper;
	
    @Autowired
    private CustomUserDetailsService userDetailsService;
    
	
	@PostMapping(value="/login")
	public ResponseEntity<?> createAuthenticationToken(
			@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws AuthenticationException, IOException{
		
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						authenticationRequest.getUsername(), 
						authenticationRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		Account account = (Account) authentication.getPrincipal();
		String jws = tokenHelper.generateToken(account.getUsername());
		int expiresIn = tokenHelper.getExpiredIn();
		return ResponseEntity.ok(new UserTokenState(jws, expiresIn));
	}
	
	 	
	@PostMapping(value = "/refresh")
    public ResponseEntity<?> refreshAuthenticationToken(
            HttpServletRequest request,
            HttpServletResponse response,
            Principal principal) {

        String authToken = tokenHelper.getToken( request );

        if (authToken != null && principal != null) {
        	
        	//Account account = (Account)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String refreshedToken = tokenHelper.refreshToken(authToken);
            int expiresIn = tokenHelper.getExpiredIn();

            return ResponseEntity.ok(new UserTokenState(refreshedToken, expiresIn));
        } else {
            UserTokenState userTokenState = new UserTokenState();
            return ResponseEntity.accepted().body(userTokenState);
        }
    }

	    @PostMapping(value = "/change_password")
	    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
	        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);
	        Map<String, String> result = new HashMap<>();
	        result.put( "result", "success" );
	        return ResponseEntity.accepted().body(result);
	    }
	    
	    @PostMapping(value = "/forgot_password")
	    public ResponseEntity<?> forgotPassword(@Valid @RequestBody AccountDTO accountDTO) {
	        String passwd = userDetailsService.forgotPassword(accountDTO);
	        Map<String, String> result = new HashMap<>();
	        result.put( "new password", passwd );
	        return ResponseEntity.accepted().body(result);
	    }

	    static class PasswordChanger {
	        public String oldPassword;
	        public String newPassword;
	    }
	
}
