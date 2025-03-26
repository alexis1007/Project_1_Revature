package org.example.controller;


import org.example.Service.ApplicationStatusService;
import org.example.model.ApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/application_statuses")
@CrossOrigin(origins = "*")
public class ApplicationStatusController {

    private final ApplicationStatusService applicationStatusService;

    @Autowired
    public ApplicationStatusController(ApplicationStatusService applicationStatusService){
        this.applicationStatusService = applicationStatusService;
    }

    // obtener todos los statuses
    @GetMapping
    public List<ApplicationStatus> getAllApplicationStatus(){
        return applicationStatusService.findAllApplicationStatus();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationStatus> getApplicationStatusById(@PathVariable Long id){
        Optional<ApplicationStatus> applicationStatus = applicationStatusService.findApplicationStatusById(id);
        return applicationStatus.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create new Loan application
    @PostMapping
    public ResponseEntity<ApplicationStatus> createApplicationStatus(@RequestBody ApplicationStatus applicationStatus){
        ApplicationStatus createdApplicationStatus = applicationStatusService.createApplicationStatus(applicationStatus);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdApplicationStatus);
    }


    // Update An existing application
    @PutMapping("/{id}")
    public ResponseEntity<ApplicationStatus> updateApplicationStatus(@PathVariable Long id, @RequestBody ApplicationStatus applicationStatus){
        Optional<ApplicationStatus> updatedApplicationStatus = applicationStatusService.updateApplicationStatus(id, applicationStatus);
        return updatedApplicationStatus.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }


    //Delete mailing Address
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplicationStatus(@PathVariable Long id){
        boolean isDeleted = applicationStatusService.deleteApplicationStatus(id);
        return isDeleted ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
