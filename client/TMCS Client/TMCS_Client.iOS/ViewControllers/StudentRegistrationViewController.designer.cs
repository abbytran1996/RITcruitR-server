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
    [Register ("StudentRegistrationViewController")]
    partial class StudentRegistrationViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UILabel lblEmail { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UILabel lblFirstName { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UILabel lblLastName { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIScrollView scrollViewStudentRegistration { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField txtBoxConfirmPassword { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField txtBoxEmail { get; set; }

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
        TMCS_Client.iOS.StudentRegistration viewStudentRegistration { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (lblEmail != null) {
                lblEmail.Dispose ();
                lblEmail = null;
            }

            if (lblFirstName != null) {
                lblFirstName.Dispose ();
                lblFirstName = null;
            }

            if (lblLastName != null) {
                lblLastName.Dispose ();
                lblLastName = null;
            }

            if (scrollViewStudentRegistration != null) {
                scrollViewStudentRegistration.Dispose ();
                scrollViewStudentRegistration = null;
            }

            if (txtBoxConfirmPassword != null) {
                txtBoxConfirmPassword.Dispose ();
                txtBoxConfirmPassword = null;
            }

            if (txtBoxEmail != null) {
                txtBoxEmail.Dispose ();
                txtBoxEmail = null;
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

            if (viewStudentRegistration != null) {
                viewStudentRegistration.Dispose ();
                viewStudentRegistration = null;
            }
        }
    }
}