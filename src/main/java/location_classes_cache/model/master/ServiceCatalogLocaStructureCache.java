package location_classes_cache.model.master;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the SERVICE_CATALOG_LOCSTRUCTURE database table.
 * 
 */
@Entity
@Table(name="SERVICE_CATALOG_LOCSTRUCTURE")
public class ServiceCatalogLocaStructureCache implements Serializable 
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ServiceCatalogLocaStructureCachePK id;

	public ServiceCatalogLocaStructureCache() {
	}

	public ServiceCatalogLocaStructureCachePK getId() {
		return this.id;
	}

	public void setId(ServiceCatalogLocaStructureCachePK id) {
		this.id = id;
	}

	public ServiceCatalogLocaStructureCache(ServiceCatalogLocaStructureCachePK id) 
	{
		super();
		this.id = id;
	}
	
	

}