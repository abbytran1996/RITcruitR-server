using Foundation;
using System;
using UIKit;
using TMCS_Client.DTOs;
using System.Collections.Generic;
using TMCS_Client.ServerComms;

namespace TMCS_Client.iOS
{
    internal class StudentRegistrationSource : UITableViewSource
    {
        //Comms
        private StudentComms studentComms = new StudentComms();
        private StudentRegistrationTableViewController grandparent;

        //All Cells
        private UIView[] cells = new UIView[12];

        //Title Cell
        private UILabel lblTitle = null;

        //First Name Cell
        private UILabel lblFirstName = null;
        private UITextField txtBoxFirstName = null;

        //Last Name Cell
        private UILabel lblLastName = null;
        private UITextField txtBoxLastName = null;

        //Email Cell
        private UILabel lblEmail = null;
        private UITextField txtBoxEmail = null;

        //Passowrd Cell
        private UILabel lblPassword = null;
        private UITextField txtBoxPassword = null;

        //Retype Password Cell
        private UILabel lblRetypePassword = null;
        private UITextField txtBoxRetypePassword = null;

        //School Name Cell
        private UILabel lblSchoolName = null;
        private UITextField txtBoxSchoolName = null;

        //Expected Graduation Cell
        private UILabel lblExpectedGraduation = null;
        private UITextField txtBoxExpectedGraduation = null;

        //Phone Number
        private UILabel lblPhoneNumber = null;
        private UITextField txtBoxPhoneNumber = null;

        //Prefered Location Cell
        private UILabel lblPreferedLocation = null;
        private UITextField txtBoxPreferedLocation = null;

        //Prefered Company Size Cell
        private UILabel lblPreferedCompanySize = null;
        private UITextField txtBoxPreferedCompanySize = null;

        //Submit Cell
        private UIButton btnRegister = null;

        //Colors for text boxes
        private UIColor paleGreen = new UIColor(0.75f, 1.0f, 0.75f, 1.0f);
        private UIColor paleRed = new UIColor(1.0f, 0.75f, 0.75f, 1.0f);

        public StudentRegistrationSource()
        {
            //Title Cell
            cells[0] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
            lblTitle = new UILabel(new CoreGraphics.CGRect(0, 0, 375, 60));
            lblTitle.Text = "Student Registration";
            lblTitle.Font = lblTitle.Font.WithSize(28);
            lblTitle.TextAlignment = UITextAlignment.Center;
            cells[0].AddSubview(lblTitle);

            //First Name Cell
            cells[1] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
            lblFirstName = new UILabel(new CoreGraphics.CGRect(16, 0, 91, 21));
            lblFirstName.Text = "First Name";
            lblFirstName.Font = lblFirstName.Font.WithSize(18);
            lblFirstName.AddConstraint(new NSLayoutConstraint());
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

            //School Name Cell
            cells[6] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
            lblSchoolName = new UILabel(new CoreGraphics.CGRect(16, 0, 110, 21));
            lblSchoolName.Text = "School Name";
            lblSchoolName.Font = lblSchoolName.Font.WithSize(18);
            txtBoxSchoolName = new UITextField(new CoreGraphics.CGRect(16, 22, 343, 30));
            txtBoxSchoolName.Placeholder = "School Name";
            txtBoxSchoolName.BorderStyle = UITextBorderStyle.RoundedRect;
            txtBoxSchoolName.Font = txtBoxSchoolName.Font.WithSize(16);
            txtBoxSchoolName.SetTextContentType(UITextContentType.OrganizationName);
            txtBoxSchoolName.KeyboardType = UIKeyboardType.ASCIICapable;
            txtBoxSchoolName.ReturnKeyType = UIReturnKeyType.Next;
            txtBoxSchoolName.AutocapitalizationType = UITextAutocapitalizationType.Words;
            txtBoxSchoolName.AutocorrectionType = UITextAutocorrectionType.No;
            txtBoxSchoolName.ShouldReturn = (UITextField textField) => txtBoxExpectedGraduation.BecomeFirstResponder();
            cells[6].AddSubviews(new UIView[] { lblSchoolName, txtBoxSchoolName });

            //Expected Graduation Cell
            cells[7] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
            lblExpectedGraduation = new UILabel(new CoreGraphics.CGRect(16, 0, 217, 21));
            lblExpectedGraduation.Text = "Expected Graduation Date";
            lblExpectedGraduation.Font = lblExpectedGraduation.Font.WithSize(18);
            txtBoxExpectedGraduation = new UITextField(new CoreGraphics.CGRect(16, 22, 343, 30));
            txtBoxExpectedGraduation.Placeholder = "mm/yy";
            txtBoxExpectedGraduation.BorderStyle = UITextBorderStyle.RoundedRect;
            txtBoxExpectedGraduation.Font = txtBoxExpectedGraduation.Font.WithSize(16);
            txtBoxExpectedGraduation.KeyboardType = UIKeyboardType.AsciiCapableNumberPad;
            txtBoxExpectedGraduation.ReturnKeyType = UIReturnKeyType.Next;
            txtBoxExpectedGraduation.AddTarget(expectedGraduationUpdate,UIControlEvent.EditingChanged);
            txtBoxExpectedGraduation.ShouldReturn = (UITextField textField) => txtBoxPhoneNumber.BecomeFirstResponder();
            cells[7].AddSubviews(new UIView[] { lblExpectedGraduation, txtBoxExpectedGraduation });

            //Phone Number Cell
            cells[8] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
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
            txtBoxPhoneNumber.ShouldReturn = (UITextField textField) => txtBoxPreferedLocation.BecomeFirstResponder();
            cells[8].AddSubviews(new UIView[] { lblPhoneNumber, txtBoxPhoneNumber });

            //Prefered Location Cell
            cells[9] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
            lblPreferedLocation = new UILabel(new CoreGraphics.CGRect(16, 0, 308, 21));
            lblPreferedLocation.Text = "What states do you prefer to work in?";
            lblPreferedLocation.Font = lblPreferedLocation.Font.WithSize(18);
            txtBoxPreferedLocation = new UITextField(new CoreGraphics.CGRect(16, 22, 343, 30));
            txtBoxPreferedLocation.Placeholder = "XX, XX, ...";
            txtBoxPreferedLocation.BorderStyle = UITextBorderStyle.RoundedRect;
            txtBoxPreferedLocation.Font = txtBoxPreferedLocation.Font.WithSize(16);
            txtBoxPreferedLocation.KeyboardType = UIKeyboardType.ASCIICapable;
            txtBoxPreferedLocation.ReturnKeyType = UIReturnKeyType.Next;
            txtBoxPreferedLocation.AutocapitalizationType = UITextAutocapitalizationType.AllCharacters;
            txtBoxPreferedLocation.AutocorrectionType = UITextAutocorrectionType.No;
            txtBoxPreferedLocation.AddTarget(preferedLocationUpdate, UIControlEvent.EditingChanged);
            txtBoxPreferedLocation.ShouldReturn = (UITextField textField) => txtBoxPreferedCompanySize.BecomeFirstResponder();
            cells[9].AddSubviews(new UIView[] { lblPreferedLocation, txtBoxPreferedLocation });

            //Prefered Company Size Cell
            cells[10] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
            lblPreferedCompanySize = new UILabel(new CoreGraphics.CGRect(16, 0, 290, 21));
            lblPreferedCompanySize.Text = "What's your desired company size?";
            lblPreferedCompanySize.Font = lblPreferedLocation.Font.WithSize(18);
            txtBoxPreferedCompanySize = new UITextField(new CoreGraphics.CGRect(16, 22, 343, 30));
            txtBoxPreferedCompanySize.Placeholder = "Company Size";
            txtBoxPreferedCompanySize.BorderStyle = UITextBorderStyle.RoundedRect;
            txtBoxPreferedCompanySize.Font = txtBoxPreferedCompanySize.Font.WithSize(16);
            txtBoxPreferedCompanySize.KeyboardType = UIKeyboardType.ASCIICapable;
            txtBoxPreferedCompanySize.ReturnKeyType = UIReturnKeyType.Done;
            txtBoxPreferedCompanySize.AutocapitalizationType = UITextAutocapitalizationType.Sentences;
            txtBoxPreferedCompanySize.ShouldReturn = (UITextField textField) => txtBoxPreferedCompanySize.ResignFirstResponder();
            cells[10].AddSubviews(new UIView[] { lblPreferedCompanySize, txtBoxPreferedCompanySize});

            //Submit Cell
            cells[11] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
            btnRegister = new UIButton(new CoreGraphics.CGRect(2, 2, 371, 56));
            btnRegister.SetTitle("Register",UIControlState.Normal);
            btnRegister.Enabled = true;
            btnRegister.SetTitleColor(UIColor.Cyan,UIControlState.Normal);
            btnRegister.AddTarget(register,UIControlEvent.TouchUpInside | UIControlEvent.TouchUpOutside);
            cells[11].AddSubview(btnRegister);
        }

        private void register(object sender, EventArgs eventArgs){
            NewStudent newStudent = NewStudent.createAndValidate(
                txtBoxFirstName.Text,
                txtBoxLastName.Text,
                txtBoxEmail.Text,
                txtBoxSchoolName.Text,
                (txtBoxExpectedGraduation.Text.Length < 5) ? "0" + txtBoxExpectedGraduation.Text : txtBoxExpectedGraduation.Text,
                txtBoxPhoneNumber.Text.Replace("(", "").Replace(")", "").Replace("-","").Replace(" ", ""),
                new List<string>(txtBoxPreferedLocation.Text.Replace(" ", "").Split(new char[] { ',' })),
                txtBoxPreferedCompanySize.Text,
                txtBoxPassword.Text,
                txtBoxRetypePassword.Text
            );

            try{
				studentComms.addStudent(newStudent);
				this.grandparent.endStudentRegistration();
            }catch(RestException exception){
                Console.WriteLine(exception.ToString());
            }catch(ArgumentException exception){
                //Shouldn't occur, data is checked before being
                //passed to addStudent
                Console.WriteLine(exception.ToString());
            }
        }

        private void expectedGraduationUpdate(object sender, EventArgs e){
            
            switch(txtBoxExpectedGraduation.Text.Replace("/","").Length){
                case(0):
                    break;
                case(1):case(2):
                    txtBoxExpectedGraduation.Text = txtBoxExpectedGraduation.Text.Replace("/", "");
                    break;
                case(3):case(4):
                    txtBoxExpectedGraduation.Text =
                        txtBoxExpectedGraduation.Text.Replace("/", "").Insert(txtBoxExpectedGraduation.Text.Replace("/","").Length - 2, "/");
                    break;
                default:
                    txtBoxExpectedGraduation.Text =
                        txtBoxExpectedGraduation.Text.Substring(0, 5);
                    break;
            }
        }

        private void phoneNumberUpdate(object sender, EventArgs e){
            string stripedText = txtBoxPhoneNumber.Text.Replace("(","").Replace(")", "").Replace("-", "").Replace(" ", "");
            string newString = "";

            if(stripedText.Length > 0){
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

        private void preferedLocationUpdate(object sender, EventArgs e){
            
        }

        private bool checkEmail(UITextField txtBox){
            if(true /*txtBoxEmail.Text matches email regex*/){
                txtBoxEmail.BackgroundColor = paleGreen;
            }else{
                txtBoxEmail.BackgroundColor = paleRed;
            }
            txtBoxPassword.BecomeFirstResponder();
            return false;
        }

        private bool checkPassword(UITextField txtBox){
            if(true /*txtBoxPassword.Text meets conditions*/){
                txtBoxPassword.BackgroundColor = paleGreen;
            }else{
                txtBoxPassword.BackgroundColor = paleRed;
            }
            txtBoxRetypePassword.BecomeFirstResponder();
            return false;
        }

        private bool checkRetypedPassword(UITextField txtBox){
            if(txtBoxPassword.Text == txtBoxRetypePassword.Text){
                txtBoxRetypePassword.BackgroundColor = paleGreen;
            }else{
                txtBoxRetypePassword.BackgroundColor = paleRed;
            }
            txtBoxSchoolName.BecomeFirstResponder();
            return false;
        }

        public override UITableViewCell GetCell(UITableView tableView, NSIndexPath indexPath)
        {

            UITableViewCell newCell = tableView.DequeueReusableCell("FormCell");

            if (newCell == null)
            {
                newCell = new UITableViewCell(UITableViewCellStyle.Default, "FormCell");
            }

            //foreach(UIView subView in newCell.ContentView.Subviews){
            //    subView.RemoveFromSuperview();
            //}

            newCell.ContentView.AddSubviews(cells[indexPath.Row].Subviews);
            newCell.SelectionStyle = UITableViewCellSelectionStyle.None;
            newCell.ContentView.AutosizesSubviews = true;

            return newCell;
        }

        public override nint RowsInSection(UITableView tableview, nint section)
        {
            return cells.Length;
        }

        public void setGrandparent(StudentRegistrationTableViewController newGrandparent){
            this.grandparent = newGrandparent;
        }
    }

    public partial class StudentRegistration : UITableView
    {
        StudentRegistrationTableViewController parent = null;

        public StudentRegistration (IntPtr handle) : base (handle){
            this.Source = new StudentRegistrationSource();
        }

        public void setParent(StudentRegistrationTableViewController newParent){
            this.parent = newParent;
            ((StudentRegistrationSource)this.Source).setGrandparent(newParent);
        }
    }
}