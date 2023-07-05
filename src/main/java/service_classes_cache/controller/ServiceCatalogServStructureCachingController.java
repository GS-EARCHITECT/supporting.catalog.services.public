package service_classes_cache.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
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
import service_classes_cache.model.master.ServiceCatalogServStructureCache;
import service_classes_cache.services.I_ServiceCatalogServStructureCache_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/serviceCatalogServStructureCacheManagement")
public class ServiceCatalogServStructureCachingController 
{

	private static final Logger logger = LoggerFactory.getLogger(ServiceCatalogServStructureCachingController.class);

	@Autowired
	private I_ServiceCatalogServStructureCache_Service serviceCatalogServStructureServ;

	@GetMapping(value = "/getAllServiceCatalogServStructusrvFromCache/{srvCatSeqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CopyOnWriteArrayList<ServiceCatalogServStructureCache>> getAllServiceCatalogServStructusrvFromCache(@PathVariable Long srvCatSeqNo) 
	{
		CopyOnWriteArrayList<ServiceCatalogServStructureCache> serviceCatalogDTOs=null;		 
		try {
			serviceCatalogDTOs = serviceCatalogServStructureServ.getAllServiceCatalogServStructusrv(srvCatSeqNo);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(serviceCatalogDTOs, HttpStatus.OK);
	}

	@GetMapping(value = "/getAllServiceCatalogServStructusrvFromFluxCache/{srvCatSeqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Flux<ServiceCatalogServStructureCache>> getAllServiceCatalogServStructusrvFromFluxCache(@PathVariable Long srvCatSeqNo) 
	{
		Flux<ServiceCatalogServStructureCache> serviceCatalogDTOs2=null;
			serviceCatalogDTOs2 =Flux.create(emitter -> {
				CompletableFuture<CopyOnWriteArrayList<ServiceCatalogServStructureCache>> future = CompletableFuture.supplyAsync(() -> 
				{
				CopyOnWriteArrayList<ServiceCatalogServStructureCache> serviceCatalogDTOs=null;	
				try {
					serviceCatalogDTOs=serviceCatalogServStructureServ.getAllServiceCatalogServStructusrv(srvCatSeqNo);
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