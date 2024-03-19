package com.himanshu.notificationinvitationservice.mapper;

import com.himanshu.notificationinvitationservice.dto.InvitationDto;
import com.himanshu.notificationinvitationservice.entity.Invitation;

public class InvitationMapper {
    public static Invitation toEntity(InvitationDto dto, Long senderId, Long listId) {
        Invitation invitation = new Invitation();
        invitation.setSenderId(senderId);
        invitation.setReceiverId(dto.getReceiverId());
        invitation.setListId(listId);
        invitation.setMessage(dto.getMessage());
        invitation.setStatus(dto.getStatus());
        return invitation;
    }

    public static InvitationDto toDto(Invitation invitation) {
        InvitationDto dto = new InvitationDto();
        dto.setReceiverId(invitation.getReceiverId());
        dto.setMessage(invitation.getMessage());
        dto.setStatus(invitation.getStatus());
        return dto;
    }
}
