package com.objectapps.regexgen.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.objectapps.regexgen.core.RegexAnalyzer;
import com.objectapps.regexgen.core.RegexMatchFinder;
import com.objectapps.regexgen.model.Request;
import com.objectapps.regexgen.model.Response;
import com.objectapps.regexgen.model.TableAnalysisResult;
import com.objectapps.regexgen.util.Logger;

public class RegexGeneratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RegexGeneratorServlet() {
		super();
	}

	@Override
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		Logger logger = Logger.instance();
		PrintWriter writer = response.getWriter();
		try {

			logger.set(new StringBuilder());

			String requestJson = IOUtils.toString(request.getInputStream());
			logger.log("Request : " + requestJson);
			Request obj = new ObjectMapper().readValue(requestJson, Request.class);
			String samplesValue = obj.getConfig().getSamples();
			String[] samples = samplesValue.split("\n");
			Set<String> expressions = new RegexAnalyzer(samples).generateRegex();
			logger.log("Regular Expressions : " + expressions);
			if (expressions != null && expressions.size() == 1) {

				String regex = expressions.iterator().next();

				List<TableAnalysisResult> results = new RegexMatchFinder().analyze(obj.getConnection().getUri(), obj
						.getConnection().getUserName(), obj.getConnection().getPassword(), obj.getConfig()
								.getRowLimit(), obj.getConfig().getThresholdPercentage(), regex);

				Response resp = new Response();
				resp.setRegex(regex);
				resp.setResults(results);
				resp.setLogs(logger.getLogger().toString());

				String responseJson = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(resp);

				writer.println(responseJson);
			} else {
				Response resp = new Response();
				resp.setLogs(logger.getLogger().toString());
				writer.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(resp));
			}
		} catch (Exception e) {
			Response resp = new Response();
			StringWriter trace = new StringWriter();
			e.printStackTrace(new PrintWriter(trace));
			logger.log(trace.toString());
			resp.setLogs(logger.getLogger().toString());
			writer.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(resp));
		} finally {
			logger.unSet();
		}

	}

	

	@Override
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		doGet(request, response);
	}

}
