package lms.be.services.user;

import lms.be.dtos.UpdateUserDTO;
import lms.be.dtos.UserDTO;
import lms.be.models.User;

import java.util.List;

public interface IUserService {
    String login(String email, String password) throws Exception;

    String loginGoogle(String email) throws Exception;

    User getUserDetailsFromToken(String token) throws Exception;

    User updateUser(int userId, UpdateUserDTO updateUserDTO) throws Exception;

    User updateAvatar(User user) throws Exception;

    List<User> getAllTeachers();
}
