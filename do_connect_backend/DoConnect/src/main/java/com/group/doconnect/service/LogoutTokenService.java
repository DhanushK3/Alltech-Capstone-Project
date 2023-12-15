package com.group.doconnect.service;

import java.util.List;

import com.group.doconnect.entity.LogoutToken;
import com.group.doconnect.repository.LogoutTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogoutTokenService {
	
	@Autowired
	LogoutTokenRepository logoutTokenRepository;
	

	public boolean TokenExists(String token) {
		return logoutTokenRepository.existsByToken(token);
	}
	

	public LogoutToken createToken(LogoutToken logoutToken) {
		return logoutTokenRepository.save(logoutToken);
	}


	public List<LogoutToken> createTokens(List<LogoutToken> logoutTokenList) {
		return logoutTokenRepository.saveAllAndFlush(logoutTokenList);
	}
	

	public List<LogoutToken> getAllTokens() {
		return logoutTokenRepository.findAll();
	}


	public void deleteAllTokens() {
		logoutTokenRepository.deleteAllInBatch();
	}

}
