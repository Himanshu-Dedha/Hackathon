package com.himanshu.notificationinvitationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "invitations")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id")
    private Long senderId; // ID of the user sending the invitation

    @Column(name = "receiver_id")
    private Long receiverId; // ID of the user receiving the invitation

    @Column(name = "list_id")
    private Long listId; // ID of the list related to the invitation

    @Column(length = 500)
    private String message;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}

