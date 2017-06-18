using System;
using System.Collections.Generic;
using System.Text;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI {
    class RecruiterPresentationResponses : ContentPage {
        private JobPosting activeJobPosting;

        public RecruiterPresentationResponses(JobPosting activeJobPosting) {
            this.activeJobPosting = activeJobPosting;
            Title = "Presentation Responses";


        }
    }
}
