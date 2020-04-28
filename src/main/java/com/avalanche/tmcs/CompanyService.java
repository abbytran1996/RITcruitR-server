package com.avalanche.tmcs;

import com.google.cloud.talent.v4beta1.*;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.collect.Lists;
import com.google.api.gax.core.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;

public class CompanyService {
    public static String createCompanyGoogleAPI(
            String projectId, String displayName, String externalId, String headquartersAddress, String size, String webURL) throws IOException {
        String strFilePath = "credentials.json";
        File file = new File(strFilePath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(file.getAbsolutePath())).createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        CompanyServiceSettings companyServiceSettings =
                CompanyServiceSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                        .build();
        String name = "";
        try (CompanyServiceClient companyServiceClient = CompanyServiceClient.create(companyServiceSettings)) {
            // projectId = "Your Google Cloud Project ID";
            // displayName = "My Company Name";
            // externalId = "Identifier of this company in my system";

            String parent = "projects/" + projectId;
            CompanySize companySize = CompanySize.COMPANY_SIZE_UNSPECIFIED;
            //DONT_CARE, STARTUP, SMALL, MEDIUM, LARGE, HUGE
            switch(size) {
                case "DONT_CARE":
                    companySize = CompanySize.COMPANY_SIZE_UNSPECIFIED;
                    break;
                case "STARTUP":
                    companySize = CompanySize.MINI;
                    break;
                case "SMALL":
                    companySize = CompanySize.SMALL;
                    break;
                case "MEDIUM":
                    companySize = CompanySize.MEDIUM;
                    break;
                case "LARGE":
                    companySize = CompanySize.BIG;
                    break;
                case "HUGE":
                    companySize = CompanySize.GIANT;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + size);
            }

            com.google.cloud.talent.v4beta1.Company company =
                    com.google.cloud.talent.v4beta1.Company.newBuilder()
                            .setDisplayName(displayName)
                            .setExternalId(externalId)
                            .setHeadquartersAddress(headquartersAddress)
                            .setSize(companySize)
                            .setWebsiteUri(webURL)
                            .addKeywordSearchableJobCustomAttributes("requiredSkills")
                            .addKeywordSearchableJobCustomAttributes("recommendedSkills")
                            .addKeywordSearchableJobCustomAttributes("companySize")
                            .build();

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
            name = response.getName();
        } catch (Exception exception) {
            System.err.println("Failed to create the client due to: " + exception);
        }
        return name;
    }

    // Get Company.
    public static Company getCompany(String companyName)
            throws IOException {
        String strFilePath = "credentials.json";
        File file = new File(strFilePath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(file.getAbsolutePath())).createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        CompanyServiceSettings companyServiceSettings =
                CompanyServiceSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                        .build();
        try (CompanyServiceClient companyServiceClient = CompanyServiceClient.create(companyServiceSettings)) {
            GetCompanyRequest request = GetCompanyRequest.newBuilder().setName(companyName).build();

            Company response = companyServiceClient.getCompany(request);
            return response;
        } catch (Exception exception) {
            System.err.println("Failed to get the company due to: " + exception);
        }
        return null;
    }

    public static void listCompaniesGoogleAPI(String projectId) throws IOException {
        String strFilePath = "credentials.json";
        File file = new File(strFilePath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(file.getAbsolutePath())).createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        CompanyServiceSettings companyServiceSettings =
                CompanyServiceSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                        .build();
        try (CompanyServiceClient companyServiceClient = CompanyServiceClient.create(companyServiceSettings)) {

            String parent = "projects/" + projectId;
            ListCompaniesRequest request = ListCompaniesRequest.newBuilder().setParent(parent).build();
            CompanyServiceClient.ListCompaniesPage results = companyServiceClient.listCompanies(request).getPage();
            List<Company> companies = new ArrayList<>();
            for (Company responseItem : companyServiceClient.listCompanies(request).iterateAll()) {
                if (responseItem.getWebsiteUri().equals("")) {
                    deleteCompanyGoogleAPI(responseItem.getName());
                } else {
                    companies.add(responseItem);
                    System.out.printf("Company Name: %s\n", responseItem.getSize());
                    System.out.printf("Display Name: %s\n", responseItem.getDisplayName());
                    System.out.printf("External ID: %s\n", responseItem.getExternalId());
                }
            }
            try {
                FileWriter myWriter = new FileWriter("created_companies.txt");
                myWriter.write(companies.toString());
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

    // Delete Company.
    public static void deleteCompanyGoogleAPI(String companyName)
            throws IOException {
        String strFilePath = "credentials.json";
        File file = new File(strFilePath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(file.getAbsolutePath())).createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
        CompanyServiceSettings companyServiceSettings =
                CompanyServiceSettings.newBuilder()
                        .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                        .build();

        try (CompanyServiceClient companyServiceClient = CompanyServiceClient.create(companyServiceSettings)) {

            DeleteCompanyRequest request =
                    DeleteCompanyRequest.newBuilder().setName(companyName).build();

            companyServiceClient.deleteCompany(request);
            System.out.println("Deleted company");
        }
    }
    ///com.google.api.gax.rpc.PermissionDeniedException: io.grpc.StatusRuntimeException: PERMISSION_DENIED:
    // Your application has authenticated using end user credentials from the Google Cloud SDK or Google Cloud Shell which are not supported by the jobs.googleapis.com.
    // We recommend configuring the billing/quota_project setting in gcloud or using a service account through the auth/impersonate_service_account setting.
    // For more information about service accounts and how to use them in your application, see https://cloud.google.com/docs/authentication/.
}
