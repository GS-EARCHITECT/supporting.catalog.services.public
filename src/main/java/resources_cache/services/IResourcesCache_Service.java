package resources_cache.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
public interface IResourcesCache_Service
{
public List<Long> getAllResourcesForCatalog(Long resCatSeqNo) throws InterruptedException, ExecutionException;    
public ArrayList<Long> getAllResources() throws InterruptedException, ExecutionException;
}