﻿﻿using TMCS_Client.Controllers;
using TMCS_Client.DTOs;
using TMCS_Client.ServerComms;
using Xamarin.Forms;

namespace TMCS_Client.UI
{
    public partial class App : Application
    {
        public Student CurrentStudent { get; internal set; }

        public App()
        {
            InitializeComponent();

            MainPage = new NavigationPage(Login.getLoginPage());
        }

        protected override void OnStart()
        {
            // Handle when your app starts
        }

        protected override void OnSleep()
        {
            // Handle when your app sleeps
        }

        protected override void OnResume()
        {
            // Handle when your app resumes
        }
    }
}
