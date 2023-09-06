package com.loyalty.dxvalley.controllers;

import java.nio.file.AccessDeniedException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.loyalty.dxvalley.models.Challenge;
import com.loyalty.dxvalley.models.CreateResponse;
import com.loyalty.dxvalley.models.InvitationDTO;
import com.loyalty.dxvalley.models.InvitedUsers;
import com.loyalty.dxvalley.models.ProductCataloge;
import com.loyalty.dxvalley.models.Transactionss;
import com.loyalty.dxvalley.models.UserChallenge;
import com.loyalty.dxvalley.models.Users;
import com.loyalty.dxvalley.repositories.RoleRepository;
import com.loyalty.dxvalley.repositories.UserRepository;
import com.loyalty.dxvalley.services.ChallengsService;
import com.loyalty.dxvalley.services.InvitedUsersService;
import com.loyalty.dxvalley.services.ProductCatalogService;
import com.loyalty.dxvalley.services.TransactionsService;
import com.loyalty.dxvalley.services.UserChallengsService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
  @Autowired
  // private UsersServiceImpl usersServiceImpl;
  private final UserRepository userRepository;
  private final RoleRepository roleRepo;
  private final PasswordEncoder passwordEncoder;
  private final ChallengsService challengsService;
  private final UserChallengsService userChallengsService;
  private final ProductCatalogService productCatalogService;
  private final InvitedUsersService invitedUsersService;
  private final TransactionsService transactionsService;

  // private boolean isSysAdmin() {
  // AtomicBoolean hasSysAdmin = new AtomicBoolean(false);
  // Authentication auth = SecurityContextHolder.getContext().getAuthentication();
  // auth.getAuthorities().forEach(grantedAuthority -> {
  // if (grantedAuthority.getAuthority().equals("sysAdmin")) {
  // hasSysAdmin.set(true);
  // }
  // });
  // return hasSysAdmin.get();
  // }
  Boolean alreadyInvited = false;

  @PostMapping("/michu/AddInvitation")
  public ResponseEntity<CreateResponse> addMichuInvitation(@RequestBody InvitationDTO invitationDTO) {
    Users users = userRepository.findByUsername(invitationDTO.getInviter());
    if (users == null) {
      CreateResponse response = new CreateResponse("Error", "User does not exist");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    List<InvitedUsers> invitedUsers = userRepository.findByUsername(invitationDTO.getInviter()).getInvitedUsers();
    alreadyInvited = false;
    List<InvitedUsers> allInvitedUsers = invitedUsersService.getInvitedUsers();
    for (int i = 0; i < allInvitedUsers.size(); i++) {
      if (allInvitedUsers.get(i).getUsername().equals(invitationDTO.getInvitee())
          && allInvitedUsers.get(i).getProductCataloge().getProductName().equals("Michu")) {
        alreadyInvited = true;
      }
    }
    if (alreadyInvited != true) {
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date currentDate = new Date();
      InvitedUsers invitedUser = new InvitedUsers();
      invitedUser.setJoinedAt(dateFormat.format(currentDate));
      invitedUser.setUsername(invitationDTO.getInvitee());
      List<ProductCataloge> productCataloges = productCatalogService.getChallengeCataloges();
      productCataloges.stream().forEach(p -> {
        if (p.getProductName().equals("Michu")) {
          invitedUser.setProductCataloge(p);
        }
      });
      invitedUsers.add(invitedUser);
      Users userToBeChekced = userRepository.findByUsername(invitationDTO.getInvitee());
      if (userToBeChekced == null) {
        Users newUser = new Users();
        newUser.setUsername(invitationDTO.getInvitee());
        Users savedUser = userRepository.save(newUser);
        List<Challenge> challenges = challengsService.getChallenges();
        challenges.stream().forEach(c -> {

          UserChallenge userChallenge = new UserChallenge();
          userChallenge.setChallenge(c);
          userChallenge.setJoinedAt(dateFormat.format(currentDate));
          userChallenge.setIsEnabled(true);
          if (c.getCategory().getCategoryName().equals("referal")
              && c.getProductCataloge().getProductName().equals("Michu")
              && c.getMaxPoints() >= (c.getPoints())) {
            userChallenge.setPoints(c.getPoints());
            Transactionss transactionss = new Transactionss();
            transactionss.setAmount(c.getPoints());
            transactionss.setGeneratedDate(dateFormat.format(currentDate));
            transactionss.setNaration("michu referal");
            transactionss.setStatus("Success");
            transactionss.setTransactionType("credit");
            transactionss.setUser(userRepository.findByUsername(invitationDTO.getInvitee()));
            transactionsService.addTransactionss(transactionss);
          } else {
            userChallenge.setPoints(0.0);
          }

          userChallenge.setUsers(savedUser);
          if (c.getProductCataloge().getPlaystoreLink() != null) {
            userChallenge.setAffliateLink(
                "https://play.google.com/store/apps/details?id=" + c.getProductCataloge().getPlaystoreLink()
                    + "&referrer=utm_content=" + newUser.getUsername() + "&utm_source=coopLoyalityApp");
          }
          userChallengsService.addUserChallenge(userChallenge);

        });
      }
      users.setInvitedUsers(invitedUsers);
      userRepository.save(users);
      List<UserChallenge> userChallenges = userChallengsService
          .getUserChallengesByuser(userRepository.findByUsername(invitationDTO.getInviter()));
      userChallenges.stream().forEach(uc -> {
        if (uc.getChallenge().getCategory().getCategoryName().equals("referal")
            && uc.getChallenge().getProductCataloge().getProductName().equals("Michu")
            && uc.getChallenge().getMaxPoints() >= (uc.getPoints() + uc.getChallenge().getPoints())) {
          uc.setPoints(uc.getPoints() + uc.getChallenge().getPoints());
          userChallengsService.addUserChallenge(uc);
          Transactionss transactionss = new Transactionss();
          transactionss.setAmount(uc.getChallenge().getPoints());
          transactionss.setGeneratedDate(dateFormat.format(currentDate));
          transactionss.setNaration("michu referal");
          transactionss.setStatus("Success");
          transactionss.setTransactionType("credit");
          transactionss.setUser(userRepository.findByUsername(invitationDTO.getInviter()));
          transactionsService.addTransactionss(transactionss);
        }
      });
      CreateResponse response = new CreateResponse("Success",
          "User's Invited List updated successfully created successfully");
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      CreateResponse response = new CreateResponse("Error", "User already invited");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

  }

  @PostMapping("/equb/AddInvitation")
  public ResponseEntity<CreateResponse> addEqubInvitation(@RequestBody InvitationDTO invitationDTO) {
    Users users = userRepository.findByUsername(invitationDTO.getInviter());
    if (users == null) {
      CreateResponse response = new CreateResponse("Error", "User does not exist");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    List<InvitedUsers> invitedUsers = userRepository.findByUsername(invitationDTO.getInviter()).getInvitedUsers();
    alreadyInvited = false;
    List<InvitedUsers> allInvitedUsers = invitedUsersService.getInvitedUsers();
    for (int i = 0; i < allInvitedUsers.size(); i++) {
      if (allInvitedUsers.get(i).getUsername().equals(invitationDTO.getInvitee())
          && allInvitedUsers.get(i).getProductCataloge().getProductName().equals("My-Equb")) {
        alreadyInvited = true;
      }
    }
    if (alreadyInvited != true) {
      DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Date currentDate = new Date();
      InvitedUsers invitedUser = new InvitedUsers();
      invitedUser.setJoinedAt(dateFormat.format(currentDate));
      invitedUser.setUsername(invitationDTO.getInvitee());
      List<ProductCataloge> productCataloges = productCatalogService.getChallengeCataloges();
      productCataloges.stream().forEach(p -> {
        if (p.getProductName().equals("My-Equb")) {
          invitedUser.setProductCataloge(p);
        }
      });
      invitedUsers.add(invitedUser);
      Users userToBeChekced = userRepository.findByUsername(invitationDTO.getInvitee());
      if (userToBeChekced == null) {
        Users newUser = new Users();
        newUser.setUsername(invitationDTO.getInvitee());
        Users savedUser = userRepository.save(newUser);
        List<Challenge> challenges = challengsService.getChallenges();
        challenges.stream().forEach(c -> {

          UserChallenge userChallenge = new UserChallenge();
          userChallenge.setChallenge(c);
          userChallenge.setJoinedAt(dateFormat.format(currentDate));
          userChallenge.setIsEnabled(true);
          if (c.getCategory().getCategoryName().equals("referal")
              && c.getProductCataloge().getProductName().equals("My-Equb")
              && c.getMaxPoints() >= (c.getPoints())) {
            userChallenge.setPoints(c.getPoints());
            Transactionss transactionss = new Transactionss();
            transactionss.setAmount(c.getPoints());
            transactionss.setGeneratedDate(dateFormat.format(currentDate));
            transactionss.setNaration("My-Equb referal");
            transactionss.setStatus("Success");
            transactionss.setTransactionType("credit");
            transactionss.setUser(userRepository.findByUsername(invitationDTO.getInvitee()));
            transactionsService.addTransactionss(transactionss);
          } else {
            userChallenge.setPoints(0.0);
          }

          userChallenge.setUsers(savedUser);
          if (c.getProductCataloge().getPlaystoreLink() != null) {
            userChallenge.setAffliateLink(
                "https://play.google.com/store/apps/details?id=" + c.getProductCataloge().getPlaystoreLink()
                    + "&referrer=utm_content=" + newUser.getUsername() + "&utm_source=coopLoyalityApp");
          }
          userChallengsService.addUserChallenge(userChallenge);

        });
      }
      users.setInvitedUsers(invitedUsers);
      userRepository.save(users);
      List<UserChallenge> userChallenges = userChallengsService
          .getUserChallengesByuser(userRepository.findByUsername(invitationDTO.getInviter()));
      userChallenges.stream().forEach(uc -> {
        if (uc.getChallenge().getCategory().getCategoryName().equals("referal")
            && uc.getChallenge().getProductCataloge().getProductName().equals("My-Equb")
            && uc.getChallenge().getMaxPoints() >= (uc.getPoints() + uc.getChallenge().getPoints())) {
          uc.setPoints(uc.getPoints() + uc.getChallenge().getPoints());
          userChallengsService.addUserChallenge(uc);
          Transactionss transactionss = new Transactionss();
          transactionss.setAmount(uc.getChallenge().getPoints());
          transactionss.setGeneratedDate(dateFormat.format(currentDate));
          transactionss.setNaration("My-Equb referal");
          transactionss.setStatus("Success");
          transactionss.setTransactionType("credit");
          transactionss.setUser(userRepository.findByUsername(invitationDTO.getInviter()));
          transactionsService.addTransactionss(transactionss);
        }
      });
      CreateResponse response = new CreateResponse("Success",
          "User's Invited List updated successfully created successfully");
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      CreateResponse response = new CreateResponse("Error", "User already invited");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

  }

  private boolean isOwnAccount(String userName) {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    return userRepository.findByUsername((String) auth.getPrincipal()).getUsername().equals(userName);
  }

  // @GetMapping("/getuserServiceImpl")
  // List<Users> getUsers() {
  // if (isSysAdmin()) {
  // return this.userRepository.findAll(Sort.by("username"));
  // }
  // var users = this.userRepository.findAll(Sort.by("username"));
  // users.removeIf(user -> {
  // var containsAdmin = false;
  // for (var role : user.getRoles()) {
  // containsAdmin = containsAdmin || role.getRoleName().equals("admin");
  // }
  // return containsAdmin;
  // });
  // return users;
  // }
  @GetMapping("/getUsers")
  List<Users> getAllUsers() {
    List<Users> users = userRepository.findAll();
    return users;
  }

  @GetMapping("/getUser/{userId}")
  public ResponseEntity<?> getByUserId(@PathVariable Long userId) {
    var user = userRepository.findByUserId(userId);
    if (user == null) {
      createUserResponse response = new createUserResponse("error", "Cannot find this user!");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @GetMapping("/getAppUsers")
  List<Users> getAppUsers() {
    List<Users> users = userRepository.findAll();
    List<Users> appUsers = new ArrayList<Users>();
    users.stream().forEach(u -> {
      u.getRoles().stream().forEach(r -> {
        if (r.getRoleName().equals("loyaltyAppUser")) {
          appUsers.add(u);
        }
      });
    });
    return appUsers;
  }

  @GetMapping("/getAdminUsers")
  List<Users> getAdminUsers() {
    List<Users> users = userRepository.findAll();
    List<Users> adminUsers = new ArrayList<Users>();
    users.stream().forEach(u -> {
      u.getRoles().stream().forEach(r -> {
        if (r.getRoleName().equals("superAdmin") || r.getRoleName().equals("admin")) {
          adminUsers.add(u);
        }
      });
    });
    return adminUsers;
  }

  // @GetMapping("/getUserProfile/{phoneNumber}")
  // public GetUserProfile getUserProfile(@PathVariable String phoneNumber) {
  // GetUserProfile getUserProfile = new GetUserProfile();
  // Users user = userRepository.findByUsername(phoneNumber);
  // getUserProfile.setFullName(user.getFullName());
  // getUserProfile.setPhoneNumber(user.getUsername());
  // getUserProfile.setEmail(user.getEmail());
  // List<Account> accounts = user.getAccounts();
  // String mainAccount = "";
  // for (int i = 0; i < accounts.size(); i++) {
  // if (accounts.get(i).getIsMainAccount() == true) {
  // mainAccount = accounts.get(i).getAccountNumber();
  // }
  // }
  // getUserProfile.setAccountNumber(mainAccount);
  // getUserProfile.setBirthDate(user.getBirthDate());
  // getUserProfile.setProfileImage(user.getImageUrl());
  // return getUserProfile;
  // }

  @GetMapping("/getUserByPhone/{username}")
  public ResponseEntity<?> getByUsername(@PathVariable String username) {
    var user = userRepository.findByUsername(username);

    if (user == null) {
      createUserResponse response = new createUserResponse("error", "Cannot find user with this phone number!");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @PostMapping("/createUser")
  public ResponseEntity<createUserResponse> accept(@RequestBody Users tempUser) {
    var user = userRepository.findByUsername(tempUser.getUsername());
    if (user != null) {
      if (user.getFullName() == null) {
        user.setRoles(
            tempUser.getRoles().stream().map(x -> this.roleRepo.findByRoleName(x.getRoleName()))
                .collect(Collectors.toList()));
        user.setFullName(tempUser.getFullName());
        user.setEmail(tempUser.getEmail());
        user.setPassword(passwordEncoder.encode(tempUser.getPassword()));
        userRepository.save(user);
        createUserResponse response = new createUserResponse("success", "user created successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
      } else {
        createUserResponse response = new createUserResponse("error", "user already exists");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
      }

    }

    tempUser.setRoles(
        tempUser.getRoles().stream().map(x -> this.roleRepo.findByRoleName(x.getRoleName()))
            .collect(Collectors.toList()));
    tempUser.setPassword(passwordEncoder.encode(tempUser.getPassword()));
    List<Challenge> challenges = challengsService.getChallenges();
    if (userRepository.findByUsername(tempUser.getUsername()) == null) {
      challenges.stream().forEach(c -> {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();
        UserChallenge userChallenge = new UserChallenge();
        userChallenge.setChallenge(c);
        userChallenge.setJoinedAt(dateFormat.format(currentDate));
        userChallenge.setIsEnabled(true);
        userChallenge.setPoints(0.0);
        Users newUser = userRepository.save(tempUser);
        userChallenge.setUsers(newUser);
        if (c.getProductCataloge().getPlaystoreLink() != null) {
          userChallenge.setAffliateLink(
              "https://play.google.com/store/apps/details?id=" + c.getProductCataloge().getPlaystoreLink()
                  + "&referrer=utm_content=" + newUser.getUsername() + "&utm_source=coopLoyalityApp");
        }
        userChallengsService.addUserChallenge(userChallenge);
      }

      );
    }
    createUserResponse response = new createUserResponse("success", "user created successfully");
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  // @GetMapping("/search")
  // public List<String> getUsers(@RequestParam Long userId) {
  // List<Users> users = usersServiceImpl.getAllUsers();
  // List<Users> filteredUsers = users.stream()
  // .filter(u -> u.getUserId().equals(userId))
  // .collect(Collectors.toList());
  // List<String> user = new ArrayList<>();
  // filteredUsers.forEach(u -> {
  // user.add(u.getEmail());
  // user.add(u.getFullName());
  // user.add(u.getUsername());});
  // return user;
  // }

  // @GetMapping("/userId")
  // public List<Users> getUsersByUserId(@RequestParam Long userId) {
  // List<Users> users = usersServiceImpl.getAllUsers();
  // return users.stream()
  // .filter(u -> u.getUserId().equals(userId))
  // .collect(Collectors.toList());
  // }

  @PutMapping("/passwordReset/{phoneNumber}")
  public ResponseEntity<pinchangeResponse> passwordReset(@RequestBody Users tempUser,
      @PathVariable String phoneNumber) {
    Users user = userRepository.findByUsername(phoneNumber);
    // edit(tempUser,user);
    // user.setUsername(tempUser.getUsername());

    user.setPassword(passwordEncoder.encode(tempUser.getPassword()));
    userRepository.save(user);
    // .setPassword(null);
    pinchangeResponse response = new pinchangeResponse("success");
    return new ResponseEntity<>(response, HttpStatus.OK);

  }

  @PutMapping("/editUser/{phoneNumber}")
  public ResponseEntity<pinchangeResponse> editUser(@RequestBody Users tempUser,
      @PathVariable String phoneNumber) {
    Users user = userRepository.findByUsername(phoneNumber);
    // edit(tempUser,user);
    // user.setUsername(tempUser.getUsername());

    user.setFullName(tempUser.getFullName());
    user.getAddress().setEmail(tempUser.getAddress().getEmail());
    user.getAddress().setRegion(tempUser.getAddress().getRegion());
    user.getAddress().setZone(tempUser.getAddress().getZone());
    user.getAddress().setWoreda(tempUser.getAddress().getWoreda());
    user.getAddress().setTown(tempUser.getAddress().getTown());
    user.getAddress().setKebele(tempUser.getAddress().getKebele());
    user.getAddress().setPhoneNumber(tempUser.getAddress().getPhoneNumber());
    userRepository.save(user);
    // .setPassword(null);
    pinchangeResponse response = new pinchangeResponse("success");
    return new ResponseEntity<>(response, HttpStatus.OK);

  }

  @PutMapping("/changePassword/{phoneNumber}/{password}")
  public ResponseEntity<pinchangeResponse> ChangePassword(@RequestBody Users tempUser,
      @PathVariable String phoneNumber, @PathVariable String password) {
    Users user = userRepository.findByUsername(phoneNumber);
    // edit(tempUser,user);
    // user.setUsername(tempUser.getUsername());
    System.out.println(user.getPassword());
    System.out.println(password);
    if (passwordEncoder.matches(password, user.getPassword())) {

      user.setPassword(passwordEncoder.encode(tempUser.getPassword()));
      userRepository.save(user);
      // .setPassword(null);
      pinchangeResponse response = new pinchangeResponse("success");
      return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
      pinchangeResponse response = new pinchangeResponse("incorrect old password");
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

  }

  @PutMapping("/manageAccount/{userName}/{usernameChange}")
  public Users manageAccount(@RequestBody UsernamePassword temp,
      @PathVariable String userName,
      @PathVariable Boolean usernameChange) throws AccessDeniedException {
    if (isOwnAccount(userName)) {
      Users user = userRepository.findByUsername(userName);
      if (passwordEncoder.matches(temp.getOldPassword(), user.getPassword())) {
        user.setPassword(passwordEncoder.encode(temp.getNewPassword()));
      }
      if (usernameChange) {
        user.setUsername(temp.getNewUsername());
      }
      Users response = userRepository.save(user);
      response.setPassword(null);
      return response;
    } else
      throw new AccessDeniedException("403 Forbidden");
  }

  @DeleteMapping("/delete/user/{userId}")
  void deleteUser(@PathVariable Long userId) {
    this.userRepository.deleteById(userId);
  }

}

@Getter
@Setter
class UsernamePassword {
  private String newUsername;
  private String newPassword;
  private String oldPassword;
}

@Getter
@Setter
@AllArgsConstructor
class createUserResponse {
  String status;
  String description;
}

@Getter
@Setter
@AllArgsConstructor
class pinchangeResponse {
  String status;
}

@Data
class GetUserProfile {
  String fullName;
  String phoneNumber;
  String email;
  String accountNumber;
  String birthDate;
  String profileImage;
}