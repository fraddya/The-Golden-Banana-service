package com.fraddy.goldenbanana.service.Impl;


import com.fraddy.goldenbanana.domain.QUser;
import com.fraddy.goldenbanana.domain.User;
import com.fraddy.goldenbanana.domain.base.ComplexValidationException;
import com.fraddy.goldenbanana.domain.criteria.UserCriteria;
import com.fraddy.goldenbanana.enums.Status;
import com.fraddy.goldenbanana.enums.UserType;
import com.fraddy.goldenbanana.repository.UserRepository;
import com.fraddy.goldenbanana.service.EmailService;
import com.fraddy.goldenbanana.service.JwtService;
import com.fraddy.goldenbanana.service.UserService;
import com.querydsl.core.BooleanBuilder;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    @Transactional
    @Override
    public User save(User user) {
        User persistedUser = userRepository.findByEmail(user.getEmail());
        if (persistedUser != null) {
            throw new ComplexValidationException("User registration", "this Email already registered");
        }
        user.setStatus(Status.ACTIVE);
        user.setDateJoin(LocalDate.now());
        if (user.getRole() == null) user.setRole(UserType.USER);
        if (user.getPassWord() != null && !user.getPassWord().isEmpty()) {
            user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        } else {
            String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            StringBuilder pass = new StringBuilder();
            java.util.Random rand = new java.util.Random();
            for (int i = 0; i < 12; i++) {
                pass.append(chars.charAt(rand.nextInt(chars.length())));
            }
            user.setPassWord(pass.toString());
            sendEmail(user);
            user.setPassWord(passwordEncoder.encode(pass.toString()));
        }
        user.setFirstTimeLogin(true);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<User> search(UserCriteria criteria) {

        PageRequest page = PageRequest.of(criteria.getPageNumber() - 1, criteria.getPageSize(),
                Sort.by(Sort.Direction.fromOptionalString(criteria.getSortDirection()).orElse(Sort.Direction.DESC),
                        criteria.getSortProperty()));

        Page<User> users = null;

        BooleanBuilder booleanBuilder = new BooleanBuilder(QUser.user.status.ne(Status.DELETED));

        if (StringUtils.isNotBlank(criteria.getEmail())) {
            booleanBuilder.and(QUser.user.email.containsIgnoreCase(criteria.getEmail()));
        }
        if (criteria.getRole() != null ) {
            booleanBuilder.and(QUser.user.role.eq(UserType.valueOf(String.valueOf(criteria.getRole()))));
        }

        if (booleanBuilder.hasValue()){
            users = userRepository.findAll(booleanBuilder, page);
        } else {
            users = userRepository.findAll(page);
        }
        return users;
    }

    @Transactional(readOnly = true)
    @Override
    public User retrieve(Long id) {
        Optional<User> userPersisted = userRepository.findById(id);
        if (!userPersisted.isPresent()) {
            throw new ComplexValidationException("user retrieval","User (%s) not found  ");
        }
        return userPersisted.get();
    }


    @Transactional
    @Override
    public User update(User user) {
        Optional<User> userPersisted = userRepository.findById(user.getId());
        if (!userPersisted.isPresent()) {
            throw new ComplexValidationException("User retrieval", "Cannot find any User ");
        } else {
            User userDb = userPersisted.get();
            updateFields(user, userDb);
            return userRepository.save(userDb);
        }
    }

    private void updateFields(User user, User userDb) {
        if (user.getFirstName() != null) userDb.setFirstName(user.getFirstName());
        if (user.getLastName() != null) userDb.setLastName(user.getLastName());
        if (user.getContactNo() != null) userDb.setContactNo(user.getContactNo());
        if (user.getDateJoin() != null) userDb.setDateJoin(user.getDateJoin());
        if (user.getGenderType() != null) userDb.setGenderType(user.getGenderType());
        if (user.getImage() != null) userDb.setImage(user.getImage());
        if (userDb.getFirstTimeLogin()) userDb.setFirstTimeLogin(false);
        if (user.getPassWord() != null) userDb.setPassWord(passwordEncoder.encode(user.getPassWord()));
        //if (user.getPassWord() != null) userDb.setPassWord((user.getPassWord()));
        if (user.getRole() != null) userDb.setRole(user.getRole());

    }

    @Transactional
    @Override
    public User delete(Long id) {
        User user = userRepository.getReferenceById(id);
        if (user != null) {
            user.setStatus(Status.DELETED);
            return userRepository.save(user);
        }else {
            throw new ComplexValidationException("User retrieval", "Cannot find any User ");
        }
    }

    @Override
    public User logIn(User user) {
        User userPersisted = userRepository.findByEmail(user.getEmail());
        if (userPersisted != null) {
            if (passwordEncoder.matches(user.getPassWord(), userPersisted.getPassWord())) {
                userPersisted.setUserLogging(LocalDateTime.now());
                String token = jwtService.generateToken(userPersisted.getEmail());
                System.out.println("Token: " + token);
                if (!token.matches("[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+")) {
                    throw new RuntimeException("JWT token is not in the correct format");
                }
                jwtService.validateToken(token);
                userRepository.save(userPersisted);
                userPersisted.setToken(token);
                return userPersisted;
            }
        }
        throw new ComplexValidationException(user.getEmail(), "User credentials Invalid");
    }

    @Override
    public String generateToken(String email) {
        return jwtService.generateToken(email);
    }

    @Override
    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    private void sendEmail(User user) {
        log.info("Welcome email start: {}");

        // Create HTML content for the email
        StringBuilder htmlContent = new StringBuilder();
        htmlContent.append("<div style='font-family: Arial, sans-serif; background-color: #fff7e1; padding: 20px;'>"); // Banana yellow background
        htmlContent.append("<h1 style='text-align: center; color: #b5651d;'>Welcome to The Golden Banana Game!</h1>"); // Brown colored heading
        htmlContent.append("<p style='text-align: center;'>Dear ").append(user.getFirstName()).append(" ").append(user.getLastName()).append(",</p>");
        htmlContent.append("<p style='text-align: center;'>We are warmly excited to have you with us!</p>");
        htmlContent.append("<p style='text-align: center;'>Your username is: <strong>").append(user.getEmail()).append("</strong></p>");
        htmlContent.append("<p style='text-align: center;'>Please use the following temporary password for your first login:</p>");
        htmlContent.append("<p style='text-align: center; font-size: 18px; font-weight: bold;'>").append(user.getPassWord()).append("</p>");
        htmlContent.append("<p style='text-align: center;'>After logging in, you can change your password in the user settings.</p>");
        htmlContent.append("<p style='text-align: center;'>We hope you enjoy playing the game and being part of the community!</p>");
        htmlContent.append("<p style='text-align: center;'>Best Regards,</p>");
        htmlContent.append("<p style='text-align: center; font-weight: bold; color: #b5651d;'>The Golden Banana Team</p>"); // Brown colored closing
        htmlContent.append("</div>");

        try {
            // Send the email using emailService
            emailService.sendInvoiceEmail(user.getEmail(), "Welcome to The Golden Banana Game!", htmlContent.toString());
            log.info("Welcome email sent successfully to: {}", user.getEmail());
        } catch (MessagingException e) {
            log.error("Failed to send welcome email to: {}, Error: {}", user.getEmail(), e.getMessage());
        }
    }
}
