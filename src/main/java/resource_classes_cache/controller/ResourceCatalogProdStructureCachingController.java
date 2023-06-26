package resource_classes_cache.controller;

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
import resource_classes_cache.model.master.ResourceCatalogProdStructureCache;
import resource_classes_cache.services.I_ResourceCatalogProdStructureCache_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/resourceCatalogProdStructureCacheManagement")
public class ResourceCatalogProdStructureCachingController 
{

	private static final Logger logger = LoggerFactory.getLogger(ResourceCatalogProdStructureCachingController.class);

	@Autowired
	private I_ResourceCatalogProdStructureCache_Service resourceCatalogProdStructureServ;

	@GetMapping(value = "/getAllResourceCatalogProdStructuresFromCache/{resCatSeqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ArrayList<ResourceCatalogProdStructureCache>> getAllResourceCatalogProdStructuresFromCache(@PathVariable Long resCatSeqNo) 
	{
		logger.info("catalog : " +resCatSeqNo);
		ArrayList<ResourceCatalogProdStructureCache> resourceCatalogDTOs=null;		 
		try {
			resourceCatalogDTOs = resourceCatalogProdStructureServ.getAllResourceCatalogProdStructures(resCatSeqNo);
			logger.info("catalog results : " +resourceCatalogDTOs.size());
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