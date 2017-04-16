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

            button.Click += (sender, e) => {
                var password = FindViewById<EditText>(Resource.Id.student_new_password).Text;
                var passwordConfirmation = FindViewById<EditText>(Resource.Id.student_new_passwordConfirm).Text;
                
                if(password != passwordConfirmation) {
                    Toast.MakeText(this.ApplicationContext, "Passwords do not match", ToastLength.Long);
                }
            };
        }
    }
}