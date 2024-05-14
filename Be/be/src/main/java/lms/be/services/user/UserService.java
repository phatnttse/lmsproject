package lms.be.services.user;

import lms.be.components.JwtTokenUtils;
import lms.be.dtos.UpdateUserDTO;
import lms.be.dtos.UserDTO;
import lms.be.enums.Role;
import lms.be.exceptions.DataNotFoundException;
import lms.be.models.User;
import lms.be.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtils jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public String login(String email, String password) throws Exception {

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new DataNotFoundException("Email or password incorrect");
        }

        User existingUser = optionalUser.get();

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email, password
        );

        //authenticate with Java Spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public String loginGoogle(String email) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()) {
            throw new DataNotFoundException("The account does not exist in the system");
        }

        User existingUser = optionalUser.get();
        return jwtTokenUtil.generateToken(existingUser);
    }


    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        if (jwtTokenUtil.isTokenExpired(token)) throw new Exception("Token is expried");

        String email = jwtTokenUtil.extractEmail(token);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent())
            return user.get();
        return null;
    }

    @Override
    public User updateUser(int userId, UpdateUserDTO updateUserDTO) throws Exception {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        if (updateUserDTO.getName() != null) {
            existingUser.setName(updateUserDTO.getName());
        }
        if (updateUserDTO.getPhoneNumber() != null) {
            existingUser.setPhoneNumber(updateUserDTO.getPhoneNumber());
        }
        if (updateUserDTO.getAddress() != null) {
            existingUser.setAddress(updateUserDTO.getAddress());
        }
        if (updateUserDTO.getDateOfBirth() != null) {
            existingUser.setDateOfBirth(updateUserDTO.getDateOfBirth());
        }
        if (updateUserDTO.getPicture() != null){
            existingUser.setPicture(updateUserDTO.getPicture());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public User updateAvatar(User user) throws Exception {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllTeachers() {
        List<User> teachers = userRepository.findByRole(Role.Teacher.name());
        return teachers;
    }


}

