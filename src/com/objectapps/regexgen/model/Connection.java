package com.objectapps.regexgen.model;

/**
 * @author Nataraj Gnanavadivel
 *
 */
public class Connection {
   private String password;
   private String uri;
   private String userName;

   public String getPassword() {
      return password;
   }

   public String getUri() {
      return uri;
   }

   public String getUserName() {
      return userName;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setUri(String uri) {
      this.uri = uri;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }
}