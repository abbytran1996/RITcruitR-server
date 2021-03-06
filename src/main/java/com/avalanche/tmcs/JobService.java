package com.avalanche.tmcs;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.Timestamp;
import com.google.cloud.talent.v4beta1.*;
import com.google.cloud.talent.v4beta1.Job.ApplicationInfo;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Abigail My Tran
 * @since 02/18/20
 */

public class JobService {
    /**
     * Create Job Posting with Google Cloud Talent Solution (CTS) API.
     *Not all of the fields in the JobPosting object are saved, only the ones that we thought are helpful for job matching
     *
     * @param projectId the Google API's project ID. Check the project in CTS's console for the ID
     * @param companyName the company's name created from CTS. This is not the display name
     * @param requiredSkills the job's ID from the AWS database
     */

    public static String createJobGoogleAPI(
            String projectId,
            String companyName,
            String companySize,
            String requisitionId, //Job Posting ID on AWS
            String title,
            String description,
            List<String> addresses,
            List<String> recommendedSkills,
            List<String> requiredSkills,
            Boolean workExperience,
            String presentationLinkString,
            String problemStatementString,
            double minimumGPA,
            String jobApplicationUrl,
            String languageCode,
            Timestamp expireTime) throws IOException {

        String strFilePath = "credentials.json";
        File file = new File(strFilePath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(file.getAbsolutePath())).createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        JobServiceSettings settings = JobServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();
        String googleName = "";
        try (JobServiceClient jobServiceClient = JobServiceClient.create(settings)) {
            String parent = "projects/" + projectId;

            List<String> uris = Arrays.asList(jobApplicationUrl);
            ApplicationInfo applicationInfo =
                    com.google.cloud.talent.v4beta1.Job.ApplicationInfo.newBuilder().addAllUris(uris).build();

            //Because Problem Statement is too long and cannot be added as a custom attribute -> Adding it to description
            //...so that job search can match against this information too.
            description += ". " + problemStatementString;

            //CustomAttributes:
            String delim = ", ";

            //TODO: Use these fields in search
            String qualifications = String.join(delim, requiredSkills) + ", " + String.join(delim, recommendedSkills);
            CustomAttribute mininumGpa = CustomAttribute.newBuilder().addLongValues((long)minimumGPA).setFilterable(true).build();
            CustomAttribute hasWorkExperience = CustomAttribute.newBuilder().addStringValues(workExperience.toString()).setFilterable(true).build();

            CustomAttribute size = CustomAttribute.newBuilder().addStringValues(companySize).setFilterable(true).build();
            CustomAttribute presentationLink = CustomAttribute.newBuilder().addStringValues(presentationLinkString).setFilterable(true).build();
            CustomAttribute reqSkills = CustomAttribute.newBuilder().addAllStringValues(requiredSkills).setFilterable(true).build();
            CustomAttribute recSkills = CustomAttribute.newBuilder().addAllStringValues(recommendedSkills).setFilterable(true).build();

            com.google.cloud.talent.v4beta1.Job job =
                    com.google.cloud.talent.v4beta1.Job.newBuilder()
                            .setCompany(companyName)
                            .setRequisitionId(requisitionId)
                            .setTitle(title)
                            .setDescription(description)
                            .setApplicationInfo(applicationInfo)
                            .addAllAddresses(addresses)
                            .setLanguageCode(languageCode)
                            .setPostingPublishTime(Timestamp.now().toProto())
                            .setPostingExpireTime(expireTime.toProto())
                            .putCustomAttributes("companySize", size)
                            .putCustomAttributes("presentationLink", presentationLink)
                            .putCustomAttributes("requiredSkills", reqSkills)
                            .putCustomAttributes("recommendedSkills", recSkills)
                            //.setQualifications(qualifications)
                            //.putCustomAttributes("minimumGpa", mininumGpa)
                            .build();
            CreateJobRequest request =
                    CreateJobRequest.newBuilder().setParent(parent).setJob(job).build();
            com.google.cloud.talent.v4beta1.Job response = jobServiceClient.createJob(request);
            System.out.printf("Created job: %s\n", response.getName());
            googleName = response.getName();
        } catch (Exception exception) {
            System.err.println("Failed to create the client due to: " + exception);
            googleName = exception.toString();
        }
        return googleName;
    }

    /**
     * Get a Job Posting with Google Cloud Talent Solution (CTS) API.
     *Not all of the fields in the JobPosting object are saved, only the ones that we thought are helpful for job matching
     *
     * @param name the job's name created from CTS. This is not the display name
     * Example: projects/recruitrtest-256719/tenants/075e3c6b-df00-0000-0000-00fbd63c7ae0/jobs/134479777856660166
     */

    public static void getJobGoogleAPI(String name) throws IOException {
        String strFilePath = "credentials.json";
        File file = new File(strFilePath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(file.getAbsolutePath())).createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        JobServiceSettings settings = JobServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();

        try (JobServiceClient jobServiceClient = JobServiceClient.create(settings)) {
            GetJobRequest request = GetJobRequest.newBuilder().setName(name).build();
            com.google.cloud.talent.v4beta1.Job response = jobServiceClient.getJob(request);
            System.out.printf("Job name: %s\n", response.getName());
            System.out.printf("Requisition ID: %s\n", response.getRequisitionId());
            System.out.printf("Title: %s\n", response.getTitle());
            System.out.printf("Description: %s\n", response.getDescription());
            System.out.printf("Posting language: %s\n", response.getLanguageCode());
            for (String address : response.getAddressesList()) {
                System.out.printf("Address: %s\n", address);
            }
            for (String email : response.getApplicationInfo().getEmailsList()) {
                System.out.printf("Email: %s\n", email);
            }
            for (String websiteUri : response.getApplicationInfo().getUrisList()) {
                System.out.printf("Website: %s\n", websiteUri);
            }
        } catch (Exception exception) {
            System.err.println("Failed to create the client due to: " + exception);
        }
    }

    /**
     * List all Job Postings under the given project with Google Cloud Talent Solution (CTS) API.
     *Not all of the fields in the JobPosting object are saved, only the ones that we thought are helpful for job matching
     *
     * @param projectId the Google API's project ID. Check the project in CTS's console for the ID
     * @param filter "companyName=[Insert CTS' Company Name]"
     */

    public static void listJobsGoogleAPI(String projectId, String filter) throws IOException {
        String strFilePath = "credentials.json";
        File file = new File(strFilePath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(file.getAbsolutePath())).createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        JobServiceSettings settings = JobServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();
        try (JobServiceClient jobServiceClient = JobServiceClient.create(settings)) {
            String parent = "projects/" + projectId;
            ListJobsRequest request = ListJobsRequest.newBuilder().setParent(parent).setFilter(filter).build();
            List<Job> jobs = new ArrayList<>();
            for (Job responseItem : jobServiceClient.listJobs(request).iterateAll()) {
                jobs.add(responseItem);
                System.out.printf("Job name: %s\n", responseItem.getName());
                System.out.printf("Job requisition ID: %s\n", responseItem.getRequisitionId());
                System.out.printf("Job title: %s\n", responseItem.getTitle());
                System.out.printf("Job description: %s\n", responseItem.getDescription());
            }
            //This is for testing purpose, so you can check what jobs are under the given company.
            //Currently, there is no other way to check this using CTS console.
            try {
                FileWriter myWriter = new FileWriter("created_jobs.txt");
                myWriter.write(jobs.toString());
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } catch (Exception exception) {
            System.err.println("Failed to create the client due to: " + exception);
        }

    }

    /**
     * Delete a Job Posting with Google Cloud Talent Solution (CTS) API.
     *Not all of the fields in the JobPosting object are saved, only the ones that we thought are helpful for job matching
     *
     * @param name the job's name created from CTS. This is not the display name
     * Example: projects/recruitrtest-256719/tenants/075e3c6b-df00-0000-0000-00fbd63c7ae0/jobs/134479777856660166
     */
    public static void deleteJobGoogleAPI(String name) throws IOException {
        String strFilePath = "credentials.json";
        File file = new File(strFilePath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(file.getAbsolutePath())).createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        JobServiceSettings settings = JobServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();
        try (JobServiceClient jobServiceClient = JobServiceClient.create(settings)) {
            DeleteJobRequest request = DeleteJobRequest.newBuilder().setName(name).build();
            jobServiceClient.deleteJob(request);
            System.out.println("Deleted job.");
        } catch (Exception exception) {
            System.err.println("Failed to create the client due to: " + exception);
        }
    }

    /**  Update */
//    public static void updateJobGoogleAPI(String projectId, String companyId, String jobId) throws IOException {
//        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(GoogleAPI.jsonPath))
//                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
//        JobServiceSettings settings = JobServiceSettings.newBuilder()
//                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
//                .build();
//        try (JobServiceClient jobServiceClient = JobServiceClient.create(settings)) {
//            // projectId = "Your Google Cloud Project ID";
//            // tenantId = "Your Tenant ID (using tenancy is optional)";
//            // jobId = "Company ID";
//            String name = "projects/" + projectId + "/tenants/" + companyId + "/jobs/" + jobId;
//            UpdateJobRequest request = UpdateJobRequest.newBuilder()
//                                        .set
//                                        .build();
//            jobServiceClient.updateJob(request);
//            System.out.println("Deleted job.");
//        } catch (Exception exception) {
//            System.err.println("Failed to create the client due to: " + exception);
//        }
//    }



    /**
     * Search Jobs with Google Cloud Talent Solution (CTS) API
     *
     * @param query Search query. More info on histogram facets, constants, and built-in functions:
     *     https://godoc.org/google.golang.org/genproto/googleapis/cloud/talent/v4beta1#SearchJobsRequest
     */
    public static List<SearchJobsResponse.MatchingJob> searchJobsGoogleAPI(String projectId, String query, List<String> locations, List<String> sizes) throws IOException {
        String strFilePath = "credentials.json";
        File file = new File(strFilePath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(file.getAbsolutePath())).createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        JobServiceSettings settings = JobServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();
        try (JobServiceClient jobServiceClient = JobServiceClient.create(settings)) {
            //TODO: This requestMetadata needs to be unique
            String parent = "projects/" + projectId;
            String domain = "www.example.com";
            String sessionId = "Hashed session identifier";
            String userId = "Hashed user identifier";
            RequestMetadata requestMetadata =
                    RequestMetadata.newBuilder()
                            .setDomain(domain)
                            .setSessionId(sessionId)
                            .setUserId(userId)
                            .build();

            List<LocationFilter> locationFilters = new ArrayList<>();
            for (String l : locations) {
                LocationFilter filter = LocationFilter.newBuilder()
                        .setAddress(l)
                        .build();
                locationFilters.add(filter);
            }

            /*
            This field was stored as a custom attribute in Job Posting.
            We planned to use CustomAttributeFilter here but the filter is not supported yet
             */
            String sizeQuery = "";
            for (String s : sizes) {
                sizeQuery += "companySize = " + s + " OR ";
            }
            sizeQuery = sizeQuery.substring(0, sizeQuery.length() - 4);

            JobQuery jobQuery = JobQuery.newBuilder()
                            .setQuery(query)
                            .addAllLocationFilters(locationFilters)
                            //.setCustomAttributeFilter(sizeQuery)
                            .build();
            SearchJobsRequest request =
                    SearchJobsRequest.newBuilder()
                            .setParent(parent)
                            .setRequestMetadata(requestMetadata)
                            .setJobQuery(jobQuery)
                            .setDisableKeywordMatch(false)
                            .setEnableBroadening(true)
                            .setDiversificationLevel(SearchJobsRequest.DiversificationLevel.DISABLED)
                            .setJobView(JobView.JOB_VIEW_FULL)
                            .setOrderBy("relevance desc")
                            .build();
            List<SearchJobsResponse.MatchingJob> jobs = new ArrayList<>();
            for (SearchJobsResponse.MatchingJob responseItem :
                    jobServiceClient.searchJobs(request).iterateAll()) {
                jobs.add(responseItem);
            }
            return jobs;
        } catch (Exception exception) {
            System.err.println("Failed to create the client due to: " + exception);
        }
        return null;
    }
}
