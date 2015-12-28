package com.objectapps.regexgen.model;

/**
 * @author Nataraj Gnanavadivel
 *
 */
public class Request {
   private Config     config;
   private Connection connection;

   public Config getConfig() {
      return config;
   }

   public Connection getConnection() {
      return connection;
   }

   public void setConfig(Config config) {
      this.config = config;
   }

   public void setConnection(Connection connection) {
      this.connection = connection;
   }

}
