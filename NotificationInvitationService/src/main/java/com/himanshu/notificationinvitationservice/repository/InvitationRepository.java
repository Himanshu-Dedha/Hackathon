package com.himanshu.notificationinvitationservice.repository;

import com.himanshu.notificationinvitationservice.entity.Invitation;
import com.himanshu.notificationinvitationservice.entity.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    List<Invitation> findBySenderId(Long senderId);

    List<Invitation> findByReceiverId(Long receiverId);

    List<Invitation> findByListId(Long listId);

    List<Invitation> findByReceiverIdAndStatus(Long receiverId, InvitationStatus status);
}