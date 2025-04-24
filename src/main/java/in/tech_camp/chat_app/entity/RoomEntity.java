package in.tech_camp.chat_app.entity;

import java.util.List;

import in.tech_camp.chat_app.validation.ValidationPriority1;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RoomEntity {
  private Integer id;

  @NotBlank(message = "Room Name can't be blank",groups = ValidationPriority1.class)
  private String name;
  private List<RoomUsersEntity> roomUsersEntities;
  private List<MessageEntity> messageEntities;
}
