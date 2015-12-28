package com.objectapps.regexgen.db.dao;

import java.util.List;

import com.objectapps.regexgen.db.transformers.ColumnNameResultTransformer;
import com.objectapps.regexgen.db.transformers.ColumnValueResultTransformer;
import com.objectapps.regexgen.db.transformers.TableNameResultTransformer;
import com.objectapps.regexgen.db.util.JDBCTemplate;
import com.objectapps.regexgen.db.util.MetadataQueryBuilder;
import com.objectapps.regexgen.db.util.MySqlMetadataQueryBuilder;

/**
 * @author Nataraj Gnanavadivel
 *
 */
public class MySqlDataExtractor implements DataExtractor {

   private MetadataQueryBuilder queryBuilder = null;
   private JDBCTemplate         template     = null;

   public MySqlDataExtractor(String connectionUri, String userName, String password) {
      this.template = new JDBCTemplate(connectionUri, userName, password);
      this.queryBuilder = new MySqlMetadataQueryBuilder();
   }

   @Override
   public List<String> getColumnNames(String tableName) {
      List<String> columns = null;
      try {
         ColumnNameResultTransformer transformer = new ColumnNameResultTransformer();
         template.openConnection();
         template.executeQuery(queryBuilder.getQueryToListAllColumns(tableName), transformer);
         columns = transformer.getResults();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         template.closeResultSet();
         template.closeConnection();
      }
      return columns;
   }

   @Override
   public List<String> getColumnValues(String tableName, String columnName, long rowLimit) {
      List<String> values = null;
      try {
         ColumnValueResultTransformer transformer = new ColumnValueResultTransformer();
         template.openConnection();
         template.executeQuery(queryBuilder.getQueryToSelectColumnValues(tableName, columnName, rowLimit), transformer);
         values = transformer.getResults();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         template.closeResultSet();
         template.closeConnection();
      }
      return values;
   }

   @Override
   public List<String> getTableNames() {
      List<String> tables = null;
      try {
         TableNameResultTransformer transformer = new TableNameResultTransformer();
         template.openConnection();
         template.executeQuery(queryBuilder.getQueryToListAllTables(), transformer);
         tables = transformer.getResults();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         template.closeResultSet();
         template.closeConnection();
      }
      return tables;
   }

}
