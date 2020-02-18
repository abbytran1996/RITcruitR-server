package com.avalanche.tmcs;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.talent.v4beta1.*;
import com.google.cloud.talent.v4beta1.Job.ApplicationInfo;
import com.google.common.collect.Lists;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JobService {
    /**
     * Create Job
     *
     * @param projectId Your Google Cloud Project ID
     */

    public static void sampleCreateJob(
            String projectId,
            String companyName,
            String requisitionId,
            String title,
            String description,
            String jobApplicationUrl,
            String addressOne,
            String addressTwo,
            String languageCode) throws IOException {
        // [START job_search_create_job_core]
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(GoogleAPI.jsonPath))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        JobServiceSettings settings = JobServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();
        try (JobServiceClient jobServiceClient = JobServiceClient.create(settings)) {
            // projectId = "Your Google Cloud Project ID";
            // companyName = "Company name, e.g. projects/your-project/companies/company-id";
            // requisitionId = "Job requisition ID, aka Posting ID. Unique per job.";
            // title = "Software Engineer";
            // description = "This is a description of this <i>wonderful</i> job!";
            // jobApplicationUrl = "https://www.example.org/job-posting/123";
            // addressOne = "1600 Amphitheatre Parkway, Mountain View, CA 94043";
            // addressTwo = "111 8th Avenue, New York, NY 10011";
            // languageCode = "en-US";
            String parent = "projects/" + projectId;

            List<String> uris = Arrays.asList(jobApplicationUrl);
            ApplicationInfo applicationInfo =
                    com.google.cloud.talent.v4beta1.Job.ApplicationInfo.newBuilder().addAllUris(uris).build();
            List<String> addresses = Arrays.asList(addressOne, addressTwo);

            //CustomAttributes:
            CustomAttribute gpa = CustomAttribute.newBuilder().addStringValues("3.5").setFilterable(true).build();

            com.google.cloud.talent.v4beta1.Job job =
                    com.google.cloud.talent.v4beta1.Job.newBuilder()
                            .setCompany(companyName)
                            .setRequisitionId(requisitionId)
                            .setTitle(title)
                            .setDescription(description)
                            .setApplicationInfo(applicationInfo)
                            .addAllAddresses(addresses)
                            .setLanguageCode(languageCode)
                            .putCustomAttributes("gpa", gpa )
                            .build();
            CreateJobRequest request =
                    CreateJobRequest.newBuilder().setParent(parent).setJob(job).build();
            com.google.cloud.talent.v4beta1.Job response = jobServiceClient.createJob(request);
            System.out.printf("Created job: %s\n", response.getName());
        } catch (Exception exception) {
            System.err.println("Failed to create the client due to: " + exception);
        }
        // [END job_search_create_job_core]
    }

    /** Get Job */
    public static void sampleGetJob(String projectId, String companyId, String jobId) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(GoogleAPI.jsonPath))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        JobServiceSettings settings = JobServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();

        try (JobServiceClient jobServiceClient = JobServiceClient.create(settings)) {
            // projectId = "Your Google Cloud Project ID";
            // jobId = "Job ID";
            String name = "projects/" + projectId + "/tenants/" + companyId + "/jobs/" + jobId;
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

    public static void sampleListJobs(String projectId, String filter) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(GoogleAPI.jsonPath))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        JobServiceSettings settings = JobServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();
        try (JobServiceClient jobServiceClient = JobServiceClient.create(settings)) {
            // projectId = "Your Google Cloud Project ID";
            // filter = "companyName=projects/my-project/companies/company-id";
            String parent = "projects/" + projectId;
            ListJobsRequest request = ListJobsRequest.newBuilder().setParent(parent).setFilter(filter).build();
            //ListJobsRequest request = ListJobsRequest.newBuilder().setParent(parent).build();
            for (Job responseItem : jobServiceClient.listJobs(request).iterateAll()) {
                System.out.printf("Job name: %s\n", responseItem.getName());
                System.out.printf("Job requisition ID: %s\n", responseItem.getRequisitionId());
                System.out.printf("Job title: %s\n", responseItem.getTitle());
                System.out.printf("Job description: %s\n", responseItem.getDescription());
            }
        } catch (Exception exception) {
            System.err.println("Failed to create the client due to: " + exception);
        }
    }

    /** Delete Job */
    public static void sampleDeleteJob(String projectId, String companyId, String jobId) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(GoogleAPI.jsonPath))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        JobServiceSettings settings = JobServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();
        try (JobServiceClient jobServiceClient = JobServiceClient.create(settings)) {
            // projectId = "Your Google Cloud Project ID";
            // tenantId = "Your Tenant ID (using tenancy is optional)";
            // jobId = "Company ID";
            String name = "projects/" + projectId + "/tenants/" + companyId + "/jobs/" + jobId;
            DeleteJobRequest request = DeleteJobRequest.newBuilder().setName(name).build();
            jobServiceClient.deleteJob(request);
            System.out.println("Deleted job.");
        } catch (Exception exception) {
            System.err.println("Failed to create the client due to: " + exception);
        }
    }

    /**
     * Search Jobs with histogram queries
     *
     * @param query Histogram query More info on histogram facets, constants, and built-in functions:
     *     https://godoc.org/google.golang.org/genproto/googleapis/cloud/talent/v4beta1#SearchJobsRequest
     */
    public static void sampleSearchJobs(String projectId, String query) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(GoogleAPI.jsonPath))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        JobServiceSettings settings = JobServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();
        try (JobServiceClient jobServiceClient = JobServiceClient.create(settings)) {
            // projectId = "Your Google Cloud Project ID";
            // tenantId = "Your Tenant ID (using tenancy is optional)";
            // query = "count(base_compensation, [bucket(12, 20)])";
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
            HistogramQuery histogramQueriesElement =
                    HistogramQuery.newBuilder().setHistogramQuery(query).build();
            List<HistogramQuery> histogramQueries = Arrays.asList(histogramQueriesElement);
            SearchJobsRequest request =
                    SearchJobsRequest.newBuilder()
                            .setParent(parent.toString())
                            .setRequestMetadata(requestMetadata)
                            .addAllHistogramQueries(histogramQueries)
                            .build();
            for (SearchJobsResponse.MatchingJob responseItem :
                    jobServiceClient.searchJobs(request).iterateAll()) {
                System.out.printf("Job summary: %s\n", responseItem.getJobSummary());
                System.out.printf("Job title snippet: %s\n", responseItem.getJobTitleSnippet());
                Job job = responseItem.getJob();
                System.out.printf("Job name: %s\n", job.getName());
                System.out.printf("Job title: %s\n", job.getTitle());
            }
        } catch (Exception exception) {
            System.err.println("Failed to create the client due to: " + exception);
        }
    }
}
