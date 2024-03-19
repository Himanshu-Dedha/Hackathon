package com.himanshu.notificationinvitationservice.dto;

import com.himanshu.notificationinvitationservice.entity.InvitationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvitationDto {

    private Long receiverId;
    private String message;
    private InvitationStatus status;

}
