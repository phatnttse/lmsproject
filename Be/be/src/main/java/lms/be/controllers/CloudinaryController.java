package lms.be.controllers;

import lms.be.models.User;
import lms.be.responses.UserResponse;
import lms.be.services.cloudinary.CloudinaryService;
import lms.be.services.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/cloudinary")
@RequiredArgsConstructor
public class CloudinaryController  {
    private final CloudinaryService cloudinaryService;
    private final IUserService userService;

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<UserResponse> upload(@RequestParam MultipartFile multipartFile,
                                               @RequestHeader("Authorization") String token) throws IOException, Exception {
//        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
//        if (bi == null){
//            return new ResponseEntity<>("Image not valid", HttpStatus.BAD_REQUEST);
//        }
        Map result = cloudinaryService.upload(multipartFile);
        String extractedToken = token.substring(7); // Loai bo Bearer
        User user = userService.getUserDetailsFromToken(extractedToken);
        user.setPicture((String) result.get("url"));
        userService.updateAvatar(user);

        return ResponseEntity.ok(UserResponse.fromUser(user));
    }
}
