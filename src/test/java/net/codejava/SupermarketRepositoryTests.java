package net.codejava;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class SupermarketRepositoryTests {

	@Autowired
	private SupermarketRepository supermarketRepository;

	@BeforeEach
	public void setUp() {
	    supermarketRepository.deleteAll();

	    Product product1 = new Product();
	    product1.setCode("ABC123");
	    product1.setDescription("Mela");
	    product1.setCategory("Frutta");
	    product1.setQuantity(10);

	    Product product2 = new Product();
	    product2.setCode("ABC789");
	    product2.setDescription("Pera");
	    product2.setCategory("Frutta");
	    product2.setQuantity(10);

	    supermarketRepository.save(product1);
	    supermarketRepository.save(product2);
	}


	@Test
	public void testFindByCode() {
		List<Product> products = supermarketRepository.find("ABC", null, null);
		assertThat(products).hasSize(2);
		assertThat(products.get(0).getCode()).isEqualTo("ABC123");
		assertThat(products.get(1).getCode()).isEqualTo("ABC789");
	}

	@Test
	public void testFindByDescription() {
		List<Product> products = supermarketRepository.find(null, "Pera", null);
		assertThat(products).hasSize(1);
		assertThat(products.get(0).getDescription()).isEqualTo("Pera");
	}

	@Test
	public void testFindByCategory() {
		List<Product> products = supermarketRepository.find(null, null, "Frutt");
		assertThat(products).hasSize(2);
		assertThat(products.get(0).getCategory()).isEqualTo("Frutta");
		assertThat(products.get(1).getCategory()).isEqualTo("Frutta");
	}

	@Test
	public void testFindByCodeAndCategory() {
		List<Product> products = supermarketRepository.find("ABC", "Per", null);
		assertThat(products).hasSize(1);
		assertThat(products.get(0).getCode()).isEqualTo("ABC789");
	}
}
