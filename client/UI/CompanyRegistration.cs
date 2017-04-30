using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;
using TMCS_Client.DTOs;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.CustomUIElements.Entries;

using Xamarin.Forms;

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
        private FormEntry txtCompanyEmailSuffix;

		//Company Name
		private FormFieldLabel lblCompanyName;
		private FormEntry txtCompanyName;

		//Phone Number Do We need Phone Number???
		private FormFieldLabel lblPhoneNumber;
		private FormEntry txtPhoneNumber;

		//Location
		private FormFieldLabel lblCompanyLocation;
		private FormEntry txtCompanyLocation;

		
        //New Company* Description
        private FormFieldLabel lblCompanyDescription;
        private Editor txtCompanyDescription;

        //Company Size
        private FormFieldLabel lblCompanySize;
        private FormEntry txtCompanySize;

        //TODO: Company Presentation Field, may need to add nuget package for youtube plugin


		//Register Button
		private Button btnRegister;

#if __IOS__
        const double ROW_HEIGHT = 60.0;
#endif
#if __ANDROID__
        const double ROW_HEIGHT = 80.0;
#endif

		public CompanyRegistration()
		{
			//Whole page
			pageContent = new ScrollView()
			{
				Orientation = ScrollOrientation.Vertical,
			};

			registrationForm = new AbsoluteLayout()
			{
				HeightRequest = (Constants.Forms.Sizes.ROW_HEIGHT * 11.0),
			};


			//Title
			registrationForm.Children.Add(lblTitle =
										  new PageTitleLabel("Company Registration"),
										  new Rectangle(0, 0, 1.0, Constants.Forms.Sizes.ROW_HEIGHT),
										  AbsoluteLayoutFlags.WidthProportional);

        

			//Email
			AbsoluteLayout emailInput = new AbsoluteLayout()
			{
			};

			emailInput.Children.Add(lblCompanyEmailSuffix =
									   new FormFieldLabel("Company Suffix"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			emailInput.Children.Add(txtCompanyEmailSuffix =
									new FormEntry("@CompanyName.com", Keyboard.Email),
									new Rectangle(0.5, 1.0, 0.9, 0.5),
									AbsoluteLayoutFlags.All);

            txtCompanyEmailSuffix.Completed += (object sender, EventArgs e) => txtCompanyName.Focus();
            txtCompanyEmailSuffix.Unfocused += (object sender, FocusEventArgs e) => suffixCheck();

			registrationForm.Children.Add(emailInput,
										 new Rectangle(0, 3.0 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, Constants.Forms.Sizes.ROW_HEIGHT),
										  AbsoluteLayoutFlags.WidthProportional);

				
			//Company Name
			AbsoluteLayout companyNameInput = new AbsoluteLayout()
			{
			};

			companyNameInput.Children.Add(lblCompanyName =
									   new FormFieldLabel("Company Name"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			companyNameInput.Children.Add(txtCompanyName =
									   new FormEntry("Company Name", Keyboard.Text),
									   new Rectangle(0.5, 1.0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			txtCompanyName.Completed += (object sender, EventArgs e) => txtPhoneNumber.Focus();

			registrationForm.Children.Add(companyNameInput,
										 new Rectangle(0, 6.0 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, Constants.Forms.Sizes.ROW_HEIGHT),
										  AbsoluteLayoutFlags.WidthProportional);
            

			AbsoluteLayout LocationInput = new AbsoluteLayout()
			{
			};

			LocationInput.Children.Add(lblCompanyLocation =
									   new FormFieldLabel("Office Location"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			LocationInput.Children.Add(txtCompanyLocation =
									   new FormEntry("State, State, ...", Keyboard.Text, true),
									   new Rectangle(0.5, 1.0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);


			registrationForm.Children.Add(LocationInput,
										 new Rectangle(0, 8.0 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, Constants.Forms.Sizes.ROW_HEIGHT),
										  AbsoluteLayoutFlags.WidthProportional);
			//Company Description

			//Register Button
			btnRegister = new Button()
			{
				Text = "Register",
				FontSize = 24,
				TextColor = Color.White,
				BackgroundColor = Color.Blue,
				Command = new Command((object obj) => register()),
			};

			registrationForm.Children.Add(btnRegister,
										 new Rectangle(0.5, 9 * Constants.Forms.Sizes.ROW_HEIGHT + 20, 0.8, Constants.Forms.Sizes.ROW_HEIGHT - 20.0),
										  AbsoluteLayoutFlags.WidthProportional |
										 AbsoluteLayoutFlags.XProportional);

			pageContent.Content = registrationForm;

			Content = pageContent;

		}

		private void register()
		{
			String invalidDataMessage = "";

            if (!suffixCheck())
            {
                invalidDataMessage += "Email Suffix already exists in our system, Please register as a Recruiter with company email.\n";
            }

            else
            {
                NewCompany newCompany = NewCompany.createAndValidate(
                    txtCompanyName.Text,
                    txtCompanyEmailSuffix.Text,
                    /*txtPhoneNumber.Text.Replace("(", "").Replace(")", "")
						.Replace(" ", "").Replace("-", ""),
						*/
                    txtCompanyLocation.Text.Replace(", ", ",").Split(',').ToString(),
                    txtCompanySize.Text,
                    txtCompanyDescription.Text

                );

                try
                {
                    CompanyController.getCompanyController().addCompany(newCompany);
                    Login.getLoginPage().updateLoginStatusMessage(Constants.Forms.LoginStatusMessage.REGISTRATION_COMPLETE);
                    Navigation.PopToRootAsync();
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                }
            }
		}


        private bool suffixCheck()
        {
            return true;
        }

		private void PhoneNumberUpdate()
		{
			String stripedPhoneNumber =
				txtPhoneNumber.Text.Replace(" ", "").Replace("-", "")
							  .Replace("(", "").Replace(")", "").Replace(".", "");

			String formattedPhoneNumber = "";

			if (stripedPhoneNumber.Length > 0)
			{
				formattedPhoneNumber += "(";
				formattedPhoneNumber += stripedPhoneNumber.Substring(0, Math.Min(3, stripedPhoneNumber.Length));
			}
			if (stripedPhoneNumber.Length > 3)
			{
				formattedPhoneNumber += ") ";
				formattedPhoneNumber += stripedPhoneNumber.Substring(3, Math.Min(3, stripedPhoneNumber.Length - 3));
			}
			if (stripedPhoneNumber.Length > 6)
			{
				formattedPhoneNumber += "-";
				formattedPhoneNumber += stripedPhoneNumber.Substring(6, Math.Min(4, stripedPhoneNumber.Length - 6));
			}

			txtPhoneNumber.Text = formattedPhoneNumber;
		}
	


		private bool phoneNumberCheck()
		{
			bool result;

			if ((txtPhoneNumber.Text == null) || (txtPhoneNumber.Text == ""))
			{
				txtPhoneNumber.BackgroundColor = Color.White;
				result = true;
			}
			else if (Regex.IsMatch(txtPhoneNumber.Text.Replace(" ", "").Replace("(", "")
								  .Replace(")", "").Replace("-", ""), "[0-9]{10}"))
			{
				result = true;
				txtPhoneNumber.BackgroundColor = Color.PaleGreen;
			}
			else
			{
				result = false;
				txtPhoneNumber.BackgroundColor = Color.PaleVioletRed;
			}

			return result;
		}
	}
}

