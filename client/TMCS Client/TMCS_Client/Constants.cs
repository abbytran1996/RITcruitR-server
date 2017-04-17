using System;
using System.Collections.Generic;
using System.Text;

namespace TMCS_Client {
    /// <summary>
    /// Holds all the constants that the TMCS client uses
    /// </summary>
    class Constants {
        public static string SERVER_URL { get => "http://10.0.2.2:8080"; }  // 10.0.2.2 is the IP address of the device the emulator is running on

        public class Students {
            public static string ADD_STUDENT_RESOURCE { get => "/students"; }
        }
    }
}
