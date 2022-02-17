package com.csxh.tools;

import java.io.Serializable;
/**
 * 
 * @author xxl
 *	分页的工具类
 */
public class PageTools implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7269677759309236796L;
	
	private int pageNow = 1;//当前页数
	private int pageCount;//每页的记录条数
	private int totalCount;//总的记录数
	private int totalPage;//页面总的页数
	private int startPos; // 每页记录开始的位置，从0开始     
    private boolean hasFirst;// 是否有首页  
    
    private boolean hasPre;// 是否有前一页  
    
    private boolean hasNext;// 是否有下一页  
    
    private boolean hasLast;// 是否有最后一页  
    
    /**
	 * 	通过构造函数传入 总记录数、当前页、每页的记录条数
	 * @param totalCount
	 * @param pageNow
	 * @param pageCount
	 */
    public PageTools(int totalCount, int pageNow,int pageCount) {  
        this.totalCount = totalCount;  
        this.pageNow = pageNow;
        this.pageCount = pageCount;
    }

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * 	取得总页数，总页数=总记录数/总页数
	 * @return
	 */
	public int getTotalPage() {
		totalPage = getTotalCount() / getPageCount();
		
		//总的记录数%每页的记录数如果不为0，总的页面数加1
		if(totalCount == 0) {
			return 1;
		}
		return (totalCount % pageCount == 0) ? totalPage : totalPage + 1;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	/**
	 * 取得记录的初始位置
	 * @return
	 */
	public int getStartPos() {
		return (pageNow - 1) * pageCount;
	}

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}
	
	/**
	 * 是否有第一页
	 * @return
	 */
	public boolean isHasFirst() {
		return (pageNow == 1) ? false : true;
	}

	public void setHasFirst(boolean hasFirst) {
		this.hasFirst = hasFirst;
	}
	
	/**
	 * 	是否有首页
	 * @return
	 */
	
	public boolean isHasPre() {
		// 如果有首页就有前一页，因为有首页就不是第一页
		return isHasFirst() ? true : false;
	}

	public void setHasPre(boolean hasPre) {
		this.hasPre = hasPre;
	}
	
	/**
	 * 	是否有下一页
	 * @return
	 */
	public boolean isHasNext() {
		return isHasLast() ? true : false;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}
	/**
	 * 	如果不是最后一页就有尾页
	 * @return
	 */
	public boolean isHasLast() {
		return (pageNow == getTotalPage()) ? false : true;
	}

	public void setHasLast(boolean hasLast) {
		this.hasLast = hasLast;
	}

	@Override
	public String toString() {
		return "PageTools [pageNow=" + pageNow + ", pageCount=" + pageCount + ", totalCount=" + totalCount
				+ ", totalPage=" + totalPage + ", startPos=" + startPos + ", hasFirst=" + hasFirst + ", hasPre="
				+ hasPre + ", hasNext=" + hasNext + ", hasLast=" + hasLast + "]";
	}
	
	
	
}
