package vn.gnut.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import vn.gnut.utils.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserRequestDTO implements Serializable {
    @NotBlank(message = "firstName must be not blank")
    private String firstName;
    @NotNull(message = "lastName must be not null")
    private String lastName;
    @Email(message = "email invalid format")
    private String email;
    //    @Pattern(regexp = "^\\d{10}$", message = "phone invalid format")//Kiểu số nguyên(d), 10 kí tự
    @PhoneNumber
    private String phone;
    @NotNull(message = "dateOfBirth must be not null")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dateOfBirth;

    /*
    Cách 1 validation bằng string
        @Pattern(regexp = "^ACTIVE|INACTIVE|NONE$", message = "status must be one in {ACTIVE, INACTIVE, NONE}")
        private String status;

    Cách 2 tạo 1 enum riêng
        *Có thể thay đổi cho nhiều enum khác nhau
        --Ví dụ:
            @EnumPattern(name = "gender", regexp = "MALE|FEMALE|OTHER")
            private Gender status;
    */
    @EnumPattern(name = "status", regexp = "ACTIVE|INACTIVE|NONE")//Thiên về quyền hạn cho admin nhiều hơn
    private UserStatus status;

    //Cách 3
    @GenderSubset(anyOf = {Gender.MALE, Gender.FEMALE, Gender.OTHER})//Nên sử dụng cho người dùng hoặc cho phân quyền
    private Gender gender;

    //Cách 4: Ưu điểm của phương pháp này là có thể áp dụng chung cho tất cả enum và dễ dàng handle exception.
    @NotNull(message = "type must be not null")
    @EnumValue(name = "type", enumClass = UserType.class)
    private String type;

    public UserRequestDTO(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Gender getGender() {
        return gender;
    }

    public String getType() {
        return type;
    }
}
