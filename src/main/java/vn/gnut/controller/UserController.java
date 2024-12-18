package vn.gnut.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;
import vn.gnut.dto.request.UserRequestDTO;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    //@PostMapping(value = "/", headers = "apiKey=v1.0") //Có thể thêm header với 1 số api dành cho mobile chẳng hạn
    //header k bắt buộc. Đây là ví dụ
    //http://localhost:8080/swagger-ui/index.html#/user-controller/addUser (test thử ở đây hoặc postman)
    @RequestMapping(method = RequestMethod.POST, path = "/") //Có thể viết như vậy nữa
    public String addUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        return "User added";
    }

    //Update toàn bộ thì sử dụng method PUT
    @PutMapping("/{userId}")
    public String updateUser(@PathVariable int userId, @Valid @RequestBody UserRequestDTO requestDTO) {
        System.out.println("Request update userID:  " + userId);
        return "User updated";
    }

    //Update 1 phần thì nên sử dụng method PATCH
    @PatchMapping("{userId}")
    public String changeStatus(
            @PathVariable @Min(1) int userId,
//            @RequestParam(required = false) boolean status //Để required = false thì k cần thiết lúc nào
            @Min(1) @RequestParam int status //Test validate
            //cũng phải nhập param này vì set vậy thì nó k bắt buộc
    ) {
        System.out.println("Request change user status: " + userId);
        return "User status changed";
    }

    @DeleteMapping("{userId}")
    public String deleteUser(@Min (1) @PathVariable int userId) { //Validate userID tối thiểu = 1
        System.out.println("Request delete userID: " + userId);
        return "User deleted";
    }

    @GetMapping("/{userId}")
    public UserRequestDTO getUser(@PathVariable int userId) {
        System.out.println("Request get user detail by userID: " + userId);
        return new UserRequestDTO("Tung", "Nguyen", "0869225083", "tungnv@gmail.com");
    }

    @GetMapping("/list")
    public List<UserRequestDTO> getAllUser(
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) {
        System.out.println("Request get user list");
        return List.of(
                new UserRequestDTO("Dinh", "Dieu Linh", "0966121999", "dinhdieulinh@gmail.com"),
                new UserRequestDTO("Dinh", "Dieu Linh", "0966121999", "dinhdieulinh@gmail.com")
        );
    }

}
