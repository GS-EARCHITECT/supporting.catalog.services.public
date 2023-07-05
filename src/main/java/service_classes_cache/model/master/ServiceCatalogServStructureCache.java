package service_classes_cache.model.master;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SERVICE_CATALOG_SERVSTRUCTURE database table.
 * 
 */
@Entity
@Table(name="SERVICE_CATALOG_SERVSTRUCTURE")
public class ServiceCatalogServStructureCache implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ServiceCatalogServStructureCachePK id;

	public ServiceCatalogServStructureCache() {
	}

	public ServiceCatalogServStructureCachePK getId() {
		return this.id;
	}

	public void setId(ServiceCatalogServStructureCachePK id) {
		this.id = id;
	}
	

}