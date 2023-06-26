package resource_classes_cache.model.master;

import java.io.Serializable;

public class ResourceCatalogProdStructureCache implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1395717423768549316L;
	private Long resourceClassSeqNo;
	private Long parResourceClassSeqNo;
	private Long resourceCatalogSeqNo;

	public Long getResourceClassSeqNo() {
		return resourceClassSeqNo;
	}

	public void setResourceClassSeqNo(Long resourceClassSeqNo) {
		this.resourceClassSeqNo = resourceClassSeqNo;
	}

	public Long getParResourceClassSeqNo() {
		return parResourceClassSeqNo;
	}

	public void setParResourceClassSeqNo(Long parResourceClassSeqNo) {
		this.parResourceClassSeqNo = parResourceClassSeqNo;
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
		result = prime * result + ((parResourceClassSeqNo == null) ? 0 : parResourceClassSeqNo.hashCode());
		result = prime * result + ((resourceCatalogSeqNo == null) ? 0 : resourceCatalogSeqNo.hashCode());
		result = prime * result + ((resourceClassSeqNo == null) ? 0 : resourceClassSeqNo.hashCode());
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
		ResourceCatalogProdStructureCache other = (ResourceCatalogProdStructureCache) obj;
		if (parResourceClassSeqNo == null) {
			if (other.parResourceClassSeqNo != null)
				return false;
		} else if (!parResourceClassSeqNo.equals(other.parResourceClassSeqNo))
			return false;
		if (resourceCatalogSeqNo == null) {
			if (other.resourceCatalogSeqNo != null)
				return false;
		} else if (!resourceCatalogSeqNo.equals(other.resourceCatalogSeqNo))
			return false;
		if (resourceClassSeqNo == null) {
			if (other.resourceClassSeqNo != null)
				return false;
		} else if (!resourceClassSeqNo.equals(other.resourceClassSeqNo))
			return false;
		return true;
	}

	public ResourceCatalogProdStructureCache(Long resourceClassSeqNo, Long parResourceClassSeqNo,
			Long resourceCatalogSeqNo) {
		super();
		this.resourceClassSeqNo = resourceClassSeqNo;
		this.parResourceClassSeqNo = parResourceClassSeqNo;
		this.resourceCatalogSeqNo = resourceCatalogSeqNo;
	}

	public ResourceCatalogProdStructureCache() {
		super();
	}

}