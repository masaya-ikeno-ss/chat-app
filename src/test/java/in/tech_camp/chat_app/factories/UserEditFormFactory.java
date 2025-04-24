package in.tech_camp.chat_app.factories;

import com.github.javafaker.Faker;

import in.tech_camp.chat_app.form.EditUserForm;

public class UserEditFormFactory {
  private static final Faker faker = new Faker();

  public static EditUserForm createEditUser() {
    EditUserForm editUserForm = new EditUserForm();

    editUserForm.setName(faker.name().username());
    editUserForm.setEmail(faker.internet().emailAddress());

    return editUserForm;
  }
}
