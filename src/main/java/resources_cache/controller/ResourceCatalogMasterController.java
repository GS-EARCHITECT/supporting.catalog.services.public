package resources_cache.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import resources_cache.services.IResourcesCache_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/resourcesCacheManagement")
public class ResourceCatalogMasterController {

	private static final Logger logger = LoggerFactory.getLogger(ResourceCatalogMasterController.class);

	@Autowired
	private IResourcesCache_Service resourceCacheServ;

	@GetMapping(value = "/getAllResources", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ArrayList<Long>> getAllResources() 
	{
		ArrayList<Long> resourceCatalogDTOs=null;
		try {
			resourceCatalogDTOs = (ArrayList<Long>)resourceCacheServ.getAllResources();			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(resourceCatalogDTOs, HttpStatus.OK);
	}

	
	@GetMapping(value = "/getAllResourcesFromCache/{resCatSeqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ArrayList<Long>> getAllResourcesFromCache(@PathVariable Long resCatSeqNo) 
	{
		ArrayList<Long> resourceCatalogDTOs=null;
		try {
			resourceCatalogDTOs = (ArrayList<Long>)resourceCacheServ.getAllResourcesForCatalog(resCatSeqNo);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<>(resourceCatalogDTOs, HttpStatus.OK);
	}

}