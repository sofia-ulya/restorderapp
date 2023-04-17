package my.edu.utem.ftmk.dad.restorderapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.edu.utem.ftmk.dad.restorderapp.model.Product;
import my.edu.utem.ftmk.dad.restorderapp.repository.ProductRepository;

//controller class which define REST web service method.
@RestController
@RequestMapping("/api/products")
public class ProductRESTController {
	@Autowired
	private ProductRepository productRepository;
	
	//method to delete a record from table product
	@DeleteMapping("{productId}")
	public ResponseEntity<HttpStatus> deleteOrderType(@PathVariable long productId){
		productRepository.deleteById(productId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//method to retrieve all records from table product
	@GetMapping
	public List<Product> getOrderTypes(){
		return productRepository.findAll();
	}
	
	//method to retrieve a record from table product with parameter
	@GetMapping("{productId}")
	public Product getOrderType(@PathVariable long productId) {
		Product product = productRepository.findById(productId).get();
		return product;
	}
	
	//method to create a new record in table product
	@PostMapping()
	public Product insertOrderType(@RequestBody Product product) {
		return productRepository.save(product);
	}
	
	//method to update a record in table product
	@PutMapping()
	public Product updateOrderType(@RequestBody Product product) {
		return productRepository.save(product);
	}
}
