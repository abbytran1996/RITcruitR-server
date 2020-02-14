package com.avalanche.tmcs;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.talent.v4beta1.*;
import com.google.cloud.talent.v4beta1.Job.ApplicationInfo;
import com.google.common.collect.Lists;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JobSearchCreateJob {
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
        String jsonPath = "/Users/abigail_tran/Documents/SeniorProject/RecruitRv3-python/GoogleAPI/service_account_key/credentials.json";
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
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
            Job.ApplicationInfo applicationInfo =
                    Job.ApplicationInfo.newBuilder().addAllUris(uris).build();
            List<String> addresses = Arrays.asList(addressOne, addressTwo);

            //CustomAttributes:
            CustomAttribute gpa = CustomAttribute.newBuilder().addStringValues("3.5").setFilterable(true).build();

            Job job =
                    Job.newBuilder()
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
            Job response = jobServiceClient.createJob(request);
            System.out.printf("Created job: %s\n", response.getName());
        } catch (Exception exception) {
            System.err.println("Failed to create the client due to: " + exception);
        }
        // [END job_search_create_job_core]
    }
}
