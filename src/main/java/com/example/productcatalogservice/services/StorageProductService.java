package com.example.productcatalogservice.services;

import com.example.productcatalogservice.dto.UserDto;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.models.State;
import com.example.productcatalogservice.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Primary
public class StorageProductService implements IProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);

        return optionalProduct.orElse(null);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product createProduct(Product input) {
        Optional<Product> productOptional = productRepo.findById(input.getId());
        if(productOptional.isEmpty()) {
            return productRepo.save(input);
        } else {
            //Throw an exception also
            return null;
        }
    }

    @Override
    public Product replaceProduct(Long id, Product input) {
        Optional<Product> productOptional = productRepo.findById(id);
        if(productOptional.isPresent()) {
            input.setId(id);
            input.setCreatedAt(productOptional.get().getCreatedAt());
            return productRepo.save(input);
        } else {
            //We can throw an exception here also
            return null;
        }
    }

    @Override
    public boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if(optionalProduct.isPresent())  {
            Product product = optionalProduct.get();
            if(product.getState().equals(State.ACTIVE)) {
                product.setState(State.DELETED);
                productRepo.save(product);
            } else {
                productRepo.deleteById(id);
            }
            return true;
        }

        return false;
    }

    @Override
    public Product getProductBasedOnUserRole(Long productId, Long userId) {
        Optional<Product> productOptional = productRepo.findById(productId);

        if (productOptional.isPresent()) {
            //if(product.isUnListed())

            //Make call to UserService
            //Add check for status code
            UserDto userDto =
                    restTemplate.getForEntity("http://UserManagementService/users/{userId}",
                            UserDto.class,userId).getBody();

            if(userDto != null) {
                System.out.println("Call to UserService successful");
                System.out.println(userDto.getEmail());

                //Add check for user role as an admin

                return productOptional.get();
            }
        }

        return null;
    }
}
