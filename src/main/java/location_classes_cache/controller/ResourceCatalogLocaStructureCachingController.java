package location_classes_cache.controller;

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
import location_classes_cache.model.master.ResourceCatalogLocaStructureCache;
import location_classes_cache.services.I_ResourceCatalogLocaStructureCache_Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/resourceCatalogLocaStructureCacheManagement")
public class ResourceCatalogLocaStructureCachingController 
{

//	private static final Logger logger = LoggerFactory.getLogger(ResourceCatalogMasterController.class);

	@Autowired
	private I_ResourceCatalogLocaStructureCache_Service resourceCatalogLocaStructureServ;

	@GetMapping(value = "/getAllResourceCatalogLocaStructuresFromCache/{resCatSeqNo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<CopyOnWriteArrayList<ResourceCatalogLocaStructureCache>> getAllResourceCatalogLocaStructuresFromCache(@PathVariable Long resCatSeqNo) 
	{
		CopyOnWriteArrayList<ResourceCatalogLocaStructureCache> resourceCatalogDTOs=null;
		try {
			resourceCatalogDTOs = resourceCatalogLocaStructureServ.getAllResourceCatalogLocaStructures(resCatSeqNo);
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