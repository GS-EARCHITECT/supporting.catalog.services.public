package services_cache.model.dto;

import java.io.Serializable;

public class ServiceClassDetail_DTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2551497695164125186L;
	private Long serviceClassSeqNo;
	private Long serviceSeqNo;
	private Long partySeqNo;

	public Long getServiceClassSeqNo() {
		return serviceClassSeqNo;
	}

	public void setServiceClassSeqNo(Long serviceClassSeqNo) {
		this.serviceClassSeqNo = serviceClassSeqNo;
	}

	public Long getServiceSeqNo() {
		return serviceSeqNo;
	}

	public void setServiceSeqNo(Long serviceSeqNo) {
		this.serviceSeqNo = serviceSeqNo;
	}

	public Long getPartySeqNo() {
		return partySeqNo;
	}

	public void setPartySeqNo(Long partySeqNo) {
		this.partySeqNo = partySeqNo;
	}

	public ServiceClassDetail_DTO(Long serviceClassSeqNo, Long serviceSeqNo, Long partySeqNo) {
		super();
		this.serviceClassSeqNo = serviceClassSeqNo;
		this.serviceSeqNo = serviceSeqNo;
		this.partySeqNo = partySeqNo;
	}

	public ServiceClassDetail_DTO() {
		super();
	}

}