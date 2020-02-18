package com.avalanche.tmcs;

import com.google.cloud.talent.v4beta1.*;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;
import com.google.api.gax.core.*;
import java.io.FileInputStream;
import java.io.IOException;

public class CompanyService {
    public static void sampleCreateCompany(
            String projectId, String displayName, String externalId) throws IOException {
        String jsonPath = "/Users/abigail_tran/Documents/SeniorProject/RecruitRv3-python/GoogleAPI/service_account_key/credentials.json";
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(jsonPath))
                .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        CompanyServiceSettings companyServiceSettings =
                CompanyServiceSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                        .build();
        try (CompanyServiceClient companyServiceClient = CompanyServiceClient.create(companyServiceSettings)) {
            // projectId = "Your Google Cloud Project ID";
            // tenantId = "Your Tenant ID (using tenancy is optional)";
            // displayName = "My Company Name";
            // externalId = "Identifier of this company in my system";

            String parent = "projects/" + projectId;
            com.google.cloud.talent.v4beta1.Company company =
                    com.google.cloud.talent.v4beta1.Company.newBuilder().setDisplayName(displayName).setExternalId(externalId).build();
            CreateCompanyRequest request =
                    CreateCompanyRequest.newBuilder()
                            .setParent(parent)
                            .setCompany(company)
                            .build();
            com.google.cloud.talent.v4beta1.Company response = companyServiceClient.createCompany(request);
            System.out.println("Created Company");
            System.out.printf("Name: %s\n", response.getName());
            System.out.printf("Display Name: %s\n", response.getDisplayName());
            System.out.printf("External ID: %s\n", response.getExternalId());
        } catch (Exception exception) {
            System.err.println("Failed to create the client due to: " + exception);
        }
    }
    ///com.google.api.gax.rpc.PermissionDeniedException: io.grpc.StatusRuntimeException: PERMISSION_DENIED:
    // Your application has authenticated using end user credentials from the Google Cloud SDK or Google Cloud Shell which are not supported by the jobs.googleapis.com.
    // We recommend configuring the billing/quota_project setting in gcloud or using a service account through the auth/impersonate_service_account setting.
    // For more information about service accounts and how to use them in your application, see https://cloud.google.com/docs/authentication/.
}
