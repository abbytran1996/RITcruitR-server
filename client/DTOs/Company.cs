﻿using System;
using System.Collections.Generic;
using System.Text;


namespace TMCS_Client.DTOs
{
    public class Company
    {
        public enum Size
        {
            DONT_CARE,
            STARTUP,
            SMALL,
            MEDIUM,
            LARGE,
            HUGE,
        };

        public long id { get; set; }
        public User user { get; set; }
        public string companyName { get; set; }
        public string emailSuffix { get; set; }
        public string location { get; set; }
        public string size { get; set; }
        public string presentation { get; set; }
        public string companyDescription { get; set; }
        public Boolean approvalStatus { get; set; }
        public string websiteURL { get; set; }
    }
}
