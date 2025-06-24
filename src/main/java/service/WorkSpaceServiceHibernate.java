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
        WorkSpace workSpace = entityManager.find(WorkSpace.class, id);

        if (workSpace != null) {
            return Optional.of(workSpace);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<WorkSpace> getAllWorkSpaces() {
        return entityManager.createQuery("SELECT e FROM WorkSpace e", WorkSpace.class).getResultList();
    }

    @Override
    public int updateWorkSpace(WorkSpace space) {
        Optional<WorkSpace> workSpace = getWorkSpaceById(space.getId());
        EntityTransaction transaction;

        if (workSpace.isEmpty()) {
            return 0;
        }

        transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(space);

        transaction.commit();

        return 1;
    }

    @Override
    public int deleteWorkspace(int id) {
        Optional<WorkSpace> workSpace = getWorkSpaceById(id);

        if (workSpace.isPresent()) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            entityManager.remove(workSpace.get());

            entityManager.flush();
            transaction.commit();
            return 1;
        }

        return 0;
    }
}
