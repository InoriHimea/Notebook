package application.bean;

import java.util.List;

public class Result<T> {

	public int currentPage;// ��ǰҳ
	public int totalCount;// �ܼ�¼
	public List<T> recordList;// �����
	public int pageCount; // ��ҳ��

	public Result(int currentPage, int totalCount, List<T> recordList, int pageCount) {
		this.currentPage = currentPage;
		this.totalCount = totalCount;
		this.recordList = recordList;
		this.pageCount = pageCount;
	}

	@Override
	public String toString() {
		return "Result [currentPage=" + currentPage + ", recordList's length = " + recordList.size() + ", totalCount=" + totalCount + ", pageCount=" + pageCount + "]";
	}
}