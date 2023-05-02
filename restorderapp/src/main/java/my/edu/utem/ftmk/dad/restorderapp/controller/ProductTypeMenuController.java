package my.edu.utem.ftmk.dad.restorderapp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import my.edu.utem.ftmk.dad.restorderapp.model.ProductType;

@Controller
public class ProductTypeMenuController {

	private String defaultURI = "http://localhost:8080/orderapp/api/producttypes";
	
	/**method that consume a GET, then display list of record on console
	 * 
	 */
//	@GetMapping("/producttype/list")
//	public ResponseEntity<String> getProductTypes(){
//		//the URI for GET product types
//		String uri = "http://localhost:8080/orderapp/api/producttypes";
//		
//		//get list order types from web service
//		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<ProductType[]> response = restTemplate.getForEntity(uri,ProductType[].class);
//		
//		//parse JSON data to array of object
//		ProductType productTypes[] = response.getBody();
//		
//		//generate message
//		System.out.print(this.getClass().getSimpleName());
//		System.out.print("Total records: "+productTypes.length+"\n");
//		
//		//display records
//		for(ProductType productType:productTypes) {
//			System.out.print(productType.getProductTypeId()+"-");
//			System.out.print(productType.getName()+"\n");
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
	@GetMapping("/producttype/list")
	public String getProductTypes(Model model) {
		//the URI for GET product types
		String uri = "http://localhost:8080/orderapp/api/producttypes";;
		
		//get list product types from web service
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ProductType[]> response = restTemplate.getForEntity(uri,ProductType[].class);
		
		//parse JSON data to array of object
		ProductType productTypes[] = response.getBody();
		
		//parse array to a list of object
		List<ProductType> productTypeList = Arrays.asList(productTypes);
		
		//attach list to model as attribute
		model.addAttribute("productTypes",productTypeList);
		
		return "producttypes";
	}
	
	/**this method gets an product type
	 * 
	 * @param productTypeId
	 * @param model
	 * @return
	 */
	@GetMapping("/producttype/{productTypeId}")
	public String getProductType(@PathVariable Integer productTypeId, Model model) {
		String pageTitle = "New Product Type";
		ProductType productType = new ProductType();
		
		//this block get an product type to be updated
		if(productTypeId>0) {
			//generate new URI and append productTypeId to it
			String uri = defaultURI+"/"+productTypeId;
			
			//get an product type from the web service
			RestTemplate restTemplate = new RestTemplate();
			productType = restTemplate.getForObject(uri, ProductType.class);
			
			//give a new title to the page
			pageTitle = "Edit Product Type";
		}
		
		//attach value to pass to front end
		model.addAttribute("productType",productType);
		model.addAttribute("pageTitle",pageTitle);
		
		return "producttypeinfo";
	}
	
	/**This method will update or add an product type.
	 * 
	 * @param productType
	 * @return
	 */
	@RequestMapping("/producttype/save")
	public String updateproductType(@ModelAttribute ProductType productType) {
		
		//create a new RestTemplate
		RestTemplate restTemplate = new RestTemplate();
		
		//create request body
		HttpEntity<ProductType> request = new HttpEntity<ProductType>(productType);
		
		String productTypeResponse = "";
		
		if(productType.getProductTypeId()>0) {
			//this block update a new product type and send request as PUT
			restTemplate.put(defaultURI, request,ProductType.class);
		}else {
			//this block add a new product type and send request as POST
			productTypeResponse = restTemplate.postForObject(defaultURI, request, String.class);
		}
		
		System.out.print(productTypeResponse);
		
		//redirect request to display a list of product type
		return "redirect:/producttype/list";
	}
	
	/**
	 * this method deletes an product type
	 * @param productTypeId
	 * @return
	 */
	@RequestMapping("/producttype/delete/{productTypeId}")
	public String deleteproductType(@PathVariable Integer productTypeId) {
		//generate new URI, similar to the mapping in productTypeRESTController
		String uri = defaultURI+"/"+productTypeId;
		
		//send a DELETE request and attach the value of productTypeId into URI
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(uri,Map.of("productTypeId",Integer.toString(productTypeId)));
		
		return "redirect:/producttype/list";
	}
}
