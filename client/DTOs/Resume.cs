using System;
using System.IO;
namespace TMCS_Client.DTOs
{
    public class Resume
    {
        public String fileName { get; set; }

        public byte[] file { get; set; }

        public Resume(String fileName)
        {
            file = File.ReadAllBytes(fileName);
            this.fileName = fileName.TrimEnd('/').Split('/')[fileName.TrimEnd('/').Split('/').Length - 1];
        }
    }
}
