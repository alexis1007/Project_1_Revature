package org.example.Service;

import org.example.model.ApplicationStatus;

import java.util.List;
import java.util.Optional;

public interface ApplicationStatusInterface {

    // get all the application status
    List<ApplicationStatus> findAllApplicationStatus();

    //get application status by id
    Optional<ApplicationStatus> findApplicationStatusById(Long id);

    //create a new application status
    ApplicationStatus createApplicationStatus(ApplicationStatus applicationStatus);

    //update an existinf application status
    Optional<ApplicationStatus> updateApplicationStatus(Long id, ApplicationStatus applicationStatus);

    //Delete an application status
    boolean deleteApplicationStatus(Long id);
}
