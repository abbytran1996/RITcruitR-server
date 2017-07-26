using System;
using TMCS_Client.Controllers;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI
{
    internal class RecruiterProfileManagement : RecruiterRegistration
    {
        private Recruiter recruiter;

        public RecruiterProfileManagement(Recruiter recruiter) : base("Recruiter Editing")
        {
            this.recruiter = recruiter;

            txtFirstName.Text = recruiter.firstName;
            txtLastName.Text = recruiter.lastName;
            txtCompanyName.Text = recruiter.company.companyName;
            txtCompanyName.IsEnabled = false;
            txtPhoneNumber.Text = recruiter.phoneNumber;
            txtCompanyEmail.Text = recruiter.email;

            btnRegister.Text = "Save";
        }

        protected void register()
        {
            recruiter.company.companyName = txtCompanyName.Text;
            recruiter.firstName = txtFirstName.Text;
            recruiter.lastName = txtLastName.Text;
            recruiter.email = txtCompanyEmail.Text;
            recruiter.phoneNumber = txtPhoneNumber.Text;
            try
            {
                RecruiterController.getRecruiterController().updateRecruiter(recruiter);
                Login.getLoginPage().updateLoginStatusMessage(Constants.Forms.LoginStatusMessage.REGISTRATION_COMPLETE);
                Navigation.PopToRootAsync();
            }
            catch(Exception e)
            {
                DisplayAlert("Error", e.Message, "Ok");
            }
        }
    }
}