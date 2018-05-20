package by.issoft.kholodok.service.impl;

import by.issoft.kholodok.dao.EnrolleeDataDao;
import by.issoft.kholodok.dao.UserDAO;
import by.issoft.kholodok.exception.BadUserRoleException;
import by.issoft.kholodok.exception.EnrolleeDataServiceException;
import by.issoft.kholodok.exception.RoleServiceException;
import by.issoft.kholodok.model.Certificate;
import by.issoft.kholodok.model.EnrolleeData;
import by.issoft.kholodok.model.role.Role;
import by.issoft.kholodok.model.role.RoleEnum;
import by.issoft.kholodok.model.user.User;
import by.issoft.kholodok.service.EnrolleeDataService;
import by.issoft.kholodok.service.RoleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Set;

@Service
public class EnrolleeDataServiceImpl implements EnrolleeDataService {

    private static final Logger LOGGER = LogManager.getLogger(EnrolleeDataServiceImpl.class);

    @Autowired
    private EnrolleeDataDao enrolleeDataDao;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleService roleService;

    @Override
    public EnrolleeData findById(int id) {
        return enrolleeDataDao.findById(id);
    }

    @Override
    public void update(EnrolleeData currEd, EnrolleeData newEd) {
        currEd.setCertificates(newEd.getCertificates());
        currEd.getCertificates().forEach(x -> enrolleeDataDao.remove(x));

        currEd.getCertificates().forEach(x -> x.setEnrolleeData(currEd));
        currEd.setSpecialtyEnrollee(newEd.getSpecialtyEnrollee());
        currEd.setBasicCertificate(newEd.getBasicCertificate());
        enrolleeDataDao.update(currEd);
    }

    @Override
    public void remove(EnrolleeData enrolleeData) {
        enrolleeDataDao.remove(enrolleeData);
    }

    @Override
    public void save(EnrolleeData enrolleeData) throws EnrolleeDataServiceException, BadUserRoleException {
        try {
            int userId = enrolleeData.getId();
            EnrolleeData testEnrolleeData = enrolleeDataDao.findById(userId);
            if (testEnrolleeData == null) {
                User testUser = userDAO.findById(userId);
                if (testUser != null) {
                    Role userRole = roleService.retrieveUserRole(testUser);
                    if (roleService.isRoleEnrollee(userRole)) {

                        enrolleeDataDao.save(enrolleeData);
                    } else {
                        throw new BadUserRoleException("It is impossible to create for a user with id " + userId + " enrolleeData, " +
                                "because he is obliged to have the role " + RoleEnum.ENROLLEE.getValue() + ", but now he has " + userRole.getName());
                    }
                } else {
                    throw new EnrolleeDataServiceException("User with id " + userId + " was not found!");
                }
            } else {
                throw new EnrolleeDataServiceException("EnrolleeData with id " + userId + " already exists!");
            }
        } catch (RoleServiceException e) {
            LOGGER.fatal(e);
            throw new RuntimeException(e);
        } catch (Exception e) {
            LOGGER.fatal(e);
            throw new EnrolleeDataServiceException(e);
        }
    }

}
