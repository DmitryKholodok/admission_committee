package by.issoft.kholodok.service;

import by.issoft.kholodok.exception.BadUserRoleException;
import by.issoft.kholodok.exception.EnrolleeDataServiceException;
import by.issoft.kholodok.model.EnrolleeData;

public interface EnrolleeDataService {

    EnrolleeData findById(int id);
    void update(EnrolleeData enrolleeData);
    void remove(EnrolleeData enrolleeData);
    void save(EnrolleeData enrolleeData) throws EnrolleeDataServiceException, BadUserRoleException;

}
