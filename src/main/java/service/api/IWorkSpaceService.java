package service.api;

import entity.WorkSpace;
import java.util.List;
import java.util.Optional;

public interface IWorkSpaceService {

    int addNewWorkspace(WorkSpace space);
    Optional<WorkSpace> getWorkSpaceById(int id);
    List<WorkSpace> getAllWorkSpaces();
    int updateWorkSpace(WorkSpace space);
    int deleteWorkspace(int id);
}
