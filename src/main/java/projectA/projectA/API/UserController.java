package projectA.projectA.API;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectA.projectA.business.UserBusiness;
import projectA.projectA.exception.BaseException;
import projectA.projectA.mapper.UserMapper;
import projectA.projectA.model.APIResponse;
import projectA.projectA.model.userModel.*;
import projectA.projectA.repository.UserRepository;

@RestController
@RequestMapping("/user")
public class UserController {
  public final UserRepository userRepository;
  public final UserBusiness userBusiness;
  public final UserMapper userMapper;

  public UserController(UserRepository userRepository, UserBusiness userBusiness, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userBusiness = userBusiness;
    this.userMapper = userMapper;
  }

  @PostMapping("/login")
  public ResponseEntity<Object> login(@RequestBody LoginReq request) throws BaseException {
//    String response = userBusiness.Login(request);
    Object respones = userBusiness.LoginUser(request);
//    APIResponse apiResponse = new APIResponse();
//    apiResponse.setData(response);
    return ResponseEntity.ok(respones);
  }

  @PostMapping("/register")
  public ResponseEntity<APIResponse> register(@RequestBody RegisterReq request)throws BaseException {
    RegisterResponse response = userBusiness.register(request);
    APIResponse apiResponse = new APIResponse();
    apiResponse.setData(response);
    return ResponseEntity.ok(apiResponse);
  }

  @GetMapping("/profile")
  public ResponseEntity<Object>profile() throws BaseException {
    Object response = userBusiness.findById();
    return ResponseEntity.ok(response);
  }

  @PostMapping("/editUser")
  public ResponseEntity<Object> editUser(@RequestBody UserEditReq request) throws BaseException {
    Object response = userBusiness.editProfile(request);
//    APIResponse apiResponse = new APIResponse();
//    apiResponse.setData(response);
    return ResponseEntity.ok(response);

  }

  @PostMapping("/changePassword")
  public ResponseEntity<Object>changePassword(@RequestBody UserChangePassWord request) throws BaseException {
    Object response = userBusiness.ChangePassWord(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping("/test-user")
  public Object getAllUser(){
    APIResponse apiResponse  = new APIResponse();
    apiResponse.setData(userBusiness.list());
    return apiResponse;
  }
}