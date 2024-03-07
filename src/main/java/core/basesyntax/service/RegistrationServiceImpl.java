package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int REQUIRED_LENGTH = 6;
    private static final int REQUIRED_AGE = 18;

    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        checkForNullOrIncorrectInput(user);
        if (storageDao.get(user.getLogin()) != null) {
            throw new InvalidInputDataException(
                    "User with this login - " + user.getLogin()
                            + " - already registered. Try to log in");
        }
        if (validateUserInfo(user)) {
            storageDao.add(user);
        }

        return user;
    }

    private void checkForNullOrIncorrectInput(User user) {
        if (user == null) {
            throw new InvalidInputDataException("User can't be null!");
        }
        if (user.getLogin() == null) {
            throw new InvalidInputDataException("Your login can't be null!");
        }
        if (user.getAge() <= 0) {
            throw new InvalidInputDataException("Your age can't be less or equals zero!");
        }
        if (user.getPassword() == null) {
            throw new InvalidInputDataException("Password can't be null!");
        }
    }

    private boolean validateUserInfo(User user) {
        if (user.getLogin().length() < REQUIRED_LENGTH) {
            throw new InvalidInputDataException(
                    "Your login length should be at least 6, now: "
                            + user.getLogin().length());
        }
        if (user.getPassword().length() < REQUIRED_LENGTH) {
            throw new InvalidInputDataException(
                    "Your password length should be at least 6, now: "
                            + user.getPassword().length());
        }
        if (user.getAge() < REQUIRED_AGE) {
            throw new InvalidInputDataException(
                    "You must be at least 18 years old");
        }
        return true;
    }
}
