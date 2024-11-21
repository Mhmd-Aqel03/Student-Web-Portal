//package com.example.Login.Auth;
//
//import com.google.common.collect.Lists;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//import static com.example.Security.Security.ApplicationUserRole.*;
//@Repository("Fake")
//public class FakeApplicationUserService implements ApplicationUserDAO {
//    private final PasswordEncoder passwordEncoder;
//
//    @Autowired
//    public FakeApplicationUserService(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
//        return getApplicationUsers().stream().filter(a -> a.getUsername().equals(username)).findFirst();
//    }
//
//    private List<ApplicationUser> getApplicationUsers() {
//        List<ApplicationUser> users = Lists.newArrayList(
//                new ApplicationUser(STUDENT.getGrantedAuthority(), "anna",
//                        passwordEncoder.encode("1234"),
//                        true,
//                        true,
//                        true,
//                        true
//                ),
//                new ApplicationUser(ADMIN.getGrantedAuthority(), "linda",
//                        passwordEncoder.encode("1234"),
//                        true,
//                        true,
//                        true,
//                        true
//                ),
//                new ApplicationUser(ADMINTRAINEE.getGrantedAuthority(), "tom",
//                        passwordEncoder.encode("1234"),
//                        true,
//                        true,
//                        true,
//                        true
//                )
//        );
//
//        return users;
//    }
//}
