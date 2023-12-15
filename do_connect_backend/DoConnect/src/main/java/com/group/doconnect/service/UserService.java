package com.group.doconnect.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.group.doconnect.dto.StatusDTO;
import com.group.doconnect.dto.UserRegisterDTO;
import com.group.doconnect.dto.UserResponseDTO;
import com.group.doconnect.dto.UserUpdateDTO;
import com.group.doconnect.entity.User;
import com.group.doconnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	//convert a User object to UserResponseDTO object
	private UserResponseDTO UserToUserResponseDTO(User user) {
		UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getUsername(), user.getName(), user.getEmail(), user.getIsAdmin());
		return userResponseDTO;
	}

	private StatusDTO<UserResponseDTO> convertOptionalUserToStatusDTOUserResponseDTO(Optional<User> optionalUser, String statusMessage) {
		if (optionalUser.isEmpty()) {
			return new StatusDTO<UserResponseDTO>(statusMessage, false, null);
		}
		return new StatusDTO<UserResponseDTO>("", true, UserToUserResponseDTO(optionalUser.get()));
	}

	public StatusDTO<UserResponseDTO> getUserByUsername(String username) {
		Optional<User> targetUser = userRepository.findByUsername(username);
		if (targetUser.isEmpty()) {
			return new StatusDTO<UserResponseDTO>("NOT FOUND", false, null);
		} else {
			return new StatusDTO<UserResponseDTO>("", true, UserToUserResponseDTO(targetUser.get()));
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepository.findByUsername(username);
		if (optionalUser.isEmpty()) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		User user = optionalUser.get();
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}


	public StatusDTO<UserResponseDTO> createUser(UserRegisterDTO userRegisterDTO) {
		if (userRepository.existsByUsername(userRegisterDTO.getUsername())) {
			return new StatusDTO<UserResponseDTO>("User with username " + userRegisterDTO.getUsername() + " already exists. Please create user with different username.", false, null);
		}
		if (userRepository.existsByEmail(userRegisterDTO.getEmail())) {
			return new StatusDTO<UserResponseDTO>("User with email " + userRegisterDTO.getEmail() + " already exists. Please create user with different email.", false, null);
		}
		if (userRegisterDTO.getPassword().length() < 8) {
			return new StatusDTO<UserResponseDTO>("Password should contain at least 8 characters.", false, null);
		}
		User user = new User(userRegisterDTO.getUsername(), userRegisterDTO.getName(), bcryptEncoder.encode(userRegisterDTO.getPassword()), userRegisterDTO.getEmail(), userRegisterDTO.getIsAdmin());
		return new StatusDTO<UserResponseDTO>("User registered!", true, UserToUserResponseDTO(userRepository.save(user)));
	}


	public List<UserResponseDTO> getAllUsers() {
		List<UserResponseDTO> result = new ArrayList<>();
		List<User> users = userRepository.findAll();

		for (User user : users){
			result.add(UserToUserResponseDTO(user));
		}

		return result;
	}


	public StatusDTO<UserResponseDTO> getUserById(Long id) {
		Optional<User> targetedOpt = userRepository.findById(id);
		if (targetedOpt.isEmpty()){
			return new StatusDTO<UserResponseDTO>("NOT FOUND", false, null);
		} else {
			return new StatusDTO<UserResponseDTO>("FOUND USER!", true, UserToUserResponseDTO(targetedOpt.get()));
		}
	}


	public StatusDTO<UserResponseDTO> updateUser(UserUpdateDTO userUpdateDTO, Long id) {
		Optional<User> userOpt = userRepository.findById(id);
		if (userOpt.isEmpty()) {
			return new StatusDTO<UserResponseDTO>("USER NOT EXISTS ID: " + id, false, null);
		}
		User user = userOpt.get();
		user.setIsAdmin(userUpdateDTO.getIsAdmin());
		user.setName(userUpdateDTO.getName());
		return new StatusDTO<UserResponseDTO>("User Updated!", true, UserToUserResponseDTO(userRepository.save(user)));
	}


	public boolean deleteUserById(Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			return false;
		} else {
			userRepository.deleteById(id);
			return true;
		}
	}


	public void deleteAllUsers() {
		userRepository.deleteAll();
	}
}
