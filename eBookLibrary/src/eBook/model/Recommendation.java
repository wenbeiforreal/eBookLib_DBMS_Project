package eBook.model;

import java.sql.Timestamp;

public class Recommendation {
	protected int recommendationId;
	protected Timestamp created;
	protected String loginName;
	protected String iSBN;
	protected Boolean recommends;
	
	public Recommendation(int recommendationId, Timestamp created, String loginName, String iSBN, Boolean recommends) {
		this.recommendationId = recommendationId;
		this.created = created;
		this.loginName = loginName;
		this.iSBN = iSBN;
		this.recommends = recommends;
	}

	public Recommendation(Timestamp created, String loginName, String iSBN, Boolean recommends) {
		this.created = created;
		this.loginName = loginName;
		this.iSBN = iSBN;
		this.recommends = recommends;
	}
	
	public Recommendation(int recommendationId) {
		this.recommendationId = recommendationId;
	}
	
	public int getRecommendationId() {
		return recommendationId;
	}

	public void setRecommendationId(int recommendationId) {
		this.recommendationId = recommendationId;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getiSBN() {
		return iSBN;
	}

	public void setiSBN(String iSBN) {
		this.iSBN = iSBN;
	}

	public Boolean getRecommends() {
		return recommends;
	}

	public void setRecommends(Boolean recommends) {
		this.recommends = recommends;
	}
}
