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

namespace TMCS_Client.Droid {
    [Activity(Label = "StudentSignup")]
    public class StudentSignup : Activity {
        protected override void OnCreate(Bundle savedInstanceState) {
            base.OnCreate(savedInstanceState);

            // Create your application here
            SetContentView(Resource.Layout.StudentSignup);

            var button = FindViewById<Button>(Resource.Id.student_new_submit);

            button.Click += delegate {
                var passwordField = FindViewById<EditText>(Resource.Id.student_new_password);
                var passwordConfirmationField = FindViewById<EditText>(Resource.Id.student_new_passwordConfirm);


            };
        }
    }
}