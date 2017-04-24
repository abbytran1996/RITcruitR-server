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
    [Register ("RegistrationViewController")]
    partial class RegistrationViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton btnRecruiterRegistration { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton btnSelectRoleStudent { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UILabel lblRole { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UILabel lblWelcome { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        TMCS_Client.iOS.Registration viewRegistration { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (btnRecruiterRegistration != null) {
                btnRecruiterRegistration.Dispose ();
                btnRecruiterRegistration = null;
            }

            if (btnSelectRoleStudent != null) {
                btnSelectRoleStudent.Dispose ();
                btnSelectRoleStudent = null;
            }

            if (lblRole != null) {
                lblRole.Dispose ();
                lblRole = null;
            }

            if (lblWelcome != null) {
                lblWelcome.Dispose ();
                lblWelcome = null;
            }

            if (viewRegistration != null) {
                viewRegistration.Dispose ();
                viewRegistration = null;
            }
        }
    }
}