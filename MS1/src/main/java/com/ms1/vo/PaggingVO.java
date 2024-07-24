package com.ms1.vo;

public class PaggingVO {
	private int count;
	private int currentPage;
	private int pageOfContentCount;
	private static final int PAGE_GROUP_OF_COUNT = 5;

	public PaggingVO(int count, int currentPage, int pageOfContentCount) {
		this.count = count;
		this.currentPage = currentPage;
		this.pageOfContentCount = pageOfContentCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageOfContentCount() {
		return pageOfContentCount;
	}

	  public int getTotalPage() {
	        return (count + pageOfContentCount - 1) / pageOfContentCount;
	    }

	    public int getTotalPageGroup() {
	        return (getTotalPage() + PAGE_GROUP_OF_COUNT - 1) / PAGE_GROUP_OF_COUNT;
	    }

	    public int getCurrentPageGroupNo() {
	        return (currentPage + PAGE_GROUP_OF_COUNT - 1) / PAGE_GROUP_OF_COUNT;
	    }

	    public int getStartPageOfPageGroup() {
	        return (getCurrentPageGroupNo() - 1) * PAGE_GROUP_OF_COUNT + 1;
	    }

	    public int getEndPageOfPageGroup() {
	        int lastNo = getCurrentPageGroupNo() * PAGE_GROUP_OF_COUNT;
	        return Math.min(lastNo, getTotalPage());
	    }

	    public boolean isPreviousPageGroup() {
	        return getCurrentPageGroupNo() > 1;
	    }

	    public boolean isNextPageGroup() {
	        return getCurrentPageGroupNo() < getTotalPageGroup();
	    }

	    @Override
	    public String toString() {
	        return "PaggingVO [count=" + count + ", currentPage=" + currentPage + ", pageOfContentCount="
	                + pageOfContentCount + ", PAGE_GROUP_OF_COUNT=" + PAGE_GROUP_OF_COUNT + ", getCurrentPage()="
	                + getCurrentPage() + ", getTotalPage()=" + getTotalPage() + ", getTotalPageGroup()="
	                + getTotalPageGroup() + ", getCurrentPageGroupNo()=" + getCurrentPageGroupNo()
	                + ", getStartPageOfPageGroup()=" + getStartPageOfPageGroup() + ", getEndPageOfPageGroup()="
	                + getEndPageOfPageGroup() + ", isPreviousPageGroup()=" + isPreviousPageGroup() + ", isNextPageGroup()="
	                + isNextPageGroup() + "]";
	    }
	}
