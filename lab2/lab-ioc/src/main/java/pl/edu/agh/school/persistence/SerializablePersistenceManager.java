package pl.edu.agh.school.persistence;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.logger.Logger;
import pl.edu.agh.school.IPersistenceManager;
import pl.edu.agh.school.SchoolClass;
import pl.edu.agh.school.Teacher;
import pl.edu.agh.school.annotations.Classes;
import pl.edu.agh.school.annotations.Teachers;

import javax.inject.Inject;

public final class SerializablePersistenceManager implements IPersistenceManager {

    private String teachersStorageFileName;

    private String classStorageFileName;

    @Inject
    private Logger logger;

    @Inject
    @Override
    public void setTeachersStorageFileName(@Teachers String teachersStorageFileName) {
        this.teachersStorageFileName = teachersStorageFileName;
    }

    @Inject
    @Override
    public void setClassStorageFileName(@Classes String classStorageFileName) {
        this.classStorageFileName = classStorageFileName;
    }

    @Override
    public void saveTeachers(List<Teacher> teachers) {
        System.out.println("HERE: " + logger);
        if (teachers == null) {
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(teachersStorageFileName))) {
            oos.writeObject(teachers);
            logger.log("Saved " + teachers.size() + " teachers data");
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            logger.log("There was an error while saving the teachers data", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Teacher> loadTeachers() {
        ArrayList<Teacher> res = null;
        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(teachersStorageFileName))) {
            res = (ArrayList<Teacher>) ios.readObject();
            logger.log("Loaded " + res.size() + " teachers data");
        } catch (FileNotFoundException e) {
            res = new ArrayList<>();
        } catch (IOException e) {
            logger.log("There was an error while loading the teachers data", e);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        return res;
    }

    @Override
    public void saveClasses(List<SchoolClass> classes) {
        if (classes == null) {
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(classStorageFileName))) {
            oos.writeObject(classes);
            logger.log("Saved " + classes.size() + " classes data");
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        } catch (IOException e) {
            logger.log("There was an error while saving the classes data", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<SchoolClass> loadClasses() {
        ArrayList<SchoolClass> res = null;
        try (ObjectInputStream ios = new ObjectInputStream(new FileInputStream(classStorageFileName))) {
            res = (ArrayList<SchoolClass>) ios.readObject();
            logger.log("Loaded " + res.size() + " classes data");
        } catch (FileNotFoundException e) {
            res = new ArrayList<>();
        } catch (IOException e) {
            logger.log("There was an error while loading the classes data", e);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
        return res;
    }
}
