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

    @Value("${sql.request.select.user.by_id}")
    private String sqlRequestSelectUserById;
    @Value("${sql.request.select.user.by_username}")
    private String sqlRequestSelectUserByUsername;
    @Value("${sql.request.select.item.by_id}")
    private String sqlRequestSelectItemById;
    @Value("${sql.request.select.item}")
    private String sqlRequestSelectItem;
    @Value("${sql.request.select.item.by_status}")
    private String sqlRequestSelectItemByStatus;

    @Value("${sql.request.select.user.amount}")
    private String sqlRequestGetAmountUsers;
    @Value("${sql.request.select.item.amount}")
    private String sqlRequestGetAmountItems;

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

    public String getSqlRequestSelectUserById() {
        return sqlRequestSelectUserById;
    }

    public String getSqlRequestSelectUserByUsername() {
        return sqlRequestSelectUserByUsername;
    }

    public String getSqlRequestSelectItemById() {
        return sqlRequestSelectItemById;
    }

    public String getSqlRequestSelectItemByStatus() {
        return sqlRequestSelectItemByStatus;
    }

    public String getSqlRequestGetAmountUsers() {
        return sqlRequestGetAmountUsers;
    }

    public String getSqlRequestGetAmountItems() {
        return sqlRequestGetAmountItems;
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

    public String getSqlRequestSelectItem() {
        return sqlRequestSelectItem;
    }

}
