package resources_cache.model.dto;

import java.io.Serializable;

public class ResourceClassDetail_DTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2551497695164125186L;
	private Long resourceClassSeqNo;
	private Long resourceSeqNo;
	private Long partySeqNo;

	public Long getResourceClassSeqNo() {
		return resourceClassSeqNo;
	}

	public void setResourceClassSeqNo(Long resourceClassSeqNo) {
		this.resourceClassSeqNo = resourceClassSeqNo;
	}

	public Long getResourceSeqNo() {
		return resourceSeqNo;
	}

	public void setResourceSeqNo(Long resourceSeqNo) {
		this.resourceSeqNo = resourceSeqNo;
	}

	public Long getPartySeqNo() {
		return partySeqNo;
	}

	public void setPartySeqNo(Long partySeqNo) {
		this.partySeqNo = partySeqNo;
	}

	public ResourceClassDetail_DTO(Long resourceClassSeqNo, Long resourceSeqNo, Long partySeqNo) {
		super();
		this.resourceClassSeqNo = resourceClassSeqNo;
		this.resourceSeqNo = resourceSeqNo;
		this.partySeqNo = partySeqNo;
	}

	public ResourceClassDetail_DTO() {
		super();
	}

}