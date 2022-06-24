package services;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import constants.JpaConst;
import models.Employee;
import models.validators.EmployeeValidator;
import utils.EncryptUtil;

public class EmployeeService {
    public List<EmployeeView> getPerPage(int page) {
        List<Employee> employee = em.createNameQuery(JpaConst.Q_EMP_GET_ALL, Employee.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE) * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return EmployeeConverter.toViewList(employees);
    }

    public long countAll() {
        long empCount = (long) em.createNamedQuery(JpaConst.Q_EMP_COUNT, Long.class)
                .getSingleResult();

        return empCount;
    }

    public EmployeeView findOne(String code, String plainPass, String pepper) {
        Employee e = null;
        try {
            String pass = EncryptUtil.getPasswordEncrypt(plainPass, pepper);

            e = em.createNamedQuery(JpaConst.Q_EMP_GET_BY_CODE_AND_PASS, Employee.class)
                    .setParameter(JpaConst.JPQL_PARM_CODE, code)
                    .setParameter(JpaConst.JPQL_PARM_PASSWORD, pass)
                    .getSingleResult();

        } catch (NoResultException ex) {
        }

        return EmployeeConverter.toView(e);
        }
    }

public EmployeeView FindOne(int id) {
    Employee e = findOneInternal(id);
    return EmployeeConverter.toView(e);
}
public long countByCode(String code) {

    long employee_count = (long) em.createNamedQuery(JpaConst.Q_EMP_COUNT_RESISTERED_BY_CODE, Long.class)
            .setParameter(JpaConst.JPQL_PARM_CODE, code)
            .getSingleResult();
    return employees_count;
}

public List<String> create(EmployeeView ev, String pepper) {

    String pass = EncryptUtil.getPasswordEncrypt(ev.getPassword(), pepper);
    ev.setPassword(pass);

    LocalDateTime now = LocalDateTime.now();
    ev.setCreaterAt(now);
    ev.setUpdatedAt(now);

    List<String> errors = EmployeeValidator.validate(this, ev, true, true);

    if(errors.size() == 0) {
        create(ev);
}

    return errors;
}

public List<String> update(EmployeeView ev, String pepper) {

    EmployeeView saveEmp = findOne(ev.getId());

    boolean validateCode = false;
    if(!savedEmp.getCode().equals(ev.getCode())); {

        validateCode = true;

        savedEmp.setCode(ev.getCode());
    }

    boolean validatePass = false;
    if(ev.getPassword() != null && !ev.getPassword().(equals("")) {

        validatePass = true;

        savedEmp.setPassword(
                EncryptUtil.getPasswordEncrypt(ev.getPassword(), pepper));
    }

    savedEmp.setName(ev.getName());
    savedEmp.setAdminFlag(ev.getAdminFlag());

    LocalDateTime today = LocalDateTime.now();
    savedEmp.setUpdatedAt(today);

    List<String> errors = EmployeeValidator.validate(this, saveEmp, validateCode, validatePass);

    if (errors.size() == 0) {
        update(savedEmp);
    }
    return errors;
}

public void destroy(Integer id) {

    EmployeeView savedEmp = findOne(id);

    LocalDateTime today = LocalDateTime.now();
    savedEmp.setUpdatedAt(today);

    savedEmp.setDeleteFlag(JpaConst.EMP_DEL_TRUE);

    update(saveEmp);

}

public Boolean validateLogin(String code, String plainPass, String pepper) {

    boolean isValidEmployee = false;
    if (code != null && code.equals("") && plainPass != null && !plainPass.equals("")) {
        EmployeeView ev = findOne(code, plainPass, pepper);

        if(ev != null && ev.getId() != null) {

            isValidEmployee = true;
        }
    }

    return isValidEmployee;

}

}
