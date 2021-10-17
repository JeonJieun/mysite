package com.douzone.mysite.vo;

import com.douzone.mysite.dao.BoardDao;

public class PageVo {
	private Long pIndex;
	private Long pages;
	private Long startPage;
	private Long endPage;
	private Long nextPage;
	private Long prevPage;
	private Long ablepIndex;
	
	public PageVo(){
		pIndex = 1L;
		pages = 5L;
		startPage = pages*(pIndex/pages)+1;
		endPage = (pIndex/pages+1)*pages;
		Long count = new BoardDao().countVo();
		ablepIndex = (long) Math.ceil((double)count/(double)pages);
	}

	public Long getpIndex() {
		return pIndex;
	}

	public void setpIndex(Long pIndex) {
		this.pIndex = pIndex;
		setStartPage(pages*(pIndex/pages)+1);
		setEndPage((pIndex/pages+1)*pages);
		setPrevPage(getStartPage()-1);
		setNextPage(getEndPage()+1);
	}

	public Long getPages() {
		return pages;
	}

	public void setPages(Long pages) {
		this.pages = pages;
	}

	public Long getStartPage() {
		return startPage;
	}

	public void setStartPage(Long startPage) {
		this.startPage = startPage;
	}

	public Long getEndPage() {
		return endPage;
	}

	public void setEndPage(Long endPage) {
		this.endPage = endPage;
	}

	public Long getNextPage() {
		return nextPage;
	}

	public void setNextPage(Long nextPage) {
		this.nextPage = nextPage;
	}

	public Long getPrevPage() {
		return prevPage;
	}

	public void setPrevPage(Long prevPage) {
		this.prevPage = prevPage;
	}

	public Long getAblepIndex() {
		return ablepIndex;
	}

	public void setAblepIndex(Long ablepIndex) {
		this.ablepIndex = ablepIndex;
	}
	
	
}
