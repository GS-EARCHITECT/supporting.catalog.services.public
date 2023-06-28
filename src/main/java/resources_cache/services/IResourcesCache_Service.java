package resources_cache.services;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
public interface IResourcesCache_Service
{
public CopyOnWriteArrayList<Long> getAllResourcesForCatalog(Long resCatSeqNo) throws InterruptedException, ExecutionException;    
}