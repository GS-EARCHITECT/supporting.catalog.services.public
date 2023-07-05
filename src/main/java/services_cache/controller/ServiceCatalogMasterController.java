package services_cache.controller;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import services_cache.services.IServicesCache_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/servicesCacheManagement")
public class ServiceCatalogMasterController {

	private static final Logger logger = LoggerFactory.getLogger(ServiceCatalogMasterController.class);

	@Autowired
	private IServicesCache_Service serviceCacheServ;

	@GetMapping(value = "/getAllServices", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ArrayList<Long>> getAllServices() 
	{
		ArrayList<Long> serviceCatalogDTOs=null;
		try {
			serviceCatalogDTOs = (ArrayList<Long>)serviceCacheServ.getAllServices();			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(serviceCatalogDTOs, HttpStatus.OK);
	}

	
	@GetMapping(value = "/getAllServicesFromCache/{resCatSeqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ArrayList<Long>> getAllServicesFromCache(@PathVariable Long resCatSeqNo) 
	{
		ArrayList<Long> serviceCatalogDTOs=null;
		try {
			serviceCatalogDTOs = (ArrayList<Long>)serviceCacheServ.getAllServicesForCatalog(resCatSeqNo);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(serviceCatalogDTOs, HttpStatus.OK);
	}

	@GetMapping(value = "/getAllServicesFromFluxCache/{srvCatSeqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Flux<Long>> getAllServicesFromFluxCache(@PathVariable Long srvCatSeqNo) 
	{
		Flux<Long> serviceCatalogDTOs2=null;
		
		serviceCatalogDTOs2 =Flux.create(emitter -> {
			CompletableFuture<ArrayList<Long>> future = CompletableFuture.supplyAsync(() -> 
			{
			ArrayList<Long> serviceCatalogDTOs=null;
			try {
				serviceCatalogDTOs = (ArrayList<Long>)serviceCacheServ.getAllServicesForCatalog(srvCatSeqNo);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return serviceCatalogDTOs;
			});
			future.whenComplete((srvCatDtoList2, exception) -> {
				if (exception == null) {
					srvCatDtoList2.forEach(emitter::next);
					emitter.complete();
				} else {
					emitter.complete();
				}
			});
		});
		
		return new ResponseEntity<>(serviceCatalogDTOs2, HttpStatus.OK);
	}

	
}