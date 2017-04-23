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
    [Register ("LoginViewController")]
    partial class LoginViewController
    {
        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton btnLogin { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UIButton btnRegister { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UILabel lblAppLogo { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UILabel lblRegister { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField txtBoxPassword { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        UIKit.UITextField txtBoxUsername { get; set; }

        [Outlet]
        [GeneratedCode ("iOS Designer", "1.0")]
        TMCS_Client.iOS.Login viewLogin { get; set; }

        void ReleaseDesignerOutlets ()
        {
            if (btnLogin != null) {
                btnLogin.Dispose ();
                btnLogin = null;
            }

            if (btnRegister != null) {
                btnRegister.Dispose ();
                btnRegister = null;
            }

            if (lblAppLogo != null) {
                lblAppLogo.Dispose ();
                lblAppLogo = null;
            }

            if (lblRegister != null) {
                lblRegister.Dispose ();
                lblRegister = null;
            }

            if (txtBoxPassword != null) {
                txtBoxPassword.Dispose ();
                txtBoxPassword = null;
            }

            if (txtBoxUsername != null) {
                txtBoxUsername.Dispose ();
                txtBoxUsername = null;
            }

            if (viewLogin != null) {
                viewLogin.Dispose ();
                viewLogin = null;
            }
        }
    }
}