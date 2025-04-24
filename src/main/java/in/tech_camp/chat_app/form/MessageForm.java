package in.tech_camp.chat_app.form;

import in.tech_camp.chat_app.validation.ValidationPriority1;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;
import lombok.Data;

@Data
public class MessageForm {
  @NotBlank(message = "Content can't be blank", groups = ValidationPriority1.class)
  private String content;

  private MultipartFile image;
}
