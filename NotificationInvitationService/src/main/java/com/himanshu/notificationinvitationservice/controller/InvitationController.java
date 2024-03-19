package com.himanshu.notificationinvitationservice.controller;

import com.himanshu.notificationinvitationservice.dto.InvitationDto;
import com.himanshu.notificationinvitationservice.entity.Invitation;
import com.himanshu.notificationinvitationservice.service.NotificationInvitationService;
import com.himanshu.notificationinvitationservice.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/invitations")
public class InvitationController {

    private final NotificationInvitationService notificationInvitationService;
    private final JwtService jwtService;

    public InvitationController(NotificationInvitationService notificationInvitationService, JwtService jwtService) {
        this.notificationInvitationService = notificationInvitationService;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<Invitation> createInvitation(@RequestBody InvitationDto invitationDto, @RequestHeader("Authorization") String token, @RequestParam("listId") Long listId) {
        Long ownerId = jwtService.getUserIdFromToken(token);
        Invitation createdInvitation = notificationInvitationService.createInvitation(invitationDto, ownerId, listId);
        return new ResponseEntity<>(createdInvitation, HttpStatus.CREATED);
    }

    @PutMapping("/{invitationId}/accept")
    public ResponseEntity<Invitation> acceptInvitation(@PathVariable Long invitationId, @RequestHeader("Authorization") String token) {
        Long ownerId = jwtService.getUserIdFromToken(token);
        return notificationInvitationService.acceptInvitation(invitationId,ownerId)
                .map(invitation -> new ResponseEntity<>(invitation, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{invitationId}/reject")
    public ResponseEntity<Invitation> rejectInvitation(@PathVariable Long invitationId,@RequestHeader("Authorization") String token) {
        Long ownerId = jwtService.getUserIdFromToken(token);
        return notificationInvitationService.rejectInvitation(invitationId, ownerId)
                .map(invitation -> new ResponseEntity<>(invitation, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/recipient/{receiverId}")
    public ResponseEntity<List<Invitation>> getInvitationsByReceiverId(@PathVariable Long receiverId, @RequestHeader("Authorization") String token) {
        Long ownerId = jwtService.getUserIdFromToken(token);
        List<Invitation> invitations = notificationInvitationService.getInvitationsByReceiverId(receiverId, ownerId);
        return ResponseEntity.ok(invitations);
    }
}
