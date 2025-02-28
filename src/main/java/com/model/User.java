package com.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * represent a user in the music app system
 * stores user info, password, friends, and courses
 * @author Makyia Irick
 */
public class User {
    private UUID id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private ArrayList<User> friends;
    private ArrayList<Course> courses;
    private double metronomeSpeedModifier = 1.0;

    /**
     * constructs a User with a specific UUID
     * @param - the unique ID of the user
     * @param - the user's first name
     * @param - the user's last name
     * @param - the user's email address
     * @param - the user's password (validated)
     * @throws - IllegalArgumentException, if password is invalid
     */
     public User(UUID id, String firstname, String lastName, String emailAddress, String password) {
           if(!isValidPassword(password)) {
            throw new IllegalArgumentException("Invalid password: Should be at least 7 characters, containing 1 number and 1 symbol.");
           }
        
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.friends = new ArrayList<>();
        this.courses = new ArrayList<>();
     }


        /**
         * constructs a User without a UUID (generates one automatically)
         * @param - the user's first name
         * @param - the user's last name
         * @param - the user's email address
         * @param - the user's password (validated)
         */
        public User(String firstName, String lastName, String emailAddress, String password) {
            this(UUID.randomUUID(), firstName, lastName, emailAddress, password);
        }

        /**
         * validates if the password meets criteria
         * @param - the password to validate
         * @return - true if password is valid, false otherwise
         */
        private boolean isValidPassword(String password){
            if (password.length() < 7) {
                return false;
            }
            boolean hasNumber = false;
            boolean hasSymbol = false;

            for(char c : password.toCharArray()) {
                if (Character.isDigit(c)) {
                    hasNumber = true;
                } else if (!Character.isLetterOrDigit(c)) {
                    hasSymbol = true;
                }
            }
            return hasNumber && hasSymbol;
        }
        

        /**
         * check if provided password matches the user's password
         * @param - the password to verify
         * @return - true if password is correct, false otherwise
         */
        public boolean isAuthorized(String password) {
            return this.password.equals(password);
        }

        /**
         * resets the user's password if new password is valid and different
         * @param - the new password to set
         * @return - true if the password was successfully reset, false otherwise
         */
        public boolean resetPassword(String newPassword) {
            if(isValidPassword(newPassword) && !this.password.equals(newPassword)) {
                this.password = newPassword;
                return true;
            }
            return false;
        }

        /**
         * create a sheet music instance (placeholder method)
         * @param - the sheet to create
         * @return - always returns true (placeholder)
         */
        public boolean createSheet(SheetMusic sheet) {
            return true; 
        }

        /**
         * simulate playing sheet music 
         * @param - the sheet music to play
         */
        public void playSheet(SheetMusic sheet) {
            System.out.println("Playing sheet music...");
        }

        /**
         * sets the metronome speed modifier if speed is greater than 0
         * @param - the speed to set
         * @return - true if speed was set, false otherwise
         */
        public boolean setMetronomeSpeed(double speed) {
            if (speed > 0 ) {
                this.metronomeSpeedModifier = speed;
                return true;
            }
            return false;
        }
    }