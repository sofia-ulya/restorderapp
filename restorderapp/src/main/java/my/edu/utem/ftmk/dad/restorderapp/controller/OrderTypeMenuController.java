package my.edu.utem.ftmk.dad.restorderapp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import my.edu.utem.ftmk.dad.restorderapp.model.OrderType;

//class that manage request from front end
//consume REST web service to manage CRUD
@Controller
public class OrderTypeMenuController {
	
	private String defaultURI = "http://localhost:8080/orderapp/api/ordertypes";

	/**method that consume a GET, then display list of record on console
	 * 
	 */
//	@GetMapping("/ordertype/list")
//	public ResponseEntity<String> getOrderTypes(){
//		//the URI for GET order types
//		String uri = "http://localhost:8080/orderapp/api/ordertypes";
//		
//		//get list order types from web service
//		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<OrderType[]> response = restTemplate.getForEntity(uri,OrderType[].class);
//		
//		//parse JSON data to array of object
//		OrderType orderTypes[] = response.getBody();
//		
//		//generate message
//		System.out.print(this.getClass().getSimpleName());
//		System.out.print("Total records: "+orderTypes.length+"\n");
//		
//		//display records
//		for(OrderType orderType:orderTypes) {
//			System.out.print(orderType.getOrderTypeId()+"-");
//			System.out.print(orderType.getCode()+"-");
//			System.out.print(orderType.getName()+"\n");
//		}
//		
//		//for postman status
//		String message = "Check out the message in the console";
//		return new ResponseEntity<>(message,HttpStatus.OK);
//	}
	
	/**method that consume a GET, then display list in browser
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/ordertype/list")
	public String getOrderTypes(Model model) {
		//the URI for GET order types
		String uri = "http://localhost:8080/orderapp/api/ordertypes";
		
		//get list order types from web service
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<OrderType[]> response = restTemplate.getForEntity(uri,OrderType[].class);
		
		//parse JSON data to array of object
		OrderType orderTypes[] = response.getBody();
		
		//parse array to a list of object
		List<OrderType> orderTypeList = Arrays.asList(orderTypes);
		
		//attach list to model as attribute
		model.addAttribute("orderTypes",orderTypeList);
		
		return "ordertypes";
	}
	
	/**this method gets an order type
	 * 
	 * @param orderTypeId
	 * @param model
	 * @return
	 */
	@GetMapping("/ordertype/{orderTypeId}")
	public String getOrderType(@PathVariable Integer orderTypeId, Model model) {
		String pageTitle = "New Order Type";
		OrderType orderType = new OrderType();
		
		//this block get an order type to be updated
		if(orderTypeId>0) {
			//generate new URI and append orderTypeId to it
			String uri = defaultURI+"/"+orderTypeId;
			
			//get an order type from the web service
			RestTemplate restTemplate = new RestTemplate();
			orderType = restTemplate.getForObject(uri, OrderType.class);
			
			//give a new title to the page
			pageTitle = "Edit Order Type";
		}
		
		//attach value to pass to front end
		model.addAttribute("orderType",orderType);
		model.addAttribute("pageTitle",pageTitle);
		
		return "ordertypeinfo";
	}
	
	/**This method will update or add an order type.
	 * 
	 * @param orderType
	 * @return
	 */
	@RequestMapping("/ordertype/save")
	public String updateOrderType(@ModelAttribute OrderType orderType) {
		
		//create a new RestTemplate
		RestTemplate restTemplate = new RestTemplate();
		
		//create request body
		HttpEntity<OrderType> request = new HttpEntity<OrderType>(orderType);
		
		String orderTypeResponse = "";
		
		if(orderType.getOrderTypeId()>0) {
			//this block update a new order type and send request as PUT
			restTemplate.put(defaultURI, request,OrderType.class);
		}else {
			//this block add a new order type and send request as POST
			orderTypeResponse = restTemplate.postForObject(defaultURI, request, String.class);
		}
		
		System.out.print(orderTypeResponse);
		
		//redirect request to display a list of order type
		return "redirect:/ordertype/list";
	}
	
	/**
	 * this method deletes an order type
	 * @param orderTypeId
	 * @return
	 */
	@RequestMapping("/ordertype/delete/{orderTypeId}")
	public String deleteOrderType(@PathVariable Integer orderTypeId) {
		//generate new URI, similar to the mapping in OrderTypeRESTController
		String uri = defaultURI+"/"+orderTypeId;
		
		//send a DELETE request and attach the value of orderTypeId into URI
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(uri,Map.of("orderTypeId",Integer.toString(orderTypeId)));
		
		return "redirect:/ordertype/list";
	}
}
