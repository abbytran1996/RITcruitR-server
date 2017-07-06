using System;
using TMCS_Client.CustomUIElements.Buttons;
using Xamarin.Forms;

namespace TMCS_Client.UI.Student
{
    public class StudentEditProfile : StudentRegistration
    {
        private AbsoluteLayout buttons;
        private FormSubmitButton btnCancel;

        public StudentEditProfile(DTOs.Student student) : base()
        {

            //Populate field with student data
            entFirstName.Text = student.firstName;
            entFirstName.IsEnabled = false;
			entLastName.Text = student.lastName;
			entLastName.IsEnabled = false;
			entEmail.Text = student.email;
			entEmail.IsEnabled = false;
            entPassword.Placeholder = "New Password";
            entRetypePassword.Placeholder = "Retype New Password";
            entSchoolName.Text = student.school;
            entGraduationDate.Text = student.graduationDate.ToString("mm/yy");
            entPhoneNumber.Text = student.phoneNumber != null ? student.phoneNumber:null;
            entPreferredLocation.Text = student.preferredStates.ToArray().ToString();
            pickPreferredCompanySize.SelectedItem = student.preferredCompanySize;
            entResumeFileLocation.Text = student.resumeLocation;

            //Update existing ui
            lblTitle.Text = Title = "Student Profile Update";

            btnCancel = new FormSubmitButton("Cancel")
            {
                BackgroundColor = Color.Red,
            };
            btnCancel.Command = new Command((object obj) => Navigation.PopAsync());

            registrationForm.Children.Remove(btnRegister);
            btnRegister.Text = "Update";
            btnRegister.Command = new Command((object obj) => update());

            buttons = new AbsoluteLayout();

            buttons.Children.Add(btnCancel,
                                new Rectangle(0.0,0.5,0.45,0.8),
                                AbsoluteLayoutFlags.All);

			buttons.Children.Add(btnRegister,
								new Rectangle(1.0, 0.5, 0.45, 0.8),
								AbsoluteLayoutFlags.All);
            
			registrationForm.Children.Add(buttons,
										  new Rectangle(0.5, (12.0 * Constants.Forms.Sizes.ROW_HEIGHT), 0.9, Constants.Forms.Sizes.ROW_HEIGHT),
										  AbsoluteLayoutFlags.WidthProportional |
										 AbsoluteLayoutFlags.XProportional);
        }

        private void update(){
            Console.WriteLine("Got Here");
        }
    }
}
