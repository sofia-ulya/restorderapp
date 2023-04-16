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

import my.edu.utem.ftmk.dad.restorderapp.model.OrderType;
import my.edu.utem.ftmk.dad.restorderapp.repository.OrderTypeRepository;

//controller class which define REST web service method.
@RestController
@RequestMapping("/api/ordertypes")
public class OrderTypeRESTController {
	
	@Autowired
	private OrderTypeRepository orderTypeRepository;
	
	//method to delete a record from table ordertype
	@DeleteMapping("{orderTypeId}")
	public ResponseEntity<HttpStatus> deleteOrderType(@PathVariable long orderTypeId){
		orderTypeRepository.deleteById(orderTypeId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	//method to retrieve all records from table ordertype
	@GetMapping
	public List<OrderType> getOrderTypes(){
		return orderTypeRepository.findAll();
	}
	
	//method to retrieve a record from table ordertype with parameter
	@GetMapping("{orderTypeId}")
	public OrderType getOrderType(@PathVariable long orderTypeId) {
		OrderType orderType = orderTypeRepository.findById(orderTypeId).get();
		return orderType;
	}
	
	//method to create a new record in table ordertype
	@PostMapping()
	public OrderType insertOrderType(@RequestBody OrderType orderType) {
		return orderTypeRepository.save(orderType);
	}
	
	//method to update a record in table ordertype
	@PutMapping()
	public OrderType updateOrderType(@RequestBody OrderType orderType) {
		return orderTypeRepository.save(orderType);
	}

}
