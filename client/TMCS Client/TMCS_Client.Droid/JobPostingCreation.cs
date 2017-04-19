using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;

namespace TMCS_Client.Droid {
    [Activity(Label = "JobPostingCreation")]
    class JobPostingCreation : Activity {

        protected override void OnCreate(Bundle savedInstanceState) {
            base.OnCreate(savedInstanceState);

            SetContentView(Resource.Layout.JobPostingCreation);

            var positionTitleInput = FindViewById<EditText>(Resource.Id.jobPosting_new_title);
            var positionUrlInput = FindViewById<EditText>(Resource.Id.jobPosting_new_url);
            var descriptionInput = FindViewById<EditText>(Resource.Id.jobPosting_new_description);
            var requiredSkillsInput = FindViewById<MultiAutoCompleteTextView>(Resource.Id.jobPosting_new_requiredSkills);
            var recommendedSkillsInput = FindViewById<MultiAutoCompleteTextView>(Resource.Id.jobPosting_new_recommendedSkills);
            var jobLocationInput = FindViewById<EditText>(Resource.Id.jobPosting_new_location);
            var problemStatementInput = FindViewById<EditText>(Resource.Id.jobPosting_new_problem);
            var phaseTimeoutInput = FindViewById<EditText>(Resource.Id.jobPosting_new_phaseDuration);

            var submitButton = FindViewById<Button>(Resource.Id.jobPosting_new_submit);

            positionTitleInput.FocusChange += validateTextNotNull;
            descriptionInput.FocusChange += validateTextNotNull;
            requiredSkillsInput.FocusChange += validateTextNotNull;
            jobLocationInput.FocusChange += validateTextNotNull;
            problemStatementInput.FocusChange += validateTextNotNull;
            phaseTimeoutInput.FocusChange += validateTextNotNull;

            submitButton.Click += (sender, e) => {
                var title = positionTitleInput.Text;
                var url = positionUrlInput.Text;
                var description = descriptionInput.Text;
                var requiredSkills = requiredSkillsInput.Text.Split(',');
                var recommendedSkills = recommendedSkillsInput.Text.Split(',');
                var location = jobLocationInput.Text;
                var problemStatement = problemStatementInput.Text;
                var phaseTimeout = phaseTimeoutInput.Text;

                var requiredSkillsList = new List<String>();
                requiredSkillsList.AddRange(requiredSkills);
                requiredSkillsList.RemoveAll(str => str.Trim().Length == 0);

                var recommendedSkillsList = new List<String>();
                recommendedSkillsList.AddRange(recommendedSkills);
                recommendedSkillsList.RemoveAll(str => str.Trim().Length == 0);
            };
        }

        private void validateTextNotNull(object sender, View.FocusChangeEventArgs e) {
            if(!e.HasFocus) {
                var editText = sender as EditText;
                if(editText.Text.Length <= 0) {
                    Toast.MakeText(ApplicationContext, editText.Tag + " cannot be null", ToastLength.Short).Show();
                }
            }
        }
    }
}