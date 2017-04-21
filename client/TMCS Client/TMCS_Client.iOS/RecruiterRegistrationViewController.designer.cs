// WARNING
//
// This file has been generated automatically by Visual Studio from the outlets and
// actions declared in your storyboard file.
// Manual changes to this file will not be maintained.
//
using Foundation;
using System;
using System.CodeDom.Compiler;
using UIKit;

namespace TMCS_Client.iOS
{
    [Register ("RecruiterRegistrationViewController")]
    partial class RecruiterRegistrationViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        TMCS_Client.iOS.EmployerRegistration Recruiter_Registration { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (Recruiter_Registration != null) {
                Recruiter_Registration.Dispose ();
                Recruiter_Registration = null;
            }
        }
    }
}