using System;
using TMCS_Client.Controllers;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI {
    internal class RecruiterCompanyEditPage : CompanyRegistration {
        private Company company;

        public RecruiterCompanyEditPage(Recruiter recruiter) : base(recruiter.user, "Company Editing") {
            this.company = recruiter.company;

            txtCompanyDescription.Text = company.companyDescription;
            txtCompanyEmailSuffix.Text = company.emailSuffix;
            txtCompanyName.Text = company.companyName;
            txtCompanyLocation.Text = company.location;
            companySizePicker.SelectedIndex = companySizePicker.Items.IndexOf(company.size );
            txtPresentationLink.Text = company.presentation;
            txtWebsiteURL.Text = company.websiteURL;

            btnRegister.Text = "Save";
        }

        protected override void onRegisterButtonClick() {
            string presentation = txtPresentationLink.Text.Replace("/", "|");
            company.companyName = txtCompanyName.Text;
            company.emailSuffix = txtCompanyEmailSuffix.Text;
            company.companyDescription = txtCompanyDescription.Text;
            company.size = companySizePicker.SelectedItem.ToString();
            company.location = txtCompanyLocation.Text;
            company.presentation = presentation;
            company.websiteURL = txtWebsiteURL.Text;

            try {
                CompanyController.getCompanyController().updateCompany(company);
                Login.getLoginPage().updateLoginStatusMessage(Constants.Forms.LoginStatusMessage.REGISTRATION_COMPLETE);
                Navigation.PopToRootAsync();
            } catch(Exception e) {
                DisplayAlert("Error", e.Message, "Ok");
            }
        }
    }
}