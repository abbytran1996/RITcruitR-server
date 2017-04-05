package com.avalanche.tmcs.students;

/**
 * Exception to throw when you can't find a student
 *
 * @author ddubois
 * @since 05-Apr-17.
 */
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(long userId) {
        super("Could not find student with id " + userId);
    }
}
