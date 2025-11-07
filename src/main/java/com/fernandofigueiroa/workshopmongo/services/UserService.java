package com.fernandofigueiroa.workshopmongo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fernandofigueiroa.workshopmongo.domain.User;
import com.fernandofigueiroa.workshopmongo.dto.UserDTO;
import com.fernandofigueiroa.workshopmongo.repository.UserRepository;
import com.fernandofigueiroa.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

	public List<User> findAll() {
		return repo.findAll();
	}

	public User findById(String id) {
		Optional<User> userOptional = repo.findById(id);

		// Usa o isPresent() para verificar se o Optional contém um valor
		if (userOptional.isPresent()) {
			// Usa o get() para extrair o valor (User)
			return userOptional.get();
		} else {
			// Lança a exceção se estiver vazio
			throw new ObjectNotFoundException("Objeto não encontrado. ID: " + id);
		}
	}

	public User insert(User obj) {
		return repo.insert(obj);
	}

	public void delete(String id) {
		findById(id);
		repo.deleteById(id);
	}

	public User update(User obj) {
		User newObj = findById(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(User newObj, User obj) {
		newObj.setName(obj.getName());
		newObj.setEmail(obj.getEmail());

	}

	public User fromDTO(UserDTO objDto) {
		return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
	}
	
	
}
