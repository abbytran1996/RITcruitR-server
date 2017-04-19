using System;

using Android.App;
using Android.Content;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using Android.OS;

namespace TMCS_Client.Droid {
    [Activity(Label = "TMCS_Client.Droid", MainLauncher = true, Icon = "@drawable/icon")]
    public class MainActivity : Activity {
        protected override void OnCreate(Bundle bundle) {
            base.OnCreate(bundle);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Main);

            // Get our button from the layout resource,
            // and attach an event to it
            var newStudentButton = FindViewById<Button>(Resource.Id.studentSignupButton);

            newStudentButton.Click += delegate {
                StartActivity(typeof(StudentSignup));
            };
        }
    }
}
