package com.vayber.util;

public class PageSupport {

    //当前页码 -- 来自于用户输入
    private int currentPageNo = 1;

    //数据总数量（表）
    private int totalCount = 0;

    //页面容量
    private int pageSize = 0;

    //总页数    (totalCount/pageSize)+1
    private int totalPageCount = 1;

    public void setCurrentPageNo(int currentPageNo) {
        if (currentPageNo  > 0)
        this.currentPageNo = currentPageNo;
    }

    public void setTotalCount(int totalCount) {
        if (totalCount > 0) {
            this.totalCount = totalCount;

            if (this.totalCount != 0 && this.pageSize != 0) {
                //设置总页数
                setTotalPageCountByRS();
            }
        }
    }

    public void setPageSize(int pageSize) {
        if (pageSize > 0)
        this.pageSize = pageSize;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }


    public  void  setTotalPageCountByRS(){
        if (this.totalCount % this.pageSize == 0){
            this.totalPageCount = this.totalCount / this.pageSize;
        }else if (this.totalCount % this.pageSize > 0){
            this.totalPageCount = (this.totalCount / this.pageSize) + 1;
        }else {
            this.totalPageCount = 0;
        }
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    @Override
    public String toString() {
        return "PageSupport{" +
                "currentPageNo=" + currentPageNo +
                ", totalCount=" + totalCount +
                ", pageSize=" + pageSize +
                ", totalPageCount=" + totalPageCount +
                '}';
    }
}
