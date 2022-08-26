package net.codejava;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
	@Autowired
	private ProductRepository repo;
	
	public List<Users> listAll() {
		return repo.findAll();
	}
	
	public void save(Users product) {
		repo.save(product);
	}
	
	public Users get(Long id) {
		return repo.findById(id).get();
	}
	
	public void delete(Long id) {
		repo.deleteById(id);
	}

	public Optional<Users> GetUserById (long id){return repo.findById(id);}
}
