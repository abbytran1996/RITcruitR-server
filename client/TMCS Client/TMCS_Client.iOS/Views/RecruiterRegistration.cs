using Foundation;
using System;
using UIKit;
using TMCS_Client.DTOs;
using System.Collections.Generic;
using TMCS_Client.ServerComms;

namespace TMCS_Client.iOS
{
	internal class RecruiterRegistrationSource : UITableViewSource
	{
		//Comms
		//Waitng for Recruiter com to be created
		//RecruiterComms recruiterComms = new RecruiterComms();

		//All Cells
		private UIView[] cells = new UIView[10];

		//Title Cell
		UILabel lblTitle = null;

		//First Name Cell
		UILabel lblFirstName = null;
		UITextField txtBoxFirstName = null;

		//Last Name Cell
		UILabel lblLastName = null;
		UITextField txtBoxLastName = null;

		//Email Cell
		UILabel lblEmail = null;
		UITextField txtBoxEmail = null;

		//Passowrd Cell
		UILabel lblPassword = null;
		UITextField txtBoxPassword = null;

		//Retype Password Cell
		UILabel lblRetypePassword = null;
		UITextField txtBoxRetypePassword = null;

		//Company Name Cell
		UILabel lblCompanyName = null;
		UITextField txtBoxCompanyName = null;

		//Phone Number
		UILabel lblPhoneNumber = null;
		UITextField txtBoxPhoneNumber = null;

		// Location Cell
		UILabel lblLocation = null;
		UITextField txtBoxLocation = null;

		// Company Size Cell - not needed; only for company registration
		//UILabel lblSize = null;
		//UITextField txtboxSize = null;

		//Submit Cell
		UIButton btnRegister = null;

		//Colors for text boxes
		UIColor paleGreen = new UIColor(0.75f, 1.0f, 0.75f, 1.0f);
		UIColor paleRed = new UIColor(1.0f, 0.75f, 0.75f, 1.0f);

		public RecruiterRegistrationSource()
		{
			//Title Cell
			cells[0] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
			lblTitle = new UILabel(new CoreGraphics.CGRect(0, 0, 375, 60));
			lblTitle.Text = "Recruiter Registration";
			lblTitle.Font = lblTitle.Font.WithSize(28);
			lblTitle.TextAlignment = UITextAlignment.Center;
			cells[0].AddSubview(lblTitle);

			//First Name Cell
			cells[1] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
			lblFirstName = new UILabel(new CoreGraphics.CGRect(16, 0, 91, 21));
			lblFirstName.Text = "First Name";
			lblFirstName.Font = lblFirstName.Font.WithSize(18);
			txtBoxFirstName = new UITextField(new CoreGraphics.CGRect(16, 22, 343, 30));
			txtBoxFirstName.Placeholder = "First Name";
			txtBoxFirstName.BorderStyle = UITextBorderStyle.RoundedRect;
			txtBoxFirstName.Font = txtBoxFirstName.Font.WithSize(16);
			txtBoxFirstName.SetTextContentType(UITextContentType.GivenName);
			txtBoxFirstName.KeyboardType = UIKeyboardType.ASCIICapable;
			txtBoxFirstName.ReturnKeyType = UIReturnKeyType.Next;
			txtBoxFirstName.AutocapitalizationType = UITextAutocapitalizationType.Words;
			txtBoxFirstName.AutocorrectionType = UITextAutocorrectionType.No;
			txtBoxFirstName.ShouldReturn = (UITextField textField) => txtBoxLastName.BecomeFirstResponder();
			cells[1].AddSubviews(new UIView[] { lblFirstName, txtBoxFirstName });

			//Last Name Cell
			cells[2] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
			lblLastName = new UILabel(new CoreGraphics.CGRect(16, 0, 91, 21));
			lblLastName.Text = "Last Name";
			lblLastName.Font = lblLastName.Font.WithSize(18);
			txtBoxLastName = new UITextField(new CoreGraphics.CGRect(16, 22, 343, 30));
			txtBoxLastName.Placeholder = "Last Name";
			txtBoxLastName.BorderStyle = UITextBorderStyle.RoundedRect;
			txtBoxLastName.Font = txtBoxLastName.Font.WithSize(16);
			txtBoxLastName.SetTextContentType(UITextContentType.FamilyName);
			txtBoxLastName.KeyboardType = UIKeyboardType.ASCIICapable;
			txtBoxLastName.ReturnKeyType = UIReturnKeyType.Next;
			txtBoxLastName.AutocapitalizationType = UITextAutocapitalizationType.Words;
			txtBoxLastName.AutocorrectionType = UITextAutocorrectionType.No;
			txtBoxLastName.ShouldReturn = (UITextField textField) => txtBoxEmail.BecomeFirstResponder();
			cells[2].AddSubviews(new UIView[] { lblLastName, txtBoxLastName });

			//Email Cell
			cells[3] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
			lblEmail = new UILabel(new CoreGraphics.CGRect(16, 0, 47, 21));
			lblEmail.Text = "Email";
			lblEmail.Font = lblEmail.Font.WithSize(18);
			txtBoxEmail = new UITextField(new CoreGraphics.CGRect(16, 22, 343, 30));
			txtBoxEmail.Placeholder = "Email";
			txtBoxEmail.BorderStyle = UITextBorderStyle.RoundedRect;
			txtBoxEmail.Font = txtBoxEmail.Font.WithSize(16);
			txtBoxEmail.SetTextContentType(UITextContentType.EmailAddress);
			txtBoxEmail.KeyboardType = UIKeyboardType.EmailAddress;
			txtBoxEmail.ReturnKeyType = UIReturnKeyType.Next;
			txtBoxEmail.AutocapitalizationType = UITextAutocapitalizationType.None;
			txtBoxEmail.AutocorrectionType = UITextAutocorrectionType.No;
			txtBoxEmail.ShouldReturn = checkEmail;
			cells[3].AddSubviews(new UIView[] { lblEmail, txtBoxEmail });

			//Password Cell
			cells[4] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
			lblPassword = new UILabel(new CoreGraphics.CGRect(16, 0, 80, 21));
			lblPassword.Text = "Password";
			lblPassword.Font = lblPassword.Font.WithSize(18);
			txtBoxPassword = new UITextField(new CoreGraphics.CGRect(16, 22, 343, 30));
			txtBoxPassword.Placeholder = "Password";
			txtBoxPassword.BorderStyle = UITextBorderStyle.RoundedRect;
			txtBoxPassword.Font = txtBoxPassword.Font.WithSize(16);
			txtBoxPassword.SecureTextEntry = true;
			txtBoxPassword.KeyboardType = UIKeyboardType.ASCIICapable;
			txtBoxPassword.ReturnKeyType = UIReturnKeyType.Next;
			txtBoxPassword.AutocapitalizationType = UITextAutocapitalizationType.None;
			txtBoxPassword.AutocorrectionType = UITextAutocorrectionType.No;
			txtBoxPassword.ShouldReturn = checkPassword;
			cells[4].AddSubviews(new UIView[] { lblPassword, txtBoxPassword });

			//Retype Password Cell
			cells[5] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
			lblRetypePassword = new UILabel(new CoreGraphics.CGRect(16, 0, 150, 21));
			lblRetypePassword.Text = "Retype Password";
			lblRetypePassword.Font = lblRetypePassword.Font.WithSize(18);
			txtBoxRetypePassword = new UITextField(new CoreGraphics.CGRect(16, 22, 343, 30));
			txtBoxRetypePassword.Placeholder = "Retype Password";
			txtBoxRetypePassword.BorderStyle = UITextBorderStyle.RoundedRect;
			txtBoxRetypePassword.Font = txtBoxPassword.Font.WithSize(16);
			txtBoxRetypePassword.SecureTextEntry = true;
			txtBoxRetypePassword.KeyboardType = UIKeyboardType.ASCIICapable;
			txtBoxRetypePassword.ReturnKeyType = UIReturnKeyType.Next;
			txtBoxRetypePassword.AutocapitalizationType = UITextAutocapitalizationType.None;
			txtBoxRetypePassword.AutocorrectionType = UITextAutocorrectionType.No;
			txtBoxRetypePassword.ShouldReturn = checkRetypedPassword;
			cells[5].AddSubviews(new UIView[] { lblRetypePassword, txtBoxRetypePassword });

			//Company Name Cell
			cells[6] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
			lblCompanyName = new UILabel(new CoreGraphics.CGRect(16, 0, 207, 21));
			lblCompanyName.Text = "Company Name";
			lblCompanyName.Font = lblCompanyName.Font.WithSize(18);
			txtBoxCompanyName = new UITextField(new CoreGraphics.CGRect(16, 22, 343, 30));
			txtBoxCompanyName.Placeholder = "Company Name";
			txtBoxCompanyName.BorderStyle = UITextBorderStyle.RoundedRect;
			txtBoxCompanyName.Font = txtBoxCompanyName.Font.WithSize(16);
			txtBoxCompanyName.SetTextContentType(UITextContentType.OrganizationName);
			txtBoxCompanyName.KeyboardType = UIKeyboardType.ASCIICapable;
			txtBoxCompanyName.ReturnKeyType = UIReturnKeyType.Next;
			txtBoxCompanyName.AutocapitalizationType = UITextAutocapitalizationType.Words;
			txtBoxCompanyName.AutocorrectionType = UITextAutocorrectionType.No;
			txtBoxCompanyName.ShouldReturn = (UITextField textField) => txtBoxPhoneNumber.BecomeFirstResponder();
			cells[6].AddSubviews(new UIView[] { lblCompanyName, txtBoxCompanyName });

			//Phone Number Cell
			cells[7] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
			lblPhoneNumber = new UILabel(new CoreGraphics.CGRect(16, 0, 207, 21));
			lblPhoneNumber.Text = "Phone Number (Optional)";
			lblPhoneNumber.Font = lblPhoneNumber.Font.WithSize(18);
			txtBoxPhoneNumber = new UITextField(new CoreGraphics.CGRect(16, 22, 343, 30));
			txtBoxPhoneNumber.Placeholder = "(xxx) xxx-xxxx";
			txtBoxPhoneNumber.BorderStyle = UITextBorderStyle.RoundedRect;
			txtBoxPhoneNumber.Font = txtBoxPhoneNumber.Font.WithSize(16);
			txtBoxPhoneNumber.KeyboardType = UIKeyboardType.AsciiCapableNumberPad;
			txtBoxPhoneNumber.ReturnKeyType = UIReturnKeyType.Next;
			txtBoxPhoneNumber.AddTarget(phoneNumberUpdate, UIControlEvent.EditingChanged);
			txtBoxPhoneNumber.ShouldReturn = (UITextField textField) => txtBoxLocation.BecomeFirstResponder();
			cells[7].AddSubviews(new UIView[] { lblPhoneNumber, txtBoxPhoneNumber });

			//Company Location Cell
			cells[8] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
			lblLocation = new UILabel(new CoreGraphics.CGRect(16, 0, 308, 21));
			lblLocation.Text = "What states do you prefer to work in?";
			lblLocation.Font = lblLocation.Font.WithSize(18);
			txtBoxLocation = new UITextField(new CoreGraphics.CGRect(16, 22, 343, 30));
			txtBoxLocation.Placeholder = "XX, XX, ...";
			txtBoxLocation.BorderStyle = UITextBorderStyle.RoundedRect;
			txtBoxLocation.Font = txtBoxLocation.Font.WithSize(16);
			txtBoxLocation.KeyboardType = UIKeyboardType.ASCIICapable;
			txtBoxLocation.ReturnKeyType = UIReturnKeyType.Next;
			txtBoxLocation.AutocapitalizationType = UITextAutocapitalizationType.AllCharacters;
			txtBoxLocation.AutocorrectionType = UITextAutocorrectionType.No;
			//txtBoxLocation.AddTarget(locationUpdate, UIControlEvent.EditingChanged);
			txtBoxLocation.ShouldReturn = (UITextField textField) => txtBoxLocation.ResignFirstResponder();
			cells[8].AddSubviews(new UIView[] { lblLocation, txtBoxLocation });

			//Submit Cell
			cells[9] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
			btnRegister = new UIButton(new CoreGraphics.CGRect(2, 2, 371, 56));
			btnRegister.SetTitle("Register", UIControlState.Normal);
			btnRegister.Enabled = true;
			btnRegister.SetTitleColor(UIColor.Cyan, UIControlState.Normal);
			btnRegister.AddTarget(register, UIControlEvent.TouchUpInside | UIControlEvent.TouchUpOutside);
			cells[9].AddSubview(btnRegister);
		}

		private void register(object sender, EventArgs e)
		{
			/*
			 * Waiting for Recruiter Class
			NewRecruiter newRecruiter = NewRecruiter.createAndValidate(
				txtBoxFirstName.Text,
				txtBoxLastName.Text,
				txtBoxEmail.Text,
				txtBoxCompanyName.Text,
				txtBoxPhoneNumber.Text.Replace("(", "").Replace(")", "").Replace("-", "").Replace(" ", ""),
				txtBoxLocation.Text,
				txtBoxPassword.Text,
				txtBoxRetypePassword.Text
			);

			RecruiterComms.addRecruiter(newRecruiter);
			*/
		}

		private void phoneNumberUpdate(object sender, EventArgs e)
		{
			string stripedText = txtBoxPhoneNumber.Text.Replace("(", "").Replace(")", "").Replace("-", "").Replace(" ", "");
			string newString = "";

			if (stripedText.Length > 0)
			{
				newString += "(";
				newString += stripedText.Substring(0, Math.Min(3, stripedText.Length));
			}
			if (stripedText.Length > 3)
			{
				newString += ") ";
				newString += stripedText.Substring(3, Math.Min(3, stripedText.Length - 3));
			}
			if (stripedText.Length > 6)
			{
				newString += "-";
				newString += stripedText.Substring(6, Math.Min(4, stripedText.Length - 6));
			}
			txtBoxPhoneNumber.Text = newString;
		}

		private bool checkEmail(UITextField txtBox)
		{
			/*if company email does not match any in our database, dialog appears
			 * and asks if they want to create a new company
			 */

			if (true /*txtBoxEmail.Text matches email regex*/)
			{
				txtBoxEmail.BackgroundColor = paleGreen;
			}
			else
			{
				txtBoxEmail.BackgroundColor = paleRed;
			}
			txtBoxPassword.BecomeFirstResponder();
			return false;
		}

		private bool checkPassword(UITextField txtBox)
		{
			if (true /*txtBoxPassword.Text meets conditions*/)
			{
				txtBoxPassword.BackgroundColor = paleGreen;
			}
			else
			{
				txtBoxPassword.BackgroundColor = paleRed;
			}
			txtBoxRetypePassword.BecomeFirstResponder();
			return false;
		}

		private bool checkRetypedPassword(UITextField txtBox)
		{
			if (txtBoxPassword.Text == txtBoxRetypePassword.Text)
			{
				txtBoxRetypePassword.BackgroundColor = paleGreen;
			}
			else
			{
				txtBoxRetypePassword.BackgroundColor = paleRed;
			}
			txtBoxCompanyName.BecomeFirstResponder();
			return false;
		}

		public override UITableViewCell GetCell(UITableView tableView, NSIndexPath indexPath)
		{

			UITableViewCell newCell = tableView.DequeueReusableCell("FormCell");

			if (newCell == null)
			{
				newCell = new UITableViewCell(UITableViewCellStyle.Default, "FormCell");
			}

			newCell.ContentView.AddSubviews(cells[indexPath.Row].Subviews);
			newCell.SelectionStyle = UITableViewCellSelectionStyle.None;

			return newCell;
		}

		public override nint RowsInSection(UITableView tableview, nint section)
		{
			return cells.Length;
		}
	}

	public partial class RecruiterRegistration : UITableView
	{
		public RecruiterRegistration(IntPtr handle) : base(handle)
		{
			this.Source = new RecruiterRegistrationSource();
		}

		/*public NewRecruiter newRecruiterFromForm()
		{
			NewRecruiter newRecruiter = null;

			return newRecruiter;
		}
		*/
	}
}
 