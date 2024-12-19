package vn.gnut.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Constraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import vn.gnut.dto.request.UserRequestDTO;
import vn.gnut.dto.response.ResponseData;
import vn.gnut.dto.response.ResponseSuccess;
import vn.gnut.utils.Gender;
import vn.gnut.utils.UserStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    //@PostMapping(value = "/", headers = "apiKey=v1.0") //Có thể thêm header với 1 số api dành cho mobile chẳng hạn
    //header k bắt buộc. Đây là ví dụ
    //http://localhost:8080/swagger-ui/index.html#/user-controller/addUser (test thử ở đây hoặc postman)
//    @RequestMapping(method = RequestMethod.POST, path = "/") //Có thể viết như vậy nữa
    @PostMapping("/")
    public ResponseData<?> addUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        System.out.println("Request add user: " + requestDTO.getFirstName() + " " + requestDTO.getLastName());
        //Thành công
//        return new ResponseData<>(HttpStatus.CREATED.value(), "Create successfully", 1);

        //Lỗi
        return new ResponseData<>(HttpStatus.BAD_REQUEST.value(), "Can not create user");
    }

    //Update toàn bộ thì sử dụng method PUT
    @PutMapping("/{userId}")
    public ResponseData<?> updateUser(@PathVariable int userId, @Valid @RequestBody UserRequestDTO requestDTO) {
        System.out.println("Request update userID:  " + userId);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User updated successfully");
    }

    //Update 1 phần thì nên sử dụng method PATCH
    @PatchMapping("{userId}")
    public ResponseData<?> changeStatus(
            @PathVariable @Min(1) int userId,
//            @RequestParam(required = false) boolean status //Để required = false thì k cần thiết lúc nào
            @Min(1) @RequestParam int status //Test validate
            //cũng phải nhập param này vì set vậy thì nó k bắt buộc
    ) {
        System.out.println("Request change user status: " + userId);
        return new ResponseData<>(HttpStatus.ACCEPTED.value(), "User change status successfully");
    }

    @DeleteMapping("{userId}")
    public ResponseData<?> deleteUser(@Min(1) @PathVariable int userId) { //Validate userID tối thiểu = 1
        System.out.println("Request delete userID: " + userId);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "User deleted successfully");
    }

    @GetMapping("/{userId}")
    public ResponseData<UserRequestDTO> getUser(@PathVariable int userId) {
        System.out.println("Request get userID: " + userId);
        try {
            Date dateOfBirth = new SimpleDateFormat("dd/MM/yyyy").parse("28/09/2004");
            return new ResponseData<>(HttpStatus.OK.value(), "Success", new UserRequestDTO(
                    "Nguyen", "Van Tung", "tungnv@gmail.com", "0869225083", dateOfBirth, UserStatus.ACTIVE, Gender.MALE, "admin"));
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing date", e);
        }
    }

    @GetMapping("/list")
    public ResponseData<List<UserRequestDTO>> getAllUser(
            @RequestParam(required = false) String email,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize) throws ParseException {
        Date dateOfBirth1 = new SimpleDateFormat("dd/MM/yyyy").parse("03/07/2004");
        Date dateOfBirth2 = new SimpleDateFormat("dd/MM/yyyy").parse("28/09/2004");
        System.out.println("Request get user list");
        return new ResponseData<>(HttpStatus.OK.value(), "users", List.of(
                new UserRequestDTO("Dinh", "Dieu Linh", "0966121999", "dinhdieulinh@gmail.com",dateOfBirth1, UserStatus.ACTIVE, Gender.FEMALE, "admin"),
                new UserRequestDTO("Nguyen", "Van Tung", "0869225083", "tungnv@gmail.com", dateOfBirth2, UserStatus.ACTIVE, Gender.MALE, "admin")
        ));
    }

}
