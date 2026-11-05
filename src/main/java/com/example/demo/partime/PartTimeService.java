package com.example.demo.partime;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import com.example.demo.partime.dto.PartTimeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import com.example.demo.user.UserRepository;
import com.example.demo.partime.dto.PartTimeRequest;
import com.example.demo.partime.dto.PartTimeUpdateRequest;
import com.example.demo.role.Role;
import com.example.demo.role.RoleRepository;
import com.example.demo.role.UserRole;
import com.example.demo.role.UserRoleId;
import com.example.demo.user.User;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Service
public class PartTimeService {
        private static final String DEFAULT_PASSWORD = "Trabea123";
        private static final String EMAIL_DOMAIN = "@trabea.co.id";
        private static final String PART_TIME_ROLE = "PartTimer";

        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final PasswordEncoder passwordEncoder;
        private final EntityManager entityManager;
        private final PartTimeRepository partTimeRepository;
        private final PartTimeMapper partTimeMapper;

        public PartTimeService(PartTimeRepository partTimeRepository,
                        UserRepository userRepository,
                        RoleRepository roleRepository,
                        PartTimeMapper partTimeMapper,
                        PasswordEncoder passwordEncoder,
                        EntityManager entityManager) {
                this.partTimeRepository = partTimeRepository;
                this.partTimeMapper = partTimeMapper;
                this.userRepository = userRepository;
                this.roleRepository = roleRepository;
                this.passwordEncoder = passwordEncoder;
                this.entityManager = entityManager;
        }

        // public class request() {
        // return new PartTimeRequest();
        // }

        // get all part-time employees by id
        public Optional<PartTimeResponse> getPartTimeEmployeeById(Integer id) {
                return partTimeRepository.findById(id)
                                .map(partTimeMapper::toResponse);
        }

        public List<PartTimeResponse> getAllPartTimeEmployees() {
                return partTimeRepository.findAll()
                                .stream()
                                .map(partTimeMapper::toResponse)
                                .toList();
        }

        // Register new part-time employee
        @Transactional
        public PartTimeResponse registerPartTimeEmployee(PartTimeRequest request) {
                String[] nameParts = request.getFullName().trim()
                                .split("\\s+");
                if (nameParts.length < 2) {
                        throw new ResponseStatusException(
                                        HttpStatus.BAD_REQUEST,
                                        "Full name must contain at least first name and last name");
                }

                String firstName = nameParts[0];

                String lastName = String.join(
                                " ",
                                java.util.Arrays.copyOfRange(
                                                nameParts,
                                                1,
                                                nameParts.length));

                // 2. Generate work email
                String workEmail = generateWorkEmail(
                                firstName,
                                lastName);

                // 3. Find default part-time role
                Role role = roleRepository
                                .findByNameIgnoreCase(PART_TIME_ROLE)
                                .orElseThrow(() -> new IllegalStateException(
                                                "PART_TIME_EMPLOYEE role is not configured in database"));

                // 4. Create User
                User user = new User();
                user.setWorkEmail(workEmail);
                user.setPassword(
                                passwordEncoder.encode(DEFAULT_PASSWORD));

                // 5. Create UserRole relationship
                UserRoleId userRoleId = new UserRoleId();
                userRoleId.setUserId(workEmail);
                userRoleId.setRoleId(role.getId());

                UserRole userRole = new UserRole();
                userRole.setId(userRoleId);
                userRole.setUser(user);
                userRole.setRole(role);
                user.setUserRoles(new ArrayList<>());
                user.getUserRoles().add(userRole);

                // 6. Save User and UserRole
                userRepository.save(user);
                // userRepository.save(user);

                // 7. Create PartTimeEmployee
                PartTimeEmployee partTimeEmployee = new PartTimeEmployee();
                partTimeEmployee.setFirstName(firstName);
                partTimeEmployee.setLastName(lastName);
                partTimeEmployee.setPersonalEmail(request.getPersonalEmail());
                partTimeEmployee.setPersonalPhoneNumber(
                                request.getPersonalPhoneNumber());
                partTimeEmployee.setLastEducation(request.getLastEducation());
                partTimeEmployee.setOnGoingEducation(
                                request.getOnGoingEducation());
                partTimeEmployee.setUser(user);

                // 8. Save PartTimeEmployee
                PartTimeEmployee savedEmployee = partTimeRepository.saveAndFlush(partTimeEmployee);

                // 9. Refresh to retrieve database-generated joinDate
                entityManager.refresh(savedEmployee);

                // 10. Convert entity to response
                return partTimeMapper.toResponse(savedEmployee);
        }

        // GENERATE UNIQUE WORK EMAIL
        private String generateWorkEmail(
                        String firstName,
                        String lastName) {

                String first = firstName
                                .replaceAll("[^\\p{L}\\p{N}]", "")
                                .toLowerCase(Locale.ROOT);
                String last = lastName
                                .replaceAll("[^\\p{L}\\p{N}]", "")
                                .toLowerCase(Locale.ROOT);

                String baseEmail = first + "." + last;
                String workEmail = baseEmail + EMAIL_DOMAIN;
                int counter = 1;

                while (userRepository.existsById(workEmail)) {
                        workEmail = baseEmail + counter + EMAIL_DOMAIN;
                        counter++;
                }

                return workEmail;
        }

        // Update part-time employee
        @Transactional
        public PartTimeResponse updPartTimeResponse(PartTimeUpdateRequest request, Integer id) {
                PartTimeEmployee partTimeEmployee = partTimeRepository
                                .findById(id)
                                .orElseThrow(() -> new ResponseStatusException(
                                                HttpStatus.NOT_FOUND,
                                                "Part-time employee not found"));

                // Generate new work email if first name or last name has changed
                String workEmail = generateWorkEmail(
                                request.getFirstName(),
                                request.getLastName());

                partTimeEmployee.getUser().setWorkEmail(workEmail);

                partTimeMapper.updateEntity(request, partTimeEmployee);
                PartTimeEmployee updatedEmployee = partTimeRepository.saveAndFlush(partTimeEmployee);
                entityManager.refresh(updatedEmployee);
                return partTimeMapper.toResponse(updatedEmployee);
        }
}
