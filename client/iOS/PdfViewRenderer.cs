using System;
using System.Drawing;

using CoreGraphics;
using Foundation;
using UIKit;
using TMCS_Client.UI;
using Xamarin.Forms.Platform.iOS;
using Xamarin.Forms;
using DisplayPDF.iOS;

[assembly: ExportRenderer (typeof(CustomWebView), typeof(CustomWebViewRenderer))]
namespace DisplayPDF.iOS
{
    public class CustomWebViewRenderer : ViewRenderer<CustomWebView, UIWebView>
    {
        protected override void OnElementChanged(ElementChangedEventArgs<CustomWebView> e)
        {
            base.OnElementChanged(e);

            if (Control == null)
            {
                SetNativeControl(new UIWebView());
            }
            if (e.OldElement != null)
            {
                // Cleanup
            }
            if (e.NewElement != null)
            {
                var customWebView = Element as CustomWebView;
                Control.LoadRequest(new NSUrlRequest(new NSUrl(customWebView.Uri, false)));
                Control.ScalesPageToFit = true;
            }
        }
    }
}