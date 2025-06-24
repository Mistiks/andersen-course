package service;

import entity.WorkSpace;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import service.api.IWorkSpaceService;
import util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class WorkSpaceServiceHibernate implements IWorkSpaceService {

    private final EntityManager entityManager;

    public WorkSpaceServiceHibernate() {
        entityManager = HibernateUtil.getEntityManager();
    }

    @Override
    public int addNewWorkspace(WorkSpace space) {
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        entityManager.persist(space);
        transaction.commit();

        return 1;
    }

    @Override
    public Optional<WorkSpace> getWorkSpaceById(int id) {
        return Optional.empty();
    }

    @Override
    public List<WorkSpace> getAllWorkSpaces() {
        return List.of();
    }

    @Override
    public int updateWorkSpace(WorkSpace space) {
        return 0;
    }

    @Override
    public int deleteWorkspace(int id) {
        return 0;
    }
}
