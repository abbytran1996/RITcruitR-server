using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using TMCS_Client.Controllers;
using TMCS_Client.DTOs;
using TMCS_Client.ServerComms;

namespace TMCS_Client.Droid {
    [Activity(Label = "StudentSignup")]
    public class StudentSignup : Activity {
        private StudentController controller = new StudentController();

        private static String[] STATES = new String[] {
            "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida",
            "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine",
            "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska",
            "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio",
            "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee",
            "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
        };

        private static String[] COMPANY_SIZES = new String[] {
            "1 - 10", "11 - 25", "26 - 50", "51 - 100", "101 - 500", "501 - 1000", "1000+", "No opinion"
        };

        protected override void OnCreate(Bundle savedInstanceState) {
            base.OnCreate(savedInstanceState);
            
            SetContentView(Resource.Layout.StudentSignup);

            var firstNameInput = FindViewById<EditText>(Resource.Id.student_new_firstName);
            var lastNameInput = FindViewById<EditText>(Resource.Id.student_new_lastName);
            var emailInput = FindViewById<EditText>(Resource.Id.student_new_email);
            var schoolNameInput = FindViewById<EditText>(Resource.Id.student_new_schoolName);
            var graduationDateInput = FindViewById<EditText>(Resource.Id.student_new_graduationDate);
            var passwordInput = FindViewById<EditText>(Resource.Id.student_new_password);
            var passwordConfirmInput = FindViewById<EditText>(Resource.Id.student_new_passwordConfirm);

            firstNameInput.FocusChange += validateTextNotNull;
            lastNameInput.FocusChange += validateTextNotNull;
            emailInput.FocusChange += validateTextNotNull;
            schoolNameInput.FocusChange += validateTextNotNull;
            graduationDateInput.FocusChange += validateTextNotNull;
            passwordInput.FocusChange += validateTextNotNull;
            passwordConfirmInput.FocusChange += validateTextNotNull;

            var statesInput = FindViewById<MultiAutoCompleteTextView>(Resource.Id.student_new_states);
            statesInput.Adapter = new ArrayAdapter<String>(this, Android.Resource.Layout.SimpleDropDownItem1Line, STATES);
            statesInput.SetTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

            var companySizeInput = FindViewById<Spinner>(Resource.Id.student_new_companySize);
            companySizeInput.Adapter = new ArrayAdapter<String>(this, Android.Resource.Layout.SimpleSpinnerItem, COMPANY_SIZES);
            string companySize = "No opinion";
            companySizeInput.ItemSelected += new EventHandler<AdapterView.ItemSelectedEventArgs>((sender, e) => companySize = (sender as Spinner).GetItemAtPosition(e.Position).ToString());

            var button = FindViewById<Button>(Resource.Id.student_new_submit);
            button.Click += (sender, e) => {
                Console.WriteLine("Clicked the submit button, performing input validation");
                var firstName = firstNameInput.Text;
                var lastName = lastNameInput.Text;
                var email =  emailInput.Text;
                var school = schoolNameInput.Text;
                var graduationDate = graduationDateInput.Text;
                var phoneNumber = FindViewById<EditText>(Resource.Id.student_new_phoneNumber).Text;
                var password = passwordInput.Text;
                var passwordConfirmation = passwordConfirmInput.Text;
                var states = statesInput.Text;

                var statesList = new List<String>();
                statesList.AddRange(states.Split(','));
                statesList.RemoveAll(str => str.Trim().Length == 0);
                
                try {
                    var newStudent = NewStudent.createAndValidate(firstName, lastName, email, school, graduationDate, phoneNumber, statesList, companySize, password, passwordConfirmation);
                    controller.addStudent(newStudent);
                } catch(Exception ex) {
                    Toast.MakeText(ApplicationContext, ex.Message, ToastLength.Long).Show();
                }
            };
        }

        private void validateTextNotNull(object sender, View.FocusChangeEventArgs e) {
            if(!e.HasFocus) {
                var editText = sender as EditText;
                if(editText.Text.Length <= 0) {
                    Toast.MakeText(ApplicationContext, editText.Tag + " cannot be null", ToastLength.Short).Show();
                }
            }
        }
    }
}