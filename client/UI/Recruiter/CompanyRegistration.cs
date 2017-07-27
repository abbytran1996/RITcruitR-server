using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;
using TMCS_Client.DTOs;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.CustomUIElements.Entries;

using Xamarin.Forms;
using TMCS_Client.CustomUIElements.Pickers;

namespace TMCS_Client.UI
{
    public class CompanyRegistration : ContentPage
    {
        //Whole Page
        private ScrollView pageContent;
        private AbsoluteLayout registrationForm;

        //Title
        private PageTitleLabel lblTitle;

        //CompanyEmail
        private FormFieldLabel lblCompanyEmailSuffix;
        protected FormEntry txtCompanyEmailSuffix;

        //Company Name
        private FormFieldLabel lblCompanyName;
        protected FormEntry txtCompanyName;

        //Location
        private FormFieldLabel lblCompanyLocation;
        protected FormEntry txtCompanyLocation;

        //New Company* Description
        private FormFieldLabel lblCompanyDescription;
        protected Editor txtCompanyDescription;

        //Company Size
        private FormFieldLabel lblCompanySize;
        protected Picker companySizePicker;

        //Company Presentation
        private FormFieldLabel lblPresentationLink;
        protected FormEntry txtPresentationLink;

        private FormFieldLabel lblWebsiteURL;
        protected FormEntry txtWebsiteURL;

        //Register Button
        protected Button btnRegister;

        public CompanyRegistration(string title = "Company Registration")
        {
            this.Title = title;

            //Whole page
            pageContent = new ScrollView()
            {
                Orientation = ScrollOrientation.Vertical,
            };

            registrationForm = new AbsoluteLayout()
            {
                HeightRequest = (Constants.Forms.Sizes.ROW_HEIGHT * 12.0),
            };


            //Title
            registrationForm.Children.Add(lblTitle = new PageTitleLabel(title),
                                          new Rectangle(0, 0, 1.0, Constants.Forms.Sizes.ROW_HEIGHT),
                                          AbsoluteLayoutFlags.WidthProportional);


            //Company Name
            AbsoluteLayout companyNameInput = new AbsoluteLayout() { };

            companyNameInput.Children.Add(lblCompanyName = new FormFieldLabel("Company Name"),
                                       new Rectangle(0.5, 0, 0.9, 0.5), AbsoluteLayoutFlags.All);

            companyNameInput.Children.Add(txtCompanyName = new FormEntry("Company Name", Keyboard.Text),
                                       new Rectangle(0.5, 1.0, 0.9, 0.5), AbsoluteLayoutFlags.All);

            txtCompanyName.Completed += (object sender, EventArgs e) => txtCompanyEmailSuffix.Focus();

            registrationForm.Children.Add(companyNameInput, 
                                          new Rectangle(0, Constants.Forms.Sizes.ROW_HEIGHT, 1.0, 
                                                        Constants.Forms.Sizes.ROW_HEIGHT),
                                          AbsoluteLayoutFlags.WidthProportional);


            //Email
            AbsoluteLayout emailInput = new AbsoluteLayout() { };

            emailInput.Children.Add(lblCompanyEmailSuffix = new FormFieldLabel("Company Suffix"),
                                       new Rectangle(0.5, 0, 0.9, 0.5), AbsoluteLayoutFlags.All);

            emailInput.Children.Add(txtCompanyEmailSuffix = new FormEntry("@CompanyName.com", Keyboard.Email),
                                    new Rectangle(0.5, 1.0, 0.9, 0.5), AbsoluteLayoutFlags.All);

            txtCompanyEmailSuffix.Completed += (object sender, EventArgs e) => txtCompanyLocation.Focus();
            txtCompanyEmailSuffix.Unfocused += (object sender, FocusEventArgs e) => suffixCheck();

            registrationForm.Children.Add(emailInput, 
                                          new Rectangle(0, 2.0 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, 
                                                        Constants.Forms.Sizes.ROW_HEIGHT),
                                          AbsoluteLayoutFlags.WidthProportional);

            //Location
            AbsoluteLayout LocationInput = new AbsoluteLayout() { };

            LocationInput.Children.Add(lblCompanyLocation = new FormFieldLabel("Company Location(s)"),
                                       new Rectangle(0.5, 0, 0.9, 0.5), AbsoluteLayoutFlags.All);

            LocationInput.Children.Add(txtCompanyLocation = new FormEntry("State, State, ...", Keyboard.Text),
                                       new Rectangle(0.5, 1.0, 0.9, 0.5), AbsoluteLayoutFlags.All);
            
            txtCompanyLocation.Completed += (object sender, EventArgs e) => companySizePicker.Focus();

            registrationForm.Children.Add(LocationInput, 
                                          new Rectangle(0, 3.0 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, 
                                                        Constants.Forms.Sizes.ROW_HEIGHT),
                                          AbsoluteLayoutFlags.WidthProportional);

            //Company Size
            AbsoluteLayout SizeInput = new AbsoluteLayout() { };

            SizeInput.Children.Add(lblCompanySize = new FormFieldLabel("Company Size"), new Rectangle(0.5, 0, 0.9, 0.5), 
                                   AbsoluteLayoutFlags.All);
            
            SizeInput.Children.Add(companySizePicker = new CompanySizePicker(), new Rectangle(0.5, 1.0, 0.9, 0.5), 
                                   AbsoluteLayoutFlags.All);

            registrationForm.Children.Add(SizeInput, 
                                          new Rectangle(0, 4 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, 
                                                        Constants.Forms.Sizes.ROW_HEIGHT),
                                          AbsoluteLayoutFlags.WidthProportional);
            //Company Description
            AbsoluteLayout Descriptionlbl = new AbsoluteLayout() { };

            Descriptionlbl.Children.Add(lblCompanyDescription = new FormFieldLabel("Company Description"),
                                        new Rectangle(0.5, 0, 0.9, 0.5), AbsoluteLayoutFlags.All);


            registrationForm.Children.Add(Descriptionlbl, 
                                          new Rectangle(0, 5 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, 
                                                        Constants.Forms.Sizes.ROW_HEIGHT),
                                          AbsoluteLayoutFlags.WidthProportional);

            AbsoluteLayout DescriptionInput = new AbsoluteLayout() { };
            DescriptionInput.Children.Add(txtCompanyDescription =
                                      new Editor
                                      {
                                          FontSize = 12,
                                          BackgroundColor = Color.LightGray,
                                      },
                                      new Rectangle(0.5, 1.0, 0.9, 0.85), AbsoluteLayoutFlags.All);
            txtCompanyDescription.Completed += (object sender, EventArgs e) => txtPresentationLink.Focus();
            registrationForm.Children.Add(DescriptionInput, 
                                          new Rectangle(0, 5 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, 3 * 
                                                        Constants.Forms.Sizes.ROW_HEIGHT), 
                                          AbsoluteLayoutFlags.WidthProportional);

            //Presentation
            AbsoluteLayout PresentationInput = new AbsoluteLayout() { };

            PresentationInput.Children.Add(lblPresentationLink =
                                           new FormFieldLabel("Company Presentation (Youtube Link)"),
                                           new Rectangle(0.5, 0, 0.9, 0.5), AbsoluteLayoutFlags.All);
            PresentationInput.Children.Add(txtPresentationLink = 
                                           new FormEntry("Company Presentation (Youtube Link)", Keyboard.Text), 
                                           new Rectangle(0.5, 1.0, 0.9, 0.5), AbsoluteLayoutFlags.All);

            txtCompanyDescription.Completed += (object sender, EventArgs e) => txtWebsiteURL.Focus();

            registrationForm.Children.Add(PresentationInput, 
                                          new Rectangle(0, 8.0 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, 
                                                        Constants.Forms.Sizes.ROW_HEIGHT),
                                          AbsoluteLayoutFlags.WidthProportional);

            //URL
            AbsoluteLayout URLInput = new AbsoluteLayout() { };

            URLInput.Children.Add(lblWebsiteURL = new FormFieldLabel("Company Website URL"), 
                                  new Rectangle(0.5, 0, 0.9, 0.5), AbsoluteLayoutFlags.All);
            URLInput.Children.Add(txtWebsiteURL = new FormEntry("Company Website URL", Keyboard.Text), 
                                  new Rectangle(0.5, 1.0, 0.9, 0.5), AbsoluteLayoutFlags.All);
            registrationForm.Children.Add(URLInput, 
                                          new Rectangle(0, 9.0 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, 
                                                        Constants.Forms.Sizes.ROW_HEIGHT),
                                          AbsoluteLayoutFlags.WidthProportional);
            
            //Register Button
            btnRegister = new Button()
            {
                Text = "Register",
                FontSize = 24,
                TextColor = Color.White,
                BackgroundColor = Color.Blue,
                Command = new Command((object obj) => onRegisterButtonClick()),
            };
            registrationForm.Children.Add(btnRegister, 
                                          new Rectangle(0.5, 10 * Constants.Forms.Sizes.ROW_HEIGHT + 20, 0.8, 
                                                        Constants.Forms.Sizes.ROW_HEIGHT - 20.0),
                                          AbsoluteLayoutFlags.WidthProportional | AbsoluteLayoutFlags.XProportional);

            pageContent.Content = registrationForm;
            Content = pageContent;
        }

        virtual protected void onRegisterButtonClick()
        {
            String invalidDataMessage = "";
            
            if(!suffixCheck())
            {
                invalidDataMessage += 
                    "Email Suffix already exists in our system, Please register as a Recruiter with company email.\n";
            }
            else
            {
                string presentation = txtPresentationLink.Text.Replace("/", "|");
                NewCompany newCompany = NewCompany.createAndValidate(
                    txtCompanyName.Text,
                    txtCompanyEmailSuffix.Text,
                    txtCompanyDescription.Text,
                    companySizePicker.SelectedItem.ToString(),
                    txtCompanyLocation.Text,
                    presentation,
                    txtWebsiteURL.Text
                );

                try
                {
                    CompanyController.getCompanyController().addCompany(newCompany);
                    Login.getLoginPage().updateLoginStatusMessage(Constants.Forms.LoginStatusMessage.REGISTRATION_COMPLETE);
                    Navigation.PopToRootAsync();
                }
                catch(Exception e)
                {
                    Console.WriteLine(e.ToString());
                }
            }
        }


        private bool suffixCheck()
        {
            //TODO Compare to server to make see if company exist or whether a new one needs to be registered
            return true;
        }

    }
}

