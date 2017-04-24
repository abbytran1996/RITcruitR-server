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
    [Register ("RecruiterRegistrationTableViewController")]
    partial class RecruiterRegistrationTableViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        TMCS_Client.iOS.RecruiterRegistration tblRecruiterRegistration { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (tblRecruiterRegistration != null) {
                tblRecruiterRegistration.Dispose ();
                tblRecruiterRegistration = null;
            }
        }
    }
}