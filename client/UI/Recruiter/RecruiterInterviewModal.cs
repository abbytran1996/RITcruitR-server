using System;
using System.IO;
using System.Collections.Generic;
using System.Text;
using TMCS_Client.Controllers;
using TMCS_Client.CustomUIElements.Buttons;
using TMCS_Client.CustomUIElements.Labels;
using TMCS_Client.DTOs;
using Xamarin.Forms;

namespace TMCS_Client.UI {
    class RecruiterInterviewModal : ContentPage {
        private Match match;
        private DTOs.Student student;

        public RecruiterInterviewModal(Match match) {
            this.match = match;
            this.student = match.student;

            Title = "Interview";
            DeclineButton dbutton = new DeclineButton();
            dbutton.Clicked += (object sender, EventArgs e2) =>
            {
                this.match.applicationStatus = Match.ApplicationStatus.REJECTED;
                this.match.currentPhase = Match.CurrentPhase.NONE;
                updateMatch();
            };

            Button resumebutton = new Button();
            resumebutton.Text = "Download Resume";
            resumebutton.Clicked += (object sender, EventArgs e3) =>
            {
                downloadAndOpenResume(student.id);
            };

            Content = new StackLayout
            {
                Children = {
                    new PageTitleLabel(student.firstName + " " + student.lastName),
                    new SubSectionTitleLabel("School"),
                    new Label { Text = student.school },
                    new SubSectionTitleLabel("Graduation Date"),
                    new Label { Text = student.graduationDate.ToShortDateString() },
                    //TODO resume section
                    new SubSectionTitleLabel("Resume"),
                    resumebutton,
                    new SubSectionTitleLabel("Student Contact Information"),
                    new Label { Text = "Email:\t" + student.email },
                    new Label { Text = "Phone:\t" + student.phoneNumber },
                    dbutton
                }
            };
        }

        void updateMatch() {
            MatchController.getMatchController().updateMatch(match);
            Navigation.PopModalAsync();
        }
        //only working for android at the moment
        void downloadAndOpenResume(long id)
        {
            byte[] bytes = StudentController.getStudentController().getResumeforStudent(id);
#if __ANDROID__
            //saves the file
            var directory = Android.OS.Environment.ExternalStorageDirectory.AbsolutePath;
            directory = Path.Combine(directory, Android.OS.Environment.DirectoryDownloads);
            var fdirectory = Android.OS.Environment.GetExternalStoragePublicDirectory(Android.OS.Environment.DirectoryDownloads);
            string filePath = Path.Combine(fdirectory.AbsolutePath, student.lastName + "resume.pdf");
            File.WriteAllBytes(filePath, bytes);

            //Open the Pdf file with Defualt app.
            try
            {
                Android.Net.Uri pdfPath = Android.Net.Uri.FromFile(new Java.IO.File(filePath));
                Android.Content.Intent intent = new Android.Content.Intent(Android.Content.Intent.ActionView);
                intent.SetDataAndType(pdfPath, "application/pdf");
                intent.SetFlags(Android.Content.ActivityFlags.NewTask);
                Forms.Context.StartActivity(intent);
            }
            catch(Exception e)
            {
                //assume this means you don't have a pdf viewer installed
                Android.Widget.Toast.MakeText(Forms.Context.ApplicationContext, "There was an issue opening the file", Android.Widget.ToastLength.Long).Show();
                
            }
#elif __IOS__
#endif
        }
    }
}
