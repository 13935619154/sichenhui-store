package cn.tedu.store.entity;

/**
 * 收藏数据的实体类
 */
public class Favorite extends BaseEntity {

	private static final long serialVersionUID = -4578448901952764579L;
	private Integer fid;
	private Integer uid;
	private Integer pid;

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fid == null) ? 0 : fid.hashCode());
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
		Favorite other = (Favorite) obj;
		if (fid == null) {
			if (other.fid != null)
				return false;
		} else if (!fid.equals(other.fid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Favorite [fid=" + fid + ", uid=" + uid + ", pid=" + pid + ", toString()=" + super.toString() + "]";
	}

}
