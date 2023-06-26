package resources_cache.model.master;

import java.io.Serializable;

public class ResourceCacheMaster implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4503794511815365428L;
	private Long resourceSeqNo;

	public ResourceCacheMaster() {
	}

	public Long getResourceSeqNo() {
		return resourceSeqNo;
	}

	public void setResourceSeqNo(Long resourceSeqNo) {
		this.resourceSeqNo = resourceSeqNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resourceSeqNo == null) ? 0 : resourceSeqNo.hashCode());
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
		ResourceCacheMaster other = (ResourceCacheMaster) obj;
		if (resourceSeqNo == null) {
			if (other.resourceSeqNo != null)
				return false;
		} else if (!resourceSeqNo.equals(other.resourceSeqNo))
			return false;
		return true;
	}

	public ResourceCacheMaster(Long resourceSeqNo) {
		super();
		this.resourceSeqNo = resourceSeqNo;
	}

}