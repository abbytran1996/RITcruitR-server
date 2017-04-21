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
        UIKit.UILabel lblRecruiterRegistration { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        TMCS_Client.iOS.EmployerRegistration Recruiter_Registration { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField txtBoxCompanyEmail { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField txtBoxCompanyLocation { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField txtBoxCompanyName { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField txtBoxConfirmPassword { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField txtBoxFirstName { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField txtBoxLastName { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField txtBoxPassword { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField txtBoxPhoneNumber { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (lblRecruiterRegistration != null) {
                lblRecruiterRegistration.Dispose ();
                lblRecruiterRegistration = null;
            }

            if (Recruiter_Registration != null) {
                Recruiter_Registration.Dispose ();
                Recruiter_Registration = null;
            }

            if (txtBoxCompanyEmail != null) {
                txtBoxCompanyEmail.Dispose ();
                txtBoxCompanyEmail = null;
            }

            if (txtBoxCompanyLocation != null) {
                txtBoxCompanyLocation.Dispose ();
                txtBoxCompanyLocation = null;
            }

            if (txtBoxCompanyName != null) {
                txtBoxCompanyName.Dispose ();
                txtBoxCompanyName = null;
            }

            if (txtBoxConfirmPassword != null) {
                txtBoxConfirmPassword.Dispose ();
                txtBoxConfirmPassword = null;
            }

            if (txtBoxFirstName != null) {
                txtBoxFirstName.Dispose ();
                txtBoxFirstName = null;
            }

            if (txtBoxLastName != null) {
                txtBoxLastName.Dispose ();
                txtBoxLastName = null;
            }

            if (txtBoxPassword != null) {
                txtBoxPassword.Dispose ();
                txtBoxPassword = null;
            }

            if (txtBoxPhoneNumber != null) {
                txtBoxPhoneNumber.Dispose ();
                txtBoxPhoneNumber = null;
            }
        }
    }
}