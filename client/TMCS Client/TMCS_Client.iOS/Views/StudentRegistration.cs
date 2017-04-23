using Foundation;
using System;
using UIKit;
using TMCS_Client.DTOs;

namespace TMCS_Client.iOS
{
    internal class StudentRegistrationSource : UITableViewSource
    {

        //All Cells
        private UIView[] cells = new UIView[12];

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

        //School Name Cell
        UILabel lblSchoolName = null;
        UITextField txtBoxSchoolName = null;

        //Expected Graduation Cell
        UILabel lblExpectedGraduation = null;
        UITextField txtBoxExpectedGraduation = null;

        //Phone Number
        UILabel lblPhoneNumber = null;
        UITextField txtBoxPhoneNumber = null;

        //Prefered Location Cell
        UILabel lblPreferedLocation = null;
        UITextField txtBoxPreferedLocation = null;

        //Prefered Company Size Cell
        UILabel lblPreferedCompanySize = null;
        UITextField txtBoxPreferedCompanySize = null;

        //Submit Cell
        UIButton btnSubmit = null;

        //Colors for text boxes
        UIColor paleGreen = new UIColor(0.75f, 1.0f, 0.75f, 1.0f);
        UIColor paleRed = new UIColor(1.0f, 0.75f, 0.75f, 1.0f);

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
            txtBoxEmail.ShouldReturn = (UITextField textField) => txtBoxPassword.BecomeFirstResponder();
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
            txtBoxPassword.ShouldReturn = (UITextField textField) => txtBoxRetypePassword.BecomeFirstResponder();
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

            //Expected Graduation Cell
            cells[7] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
            lblExpectedGraduation = new UILabel(new CoreGraphics.CGRect(16, 0, 217, 21));
            lblExpectedGraduation.Text = "Expected Graduation Date";
            lblExpectedGraduation.Font = lblExpectedGraduation.Font.WithSize(18);
            txtBoxExpectedGraduation = new UITextField(new CoreGraphics.CGRect(16, 22, 343, 30));
            txtBoxExpectedGraduation.Placeholder = "mm/yy";
            txtBoxExpectedGraduation.BorderStyle = UITextBorderStyle.RoundedRect;
            txtBoxExpectedGraduation.Font = txtBoxExpectedGraduation.Font.WithSize(16);
            txtBoxExpectedGraduation.KeyboardType = UIKeyboardType.NumberPad;
            txtBoxExpectedGraduation.ReturnKeyType = UIReturnKeyType.Next;
            txtBoxExpectedGraduation.AddTarget(expectedGraduationUpdate,UIControlEvent.AllEvents);
            txtBoxExpectedGraduation.ShouldReturn = (UITextField textField) => txtBoxPhoneNumber.BecomeFirstResponder();
            cells[7].AddSubviews(new UIView[] { lblExpectedGraduation, txtBoxExpectedGraduation });

            //Phone Number Cell
            cells[8] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));

            //Prefered Location Cell
            cells[9] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));

            //Prefered Company Size Cell
            cells[10] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));

            //Submit Cell
            cells[11] = new UIView(new CoreGraphics.CGRect(0, 0, 375, 60));
        }

        private void expectedGraduationUpdate(object sender, EventArgs e){
            
            switch(txtBoxExpectedGraduation.Text.Replace("/","").Length){
                case(1):case(2):
                    txtBoxExpectedGraduation.Text = txtBoxExpectedGraduation.Text.Replace("/", "");
                    break;
                case(3):case(4):
                    txtBoxExpectedGraduation.Text =
                        txtBoxExpectedGraduation.Text.Replace("/", "").Insert(txtBoxExpectedGraduation.Text.Replace("/","").Length - 2, "/");
                    break;
            }
        }

        private bool checkEmail(){
            if(true /*txtBoxEmail.Text matches email regex*/){
                txtBoxEmail.BackgroundColor = paleGreen;
            }else{
                txtBoxEmail.BackgroundColor = paleRed;
            }
            txtBoxEmail.BecomeFirstResponder();
            return false;
        }

        private bool checkPassword(){
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

            newCell.ContentView.AddSubviews(cells[indexPath.Row].Subviews);
            newCell.SelectionStyle = UITableViewCellSelectionStyle.None;

            return newCell;
        }

        public override nint RowsInSection(UITableView tableview, nint section)
        {
            return cells.Length;
        }
    }

    public partial class StudentRegistration : UITableView
    {
        public StudentRegistration (IntPtr handle) : base (handle){
            this.Source = new StudentRegistrationSource();
        }

        public NewStudent newStudentFromForm(){
            NewStudent newStudent = null;

            return newStudent;
        }
    }
}