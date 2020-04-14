package com.avalanche.tmcs.students;

import com.avalanche.tmcs.auth.User;
import com.avalanche.tmcs.company.Company;
import com.avalanche.tmcs.job_posting.JobPosting;
import com.avalanche.tmcs.matching.*;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.avalanche.tmcs.matching.MatchingService.getStringSetIntersection;
import static com.avalanche.tmcs.matching.MatchingService.getIndustrySetIntersection;

/**
 * Class to represent a student in the database
 *
 * @author David Dubois
 * @since 05-Apr-17.
 */
@Entity
@Table(name="students")
public class Student {
    private long id;

    private String firstName;
    private String lastName;

    private String email;

    private Date graduationDate;

    private University school;

    private Major major;

    private double gpa;

    private User user;

    private String phoneNumber;

    private String contactEmail;

    private String website; // TODO: Remove field

    private Set<Skill> skills;

    private Set<String> preferredLocations;
    private double preferredLocationsWeight = 0.4f;

    private Set<Industry> preferredIndustries;
    private double preferredIndustriesWeight = 0.3f;

    private Set<Company.Size> preferredCompanySizes = new HashSet<>();
    private double preferredCompanySizeWeight = 0.2f;

    private Set<PresentationLink> presentationLinks;

    private Set<ProblemStatement> problemStatements;

    public Student(){
        this.preferredCompanySizes.add(Company.Size.DONT_CARE);
        this.preferredLocations = new HashSet<>();
        this.preferredCompanySizes = new HashSet<>();
        this.graduationDate = new Date(LocalDate.now().toEpochDay());
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.gpa = 0;
        this.preferredIndustries = new HashSet<>();
        this.skills = new HashSet<>();
        this.preferredCompanySizes = new HashSet<>();
    }

    public boolean readyToMatch(){
        return !(this.skills == null || this.skills.isEmpty());
    }

    private boolean isSetup;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull
    @Pattern(regexp = "^.+@.+\\..+$")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    public Date getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(Date graduationDate) {
        this.graduationDate = graduationDate;
    }

    @ManyToOne
    @JoinColumn(name = "school")
    public University getSchool() {
        return school;
    }

    public void setSchool(University school) {
        this.school = school;
    }

    @ManyToOne
    @JoinColumn(name = "major")
    public Major getMajor() { return major; }

    public void setMajor(Major major) { this.major = major; }

    public double getGpa() { return gpa; }

    public void setGpa(double gpa) { this.gpa = gpa; }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @OneToOne
    @NotNull
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToMany
    @JoinTable(name = "student_skill", joinColumns = @JoinColumn(name = "student_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    @ElementCollection
    public Set<String> getPreferredLocations() {
        return preferredLocations;
    }
    public void setPreferredLocations(Set<String> preferredLocations) {
        this.preferredLocations = preferredLocations;
    }

    @NotNull
    public double getPreferredLocationsWeight() {
        return this.preferredLocationsWeight;
    }
    public void setPreferredLocationsWeight(double preferredLocationsWeight) {
        this.preferredLocationsWeight = preferredLocationsWeight;
    }

    @ElementCollection
    public Set<Industry> getPreferredIndustries() {
        return preferredIndustries;
    }
    public void setPreferredIndustries(Set<Industry> preferredIndustries) {
        this.preferredIndustries = preferredIndustries;
    }

    @NotNull
    public double getPreferredIndustriesWeight() {
        return this.preferredIndustriesWeight;
    }
    public void setPreferredIndustriesWeight(double preferredIndustriesWeight) {
        this.preferredIndustriesWeight = preferredIndustriesWeight;
    }

    @ElementCollection
    public Set<Company.Size> getPreferredCompanySizes() {
        return preferredCompanySizes;
    }
    public void setPreferredCompanySizes(Set<Company.Size> preferredCompanySizes) {
        this.preferredCompanySizes = preferredCompanySizes;
    }

    @NotNull
    public double getPreferredCompanySizeWeight() {
        return this.preferredCompanySizeWeight;
    }
    public void setPreferredCompanySizeWeight(double preferredCompanySizeWeight) {
        this.preferredCompanySizeWeight = preferredCompanySizeWeight;
    }

    @ElementCollection
    public Set<PresentationLink> getPresentationLinks() {
        return presentationLinks;
    }

    public void setPresentationLinks(Set<PresentationLink> presentationLinks) {
        this.presentationLinks = presentationLinks;
    }

    @ElementCollection
    public Set<ProblemStatement> getProblemStatements() {
        return problemStatements;
    }

    public void setProblemStatements(Set<ProblemStatement> problemStatements) {
        this.problemStatements = problemStatements;
    }

    public boolean getIsSetup() {
        return this.isSetup;
    }

    public void setIsSetup(boolean flag) {
        this.isSetup = flag;
    }

    public double calculateStudentPreferencesWeight(){
        return preferredCompanySizeWeight + preferredIndustriesWeight + preferredLocationsWeight;
    }

    public double calculateStudentPreferencesScore(JobPosting job, Match newMatch){
        double sumScores = 0;
        double normalizedWeightDenominator = calculateStudentPreferencesWeight();

        Set<String> matchedLocations = getStringSetIntersection(preferredLocations, job.getLocations());
        boolean locationMatch = preferredLocations.isEmpty() || !matchedLocations.isEmpty();
        if(locationMatch){
            sumScores += preferredLocationsWeight;
            newMatch.setMatchedLocations(matchedLocations);
        }

        boolean sizeMatch = preferredCompanySizes.isEmpty() ||
                preferredCompanySizes.contains(Company.Size.DONT_CARE) ||
                preferredCompanySizes.contains(job.getCompany().getSize());
        if(sizeMatch){ sumScores += preferredCompanySizeWeight; }

        Set<Industry> matchedIndustries = getIndustrySetIntersection(preferredIndustries, job.getCompany().getIndustries());
        boolean industryMatch = preferredIndustries.isEmpty() || !matchedIndustries.isEmpty();
        if(industryMatch){
            sumScores += preferredIndustriesWeight;
            newMatch.setMatchedIndustries(matchedIndustries);
        }

        return (sumScores * 1.0f)/normalizedWeightDenominator;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(!(o instanceof Student)) {
            return false;
        }

        Student student = (Student) o;

        return id == student.id && email.equals(student.email);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + email.hashCode();
        return result;
    }
}
