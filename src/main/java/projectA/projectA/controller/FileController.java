package projectA.projectA.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projectA.projectA.entity.Company;
import projectA.projectA.entity.User;
import projectA.projectA.exception.UserException;
import projectA.projectA.model.Response;
import projectA.projectA.model.companyModel.CompanyUploadProFile;
import projectA.projectA.model.UploadFileReq;
import projectA.projectA.repository.CompanyRepository;
import projectA.projectA.repository.UserRepository;
import projectA.projectA.service.TokenService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {
    public static String uploadDirectory = System.getProperty("user.dir");
    public final UserRepository userRepository;
    public final TokenService tokenService;
    public final CompanyRepository companyRepository;

    public FileController(UserRepository userRepository, TokenService tokenService, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.companyRepository = companyRepository;
    }

    @PostMapping("/image/user-profile")
    public Object uploadProfilePicture(@RequestParam("fileName") MultipartFile file) throws IOException {

        System.out.println(uploadDirectory);
        User user = tokenService.getUserByIdToken();
        String dir = new UploadFileReq().getDirUserProfile();
        String timeStamp = new UploadFileReq().getTimeStamp();
        String imgName = new UploadFileReq().getImgName();

        //validate file
        if (file == null) {
            //throw error
            throw UserException.notFoundId();
        }

        //validate size
        if (file.getSize() > 1048576 * 5) {
            //throw error
            throw UserException.loginFailEmailNotFound();
        }
        String contentType = file.getContentType();
        if (contentType == null) {
            //throw  error
            throw UserException.passwordOldIncorrect();
        }

        StringBuilder fileNames = new StringBuilder();

        Path fileNameAndPath = Paths.get(uploadDirectory + dir, imgName);
        fileNames.append(file.getOriginalFilename());
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            byte[] bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Object, Object> img = new HashMap<>();
        img.put("url", "http://localhost:8080" + dir + imgName);
        img.put("name", imgName);
        user.setPicture(imgName);
        userRepository.save(user);

        return new Response().ok("upload success", "img", img);

    }

    @PostMapping("/image/company-profile")
    public Object uploadCompanyProfilePicture(@RequestParam("fileName") MultipartFile file) throws IOException {

        Company companyByIdToken = tokenService.getCompanyByIdToken();
        String dir = new CompanyUploadProFile().getDir();
        String timeStamp = new CompanyUploadProFile().getTimeStamp();
        String imgName = new CompanyUploadProFile().getImgName();

        //validate file
        if (file == null) {
            //throw error
            throw UserException.notFoundId();
        }

        //validate size
        if (file.getSize() > 1048576 * 5) {
            //throw error
            throw UserException.loginFailEmailNotFound();
        }
        String contentType = file.getContentType();
        if (contentType == null) {
            //throw  error
            throw UserException.passwordOldIncorrect();
        }

        StringBuilder fileNames = new StringBuilder();

        Path fileNameAndPath = Paths.get(uploadDirectory + dir, imgName);
        fileNames.append(file.getOriginalFilename());
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            byte[] bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Object, Object> img = new HashMap<>();
        img.put("url", "http://localhost:8080" + dir + imgName);
        img.put("name", imgName);
        companyByIdToken.setPicture(imgName);
        companyRepository.save(companyByIdToken);

        return new Response().ok("upload success", "img", img);

    }
}
