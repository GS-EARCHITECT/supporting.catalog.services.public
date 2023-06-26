package compclasses_cache.controller;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import compclasses_cache.model.master.ResourceCatalogCompClassesCache;
import compclasses_cache.services.IResourceCatalogCompClassesCache_Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/resourceCatalogCompClassesCacheManagement")
public class ResourceCatalogCompClassesCachingController 
{

//	private static final Logger logger = LoggerFactory.getLogger(ResourceCatalogMasterController.class);

	@Autowired
	private IResourceCatalogCompClassesCache_Service resourceCatalogCompClassesCacheServ;

	@GetMapping(value = "/getAllResourceCatalogCompClassesFromCache/{resCatSeqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ArrayList<ResourceCatalogCompClassesCache>> getAllResourceCatalogCompClassesFromCache(@PathVariable Long resCatSeqNo) 
	{
		ArrayList<ResourceCatalogCompClassesCache> resourceCatalogDTOs=null;
		try {
			resourceCatalogDTOs = resourceCatalogCompClassesCacheServ.getAllResourceCatalogCompClasses(resCatSeqNo);
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