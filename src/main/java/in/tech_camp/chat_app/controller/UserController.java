package in.tech_camp.chat_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.tech_camp.chat_app.entity.UserEntity;
import in.tech_camp.chat_app.form.EditUserForm;
import in.tech_camp.chat_app.form.LoginForm;
import in.tech_camp.chat_app.form.UserForm;
import in.tech_camp.chat_app.repository.UserRepository;
import in.tech_camp.chat_app.service.UserService;
import lombok.AllArgsConstructor;




@Controller
@AllArgsConstructor
public class UserController {
  private final UserRepository userRepository;
  private final UserService userService;

  @GetMapping("/users/sign_up")
  public String showSignUp(Model model){
    model.addAttribute("userForm", new UserForm());
    return "users/signUp";
  }

  @GetMapping("/users/login")
  public String showLogin(Model model) {
    model.addAttribute("loginForm", new LoginForm());
    return "users/login";
  }

  @GetMapping("/login")
  public String showLoginWithError(@RequestParam(value = "error", required = false) String error, @ModelAttribute("loginForm") LoginForm loginForm, Model model) {
    if (error != null) {
      model.addAttribute("loginError", "Invalid email or password.");
    }
    return "users/login";
  }

  @GetMapping("/users/{id}/edit")
  public String showEditPage(@PathVariable("id") Integer id, Model model) {
    UserEntity userEntity = userRepository.findById(id);

    EditUserForm userForm = new EditUserForm();
    userForm.setId(userEntity.getId());
    userForm.setName(userEntity.getName());
    userForm.setEmail(userEntity.getEmail());

    model.addAttribute("user", userForm);
    return "users/edit";
  }

  @PostMapping("/user")
  public String createUser(@ModelAttribute("userForm") UserForm userForm, Model model) {

    UserEntity userEntity = new UserEntity();
    userEntity.setName(userForm.getName());
    userEntity.setEmail(userForm.getEmail());
    userEntity.setPassword(userForm.getPassword());

    try {
      userService.createUserWithEncryptedPassword(userEntity);
    } catch (Exception e) {
      System.out.println("エラー：" + e);
      model.addAttribute("userForm", userForm);
      return "users/signUp";
    }
    return "redirect:/";
  }

  @PostMapping("/users/{id}")
  public String updateUser(
    @ModelAttribute("userForm") @Validated EditUserForm editUserForm,
    @PathVariable("id") Integer id,
    Model model
    ) {
      UserEntity user = userRepository.findById(id);
      user.setName(editUserForm.getName());
      user.setEmail(editUserForm.getEmail());

      try {
        userRepository.update(user);
      } catch (Exception e) {
        System.out.println("エラー" + e);
        model.addAttribute("editUserForm", editUserForm);
        return "users/edit";
      }
      return "redirect:/";
  }
  
}
