package net.codejava;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SupermarketController.class)
public class SupermarketControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupermarketService supermarketService;

    @Test
    public void testViewHomePage() throws Exception {
    	Product product = new Product();
    	product.setCode("001");
    	product.setDescription("Mela");
    	product.setCategory("Frutta");
    	product.setQuantity(10);
        List<Product> listProducts = Arrays.asList(product);
        
        Mockito.when(supermarketService.getAll()).thenReturn(listProducts);

        mockMvc.perform(get("/"))
               .andExpect(status().isOk())
               .andExpect(view().name("index"))
               .andExpect(model().attribute("listProducts", listProducts));
    }

    @Test
    public void testFilterProducts() throws Exception {
    	Product product = new Product();
    	product.setCode("001");
    	product.setDescription("Mela");
    	product.setCategory("Frutta");
    	product.setQuantity(10);
        List<Product> filteredList = Arrays.asList(product);
        
        Mockito.when(supermarketService.getProducts("001", null, null)).thenReturn(filteredList);

        mockMvc.perform(get("/filter")
                .param("code", "001"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("listProducts", filteredList))
                .andExpect(model().attribute("code", "001"));
    }

    @Test
    public void testCreateNewProductPage() throws Exception {
        mockMvc.perform(get("/create"))
               .andExpect(status().isOk())
               .andExpect(view().name("create"))
               .andExpect(model().attributeExists("product"));
    }

    @Test
    public void testShowUpdateProductPage() throws Exception {
    	Product product = new Product();
    	product.setCode("001");
    	product.setDescription("Mela");
    	product.setCategory("Frutta");
    	product.setQuantity(10);
    	
        Mockito.when(supermarketService.getProduct("001")).thenReturn(Optional.of(product));

        mockMvc.perform(get("/update/001"))
               .andExpect(status().isOk())
               .andExpect(view().name("update"))
               .andExpect(model().attribute("product", product));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        mockMvc.perform(get("/delete/001"))
               .andExpect(redirectedUrl("/"));
        
        Mockito.verify(supermarketService).deleteProduct("001");
    }

    @Test
    public void testSaveProduct() throws Exception {
    	Product product = new Product();
    	product.setCode("001");
    	product.setDescription("Mela");
    	product.setCategory("Frutta");
    	product.setQuantity(10);
    	
        mockMvc.perform(post("/save")
                .param("code", "001")
                .param("description", "Mela")
                .param("category", "Frutta")
                .param("quantity", "10"))
                .andExpect(redirectedUrl("/"));

        Mockito.verify(supermarketService).save(product);
    }


    @Test
    public void testShowSearchPage() throws Exception {
        mockMvc.perform(get("/search"))
               .andExpect(status().isOk())
               .andExpect(view().name("search"))
               .andExpect(model().attributeExists("product"));
    }

    @Test
    public void testSearchProduct() throws Exception {
    	Product product = new Product();
    	product.setCode("001");
    	product.setDescription("Mela");
    	product.setCategory("Frutta");
    	product.setQuantity(10);
    	
        Mockito.when(supermarketService.getProduct("001")).thenReturn(Optional.of(product));

        mockMvc.perform(post("/search")
                .param("code", "001"))
                .andExpect(status().isOk())
                .andExpect(view().name("search"))
                .andExpect(model().attribute("product", product));
    }
}
