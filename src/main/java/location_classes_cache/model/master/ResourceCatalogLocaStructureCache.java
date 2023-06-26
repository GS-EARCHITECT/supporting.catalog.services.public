package location_classes_cache.model.master;

import java.io.Serializable;

public class ResourceCatalogLocaStructureCache implements Serializable 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3312509033220225405L;
	private Long locationClassSeqNo;
	private Long parLocationClassSeqNo;
	private Long resourceCatalogSeqNo;

	public Long getLocationClassSeqNo() {
		return locationClassSeqNo;
	}

	public void setLocationClassSeqNo(Long locationClassSeqNo) {
		this.locationClassSeqNo = locationClassSeqNo;
	}

	public Long getParLocationClassSeqNo() {
		return parLocationClassSeqNo;
	}

	public void setParLocationClassSeqNo(Long parLocationClassSeqNo) {
		this.parLocationClassSeqNo = parLocationClassSeqNo;
	}

	public Long getLocationCatalogSeqNo() {
		return resourceCatalogSeqNo;
	}

	public void setLocationCatalogSeqNo(Long resourceCatalogSeqNo) {
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((parLocationClassSeqNo == null) ? 0 : parLocationClassSeqNo.hashCode());
		result = prime * result + ((resourceCatalogSeqNo == null) ? 0 : resourceCatalogSeqNo.hashCode());
		result = prime * result + ((locationClassSeqNo == null) ? 0 : locationClassSeqNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResourceCatalogLocaStructureCache other = (ResourceCatalogLocaStructureCache) obj;
		if (parLocationClassSeqNo == null) {
			if (other.parLocationClassSeqNo != null)
				return false;
		} else if (!parLocationClassSeqNo.equals(other.parLocationClassSeqNo))
			return false;
		if (resourceCatalogSeqNo == null) {
			if (other.resourceCatalogSeqNo != null)
				return false;
		} else if (!resourceCatalogSeqNo.equals(other.resourceCatalogSeqNo))
			return false;
		if (locationClassSeqNo == null) {
			if (other.locationClassSeqNo != null)
				return false;
		} else if (!locationClassSeqNo.equals(other.locationClassSeqNo))
			return false;
		return true;
	}

	public ResourceCatalogLocaStructureCache(Long locationClassSeqNo, Long parLocationClassSeqNo,
			Long resourceCatalogSeqNo) {
		super();
		this.locationClassSeqNo = locationClassSeqNo;
		this.parLocationClassSeqNo = parLocationClassSeqNo;
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
	}

	public ResourceCatalogLocaStructureCache() {
		super();
	}

}