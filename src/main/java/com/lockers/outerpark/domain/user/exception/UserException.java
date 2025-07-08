package com.lockers.outerpark.domain.user.exception;

import com.lockers.outerpark.common.exception.BusinessException;

public class UserException {

    public static class UserNotFoundException extends BusinessException {
        public UserNotFoundException() {
            super(UserErrorCode.USER_NOT_FOUND);
        }
    }

    public static class EmailAlreadyExistsException extends BusinessException {
        public EmailAlreadyExistsException() {
            super(UserErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    public static class InvalidUserRoleException extends BusinessException {
        public InvalidUserRoleException() {
            super(UserErrorCode.INVALID_USER_ROLE);
        }
    }

    public static class UserDeletedException extends BusinessException {
        public UserDeletedException() {
            super(UserErrorCode.USER_ALREADY_DELETED);
        }
    }

    public static class NicknameAlreadyExistsException extends BusinessException {
        public NicknameAlreadyExistsException() {
            super(UserErrorCode.NICKNAME_ALREADY_EXISTS);
        }
    }
}
