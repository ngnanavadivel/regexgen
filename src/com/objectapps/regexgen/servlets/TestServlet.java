package com.objectapps.regexgen.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.objectapps.regexgen.core.RegexAnalyzer;
import com.objectapps.regexgen.util.Logger;

/**
 * Servlet implementation class TestServlet
 */
/**
 * @author Nataraj Gnanavadivel
 *
 */
public class TestServlet extends HttpServlet {
   private static final long serialVersionUID = 1L;

   /**
    * @see HttpServlet#HttpServlet()
    */
   public TestServlet() {
      super();
   }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   @Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      Logger logger = Logger.instance();
      PrintWriter out = response.getWriter();
      try {
         logger.set(new StringBuilder());
         String paramSamples = request.getParameter("samples");
         System.out.println(paramSamples);
         if (paramSamples != null && paramSamples.trim().length() == 0) {
            out.println("<div style='color:red'>Please give some samples above!</div>");
            return;
         }

         paramSamples = URLDecoder.decode(paramSamples, "UTF-8");
         System.out.println(paramSamples);
         String[] samples = paramSamples.split("\n");
         System.out.println(Arrays.toString(samples));

         Set<String> expressions = new RegexAnalyzer(samples).generateRegex();
         // out.println("<h1>Regular Expression matching your
         // samples:</h1><br/>");
         for (String expr : expressions) {
            out.println("<p style='padding-left:25px;padding:5px'>");
            out.println(expr);
            out.println("</p>");
         }
      } catch (Exception e) {
         e.printStackTrace();
         out.println(e.getMessage());
      } finally {
         logger.unSet();
      }
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   @Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
         IOException {
      doGet(request, response);
   }

}
