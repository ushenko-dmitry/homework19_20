package ru.mail.dimaushenko.repository.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RequestProperties {

    @Value("${sql.request.insert.user}")
    private String sqlRequestInsertUser;
    @Value("${sql.request.insert.item}")
    private String sqlRequestInsertItem;

    @Value("${sql.request.select.user.all}")
    private String sqlRequestSelectAllUsers;
    @Value("${sql.request.select.item.all}")
    private String sqlRequestSelectAllItems;
    @Value("${sql.request.select.item.limit}")
    private String sqlRequestSelectLimitItems;

    @Value("${sql.request.select.user.by_uuid}")
    private String sqlRequestSelectUserByUUID;
    @Value("${sql.request.select.user.by_username}")
    private String sqlRequestSelectUserByUsername;
    @Value("${sql.request.select.item.by_uuid}")
    private String sqlRequestSelectItemByUUID;
    @Value("${sql.request.select.item.by_status}")
    private String sqlRequestSelectItemByStatus;
    @Value("${sql.request.select.item.by_status.limit}")
    private String sqlRequestSelectLimitItemsByStatus;

    @Value("${sql.request.select.user.amount}")
    private String sqlRequestGetAmountUsers;
    @Value("${sql.request.select.item.amount}")
    private String sqlRequestGetAmountItems;
    @Value("${sql.request.select.item.amount.by_status}")
    private String sqlRequestGetAmountItemsByStatus;

    @Value("${sql.request.update.user}")
    private String sqlRequestUpdateUser;
    @Value("${sql.request.update.item}")
    private String sqlRequestUpdateItem;

    @Value("${sql.request.delete.user}")
    private String sqlRequestDeleteUser;
    @Value("${sql.request.delete.item}")
    private String sqlRequestDeleteItem;

    public String getSqlRequestInsertUser() {
        return sqlRequestInsertUser;
    }

    public String getSqlRequestInsertItem() {
        return sqlRequestInsertItem;
    }

    public String getSqlRequestSelectAllUsers() {
        return sqlRequestSelectAllUsers;
    }

    public String getSqlRequestSelectAllItems() {
        return sqlRequestSelectAllItems;
    }

    public String getSqlRequestSelectLimitItems() {
        return sqlRequestSelectLimitItems;
    }

    public String getSqlRequestSelectUserByUUID() {
        return sqlRequestSelectUserByUUID;
    }

    public String getSqlRequestSelectUserByUsername() {
        return sqlRequestSelectUserByUsername;
    }

    public String getSqlRequestSelectItemByUUID() {
        return sqlRequestSelectItemByUUID;
    }

    public String getSqlRequestSelectItemByStatus() {
        return sqlRequestSelectItemByStatus;
    }

    public String getSqlRequestSelectLimitItemsByStatus() {
        return sqlRequestSelectLimitItemsByStatus;
    }

    public String getSqlRequestGetAmountUsers() {
        return sqlRequestGetAmountUsers;
    }

    public String getSqlRequestGetAmountItems() {
        return sqlRequestGetAmountItems;
    }

    public String getSqlRequestGetAmountItemsByStatus() {
        return sqlRequestGetAmountItemsByStatus;
    }

    public String getSqlRequestUpdateUser() {
        return sqlRequestUpdateUser;
    }

    public String getSqlRequestUpdateItem() {
        return sqlRequestUpdateItem;
    }

    public String getSqlRequestDeleteUser() {
        return sqlRequestDeleteUser;
    }

    public String getSqlRequestDeleteItem() {
        return sqlRequestDeleteItem;
    }

}
