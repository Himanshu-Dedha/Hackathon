package com.himanshu.notificationinvitationservice.service;

import com.himanshu.notificationinvitationservice.dto.InvitationDto;
import com.himanshu.notificationinvitationservice.entity.Invitation;
import com.himanshu.notificationinvitationservice.entity.InvitationStatus;
import com.himanshu.notificationinvitationservice.entity.Notification;
import com.himanshu.notificationinvitationservice.repository.InvitationRepository;
import com.himanshu.notificationinvitationservice.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.himanshu.notificationinvitationservice.mapper.InvitationMapper.toEntity;

@Service
public class NotificationInvitationService {

    private final NotificationRepository notificationRepository;
    private final InvitationRepository invitationRepository;

    @Autowired
    public NotificationInvitationService(NotificationRepository notificationRepository, InvitationRepository invitationRepository) {
        this.notificationRepository = notificationRepository;
        this.invitationRepository = invitationRepository;
    }

    // Notification Operations
    public Notification createNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public List<Notification> getNotificationsByRecipientId(Long recipientId) {
        return notificationRepository.findByRecipientId(recipientId);
    }

    public void markNotificationAsRead(Long notificationId) {
        notificationRepository.findById(notificationId).ifPresent(notification -> {
            notification.setRead(true);
            notificationRepository.save(notification);
        });
    }

    // Invitation Operations
    public Invitation createInvitation(InvitationDto invitationDto, Long ownerId, Long listId) {
        Invitation invitation = toEntity(invitationDto,ownerId,listId);
        invitation.setSenderId(ownerId);
        invitation.setListId(listId);
        invitation.setStatus(InvitationStatus.SENT); // Default status
        return invitationRepository.save(invitation);
    }

    public Optional<Invitation> acceptInvitation(Long invitationId, Long ownerId) {
        return invitationRepository.findById(invitationId).flatMap(invitation -> {
            if (invitation.getReceiverId().equals(ownerId)) {
                invitation.setStatus(InvitationStatus.ACCEPTED);
                return Optional.of(invitationRepository.save(invitation));
            } else {
                return Optional.empty();
            }
        });
    }

    public Optional<Invitation> rejectInvitation(Long invitationId, Long ownerId) {
        return invitationRepository.findById(invitationId).flatMap(invitation -> {
            if (invitation.getReceiverId().equals(ownerId)) {
                invitation.setStatus(InvitationStatus.REJECTED);
                return Optional.of(invitationRepository.save(invitation));
            } else {
                return Optional.empty();
            }
        });
    }

    public List<Invitation> getInvitationsByReceiverId(Long receiverId, Long ownerId) {
        if (!receiverId.equals(ownerId)) {
            return Collections.emptyList();
        }
        return invitationRepository.findByReceiverId(receiverId);
    }
}