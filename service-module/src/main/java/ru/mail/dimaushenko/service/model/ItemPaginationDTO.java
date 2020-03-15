package ru.mail.dimaushenko.service.model;

public class ItemPaginationDTO {

    private Integer currentPage;
    private Integer itemsPerPage;
    private Integer amountPages;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public Integer getAmountPages() {
        return amountPages;
    }

    public void setAmountPages(Integer amountPages) {
        this.amountPages = amountPages;
    }

}
