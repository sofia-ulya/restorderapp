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

import my.edu.utem.ftmk.dad.restorderapp.model.ProductType;
import my.edu.utem.ftmk.dad.restorderapp.repository.ProductTypeRepository;

public class ProductTypeRESTController {

	@Autowired
	private ProductTypeRepository productTypeRepository;
	
	//method to delete a record from table producttype
	@DeleteMapping("{orderTypeId}")
	public ResponseEntity<HttpStatus> deleteProductType(@PathVariable long productTypeId){
		productTypeRepository.deleteById(productTypeId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//method to retrieve all records from table producttype
	@GetMapping
	public List<ProductType> getProductTypes(){
		return productTypeRepository.findAll();
	}
	
	//method to retrieve a record from table producttype with parameter
	@GetMapping("{productTypeId}")
	public ProductType getProductType(@PathVariable long productTypeId) {
		ProductType productType = productTypeRepository.findById(productTypeId).get();
		return productType;
	}
	
	//method to create a new record in table producttype
	@PostMapping()
	public ProductType insertOrderType(@RequestBody ProductType productType) {
		return productTypeRepository.save(productType);
	}
	
	//method to update a record in table producttype
	@PutMapping()
	public ProductType updateOrderType(@RequestBody ProductType productType) {
		return productTypeRepository.save(productType);
	}
}
