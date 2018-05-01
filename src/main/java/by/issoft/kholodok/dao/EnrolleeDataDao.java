package by.issoft.kholodok.dao;

import by.issoft.kholodok.model.EnrolleeData;

public interface EnrolleeDataDao {

    EnrolleeData findById(int id);
    void update(EnrolleeData enrolleeData);
    void remove(EnrolleeData enrolleeData);
    void save(EnrolleeData enrolleeData);
}
