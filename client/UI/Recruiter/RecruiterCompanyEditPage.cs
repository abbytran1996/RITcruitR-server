using System;
using TMCS_Client.Controllers;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI {
    internal class RecruiterCompanyEditPage : CompanyRegistration {
        private Company company;

        public RecruiterCompanyEditPage(Company company) : base("Company Editing") {
            this.company = company;

            txtCompanyDescription.Text = company.companyDescription;
            txtCompanyEmailSuffix.Text = company.emailSuffix;
            txtCompanyName.Text = company.companyName;
            txtCompanyLocation.Text = company.location;
            txtCompanySize.Text = company.size;
            txtPresentationLink.Text = company.presentation;
            txtWebsiteURL.Text = company.websiteURL;

            btnRegister.Text = "Save";
        }

        protected override void onRegisterButtonClick() {
            string presentation = txtPresentationLink.Text.Replace("/", "|");
            Company newCompany = new Company() {
                companyName = txtCompanyName.Text,
                emailSuffix = txtCompanyEmailSuffix.Text,
                companyDescription = txtCompanyDescription.Text,
                size = txtCompanySize.Text,
                location = txtCompanyLocation.Text,
                presentation = presentation,
                websiteURL = txtWebsiteURL.Text
            };

            try {
                CompanyController.getCompanyController().updateCompany(newCompany);
                Login.getLoginPage().updateLoginStatusMessage(Constants.Forms.LoginStatusMessage.REGISTRATION_COMPLETE);
                Navigation.PopToRootAsync();
            } catch(Exception e) {
                Console.WriteLine(e.ToString());
            }
        }
    }
}