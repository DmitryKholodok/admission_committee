package by.issoft.kholodok.dao.query;

public interface UserQueryProvider {

    String findByLogin(String login);
    String findAll();

}
