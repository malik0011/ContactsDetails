package com.example.khabar;

 public class Phone {
     private String office;
     private String mobile;
     private String home;

     public String getMobile() {
            return mobile;
        }
     public Phone(String office, String mobile, String home) {
         this.office = office;
         this.mobile = mobile;
         this.home = home;
     }

     public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getHome() {
            return home;
        }

        public void setHome(String home) {
            this.home = home;
        }

        public String getOffice() {
            return office;
        }

        public void setOffice(String office) {
            this.office = office;
        }

 }
