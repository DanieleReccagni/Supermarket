package net.codejava;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Product {

    @Id @NotBlank
    private String code;
    @NotBlank
    private String description;
    @NotBlank
    private String category;
    @NotNull @Min(0)
    private Integer quantity;

    public String getCode() {
        return code;
    }

    public void setCode(@NotBlank String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(@NotBlank String category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(@NotNull @Min(0) Integer quantity) {
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
    	StringBuilder s = new StringBuilder();
    	s.append("Codice: ");
    	s.append(code);
    	s.append("\nDescrizione: ");
    	s.append(description);
    	s.append("\nCategoria: ");
    	s.append(category);
    	s.append("\nQuantità: ");
    	s.append(quantity);
    	return s.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return quantity == product.quantity &&
                Objects.equals(code, product.code) &&
                Objects.equals(description, product.description) &&
                Objects.equals(category, product.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, description, category, quantity);
    }
}
