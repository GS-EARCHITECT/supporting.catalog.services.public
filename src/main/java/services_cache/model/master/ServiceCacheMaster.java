package services_cache.model.master;

import java.io.Serializable;

public class ServiceCacheMaster implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4503794511815365428L;
	private Long serviceSeqNo;

	public ServiceCacheMaster() {
	}

	public Long getServiceSeqNo() {
		return serviceSeqNo;
	}

	public void setServiceSeqNo(Long serviceSeqNo) {
		this.serviceSeqNo = serviceSeqNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((serviceSeqNo == null) ? 0 : serviceSeqNo.hashCode());
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
		ServiceCacheMaster other = (ServiceCacheMaster) obj;
		if (serviceSeqNo == null) {
			if (other.serviceSeqNo != null)
				return false;
		} else if (!serviceSeqNo.equals(other.serviceSeqNo))
			return false;
		return true;
	}

	public ServiceCacheMaster(Long serviceSeqNo) {
		super();
		this.serviceSeqNo = serviceSeqNo;
	}

}