package fr.istic.m2gla.mmm.map_activities;

/**
 * La classe des coordonnées utilisateurs
 */
public class Contact {

        String email;
        String regId;
        double latitude;
        double longitude;

        private Contact() {
        }

        private Contact(String email, String regId, double latitude, double longitude) {
            this.email = email;
            this.regId = regId;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public String getEmail() {
            return email;
        }

        public String getRegId() {
            return regId;
        }

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setRegId(String regId) {
            this.regId = regId;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

}
