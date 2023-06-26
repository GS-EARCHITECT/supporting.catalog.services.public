package compclasses_cache.model.master;

import java.io.Serializable;

public class ResourceCatalogCompClassesCache implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8836518265964589501L;
	private Long compClassSeqNo;
	private Long resourceCatalogSeqNo;

	public Long getCompClassSeqNo() {
		return compClassSeqNo;
	}

	public void setCompClassSeqNo(Long compClassSeqNo) {
		this.compClassSeqNo = compClassSeqNo;
	}

	public Long getResourceCatalogSeqNo() {
		return resourceCatalogSeqNo;
	}

	public void setResourceCatalogSeqNo(Long resourceCatalogSeqNo) {
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((compClassSeqNo == null) ? 0 : compClassSeqNo.hashCode());
		result = prime * result + ((resourceCatalogSeqNo == null) ? 0 : resourceCatalogSeqNo.hashCode());
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
		ResourceCatalogCompClassesCache other = (ResourceCatalogCompClassesCache) obj;
		if (compClassSeqNo == null) {
			if (other.compClassSeqNo != null)
				return false;
		} else if (!compClassSeqNo.equals(other.compClassSeqNo))
			return false;
		if (resourceCatalogSeqNo == null) {
			if (other.resourceCatalogSeqNo != null)
				return false;
		} else if (!resourceCatalogSeqNo.equals(other.resourceCatalogSeqNo))
			return false;
		return true;
	}

	public ResourceCatalogCompClassesCache(Long compClassSeqNo, Long resourceCatalogSeqNo) {
		super();
		this.compClassSeqNo = compClassSeqNo;
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
	}

	public ResourceCatalogCompClassesCache() {
		super();
	}

}