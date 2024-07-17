package net.codejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SupermarketService {

    @Autowired
    private SupermarketRepository repo;

    public List<Product> getAll() {
    	return repo.findAll();
    }
    
    public Optional<Product> getProduct(String code) {
    	return repo.findById(code);
    }
    
    public List<Product> getProducts(String code, String description, String category) {
        return repo.find(code, description, category);
    }
    
	public boolean existsByCode(String code) {
	    return repo.existsById(code);
	}

    public Product createProduct(Product product) {
        return repo.save(product);
    }

    public Product updateProduct(String code, Product product) {
        Product updated = repo.findById(code).orElse(null);
        if (updated != null) {
            updated.setCategory(product.getCategory());
            updated.setDescription(product.getDescription());
            updated.setQuantity(product.getQuantity());
            return repo.save(updated);
        }
        return null;
    }

    public void deleteProduct(String code) {
        repo.deleteById(code);
    }

	public void save(Product product) {
		repo.save(product);
	}
}
