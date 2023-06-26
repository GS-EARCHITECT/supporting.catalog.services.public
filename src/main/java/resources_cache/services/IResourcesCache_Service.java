package resources_cache.services;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public interface IResourcesCache_Service
{
public ArrayList<Long> getAllResourcesForCatalog(Long resCatSeqNo) throws InterruptedException, ExecutionException;    
}