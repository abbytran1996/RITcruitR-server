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
    
        public object NetStudent { get; private set; }

        protected override void OnCreate(Bundle savedInstanceState) {
            base.OnCreate(savedInstanceState);

            // Create your application here
            SetContentView(Resource.Layout.StudentSignup);

            FindViewById<EditText>(Resource.Id.student_new_firstName).FocusChange += validateTextNotNull;
            FindViewById<EditText>(Resource.Id.student_new_lastName).FocusChange += validateTextNotNull;
            FindViewById<EditText>(Resource.Id.student_new_email).FocusChange += validateTextNotNull;
            FindViewById<EditText>(Resource.Id.student_new_schoolName).FocusChange += validateTextNotNull;
            FindViewById<EditText>(Resource.Id.student_new_graduationDate).FocusChange += validateTextNotNull;
            FindViewById<EditText>(Resource.Id.student_new_password).FocusChange += validateTextNotNull;
            FindViewById<EditText>(Resource.Id.student_new_passwordConfirm).FocusChange += validateTextNotNull;

            var button = FindViewById<Button>(Resource.Id.student_new_submit);

            button.Click += (sender, e) => {
                Console.WriteLine("Clicked the submit button, performing input validation");
                var firstName = FindViewById<EditText>(Resource.Id.student_new_firstName).Text;
                var lastName = FindViewById<EditText>(Resource.Id.student_new_lastName).Text;
                var email = FindViewById<EditText>(Resource.Id.student_new_email).Text;
                var school = FindViewById<EditText>(Resource.Id.student_new_schoolName).Text;
                var graduationDate = FindViewById<EditText>(Resource.Id.student_new_graduationDate).Text;
                var phoneNumber = FindViewById<EditText>(Resource.Id.student_new_phoneNumber).Text;
                var password = FindViewById<EditText>(Resource.Id.student_new_password).Text;
                var passwordConfirmation = FindViewById<EditText>(Resource.Id.student_new_passwordConfirm).Text;
                
                try {
                    var newStudent = NewStudent.createAndValidate(firstName, lastName, email, school, graduationDate, phoneNumber, null, password, passwordConfirmation);
                    controller.addStudent(newStudent);
                } catch(Exception ex) {
                    Toast.MakeText(ApplicationContext, ex.Message, ToastLength.Short).Show();
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