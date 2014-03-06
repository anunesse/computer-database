package com.excilys.formation.projet.controller.wrapper;


import java.util.List;

public class Page<T> {

public List<T> results;
    public String resultsOrderedBy;
    public String orderDirection;
    public int pageNumber;
    public int pageSize;
    public int recordsOnThisPage;
    public int totalNumberOfRecords;
    public int numberOfPages;

    public List<T> getResults() {
return results;
}



public void setResults(List<T> results) {
this.results = results;
}



public String getResultsOrderedBy() {
return resultsOrderedBy;
}



public void setResultsOrderedBy(String resultsOrderedBy) {
this.resultsOrderedBy = resultsOrderedBy;
}



public String getOrderDirection() {
return orderDirection;
}



public void setOrderDirection(String orderDirection) {
this.orderDirection = orderDirection;
}



public int getPageNumber() {
return pageNumber;
}



public void setPageNumber(int pageNumber) {
this.pageNumber = pageNumber;
}



public int getPageSize() {
return pageSize;
}



public void setPageSize(int pageSize) {
this.pageSize = pageSize;
}



public int getRecordsOnThisPage() {
return recordsOnThisPage;
}



public void setRecordsOnThisPage(int recordsOnThisPage) {
this.recordsOnThisPage = recordsOnThisPage;
}



public int getTotalNumberOfRecords() {
return totalNumberOfRecords;
}



public void setTotalNumberOfRecords(int totalNumberOfRecords) {
this.totalNumberOfRecords = totalNumberOfRecords;
}



public int getNumberOfPages() {
return numberOfPages;
}



public void setNumberOfPages(int numberOfPages) {
this.numberOfPages = numberOfPages;
}
    
    
    
    @Override
    public String toString() {
        return String.format("{ Results: %s, ResultsOrderedBy: %s, OrderDirection: %s, PageNumber: %s, PageSize: %s, RecordsOnThisPage: %s, TotalNumberOfRecords: %s, NumberOfPages: %s }",
            results.size(), resultsOrderedBy, orderDirection, pageNumber,
            pageSize, recordsOnThisPage, totalNumberOfRecords,
            numberOfPages);
    }
}