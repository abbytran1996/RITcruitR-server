﻿using System;
using System.Collections.Generic;
using System.Text.RegularExpressions;
using TMCS_Client.DTOs;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.CustomUIElements.Entries;
using System.Net.Mail;
using System.Threading.Tasks;

using Xamarin.Forms;

namespace TMCS_Client.UI
{
	public class RecruiterRegistration : ContentPage
	{
		//Whole Page
		private ScrollView pageContent;
		private AbsoluteLayout registrationForm;

		//Title
		private PageTitleLabel lblTitle;

		//First Name
		private FormFieldLabel lblFirstName;
		private FormEntry txtFirstName;

		//Last Name
		private FormFieldLabel lblLastName;
		private FormEntry txtLastName;

		//Email
		private FormFieldLabel lblCompanyEmail;
		private FormEntry txtCompanyEmail;

		//Password
		private FormFieldLabel lblPassword;
		private FormEntry txtPassword;

		//Retype Password
		private FormFieldLabel lblRetypePassword;
		private FormEntry txtRetypePassword;

		//Company Name
		private FormFieldLabel lblCompanyName;
		private FormEntry txtCompanyName;

		//Phone Number
		private FormFieldLabel lblPhoneNumber;
		private FormEntry txtPhoneNumber;
        
		//Register Button
		private Button btnRegister;

#if __IOS__
		const double ROW_HEIGHT = 60.0;
#endif
#if __ANDROID__
        const double ROW_HEIGHT = 80.0;
#endif

		public RecruiterRegistration()
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
										  new PageTitleLabel("Recruiter Registration"),
                                          new Rectangle(0, 0, 1.0, Constants.Forms.Sizes.ROW_HEIGHT),
										  AbsoluteLayoutFlags.WidthProportional);


			//First Name
			AbsoluteLayout firstNameInput = new AbsoluteLayout()
			{
			};

			firstNameInput.Children.Add(lblFirstName =
										new FormFieldLabel("First Name"),
										new Rectangle(0.5, 0, 0.9, 0.5),
										AbsoluteLayoutFlags.All);

			firstNameInput.Children.Add(txtFirstName =
										new FormEntry("First Name", Keyboard.Text),
										new Rectangle(0.5, 1.0, 0.9, 0.5),
										AbsoluteLayoutFlags.All);

			txtFirstName.Completed += (object sender, EventArgs e) => txtLastName.Focus();

			registrationForm.Children.Add(firstNameInput,
										 new Rectangle(0, Constants.Forms.Sizes.ROW_HEIGHT, 1.0,
                                                      Constants.Forms.Sizes.ROW_HEIGHT),
										  AbsoluteLayoutFlags.WidthProportional);

			//Last Name
			AbsoluteLayout lastNameInput = new AbsoluteLayout()
			{
			};

			lastNameInput.Children.Add(lblLastName =
									   new FormFieldLabel("Last Name"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			lastNameInput.Children.Add(txtLastName =
									   new FormEntry("Last Name", Keyboard.Text),
									   new Rectangle(0.5, 1.0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

            txtLastName.Completed += (object sender, EventArgs e) => txtCompanyEmail.Focus();

			registrationForm.Children.Add(lastNameInput,
										 new Rectangle(0, 2.0 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, Constants.Forms.Sizes.ROW_HEIGHT),
										  AbsoluteLayoutFlags.WidthProportional);

			//Email
			AbsoluteLayout emailInput = new AbsoluteLayout()
			{
			};

			emailInput.Children.Add(lblCompanyEmail =
									   new FormFieldLabel("Company Email"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			emailInput.Children.Add(txtCompanyEmail =
									new FormEntry("Company Email", Keyboard.Email),
									new Rectangle(0.5, 1.0, 0.9, 0.5),
									AbsoluteLayoutFlags.All);

			txtCompanyEmail.Completed += (object sender, EventArgs e) => txtPassword.Focus();
            txtCompanyEmail.Unfocused += async (object sender, FocusEventArgs e) => await SuffixCheck();
            txtCompanyEmail.Unfocused += (object sender, FocusEventArgs e) => emailCheck();

			registrationForm.Children.Add(emailInput,
										 new Rectangle(0, 3.0 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, Constants.Forms.Sizes.ROW_HEIGHT),
										  AbsoluteLayoutFlags.WidthProportional);

			//Password
			AbsoluteLayout passwordInput = new AbsoluteLayout()
			{
			};

			passwordInput.Children.Add(lblPassword =
									   new FormFieldLabel("Password"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			passwordInput.Children.Add(txtPassword =
									   new FormEntry("Password", Keyboard.Text, true),
									   new Rectangle(0.5, 1.0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			txtPassword.Completed += (object sender, EventArgs e) => txtRetypePassword.Focus();
			txtPassword.Unfocused += (object sender, FocusEventArgs e) => passwordCheck();

			registrationForm.Children.Add(passwordInput,
										 new Rectangle(0, 4.0 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, Constants.Forms.Sizes.ROW_HEIGHT),
										  AbsoluteLayoutFlags.WidthProportional);

			//Retype Password
			AbsoluteLayout retypePasswordInput = new AbsoluteLayout()
			{
			};

			retypePasswordInput.Children.Add(lblRetypePassword =
									   new FormFieldLabel("Retype Password"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			retypePasswordInput.Children.Add(txtRetypePassword =
									   new FormEntry("Retype Password", Keyboard.Text, true),
									   new Rectangle(0.5, 1.0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			txtRetypePassword.Completed += (object sender, EventArgs e) => txtCompanyName.Focus();
			txtRetypePassword.Unfocused += (object sender, FocusEventArgs e) => retypePasswordCheck();

			registrationForm.Children.Add(retypePasswordInput,
										 new Rectangle(0, 5.0 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, Constants.Forms.Sizes.ROW_HEIGHT),
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




			//Phone Number
			AbsoluteLayout phoneNumberInput = new AbsoluteLayout()
			{
			};

			phoneNumberInput.Children.Add(lblPhoneNumber =
									   new FormFieldLabel("Phone Number (Optional)"),
									   new Rectangle(0.5, 0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);

			phoneNumberInput.Children.Add(txtPhoneNumber =
									   new FormEntry("(xxx) xxx-xxxx", Keyboard.Numeric),
									   new Rectangle(0.5, 1.0, 0.9, 0.5),
									   AbsoluteLayoutFlags.All);
            
			txtPhoneNumber.TextChanged += (object sender, TextChangedEventArgs e) => PhoneNumberUpdate();

			registrationForm.Children.Add(phoneNumberInput,
										 new Rectangle(0, 7.0 * Constants.Forms.Sizes.ROW_HEIGHT, 1.0, Constants.Forms.Sizes.ROW_HEIGHT),
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
										 new Rectangle(0.5, 8 * Constants.Forms.Sizes.ROW_HEIGHT + 20, 0.8, Constants.Forms.Sizes.ROW_HEIGHT - 20.0),
										  AbsoluteLayoutFlags.WidthProportional |
										 AbsoluteLayoutFlags.XProportional);

			pageContent.Content = registrationForm;

			Content = pageContent;

		}

		private async void register()
		{
			String invalidDataMessage = "";

			if (!emailCheck())
			{
				invalidDataMessage += "Email is not in proper format.\n";
			}
            if (!SuffixCheck().Result)
            {
                invalidDataMessage += "Company Email must be registered before Recruiter can.\n";
				var ans = await DisplayAlert("Your Company Email Does Not Match Any Company In Our Records.", "Would You Like To Register A New Company", "Yes", "No");
				if (ans == true)
				{
					await this.Navigation.PushAsync(new CompanyRegistration());
					return;
				}
				else
				{
                    return;

				}
            }

            if (!passwordCheck())
                {
                    invalidDataMessage += "Password does not meet the complexity requirements.\n";
                }
			if (!retypePasswordCheck())
			{
				invalidDataMessage += "Passwords do not match.\n";
			}
			if (!phoneNumberCheck())
			{
				invalidDataMessage += "Phone number is not a valid 10 digit phone number.\n";
			}
			else {
                var company = CompanyController.getCompanyController().getCompanyByName(txtCompanyName.Text);

                NewRecruiter newRecruiter = NewRecruiter.CreateAndValidate(
					txtFirstName.Text,
					txtLastName.Text,
					txtCompanyEmail.Text,
                    company,
					txtPhoneNumber.Text.Replace("(", "").Replace(")", "")
						.Replace(" ", "").Replace("-", ""),
                    txtPassword.Text,
                    txtRetypePassword.Text
				);

				try
				{
                    RecruiterController.getRecruiterController().addRecruiter(newRecruiter);
                    Login.getLoginPage().updateLoginStatusMessage(Constants.Forms.LoginStatusMessage.REGISTRATION_COMPLETE);
					await Navigation.PopToRootAsync();
				}
				catch (Exception e)
				{
					Console.WriteLine(e.ToString());
				}
			}
		}
        public async Task<bool> SuffixCheck()
        {

            //bool result;
            //if (txtCompanyEmail.Text == null)
            try
                {
                    MailAddress address = new MailAddress(txtCompanyEmail.Text);
                    string suffix = address.Host;

                var CompanySuffix = CompanyController.getCompanyController().getCompanyByEmailSuffix(suffix);

                if (CompanySuffix.emailSuffix == null)
                    {
                        var ans = await DisplayAlert("Your Company Email Does Not Match Any Company In Our Records.", "Would You Like To Register A New Company", "Yes", "No");
                        if (ans == true)
                        {
                            await this.Navigation.PushAsync(new CompanyRegistration());
                            return ans;
                        }
                        else
                        {
                            return ans;

                        }
                    }
                    else
                    {
                        txtCompanyName.Text = CompanySuffix.companyName;
                        return true;
                    }
                }
                catch
                {
                    return true;
                }

        }

		private bool emailCheck()
		{

            bool result;
            if (Regex.IsMatch(txtCompanyEmail.Text, Constants.Emails.RECRUITER))
			{
				txtCompanyEmail.BackgroundColor = Color.PaleGreen;
				result = true;
			}
			else
			{
				txtCompanyEmail.BackgroundColor = Color.PaleVioletRed;
				result = false;
			}
			return result;
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


		private bool passwordCheck()
		{
			bool result;
			if (Regex.IsMatch(txtPassword.Text, Constants.PASSWORD_REGEX))
			{
				txtPassword.BackgroundColor = Color.PaleGreen;
				result = true;
			}
			else
			{
				txtPassword.BackgroundColor = Color.PaleVioletRed;
				result = false;
			}
			return result;
		}

		private bool retypePasswordCheck()
		{
			bool result;
			if ((txtPassword.Text == null ? "" : txtPassword.Text) !=
			  (txtRetypePassword.Text == null ? "" : txtRetypePassword.Text))
			{
				txtRetypePassword.BackgroundColor = Color.PaleVioletRed;
				result = false;
			}
			else if ((txtRetypePassword.Text != null) && (txtRetypePassword.Text != ""))
			{
				txtRetypePassword.BackgroundColor = Color.PaleGreen;
				result = true;
			}
			else
			{
				txtRetypePassword.BackgroundColor = Color.White;
				result = true;
			}
			return result;
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

