using System;
using TMCS_Client.CustomUIElements.Buttons;
using Xamarin.Forms;
using System.Collections.Generic;
using TMCS_Client.Controllers;
using TMCS_Client.DTOs;

namespace TMCS_Client.UI.Student
{
    public class StudentEditProfile : StudentRegistration
    {
        private AbsoluteLayout buttons;
        private FormSubmitButton btnCancel;
        private DTOs.Student student;

        public StudentEditProfile(DTOs.Student student) : base()
        {
            this.student = student;

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
            entGraduationDate.Text = student.graduationDate.ToString("MM/yy");
            entPhoneNumber.Text = student.phoneNumber != null ? student.phoneNumber:null;
			if (student.preferredStates.Count > 0)
			{
                entPreferredLocation.Text = "";
	            foreach(String location in student.preferredStates){
	                entPreferredLocation.Text += location + ", ";
	            }
                entPreferredLocation.Text = entPreferredLocation.Text.Substring(0, entPreferredLocation.Text.Length - 2);
            }
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
            String invalidDataMessage = formValidation(false);

            if (invalidDataMessage != "")
            {
                this.DisplayAlert("Invalid Data:", invalidDataMessage, "OK");
            }
            else
            {
                //TODO implement password updating

                student.school = entSchoolName.Text;
                student.graduationDate = DateTime.ParseExact(entGraduationDate.Text.Length < 5 ? "0" + entGraduationDate.Text :
                    entGraduationDate.Text, "MM/yy", null);
                student.phoneNumber = entPhoneNumber.Text != null ? entPhoneNumber.Text.Replace("(", "").Replace(")", "")
                    .Replace(" ", "").Replace("-", "") : "";
                student.preferredStates = new List<String>(entPreferredLocation.Text.Replace(", ", ",").Split(','));
                student.preferredCompanySize = pickPreferredCompanySize.getPreferredSize();
                StudentController.getStudentController().updateStudent(student);

                if(entResumeFileLocation.Text != student.resumeLocation){
                    StudentController.getStudentController().uploadResume(student.id, new Resume(entResumeFileLocation.Text));
                }
                Navigation.PopAsync();
            }
        }
    }
}
