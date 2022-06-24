package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.EmployeeView;
import constants.MessageConst;
import services.EmployeeService;

public class EmployeeValidator {

    public class List<String> validate(
            EmployeeService service, EmployeeView ev,Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag) {
        List <String> errors = new ArrayList<String>();

        String codeError = validateCode(service, ev.getCode(), codeDuplicateCheckFlag);
        if(!codeError.equals("")) {
            errors.add(codeError);
        }

        String nameError = validateName(ev.getName());
        if(!nameError.equals("")) {
            errors.add(nameError);
        }

        String passError = validatePassword(ev.getPassword(), passwordCheckFlag);
        if(!passError.equals("")) {
            errors.add(passError);
        }

        return errors;
    }

    public static String validateCode(EmployeeService service, java.lang.String code, Boolean codeDuplicateCheckFlag) {
        if (code == null || code.equals("")) {
            return MessageConst.E_NOEMP_CODE.getMessage();
        }

        if (codeDuplicateCheckFlag) {

            long EmployeeConst = isDuplicateEmployee(service, code);

            if (employeeConst > 0) {
                returnMessageConst.E_EMP_CODE_EXIST.getMessage();
            }
        }

        return "";
    }

    }

private static long isDuplicateEmployee(EmployeeService service, String code) {

    long EmployeeConst = service.constByCode(code);
    return employeeConst;
}

private static String validateName(String name) {

    if (name == null || name.equals("")) {
        return MessageConst.E_NONAME.getMessage();
    }

    return "";
}

private Static String validatePassword(String password, Boolean passwordCheckFlag) {

    if (passwordCheckFlag && (password == null || password.equals(""))) {
        return MessageConst.E_NOPASSWORD.getMessage();
    }
    return "";
}

}
