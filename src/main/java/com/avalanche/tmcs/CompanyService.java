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

/**
 * @author Abigail My Tran
 * @since 02/18/20
 */

//TODO: Make the filePath to "credentials.json" a static variable so that you don't have to repeat the code everytime.
public class CompanyService {
    /**
     * Create Company with Google Cloud Talent Solution (CTS) API.
     * Not all of the fields in the Company object are saved, only the ones that we thought are helpful for job matching
     *
     * @param projectId the Google API's project ID. Check the project in CTS's console for the ID
     * @param externalId the company's ID from the AWS database
     */
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
            //This is how CTS uses the projectID
            String parent = "projects/" + projectId;
            CompanySize companySize;

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
            //Refer to this documentation for the meanings of the fields:
            //https://cloud.google.com/talent-solution/job-search/docs/reference/rest/v4beta1/projects.companies
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

    /**
     * Get Company with Google Cloud Talent Solution (CTS) API.
     *
     * @param companyName the company's name created from CTS. This is not the display name.
     * Example: projects/recruitrtest-256719/tenants/075e3c6b-df00-0000-0000-00fbd63c7ae0/companies/9785d3ca-50e0-40a4-98e6-5b76d43f52c1
     */
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

    /**
     * List all companies under the given project with Google Cloud Talent Solution (CTS) API.
     *
     * @param projectId the Google API's project ID. Check the project in CTS's console for the ID
     */
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
            //This is for testing purpose, so you can check what companies are under the project.
            //Currently, there is no other way to check this using CTS console.
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

    /**
     * Delete the given company with Google Cloud Talent Solution (CTS) API.
     *
     * @param companyName the company's name created from CTS. This is not the display name
     */
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
}
